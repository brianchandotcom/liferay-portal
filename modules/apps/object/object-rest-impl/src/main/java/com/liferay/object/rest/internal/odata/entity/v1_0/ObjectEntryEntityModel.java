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

package com.liferay.object.rest.internal.odata.entity.v1_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Javier de Arcos
 */
public class ObjectEntryEntityModel implements EntityModel {

	public ObjectEntryEntityModel(List<ObjectField> objectFields) {
		_entityFieldsMap = HashMapBuilder.<String, EntityField>put(
			"creator",
			new StringEntityField(
				"creator",
				locale -> Field.getSortableFieldName(Field.USER_NAME))
		).put(
			"creatorId",
			new IntegerEntityField("creatorId", locale -> Field.USER_ID)
		).put(
			"dateCreated",
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE)
		).put(
			"dateModified",
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE)
		).put(
			"id",
			new IdEntityField(
				"id",
				locale -> Field.getSortableFieldName(Field.ENTRY_CLASS_PK),
				String::valueOf)
		).put(
			"objectDefinitionId",
			new IntegerEntityField(
				"objectDefinitionId", locale -> "objectDefinitionId")
		).put(
			"siteId", new IntegerEntityField("siteId", locale -> Field.GROUP_ID)
		).put(
			"status",
			new CollectionEntityField(
				new IntegerEntityField("status", locale -> Field.STATUS))
		).put(
			"userId", new IntegerEntityField("userId", locale -> Field.USER_ID)
		).build();

		for (ObjectField objectField : objectFields) {
			if (Objects.equals(
					objectField.getRelationshipType(), "oneToMany")) {

				String objectFieldName = objectField.getName();

				String relationshipIdName = objectFieldName.substring(
					objectFieldName.lastIndexOf(StringPool.UNDERLINE) + 1);

				_entityFieldsMap.put(
					relationshipIdName,
					new IntegerEntityField(
						relationshipIdName,
						locale ->
							"nestedFieldArray.value_long#" + objectFieldName));
			}
			else {
				_getEntityField(
					objectField
				).ifPresent(
					entityField -> _entityFieldsMap.put(
						objectField.getName(), entityField)
				);
			}
		}
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private Optional<EntityField> _getEntityField(ObjectField objectField) {
		if (objectField.isIndexedAsKeyword()) {
			return Optional.of(
				new StringEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_keyword#" +
							objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				 Objects.equals(
					 objectField.getDBType(),
					 ObjectFieldConstants.DB_TYPE_CLOB) ||
				 Objects.equals(
					 objectField.getDBType(),
					 ObjectFieldConstants.DB_TYPE_STRING)) {

			return Optional.of(
				new StringEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_keyword_lowercase#" +
							objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL) ||
				 Objects.equals(
					 objectField.getDBType(),
					 ObjectFieldConstants.DB_TYPE_DOUBLE)) {

			return Optional.of(
				new DoubleEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_double#" +
							objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BOOLEAN)) {

			return Optional.of(
				new BooleanEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_boolean#" +
							objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_DATE)) {

			return Optional.of(
				new DateEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_date#" + objectField.getName(),
					locale ->
						"nestedFieldArray.value_date#" +
							objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_INTEGER)) {

			return Optional.of(
				new IntegerEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_integer#" +
							objectField.getName()));
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_LONG)) {

			return Optional.of(
				new IntegerEntityField(
					objectField.getName(),
					locale ->
						"nestedFieldArray.value_long#" +
							objectField.getName()));
		}

		return Optional.empty();
	}

	private final Map<String, EntityField> _entityFieldsMap;

}