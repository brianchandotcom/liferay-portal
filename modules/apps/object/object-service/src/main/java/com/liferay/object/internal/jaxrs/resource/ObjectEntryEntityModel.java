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

package com.liferay.object.internal.jaxrs.resource;

import com.liferay.object.model.ObjectField;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Javier de Arcos
 */
public class ObjectEntryEntityModel implements EntityModel {

	public ObjectEntryEntityModel(List<ObjectField> objectFields) {
		_entityFieldsMap = new HashMap<>();

		for (ObjectField objectField : objectFields) {
			_getEntityField(
				objectField
			).ifPresent(
				entityField -> _entityFieldsMap.put(
					objectField.getName(), entityField)
			);
		}
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private Optional<EntityField> _getEntityField(ObjectField objectField) {
		String entityFieldName = objectField.getName();

		String type = objectField.getType();

		if (type.equals("Boolean")) {
			return Optional.of(
				new BooleanEntityField(
					entityFieldName, locale -> entityFieldName));
		}
		else if (type.equals("BigDecimal") || type.equals("Double")) {
			return Optional.of(
				new DoubleEntityField(
					entityFieldName, locale -> entityFieldName));
		}
		else if (type.equals("Date")) {
			return Optional.of(
				new DateEntityField(
					entityFieldName, locale -> entityFieldName,
					locale -> entityFieldName));
		}
		else if (type.equals("Integer") || type.equals("Long")) {
			return Optional.of(
				new IntegerEntityField(
					entityFieldName, locale -> entityFieldName));
		}
		else if (type.equals("String")) {
			return Optional.of(
				new StringEntityField(
					entityFieldName, locale -> entityFieldName));
		}

		return Optional.empty();
	}

	private final Map<String, EntityField> _entityFieldsMap;

}