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

package com.liferay.headless.web.experience.internal.odata.entity.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = EntityFieldHelper.class)
public class EntityFieldHelper {

	public List<EntityField> getEntityFields(DDMStructure ddmStructure)
		throws PortalException {

		List<EntityField> entityFields = new ArrayList<>();

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			Optional<EntityField> entityFieldOptional = _createEntityField(
				ddmStructure, ddmFormField);

			if (entityFieldOptional.isPresent()) {
				entityFields.add(entityFieldOptional.get());
			}
		}

		return entityFields;
	}

	private static String _encodeFilterAndSortIdentifier(
		DDMStructure ddmStructure, String name) {

		return StringBundler.concat(
			StringPool.UNDERLINE, ddmStructure.getStructureId(),
			StringPool.UNDERLINE, name);
	}

	private Optional<EntityField> _createEntityField(
			DDMStructure ddmStructure, DDMFormField ddmFormField)
		throws PortalException {

		String indexType = ddmStructure.getFieldProperty(
			ddmFormField.getName(), "indexType");

		if (Validator.isNull(indexType)) {
			return Optional.empty();
		}

		if (Objects.equals(ddmFormField.getType(), DDMFormFieldType.CHECKBOX)) {
			return Optional.of(
				new BooleanEntityField(
					_encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> _encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DATE)) {

			return Optional.of(
				new DateEntityField(
					_encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> _encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String"),
					locale -> _encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String"),
					this::_getDDMDateFieldValue));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DOUBLE) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.NUMBER)) {

			return Optional.of(
				new DoubleEntityField(
					_encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> _encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "Number")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.LONG) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.INTEGER)) {

			return Optional.of(
				new IntegerEntityField(
					_encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> _encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "Number")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), DDMFormFieldType.RADIO) ||
				 (Objects.equals(
					 ddmFormField.getType(), DDMFormFieldType.TEXT) &&
				  Objects.equals(ddmFormField.getIndexType(), "keyword"))) {

			return Optional.of(
				new StringEntityField(
					_encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> _encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String")));
		}

		return Optional.empty();
	}

	private String _encodeName(
		long ddmStructureId, String fieldName, Locale locale, String type) {

		return Field.getSortableFieldName(
			com.liferay.portal.kernel.util.StringBundler.concat(
				_ddmIndexer.encodeName(ddmStructureId, fieldName, locale),
				StringPool.UNDERLINE, type));
	}

	private String _getDDMDateFieldValue(Object fieldValue) {
		DateFormat indexDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		try {
			Date date = indexDateFormat.parse(String.valueOf(fieldValue));

			DateFormat searchDateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

			return searchDateFormat.format(date);
		}
		catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Reference
	private DDMIndexer _ddmIndexer;

}