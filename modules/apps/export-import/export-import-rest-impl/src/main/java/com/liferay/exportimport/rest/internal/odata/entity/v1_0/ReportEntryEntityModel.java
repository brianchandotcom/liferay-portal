/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class ReportEntryEntityModel implements EntityModel {

	public ReportEntryEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new ComplexEntityField(
				"origin",
				Collections.singletonList(
					new IntegerEntityField(
						"code", locale -> "origin_integer"))),
			new ComplexEntityField(
				"status",
				Collections.singletonList(
					new IntegerEntityField("code", locale -> Field.STATUS))),
			new ComplexEntityField(
				"type",
				Arrays.asList(
					new IntegerEntityField(
						"code",
						locale -> Field.getSortableFieldName("type_integer")),
					new StringEntityField(
						"label",
						locale -> Field.getSortableFieldName(
							Field.getLocalizedName(locale, "type_label")),
						locale -> {
							String sortableFieldName =
								Field.getSortableFieldName(
									Field.getLocalizedName(
										locale, "type_label"));

							return sortableFieldName.concat(
								".keyword_lowercase");
						}))),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new IntegerEntityField("id", locale -> Field.ENTRY_CLASS_PK),
			new StringEntityField(
				"classExternalReferenceCode",
				locale -> Field.getSortableFieldName(
					"classExternalReferenceCode"),
				locale -> "classExternalReferenceCode"),
			new StringEntityField(
				"modelName",
				locale -> Field.getSortableFieldName(
					Field.getLocalizedName(locale, "modelName")),
				locale -> {
					String sortableFieldName = Field.getSortableFieldName(
						Field.getLocalizedName(locale, "modelName"));

					return sortableFieldName.concat(".keyword_lowercase");
				}));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}