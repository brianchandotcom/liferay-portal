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

package com.liferay.headless.web.experience.internal.dto.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.Fields;
import com.liferay.headless.web.experience.dto.v1_0.Options;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class ContentStructureUtil {

	public static ContentStructure toContentStructure(
			DDMStructure ddmStructure, Locale locale,
			UserLocalService userLocalService)
		throws Exception {

		if (ddmStructure == null) {
			return null;
		}

		return new ContentStructure() {
			{
				setAvailableLanguages(
					LocaleUtil.toW3cLanguageIds(
						ddmStructure.getAvailableLanguageIds()));
				setContentSpace(ddmStructure.getGroupId());
				setCreator(
					CreatorUtil.toCreator(
						userLocalService.getUserById(
							ddmStructure.getUserId())));
				setDateCreated(ddmStructure.getCreateDate());
				setDateModified(ddmStructure.getModifiedDate());
				setDescription(ddmStructure.getDescription(locale));
				setFields(_getFields(ddmStructure, locale));
				setId(ddmStructure.getStructureId());
				setName(ddmStructure.getName(locale));
			}
		};
	}

	private static String _getFieldDataType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (Objects.equals(type, DDMFormFieldType.DOCUMENT_LIBRARY)) {
			return "document";
		}
		else if (Objects.equals(type, DDMFormFieldType.JOURNAL_ARTICLE)) {
			return "structuredContent";
		}
		else if (Objects.equals(type, DDMFormFieldType.LINK_TO_PAGE)) {
			return "url";
		}
		else if (Objects.equals(type, DDMFormFieldType.RADIO)) {
			return "string";
		}

		return ddmFormField.getDataType();
	}

	private static String _getFieldInputControl(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (DDMFormFieldType.CHECKBOX.equals(type) ||
			DDMFormFieldType.RADIO.equals(type) ||
			DDMFormFieldType.SELECT.equals(type) ||
			DDMFormFieldType.TEXT.equals(type) ||
			DDMFormFieldType.TEXT_AREA.equals(type)) {

			return type;
		}

		return null;
	}

	private static Fields[] _getFields(
		DDMStructure ddmStructure, Locale locale) {

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(true);

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.map(
			ddmFormField -> _toFields(ddmFormField, locale)
		).toArray(
			Fields[]::new
		);
	}

	private static String _getLocalizedString(
		LocalizedValue localizedValue, Locale locale) {

		return Optional.ofNullable(
			localizedValue
		).map(
			value -> value.getString(locale)
		).orElse(
			null
		);
	}

	private static Fields _toFields(DDMFormField field, Locale locale) {
		return new Fields() {
			{
				setDataType(_getFieldDataType(field));
				setInputControl(_getFieldInputControl(field));
				setLabel(_getLocalizedString(field.getLabel(), locale));
				setLocalizable(field.isLocalizable());
				setMultiple(field.isMultiple());
				setName(field.getName());
				setOptions(_toOptions(field.getDDMFormFieldOptions(), locale));
				setPredefinedValue(
					_getLocalizedString(field.getPredefinedValue(), locale));
				setRepeatable(field.isRepeatable());
				setRequired(field.isRequired());
				setShowLabel(field.isShowLabel());
			}
		};
	}

	private static Options[] _toOptions(
		DDMFormFieldOptions ddmFormFieldOptions, Locale locale) {

		return Optional.ofNullable(
			ddmFormFieldOptions
		).map(
			DDMFormFieldOptions::getOptions
		).map(
			Map::entrySet
		).map(
			Set::stream
		).orElseGet(
			Stream::empty
		).map(
			entry -> new Options() {
				{
					setLabel(_getLocalizedString(entry.getValue(), locale));
					setValue(entry.getKey());
				}
			}
		).toArray(
			Options[]::new
		);
	}

}