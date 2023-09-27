/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.odata.entity.v1_0;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = EntityFieldsProvider.class)
public class EntityFieldsProvider {

	public List<EntityField> provide(DDMStructure ddmStructure)
		throws Exception {

		List<EntityField> entityFields = new ArrayList<>();

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			EntityField entityField = _createEntityField(
				ddmStructure, ddmFormField);

			if (entityField != null) {
				entityFields.add(entityField);
			}
		}

		return entityFields;
	}

	private EntityField _createEntityField(
			DDMStructure ddmStructure, DDMFormField ddmFormField)
		throws Exception {

		String indexType = ddmStructure.getFieldProperty(
			ddmFormField.getName(), "indexType");

		if (Validator.isNull(indexType)) {
			return null;
		}

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.CHECKBOX)) {

			return new BooleanEntityField(
				ddmFormField.getFieldReference(),
				locale -> _toFilterableOrSortableFieldName(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(), locale, "String"));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DATE)) {

			return new DateEntityField(
				ddmFormField.getFieldReference(),
				locale -> _toFilterableOrSortableFieldName(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(), locale, "String"),
				locale -> _toFilterableOrSortableFieldName(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(), locale, "String"),
				this::_toFieldValue);
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DOUBLE) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.NUMBER)) {

			return new DoubleEntityField(
				ddmFormField.getFieldReference(),
				locale -> _toFilterableOrSortableFieldName(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(), locale, "Number"));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.INTEGER) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.LONG)) {

			return new IntegerEntityField(
				ddmFormField.getFieldReference(),
				locale -> _toFilterableOrSortableFieldName(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(), locale, "Number"));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(),
					DDMFormFieldTypeConstants.RADIO) ||
				 (Objects.equals(ddmFormField.getIndexType(), "keyword") &&
				  (Objects.equals(
					  ddmFormField.getType(),
					  DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE) ||
				   Objects.equals(
					   ddmFormField.getType(),
					   DDMFormFieldTypeConstants.SELECT) ||
				   Objects.equals(
					   ddmFormField.getType(),
					   DDMFormFieldTypeConstants.TEXT)))) {

			return new StringEntityField(
				ddmFormField.getFieldReference(),
				locale -> _toFilterableOrSortableFieldName(
					ddmStructure.getStructureId(),
					ddmFormField.getFieldReference(), locale, "String"));
		}

		return null;
	}

	private String _toFieldValue(Object fieldValue) {
		DateFormat indexDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		try {
			Date date = indexDateFormat.parse(String.valueOf(fieldValue));

			DateFormat searchDateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

			return searchDateFormat.format(date);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}
	}

	private String _toFilterableOrSortableFieldName(
		long ddmStructureId, String fieldReference, Locale locale,
		String type) {

		return Field.getSortableFieldName(
			StringBundler.concat(
				_ddmIndexer.encodeName(ddmStructureId, fieldReference, locale),
				StringPool.UNDERLINE, type));
	}

	@Reference
	private DDMIndexer _ddmIndexer;

}