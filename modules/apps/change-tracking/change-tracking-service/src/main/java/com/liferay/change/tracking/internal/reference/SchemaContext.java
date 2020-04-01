/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.internal.reference.closure.Edge;
import com.liferay.change.tracking.internal.reference.closure.GraphUtil;
import com.liferay.change.tracking.internal.reference.closure.Node;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.spi.ast.DefaultASTNodeListener;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import javax.sql.DataSource;

/**
 * @author Preston Crary
 */
public class SchemaContext {

	public SchemaContext(
		Map<Table<?>, Long> tableClassNameIds,
		Map<Long, TableReferenceInfo<?>> combinedTableReferenceInfos) {

		_tableClassNameIds = tableClassNameIds;
		_combinedTableReferenceInfos = combinedTableReferenceInfos;
	}

	public Map<Node, Collection<Node>> buildClosureMap(
		long ctCollectionId, Map<Long, List<Long>> map) {

		Set<Node> nodes = new HashSet<>();

		for (Map.Entry<Long, List<Long>> entry : map.entrySet()) {
			long classNameId = entry.getKey();
			List<Long> primaryKeys = entry.getValue();

			for (long primaryKey : primaryKeys) {
				nodes.add(new Node(classNameId, primaryKey));
			}
		}

		Map<Node, Collection<Edge>> edgeMap = new LinkedHashMap<>();

		Queue<Map.Entry<Long, List<Long>>> queue = new LinkedList<>(
			map.entrySet());

		Map.Entry<Long, List<Long>> queueEntry = null;

		while ((queueEntry = queue.poll()) != null) {
			long childClassNameId = queueEntry.getKey();
			List<Long> childPrimaryKeys = queueEntry.getValue();

			TableReferenceInfo<?> childTableReferenceInfo =
				_combinedTableReferenceInfos.get(childClassNameId);

			if (childTableReferenceInfo == null) {
				throw new IllegalArgumentException(
					"No table reference definition for " + childClassNameId);
			}

			Map<Table<?>, List<TableJoinHolder>> parentJoinHolders =
				childTableReferenceInfo.getParentJoinHoldersMap();

			for (Map.Entry<Table<?>, List<TableJoinHolder>> entry :
					parentJoinHolders.entrySet()) {

				Long parentClassNameId = _tableClassNameIds.get(entry.getKey());

				if (parentClassNameId == null) {
					throw new IllegalStateException(
						"No table reference definition for " + entry.getKey());
				}

				TableReferenceInfo<?> parentTableReferenceInfo =
					_combinedTableReferenceInfos.get(parentClassNameId);

				List<Long> newParents = _collectParents(
					ctCollectionId, childClassNameId,
					childPrimaryKeys.toArray(new Long[0]), parentClassNameId,
					parentTableReferenceInfo, entry.getValue(), nodes, edgeMap);

				if (newParents != null) {
					queue.add(
						new AbstractMap.SimpleImmutableEntry<>(
							parentClassNameId, newParents));
				}
			}
		}

		return GraphUtil.getNodeMap(nodes, edgeMap);
	}

	private <P extends Table<P>> List<Long> _collectParents(
		long ctCollectionId, long childClassNameId, Long[] childPrimaryKeys,
		long parentClassNameId, TableReferenceInfo<P> parentTableReferenceInfo,
		List<TableJoinHolder> parentJoinHolders, Set<Node> nodes,
		Map<Node, Collection<Edge>> edgeMap) {

		DSLQuery dslQuery = null;

		for (TableJoinHolder parentJoinHolder : parentJoinHolders) {
			Column<?, Long> parentPKColumn = parentJoinHolder.getFromPKColumn();
			Column<?, Long> childPKColumn = parentJoinHolder.getJoinPKColumn();

			FromStep fromStep = DSLQueryFactoryUtil.selectDistinct(
				parentPKColumn, childPKColumn);

			Function<FromStep, JoinStep> joinFunction =
				parentJoinHolder.getJoinFunction();

			JoinStep joinStep = joinFunction.apply(fromStep);

			GroupByStep groupByStep = joinStep.where(
				() -> {
					Predicate predicate = childPKColumn.in(childPrimaryKeys);

					Table<?> parentTable = parentPKColumn.getTable();

					Column<?, Long> ctCollectionIdColumn =
						parentTable.getColumn("ctCollectionId", Long.class);

					if ((ctCollectionIdColumn != null) &&
						ctCollectionIdColumn.isPrimaryKey()) {

						predicate = predicate.and(
							ctCollectionIdColumn.eq(
								CTConstants.CT_COLLECTION_ID_PRODUCTION
							).or(
								ctCollectionIdColumn.eq(ctCollectionId)
							).withParentheses());
					}

					return predicate;
				});

			if (dslQuery == null) {
				dslQuery = groupByStep;
			}
			else {
				dslQuery = dslQuery.union(groupByStep);
			}
		}

		TableReferenceDefinition<P> tableReferenceDefinition =
			parentTableReferenceInfo.getTableReferenceDefinition();

		PersistedModelLocalService persistedModelLocalService =
			tableReferenceDefinition.getPersistedModelLocalService();

		BasePersistence<?> basePersistence =
			persistedModelLocalService.getBasePersistence();

		DataSource dataSource = basePersistence.getDataSource();

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					dslQuery.toSQL(defaultASTNodeListener)))) {

			List<Object> scalarValues =
				defaultASTNodeListener.getScalarValues();

			for (int i = 0; i < scalarValues.size(); i++) {
				preparedStatement.setObject(i + 1, scalarValues.get(i));
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				List<Long> newParents = null;

				while (resultSet.next()) {
					Node parentNode = new Node(
						parentClassNameId, resultSet.getLong(1));
					Node childNode = new Node(
						childClassNameId, resultSet.getLong(2));

					if (nodes.add(parentNode)) {
						if (newParents == null) {
							newParents = new ArrayList<>();
						}

						newParents.add(parentNode.getPrimaryKey());
					}

					Collection<Edge> edges = edgeMap.computeIfAbsent(
						parentNode, key -> new LinkedList<>());

					edges.add(new Edge(parentNode, childNode));
				}

				return newParents;
			}
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	private final Map<Long, TableReferenceInfo<?>> _combinedTableReferenceInfos;
	private final Map<Table<?>, Long> _tableClassNameIds;

}