/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
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

/**
 * @author Magdalena Jedraszak
 */
public class TestEntityEntityModel implements EntityModel {

	public TestEntityEntityModel(List<EntityField> entityFields) {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new BooleanEntityField("published", locale -> Field.STATUS),
			new CollectionEntityField(
				new IntegerEntityField("statusCode", locale -> Field.STATUS)),
			new CollectionEntityField(
				new StringEntityField(
					"keywords", locale -> "assetTagNames.lowercase")),
			new ComplexEntityField("customFields", entityFields),
			new DateEntityField(
				"expirationDate", locale -> Field.EXPIRATION_DATE,
				locale -> Field.EXPIRATION_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new DoubleEntityField(
				Field.PRIORITY,
				locale -> Field.getSortableFieldName(
					Field.PRIORITY + "_Number")),
			new IdEntityField(
				"companyId", locale -> Field.COMPANY_ID,
				locale -> Field.getSortableFieldName(Field.COMPANY_ID)),
			new IdEntityField(
				"folderId", locale -> Field.FOLDER_ID,
				locale -> Field.getSortableFieldName(Field.FOLDER_ID)),
			new IdEntityField(
				"id", locale -> Field.ENTRY_CLASS_PK, String::valueOf),
			new IntegerEntityField("creatorId", locale -> Field.USER_ID),
			new IntegerEntityField("viewCount", locale -> "viewCount"),
			new StringEntityField("description", locale -> Field.DESCRIPTION),
			new StringEntityField(
				"friendlyUrl",
				locale -> Field.getSortableFieldName("urlTitle_String")),
			new StringEntityField(
				"title", locale -> Field.getSortableFieldName(Field.TITLE)));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}