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

package com.liferay.object.internal.petra.sql.dsl;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.ObjectFilterUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.filter.FilterParserProvider;

import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = {})
public class DynamicObjectDefinitionTableFactory {

	public static DynamicObjectDefinitionTable
			createDynamicObjectDefinitionTable(
				ObjectDefinition objectDefinition,
				List<ObjectField> objectFields, String tableName)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(
				objectFields, objectDefinition.getPKObjectFieldDBColumnName(),
				tableName);

		dynamicObjectDefinitionTable.addSelectExpression(
			dynamicObjectDefinitionTable.createColumn(
				dynamicObjectDefinitionTable.getPrimaryKeyColumnName(),
				Long.class, Types.BIGINT));

		for (ObjectField objectField : objectFields) {
			if (!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

				dynamicObjectDefinitionTable.addSelectExpression(
					dynamicObjectDefinitionTable.createColumn(
						objectField.getDBColumnName(),
						DynamicObjectDefinitionTable.getJavaClass(
							objectField.getDBType()),
						DynamicObjectDefinitionTable.getSQLType(
							objectField.getDBType())));

				continue;
			}

			Map<String, Object> objectFieldSettingsValuesMap = new HashMap<>();

			List<ObjectFieldSetting> objectFieldSettings =
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
						objectField.getObjectFieldId());

			for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
				if (!StringUtil.equals(
						objectFieldSetting.getName(), "filters")) {

					objectFieldSettingsValuesMap.put(
						objectFieldSetting.getName(),
						objectFieldSetting.getValue());
				}
				else {
					objectFieldSettingsValuesMap.put(
						objectFieldSetting.getName(),
						objectFieldSetting.getObjectFilters());
				}
			}

			ObjectRelationship relationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectDefinition.getObjectDefinitionId(),
					GetterUtil.getString(
						objectFieldSettingsValuesMap.get(
							"objectRelationshipName")));

			ObjectDefinition relatedObjectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					relationship.getObjectDefinitionId2());

			DynamicObjectDefinitionTable relatedObjectDefinitionTable =
				createDynamicObjectDefinitionTable(
					relatedObjectDefinition,
					_objectFieldLocalService.getObjectFields(
						relatedObjectDefinition.getObjectDefinitionId()),
					relatedObjectDefinition.getDBTableName());

			Expression<? extends Comparable> column = null;

			String function = GetterUtil.getString(
				objectFieldSettingsValuesMap.get("function"));

			if (!Objects.equals(function, "COUNT")) {
				column =
					(Expression<? extends Comparable>)
						_objectFieldLocalService.getColumn(
							relatedObjectDefinition.getObjectDefinitionId(),
							GetterUtil.getString(
								objectFieldSettingsValuesMap.get(
									"objectFieldName")));
			}
			else {
				column = relatedObjectDefinitionTable.getPrimaryKeyColumn();
			}

			Expression<?> expression = null;

			if (function.equals("SUM")) {
				expression = DSLFunctionFactoryUtil.sum(
					(Expression<? extends Number>)column);
			}
			else if (function.equals("COUNT")) {
				expression = DSLFunctionFactoryUtil.count(column);
			}
			else if (function.equals("AVERAGE")) {
				expression = DSLFunctionFactoryUtil.avg(
					(Expression<? extends Number>)column);
			}
			else if (function.equals("MAX")) {
				expression = DSLFunctionFactoryUtil.max(column);
			}
			else if (function.equals("MIN")) {
				expression = DSLFunctionFactoryUtil.min(column);
			}

			DynamicObjectDefinitionTable relatedObjectDefinitionExtensionTable =
				createDynamicObjectDefinitionTable(
					relatedObjectDefinition,
					_objectFieldLocalService.getObjectFields(
						relatedObjectDefinition.getObjectDefinitionId()),
					relatedObjectDefinition.getExtensionDBTableName());

			ObjectField relatedField = _objectFieldLocalService.getObjectField(
				relationship.getObjectFieldId2());

			Column<DynamicObjectDefinitionTable, Long>
				relatedObjectDefinitionColumn =
					(Column<DynamicObjectDefinitionTable, Long>)
						_objectFieldLocalService.getColumn(
							relatedObjectDefinition.getObjectDefinitionId(),
							relatedField.getName());

			Predicate predicate = relatedObjectDefinitionColumn.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn());

			List<String> oDataFilters = ObjectFilterUtil.getODataFilters(
				(List<ObjectFilter>)objectFieldSettingsValuesMap.get(
					"filters"));

			for (String oDataFilter : oDataFilters) {
				predicate = predicate.and(
					_filterPredicateFactory.create(
						_filterParserProvider, oDataFilter,
						relatedObjectDefinition.getObjectDefinitionId(),
						_objectFieldLocalService));
			}

			JoinStep joinStep = DSLQueryFactoryUtil.select(
				expression
			).from(
				relatedObjectDefinitionTable
			).innerJoinON(
				relatedObjectDefinitionExtensionTable,
				relatedObjectDefinitionExtensionTable.getPrimaryKeyColumn(
				).eq(
					relatedObjectDefinitionTable.getPrimaryKeyColumn()
				)
			);

			if (!relatedObjectDefinition.isSystem()) {
				joinStep = joinStep.innerJoinON(
					ObjectEntryTable.INSTANCE,
					ObjectEntryTable.INSTANCE.objectEntryId.eq(
						relatedObjectDefinitionTable.getPrimaryKeyColumn()));
			}

			dynamicObjectDefinitionTable.addSelectExpression(
				DSLQueryFactoryUtil.scalarSubDSLQuery(
					joinStep.where(predicate),
					DynamicObjectDefinitionTable.getJavaClass(
						objectField.getDBType()),
					objectField.getName(),
					DynamicObjectDefinitionTable.getSQLType(
						objectField.getDBType())));
		}

		return dynamicObjectDefinitionTable;
	}

	@Reference(unbind = "-")
	private void _setFilterParserProvider(
		FilterParserProvider filterParserProvider) {

		_filterParserProvider = filterParserProvider;
	}

	@Reference(unbind = "-")
	private void _setObjectDefinitionLocalService(
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	@Reference(unbind = "-")
	private void _setObjectFieldLocalService(
		ObjectFieldLocalService objectFieldLocalService) {

		_objectFieldLocalService = objectFieldLocalService;
	}

	@Reference(unbind = "-")
	private void _setObjectFieldSettingLocalService(
		ObjectFieldSettingLocalService objectFieldSettingLocalService) {

		_objectFieldSettingLocalService = objectFieldSettingLocalService;
	}

	@Reference(unbind = "-")
	private void _setObjectRelationshipLocalService(
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Reference(unbind = "-")
	private void _setPredicateFactory(
		FilterPredicateFactory filterPredicateFactory) {

		_filterPredicateFactory = filterPredicateFactory;
	}

	private static FilterParserProvider _filterParserProvider;
	private static FilterPredicateFactory _filterPredicateFactory;
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;
	private static ObjectFieldLocalService _objectFieldLocalService;
	private static ObjectFieldSettingLocalService
		_objectFieldSettingLocalService;
	private static ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}