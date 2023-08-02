/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.closure;

import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.internal.reference.TableJoinHolder;
import com.liferay.change.tracking.internal.reference.TableReferenceDefinitionManager;
import com.liferay.change.tracking.internal.reference.TableReferenceInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 * @author Preston Crary
 */
@Component(service = CTClosureFactory.class)
public class CTClosureFactoryImpl implements CTClosureFactory {

	@Override
	public CTClosure create(long ctCollectionId) {
		return create(ctCollectionId, Collections.emptySet());
	}

	@Override
	public CTClosure create(long ctCollectionId, long classNameId) {
		return create(ctCollectionId, Collections.singleton(classNameId));
	}

	@Override
	public CTClosure create(long ctCollectionId, Set<Long> classNameIds) {
		Map<Long, TableReferenceInfo<?>> combinedTableReferenceInfos;

		if (classNameIds.isEmpty()) {
			combinedTableReferenceInfos =
				_tableReferenceDefinitionManager.
					getCombinedTableReferenceInfos();

			return new CTClosureImpl(
				ctCollectionId,
				_buildClosureMap(
					ctCollectionId, Collections.emptySet(),
					combinedTableReferenceInfos));
		}

		combinedTableReferenceInfos =
			_tableReferenceDefinitionManager.getCombinedTableReferenceInfos(
				classNameIds);

		return new CTClosureImpl(
			ctCollectionId,
			_buildClosureMap(
				ctCollectionId, classNameIds, combinedTableReferenceInfos));
	}

	private Map<Node, Collection<Node>> _buildClosureMap(
		long ctCollectionId, Set<Long> classNameIds,
		Map<Long, TableReferenceInfo<?>> combinedTableReferenceInfos) {

		Map<Long, List<Long>> map = new LinkedHashMap<>();
		List<Node> nodes = new ArrayList<>();

		List<CTEntry> ctEntries = new ArrayList<>(
			_ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId));

		ctEntries.sort(
			(ctEntry1, ctEntry2) ->
				(int)(ctEntry1.getCtEntryId() - ctEntry2.getCtEntryId()));

		for (CTEntry ctEntry : ctEntries) {
			if (!classNameIds.isEmpty() &&
				!combinedTableReferenceInfos.containsKey(
					ctEntry.getModelClassNameId())) {

				continue;
			}

			List<Long> primaryKeys = map.computeIfAbsent(
				ctEntry.getModelClassNameId(), key -> new ArrayList<>());

			primaryKeys.add(ctEntry.getModelClassPK());

			nodes.add(
				new Node(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK()));
		}

		Map<Node, Collection<Edge>> edgeMap = new LinkedHashMap<>();

		Queue<Map.Entry<Long, List<Long>>> queue = new LinkedList<>(
			map.entrySet());

		while (queue.size() > 0) {
			Map.Entry<Long, List<Long>> queueEntry = queue.poll();

			long childClassNameId = queueEntry.getKey();

			TableReferenceInfo<?> childTableReferenceInfo =
				combinedTableReferenceInfos.get(childClassNameId);

			if (childTableReferenceInfo == null) {
				CTCollection ctCollection =
					_ctCollectionPersistence.fetchByPrimaryKey(ctCollectionId);

				if ((ctCollection != null) &&
					(ctCollection.getStatus() !=
						WorkflowConstants.STATUS_DRAFT) &&
					(ctCollection.getStatus() !=
						WorkflowConstants.STATUS_PENDING)) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"No table reference definition for " +
								childClassNameId);
					}

