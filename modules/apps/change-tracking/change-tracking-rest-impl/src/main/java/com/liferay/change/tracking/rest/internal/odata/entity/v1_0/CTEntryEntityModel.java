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

package com.liferay.change.tracking.rest.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Pei-Jung Lan
 */
public class CTEntryEntityModel implements EntityModel {

	public CTEntryEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new IntegerEntityField("changeType", locale -> "changeType"),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new BooleanEntityField("hideable", locale -> "hideable"),
			new IdEntityField(
				"modelClassNameId", locale -> "modelClassNameId",
				String::valueOf),
			new StringEntityField("ownerName", locale -> Field.USER_NAME),
			new StringEntityField("siteName", locale -> "groupName"),
			new IntegerEntityField("status", locale -> Field.STATUS),
			new StringEntityField("title", locale -> Field.TITLE));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}