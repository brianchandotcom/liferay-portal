/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.odata.entity;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Carlos Correa
 */
public class APISchemaEntityModel implements EntityModel {

	public APISchemaEntityModel(
		EntityModel entityModel, APIApplication.Schema schema) {

		_entityModel = entityModel;
		_schema = schema;
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		Map<String, EntityField> entityModelEntityFieldsMap =
			_entityModel.getEntityFieldsMap();

		if (MapUtil.isEmpty(entityModelEntityFieldsMap)) {
			return entityModelEntityFieldsMap;
		}

		Map<String, EntityField> entityFieldsMap = new HashMap<>();

		for (Map.Entry<String, EntityField> entry :
				entityModelEntityFieldsMap.entrySet()) {

			for (APIApplication.Property property : _schema.getProperties()) {
				if (!StringUtil.equals(
						property.getSourceFieldName(), entry.getKey())) {

					continue;
				}

				EntityField entityField = entry.getValue();

				entityFieldsMap.put(
					property.getName(),
					_toAPIPropertyEntityField(entityField, property));

				break;
			}
		}

		return entityFieldsMap;
	}

	@Override
	public Map<String, EntityRelationship> getEntityRelationshipsMap() {
		return _entityModel.getEntityRelationshipsMap();
	}

	@Override
	public String getName() {
		return _entityModel.getName();
	}

	private EntityField _toAPIPropertyEntityField(
		EntityField entityField, APIApplication.Property property) {

		if (Objects.equals(
				entityField.getType(), EntityField.Type.COLLECTION)) {

			CollectionEntityField collectionEntityField =
				(CollectionEntityField)entityField;

			return new CollectionEntityField(
				_toAPIPropertyEntityField(
					collectionEntityField.getEntityField(), property));
		}

		return new APIPropertyEntityField(
			entityField.getName(), property.getName(), entityField.getType(),
			locale -> property.getName(), locale -> property.getName(),
			locale -> property.getName());
	}

	private final EntityModel _entityModel;
	private final APIApplication.Schema _schema;

}