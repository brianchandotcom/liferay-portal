/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Pei-Jung Lan
 */
public class CTEntryEntityModel implements EntityModel {

	public CTEntryEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new BooleanEntityField("hideable", locale -> "hideable"),
			new DateTimeEntityField(
				"ctCollectionStatusDate",
				locale -> Field.getSortableFieldName(
					"ctCollectionStatusDate_Number"),
				locale -> "ctCollectionStatusDate"),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new EntityField(
				"ctCollectionStatus", EntityField.Type.INTEGER,
				locale -> Field.getSortableFieldName(
					"ctCollectionStatusLabel_".concat(
						LocaleUtil.toLanguageId(locale))),
				locale -> "ctCollectionStatus", String::valueOf),
			new EntityField(
				"status", EntityField.Type.INTEGER,
				locale -> Field.getSortableFieldName(
					"statusLabel_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> "status", String::valueOf),
			new IdEntityField(
				"modelClassNameId", locale -> "modelClassNameId",
				String::valueOf),
			new IdEntityField(
				"ownerId", locale -> Field.USER_ID, String::valueOf),
			new IdEntityField(
				"siteId", locale -> Field.GROUP_ID, String::valueOf),
			new StringEntityField(
				"changeType",
				locale -> Field.getSortableFieldName(
					"changeTypeLabel_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> "changeTypeLabel"),
			new StringEntityField(
				"ctCollectionName",
				locale -> Field.getSortableFieldName(
					"ctCollectionName_String")),
			new StringEntityField(
				"ctCollectionStatusUserName",
				locale -> Field.getSortableFieldName(
					"ctCollectionStatusUserName_String")),
			new StringEntityField("ownerName", locale -> Field.USER_NAME),
			new StringEntityField(
				"siteName",
				locale -> Field.getSortableFieldName(
					"groupName_".concat(LocaleUtil.toLanguageId(locale)))),
			new StringEntityField(
				"title",
				locale -> Field.getSortableFieldName(
					"title_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> Field.TITLE),
			new StringEntityField(
				"typeName",
				locale -> Field.getSortableFieldName(
					"typeName_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> "typeName"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}