					continue;
				}

				throw new IllegalArgumentException(
					"No table reference definition for " + childClassNameId);
			}

			List<Long> childPrimaryKeys = queueEntry.getValue();

			Long[] childPrimaryKeysArray = childPrimaryKeys.toArray(
				new Long[0]);

			Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap =
				childTableReferenceInfo.getParentTableJoinHoldersMap();

			for (Map.Entry<Table<?>, List<TableJoinHolder>> entry :
					parentTableJoinHoldersMap.entrySet()) {

				long parentClassNameId =
					_tableReferenceDefinitionManager.getClassNameId(
						entry.getKey());

				if (!classNameIds.isEmpty() &&
					!map.containsKey(parentClassNameId)) {

					continue;
				}

				TableReferenceInfo<?> parentTableReferenceInfo =
					combinedTableReferenceInfos.get(parentClassNameId);

				int i = 0;

				while (i < childPrimaryKeysArray.length) {
					int batchSize = _SQL_PLACEHOLDER_LIMIT;

					if ((i + batchSize) > childPrimaryKeysArray.length) {
						batchSize = childPrimaryKeysArray.length - i;
					}

					Long[] batchChildPrimaryKeys = new Long[batchSize];

					System.arraycopy(
						childPrimaryKeysArray, i, batchChildPrimaryKeys, 0,
						batchSize);

					List<Long> newParentPrimaryKeys = _collectParentPrimaryKeys(
						childClassNameId, batchChildPrimaryKeys, ctCollectionId,
						entry, edgeMap, nodes, parentClassNameId, classNameIds,
						parentTableReferenceInfo);

					if (newParentPrimaryKeys != null) {
						queue.add(
							new AbstractMap.SimpleImmutableEntry<>(
								parentClassNameId, newParentPrimaryKeys));
					}

					i += batchSize;
				}
			}
		}

		return _getNodeMap(nodes, edgeMap);
	}

	private List<Long> _collectParentPrimaryKeys(
		long childClassNameId, Long[] childPrimaryKeys, long ctCollectionId,
		Map.Entry<Table<?>, List<TableJoinHolder>> entry,
		Map<Node, Collection<Edge>> edgeMap, List<Node> nodes,
		long parentClassNameId, Set<Long> classNameIds,
		TableReferenceInfo<?> parentTableReferenceInfo) {

		List<Long> newParentPrimaryKeys = null;

		int i = 0;

		while (i < childPrimaryKeys.length) {
			int batchSize = _SQL_SERVER_PARAMETER_LIMIT;

			if ((i + batchSize) > childPrimaryKeys.length) {
				batchSize = childPrimaryKeys.length - i;
			}

			Long[] batchChildPrimaryKeys = new Long[batchSize];

			System.arraycopy(
				childPrimaryKeys, i, batchChildPrimaryKeys, 0, batchSize);

			DSLQuery dslQuery = _getDSLQuery(
				ctCollectionId, batchChildPrimaryKeys, entry.getValue());

			try (Connection connection = _getConnection(
					parentTableReferenceInfo);
				PreparedStatement preparedStatement = _getPreparedStatement(
					connection, dslQuery);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					Node parentNode = new Node(
						parentClassNameId, resultSet.getLong(1));

					if (!classNameIds.isEmpty() &&
						!nodes.contains(parentNode)) {

						continue;
					}

					Node childNode = new Node(
						childClassNameId, resultSet.getLong(2));

					if (!nodes.contains(parentNode)) {
						nodes.add(parentNode);

						if (newParentPrimaryKeys == null) {
							newParentPrimaryKeys = new ArrayList<>();
						}

						newParentPrimaryKeys.add(parentNode.getPrimaryKey());
					}

					Collection<Edge> edges = edgeMap.computeIfAbsent(
						parentNode, key -> new LinkedList<>());

					edges.add(new Edge(parentNode, childNode));
				}
			}
			catch (SQLException sqlException) {
				throw new ORMException(
					"Unable to execute query: " + dslQuery, sqlException);
			}

			i += batchSize;
		}

		return newParentPrimaryKeys;
	}

	private void _filterCyclingEdges(
		Edge edge, Map<Node, Collection<Edge>> edgeMap,
		Deque<Edge> backtraceEdges, Set<Edge> cyclingEdges,
		Set<Edge> resolvedEdges) {

		if (backtraceEdges.contains(edge)) {
			cyclingEdges.add(edge);

			return;
		}

		if (resolvedEdges.contains(edge) || cyclingEdges.contains(edge)) {
			return;
		}

		Collection<Edge> nextEdges = edgeMap.get(edge.getToNode());

		if (nextEdges == null) {
			resolvedEdges.add(edge);

			return;
		}

		backtraceEdges.push(edge);

		for (Edge nextEdge : nextEdges) {
			_filterCyclingEdges(
				nextEdge, edgeMap, backtraceEdges, cyclingEdges, resolvedEdges);
		}

		backtraceEdges.pop();

		if (!cyclingEdges.contains(edge)) {
			resolvedEdges.add(edge);
		}
	}

	private Predicate _getChildPKColumnPredicate(
		Column<?, Long> childPKColumn, Long[] childPrimaryKeysArray) {

		Predicate predicate = null;

		int i = 0;

		while (i < childPrimaryKeysArray.length) {
			int batchSize = _ORACLE_IN_CLAUSE_LIMIT;

			if ((i + batchSize) > childPrimaryKeysArray.length) {
				batchSize = childPrimaryKeysArray.length - i;
			}

			Long[] batchChildPrimaryKeys = new Long[batchSize];

			System.arraycopy(
				childPrimaryKeysArray, i, batchChildPrimaryKeys, 0, batchSize);

			if (predicate == null) {
				predicate = childPKColumn.in(batchChildPrimaryKeys);
			}
			else {
				predicate = predicate.or(
					childPKColumn.in(batchChildPrimaryKeys));
			}

			i += batchSize;
		}

		return predicate.withParentheses();
	}

	private Connection _getConnection(TableReferenceInfo<?> tableReferenceInfo)
		throws SQLException {

		TableReferenceDefinition<?> tableReferenceDefinition =
			tableReferenceInfo.getTableReferenceDefinition();

		BasePersistence<?> basePersistence =
			tableReferenceDefinition.getBasePersistence();

		DataSource dataSource = basePersistence.getDataSource();

		return dataSource.getConnection();
	}

	private DSLQuery _getDSLQuery(
		long ctCollectionId, Long[] childPrimaryKeysArray,
		List<TableJoinHolder> tableJoinHolders) {

		DSLQuery dslQuery = null;

		for (TableJoinHolder parentJoinHolder : tableJoinHolders) {
			Column<?, Long> parentPKColumn =
				parentJoinHolder.getParentPKColumn();
			Column<?, Long> childPKColumn = parentJoinHolder.getChildPKColumn();

			FromStep fromStep = DSLQueryFactoryUtil.selectDistinct(
				parentPKColumn, childPKColumn);

			Function<FromStep, JoinStep> joinFunction =
				parentJoinHolder.getJoinFunction();

			JoinStep joinStep = joinFunction.apply(fromStep);

			GroupByStep groupByStep = joinStep.where(
				() -> _getChildPKColumnPredicate(
					childPKColumn, childPrimaryKeysArray
				).and(
					() -> {
						Table<?> parentTable = parentPKColumn.getTable();

						Column<?, Long> ctCollectionIdColumn =
							parentTable.getColumn("ctCollectionId", Long.class);

						if ((ctCollectionIdColumn != null) &&
							ctCollectionIdColumn.isPrimaryKey()) {

							return ctCollectionIdColumn.eq(
								CTConstants.CT_COLLECTION_ID_PRODUCTION
							).or(
								ctCollectionIdColumn.eq(ctCollectionId)
							).withParentheses();
						}

						return null;
					}
				));

			if (dslQuery == null) {
				dslQuery = groupByStep;
			}
			else {
				dslQuery = dslQuery.union(groupByStep);
			}
		}

		return dslQuery;
	}

	private Map<Node, Collection<Node>> _getNodeMap(
		List<Node> nodes, Map<Node, Collection<Edge>> edgeMap) {

		Deque<Edge> backtraceEdges = new LinkedList<>();
		Set<Edge> cyclingEdges = new HashSet<>();
		Set<Edge> resolvedEdges = new HashSet<>();

		for (Collection<Edge> edges : edgeMap.values()) {
			for (Edge edge : edges) {
				_filterCyclingEdges(
					edge, edgeMap, backtraceEdges, cyclingEdges, resolvedEdges);
			}
		}

		Map<Node, Collection<Node>> nodeMap = new HashMap<>();

		for (Edge edge : resolvedEdges) {
			Collection<Node> children = nodeMap.computeIfAbsent(
				edge.getFromNode(), node -> new ArrayList<>());

			Node toNode = edge.getToNode();

			children.add(toNode);

			nodes.remove(toNode);
		}

		nodeMap.put(Node.ROOT_NODE, nodes);

		return nodeMap;
	}

	private PreparedStatement _getPreparedStatement(
			Connection connection, DSLQuery dslQuery)
		throws SQLException {

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		PreparedStatement preparedStatement = connection.prepareStatement(
			SQLTransformer.transform(dslQuery.toSQL(defaultASTNodeListener)));

		List<Object> scalarValues = defaultASTNodeListener.getScalarValues();

		for (int i = 0; i < scalarValues.size(); i++) {
			preparedStatement.setObject(i + 1, scalarValues.get(i));
		}

		return preparedStatement;
	}

	private static final int _ORACLE_IN_CLAUSE_LIMIT = 1000;

	private static final int _SQL_PLACEHOLDER_LIMIT = 65533;

	private static final int _SQL_SERVER_PARAMETER_LIMIT = 2000;

	private static final Log _log = LogFactoryUtil.getLog(
		CTClosureFactoryImpl.class);

	@Reference
	private CTCollectionPersistence _ctCollectionPersistence;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private TableReferenceDefinitionManager _tableReferenceDefinitionManager;

}