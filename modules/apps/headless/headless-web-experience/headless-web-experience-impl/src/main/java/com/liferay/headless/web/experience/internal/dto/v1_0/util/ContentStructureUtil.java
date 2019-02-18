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

package com.liferay.headless.web.experience.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.Fields;
import com.liferay.headless.web.experience.dto.v1_0.Options;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentStructureImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.FieldsImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.OptionsImpl;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class ContentStructureUtil {

	public static ContentStructure toContentStructure(
			DDMStructure ddmStructure, Locale locale, Portal portal,
			UserLocalService userLocalService)
		throws Exception {

		if (ddmStructure == null) {
			return null;
		}

		return new ContentStructureImpl() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmStructure.getAvailableLanguageIds());
				contentSpace = ddmStructure.getGroupId();
				creator = CreatorUtil.toCreator(
					portal,
					userLocalService.getUserById(ddmStructure.getUserId()));
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				description = ddmStructure.getDescription(locale);
				id = ddmStructure.getStructureId();
				name = ddmStructure.getName(locale);

				setFields(
					() -> {
						List<DDMFormField> ddmFormFields =
							ddmStructure.getDDMFormFields(true);

						Stream<DDMFormField> stream = ddmFormFields.stream();

						return stream.map(
							ddmFormField -> _toFields(ddmFormField, locale)
						).toArray(
							Fields[]::new
						);
					});
			}
		};
	}

	public static String toDataType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (DDMFormFieldType.DOCUMENT_LIBRARY.equals(type)) {
			return "document";
		}
		else if (DDMFormFieldType.JOURNAL_ARTICLE.equals(type)) {
			return "structuredContent";
		}
		else if (DDMFormFieldType.LINK_TO_PAGE.equals(type)) {
			return "url";
		}
		else if (DDMFormFieldType.RADIO.equals(type)) {
			return "string";
		}

		return ddmFormField.getDataType();
	}

	public static String toInputControl(DDMFormField ddmFormField) {
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

	private static Fields _toFields(DDMFormField ddmFormField, Locale locale) {
		return new FieldsImpl() {
			{
				label = _toString(ddmFormField.getLabel(), locale);
				localizable = ddmFormField.isLocalizable();
				multiple = ddmFormField.isMultiple();
				name = ddmFormField.getName();
				predefinedValue = _toString(
					ddmFormField.getPredefinedValue(), locale);
				repeatable = ddmFormField.isRepeatable();
				required = ddmFormField.isRequired();
				showLabel = ddmFormField.isShowLabel();

				setDataType(toDataType(ddmFormField));
				setInputControl(toInputControl(ddmFormField));
				setLabel(_toString(ddmFormField.getLabel(), locale));
				setLocalizable(ddmFormField.isLocalizable());
				setMultiple(ddmFormField.isMultiple());
				setName(ddmFormField.getName());
				setOptions(
					() -> {
						return Optional.ofNullable(
							ddmFormField.getDDMFormFieldOptions()
						).map(
							DDMFormFieldOptions::getOptions
						).map(
							Map::entrySet
						).map(
							Set::stream
						).orElseGet(
							Stream::empty
						).map(
							entry -> new OptionsImpl() {
								{
									setLabel(
										_toString(entry.getValue(), locale));
									setValue(entry.getKey());
								}
							}
						).toArray(
							Options[]::new
						);
					});
			}
		};
	}

	private static String _toString(
		LocalizedValue localizedValue, Locale locale) {

		if (localizedValue == null) {
			return null;
		}

		return localizedValue.getString(locale);
	}

}