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

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class SystemObject1toMObjectRelatedModelsProviderImpl
	<T extends BaseModel<T>>
		implements ObjectRelatedModelsProvider<T> {

	public SystemObject1toMObjectRelatedModelsProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectFieldPersistence objectFieldPersistence,
		ObjectRelationshipPersistence objectRelationshipPersistence,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		_objectFieldPersistence = objectFieldPersistence;
		_objectRelationshipPersistence = objectRelationshipPersistence;
		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
		_systemObjectDefinitionMetadata = systemObjectDefinitionMetadata;

		_dynamicObjectDefinitionTable = new DynamicObjectDefinitionTable(
			objectDefinition,
			_objectFieldPersistence.findByODI_DTN(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getExtensionDBTableName()),
			objectDefinition.getExtensionDBTableName());
	}

	public String getClassName() {
		return _systemObjectDefinitionMetadata.getClassName();
	}

	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
	}

	public List<T> getRelatedModels(
			long groupId, long objectRelationshipId, long primaryKey, int start,
			int end)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		ObjectField objectField = _objectFieldPersistence.fetchByPrimaryKey(
			objectRelationship.getObjectFieldId2());

		Column<?, Long> column = null;
		Table table = _systemObjectDefinitionMetadata.getTable();

		if (Objects.equals(
				objectField.getDBTableName(),
				_systemObjectDefinitionMetadata.getTable())) {

			column = (Column<?, Long>)table.getColumn(
				objectField.getDBColumnName());
		}
		else {
			column =
				(Column<DynamicObjectDefinitionTable, Long>)
					_dynamicObjectDefinitionTable.getColumn(
						objectField.getDBColumnName());
		}

		Predicate predicate = column.eq(primaryKey);

		Column<?, Long> groupColumn = table.getColumn("groupId");

		if (groupColumn != null) {
			predicate = predicate.and(groupColumn.eq(groupId));
		}

		Column<?, Long> companyColumn = table.getColumn("companyId");

		if (groupColumn != null) {
			predicate = predicate.and(
				companyColumn.eq(objectField.getCompanyId()));
		}

		DSLQuery dslQuery = DSLQueryFactoryUtil.selectDistinct(
			table
		).from(
			table
		).innerJoinON(
			_dynamicObjectDefinitionTable,
			_dynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				_systemObjectDefinitionMetadata.getPrimaryKeyColumn()
			)
		).where(
			predicate
		).limit(
			start, end
		);

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				_systemObjectDefinitionMetadata.getClassName());

		BasePersistence<?> basePersistence =
			persistedModelLocalService.getBasePersistence();

		return basePersistence.dslQuery(dslQuery);
	}

	private final DynamicObjectDefinitionTable _dynamicObjectDefinitionTable;
	private final ObjectFieldPersistence _objectFieldPersistence;
	private final ObjectRelationshipPersistence _objectRelationshipPersistence;
	private final PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;
	private final SystemObjectDefinitionMetadata
		_systemObjectDefinitionMetadata;

}