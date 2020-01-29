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

package com.liferay.headless.delivery.internal.odata.entity.v1_0;

import com.liferay.headless.common.spi.odata.entity.EntityFieldsFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.List;
import java.util.Map;

/**
 * @author Sarai Díaz
 */
public class DocumentFolderEntityModel implements EntityModel {

	public DocumentFolderEntityModel(List<EntityField> entityFields) {
		_entityFieldsMap =
			EntityFieldsFactory.createEntityFieldsMapWithEntityId(
				new ComplexEntityField("customFields", entityFields),
				new DateTimeEntityField(
					"dateCreated",
					locale -> Field.getSortableFieldName(Field.CREATE_DATE),
					locale -> Field.CREATE_DATE),
				new DateTimeEntityField(
					"dateModified",
					locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
					locale -> Field.MODIFIED_DATE),
				new IntegerEntityField("creatorId", locale -> Field.USER_ID),
				new StringEntityField(
					"name", locale -> Field.getSortableFieldName(Field.TITLE)));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}