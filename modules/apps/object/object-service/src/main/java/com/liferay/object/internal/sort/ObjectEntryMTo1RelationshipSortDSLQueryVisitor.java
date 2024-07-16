/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.sort;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;
import com.liferay.petra.sql.dsl.spi.query.GroupBy;
import com.liferay.petra.sql.dsl.spi.query.Select;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Stack;

/**
 * @author Sergio Jiménez del Coso
 */
public class ObjectEntryMTo1RelationshipSortDSLQueryVisitor
	extends BaseSortDSLQueryVisitor {

	public ObjectEntryMTo1RelationshipSortDSLQueryVisitor(
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		super(objectFieldLocalService, objectRelationshipLocalService);
	}

	@Override
	public DSLQuery visit(DSLQuery dslQuery, Sort sort) throws PortalException {
		RelationshipSort relationshipSort = (RelationshipSort)sort;

		ObjectRelationship objectRelationship =
			relationshipSort.getObjectRelationship();

		ObjectDefinition relatedObjectDefinition =
			relationshipSort.getRelatedObjectDefinition();

		String dbColumnName = StringBundler.concat(
			"r_", objectRelationship.getName(), "_",
			relatedObjectDefinition.getPKObjectFieldName());

		String formatedRelationshipPathName = StringUtil.replace(
			relationshipSort.getFieldPath(), CharPool.FORWARD_SLASH,
			CharPool.UNDERLINE);

		DynamicObjectDefinitionTable relatedDynamicObjectDefinitionTable =
			(DynamicObjectDefinitionTable)getAliasedTable(
				formatedRelationshipPathName,
				new DynamicObjectDefinitionTable(
					relatedObjectDefinition,
					objectFieldLocalService.getObjectFields(
						relatedObjectDefinition.getObjectDefinitionId(),
						relatedObjectDefinition.getDBTableName()),
					relatedObjectDefinition.getDBTableName()));

		ObjectDefinition objectDefinition =
			relationshipSort.getObjectDefinition();

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			(DynamicObjectDefinitionTable)getAliasedTable(
				StringUtil.removeLast(
					formatedRelationshipPathName,
					"_" + objectRelationship.getName()),
				objectFieldLocalService.getTable(
					objectDefinition.getObjectDefinitionId(), dbColumnName));

		if (!contains(dslQuery, dynamicObjectDefinitionTable)) {
			dslQuery = addLeftJoin(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn(), null,
				dslQuery, dynamicObjectDefinitionTable);
		}

		if (!contains(dslQuery, relatedDynamicObjectDefinitionTable)) {
			dslQuery = addLeftJoin(
				(Column<DynamicObjectDefinitionTable, Long>)
					dynamicObjectDefinitionTable.getColumn(dbColumnName),
				relatedDynamicObjectDefinitionTable.getPrimaryKeyColumn(),
				dslQuery, relatedDynamicObjectDefinitionTable);
		}

		Stack<BaseASTNode> allBaseASTNodes = getAllBaseASTNodes(
			GroupByStep.class, dslQuery);

		if (ListUtil.exists(allBaseASTNodes, GroupBy.class::isInstance)) {
			return dslQuery;
		}

		GroupByStep groupByStep = (GroupByStep)allBaseASTNodes.pop();

		Stack<BaseASTNode> selectAllBaseASTNodes = getAllBaseASTNodes(
			Select.class, dslQuery);

		Select select = (Select)selectAllBaseASTNodes.pop();

		BaseASTNode baseASTNode = (BaseASTNode)groupByStep.groupBy(
			select.getExpressions(
			).toArray(
				new Expression[0]
			));

		return updateParents(baseASTNode, allBaseASTNodes);
	}

}