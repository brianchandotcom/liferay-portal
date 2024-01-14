/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.dto.v1_0.ContentStructureField;
import com.liferay.headless.delivery.dto.v1_0.Option;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Cristina González
 */
public class ContentStructureUtil {

	public static ContentStructure toContentStructure(
		boolean acceptAllLanguages, GroupLocalService groupLocalService,
		Locale locale, Portal portal, UserLocalService userLocalService,
		DDMStructure ddmStructure) {

		if (ddmStructure == null) {
			return null;
		}

		Group group = groupLocalService.fetchGroup(ddmStructure.getGroupId());

		return new ContentStructure() {
			{
				setAssetLibraryKey(() -> GroupUtil.getAssetLibraryKey(group));
				setAvailableLanguages(
					() -> LocaleUtil.toW3cLanguageIds(
						ddmStructure.getAvailableLanguageIds()));
				setContentStructureFields(
					() -> TransformUtil.transformToArray(
						ddmStructure.getRootFieldNames(),
						fieldName -> _toContentStructureField(
							acceptAllLanguages,
							ddmStructure.getDDMFormField(fieldName), locale),
						ContentStructureField.class));
				setCreator(
					() -> CreatorUtil.toCreator(
						null, portal,
						userLocalService.fetchUser(ddmStructure.getUserId())));
				setDateCreated(ddmStructure::getCreateDate);
				setDateModified(ddmStructure::getModifiedDate);
				setDescription(() -> ddmStructure.getDescription(locale));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguages, ddmStructure.getDescriptionMap()));
				setId(ddmStructure::getStructureId);
				setName(() -> ddmStructure.getName(locale));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguages, ddmStructure.getDescriptionMap()));
				setSiteId(() -> GroupUtil.getSiteId(group));
			}
		};
	}

	public static String toDataType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (DDMFormFieldType.DOCUMENT_LIBRARY.equals(type) ||
			Objects.equals(DDMFormFieldTypeConstants.DOCUMENT_LIBRARY, type)) {

			return "document";
		}
		else if (DDMFormFieldType.JOURNAL_ARTICLE.equals(type) ||
				 Objects.equals(
					 JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE,
					 type)) {

			return "structuredContent";
		}
		else if (DDMFormFieldType.LINK_TO_PAGE.equals(type) ||
				 Objects.equals(
					 LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT, type)) {

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

	private static ContentStructureField _toContentStructureField(
		boolean acceptAllLanguage, DDMFormField ddmFormField, Locale locale) {

		LocalizedValue labelLocalizedValue = ddmFormField.getLabel();

		LocalizedValue predefinedLocalizedValue =
			ddmFormField.getPredefinedValue();

		return new ContentStructureField() {
			{
				setDataType(() -> toDataType(ddmFormField));
				setInputControl(() -> toInputControl(ddmFormField));
				setLabel(() -> _toString(labelLocalizedValue, locale));
				setLabel_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguage, labelLocalizedValue.getValues()));
				setLocalizable(ddmFormField::isLocalizable);
				setMultiple(ddmFormField::isMultiple);
				setName(ddmFormField::getFieldReference);
				setNestedContentStructureFields(
					() -> TransformUtil.transformToArray(
						ddmFormField.getNestedDDMFormFields(),
						ddmFormField -> _toContentStructureField(
							acceptAllLanguage, ddmFormField, locale),
						ContentStructureField.class));
				setOptions(
					() -> {
						DDMFormFieldOptions ddmFormFieldOptions =
							ddmFormField.getDDMFormFieldOptions();

						if (ddmFormFieldOptions == null) {
							return new Option[0];
						}

						Map<String, LocalizedValue> map =
							ddmFormFieldOptions.getOptions();

						return TransformUtil.transformToArray(
							map.entrySet(),
							entry -> new Option() {
								{
									LocalizedValue localizedValue =
										entry.getValue();

									setLabel(
										() -> _toString(
											localizedValue, locale));
									setLabel_i18n(
										() -> LocalizedMapUtil.getI18nMap(
											acceptAllLanguage,
											localizedValue.getValues()));

									setValue(entry::getKey);
								}
							},
							Option.class);
					});
				setPredefinedValue(
					() -> _toString(predefinedLocalizedValue, locale));
				setPredefinedValue_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguage,
						predefinedLocalizedValue.getValues()));
				setRepeatable(ddmFormField::isRepeatable);
				setRequired(ddmFormField::isRequired);
				setShowLabel(ddmFormField::isShowLabel);
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