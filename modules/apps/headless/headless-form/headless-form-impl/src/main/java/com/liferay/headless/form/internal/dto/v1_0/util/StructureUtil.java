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

package com.liferay.headless.form.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.headless.form.dto.v1_0.FormField;
import com.liferay.headless.form.dto.v1_0.FormFieldOption;
import com.liferay.headless.form.dto.v1_0.FormPage;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.FormSuccessPage;
import com.liferay.headless.form.dto.v1_0.Grid;
import com.liferay.headless.form.dto.v1_0.Validation;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Victor Oliveira
 */
public class StructureUtil {

	public static FormStructure toFormStructure(
			boolean acceptAllLanguages, DDMStructure ddmStructure,
			Locale locale, Portal portal, UserLocalService userLocalService)
		throws PortalException {

		DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			ddmForm.getDDMFormSuccessPageSettings();

		return new FormStructure() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmStructure.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					portal,
					userLocalService.getUserById(ddmStructure.getUserId()));
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				description = ddmStructure.getDescription(locale);
				description_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, ddmStructure.getDescriptionMap());
				formPages = TransformUtil.transformToArray(
					ddmFormLayout.getDDMFormLayoutPages(),
					ddmFormLayoutPage -> _toFormPage(
						acceptAllLanguages, ddmFormLayoutPage, ddmStructure,
						locale),
					FormPage.class);
				id = ddmStructure.getStructureId();
				name = ddmStructure.getName(locale);
				name_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, ddmStructure.getNameMap());
				siteId = ddmStructure.getGroupId();

				setFormSuccessPage(
					() -> {
						if (!ddmFormSuccessPageSettings.isEnabled()) {
							return null;
						}

						LocalizedValue bodyLocalizedValue =
							ddmFormSuccessPageSettings.getBody();
						LocalizedValue titleLocalizedValue =
							ddmFormSuccessPageSettings.getTitle();

						return new FormSuccessPage() {
							{
								description = _toString(
									locale, bodyLocalizedValue);
								description_i18n =
									LocalizedMapUtil.getLocalizedMap(
										acceptAllLanguages,
										bodyLocalizedValue.getValues());
								headline = _toString(
									locale, titleLocalizedValue);
								headline_i18n =
									LocalizedMapUtil.getLocalizedMap(
										acceptAllLanguages,
										titleLocalizedValue.getValues());
							}
						};
					});
			}
		};
	}

	private static List<String> _getNestedDDMFormFieldNames(
		List<String> ddmFormFieldNames, DDMStructure ddmStructure) {

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(true);

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.filter(
			ddmFormField -> ddmFormFieldNames.contains(ddmFormField.getName())
		).map(
			ddmFormField -> TransformUtil.transform(
				ddmFormField.getNestedDDMFormFields(), DDMFormField::getName)
		).map(
			nestedDDMFormFieldNames -> _getNestedDDMFormFieldNames(
				nestedDDMFormFieldNames, ddmStructure)
		).peek(
			nestedDDMFormFieldNames -> nestedDDMFormFieldNames.addAll(
				ddmFormFieldNames)
		).flatMap(
			Collection::stream
		).collect(
			Collectors.toList()
		);
	}

	private static FormField _toFormField(
		boolean acceptAllLanguages, DDMFormField ddmFormField, Locale locale) {

		String type = ddmFormField.getType();

		LocalizedValue labelLocalizedValue = ddmFormField.getLabel();

		LocalizedValue predefinedLocalizedValue =
			ddmFormField.getPredefinedValue();

		return new FormField() {
			{
				displayStyle = GetterUtil.getString(
					ddmFormField.getProperty("displayStyle"));

				formFieldOptions = Optional.ofNullable(
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
					entry -> _toFormFieldOption(
						acceptAllLanguages, entry, locale)
				).toArray(
					FormFieldOption[]::new
				);

				immutable = ddmFormField.isTransient();
				inputControl = type;
				label = _toString(locale, labelLocalizedValue);
				label_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, labelLocalizedValue.getValues());
				localizable = ddmFormField.isLocalizable();
				multiple = ddmFormField.isMultiple();
				name = ddmFormField.getName();
				predefinedValue = _toString(locale, predefinedLocalizedValue);
				predefinedValue_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, predefinedLocalizedValue.getValues());
				repeatable = ddmFormField.isRepeatable();
				required = ddmFormField.isRequired();
				showLabel = ddmFormField.isShowLabel();

				setDataType(
					() -> {
						if (Objects.equals("date", type)) {
							return type;
						}

						if (Objects.equals("document_library", type)) {
							return "document";
						}

						if (Objects.equals("paragraph", type)) {
							return "string";
						}

						return ddmFormField.getDataType();
					});
				setGrid(
					() -> {
						if (!Objects.equals("grid", type)) {
							return null;
						}

						return new Grid() {
							{
								columns = TransformUtil.transform(
									_toMapEntry(ddmFormField, "columns"),
									entry -> _toFormFieldOption(
										acceptAllLanguages, entry, locale),
									FormFieldOption.class);
								rows = TransformUtil.transform(
									_toMapEntry(ddmFormField, "rows"),
									entry -> _toFormFieldOption(
										acceptAllLanguages, entry, locale),
									FormFieldOption.class);
							}
						};
					});
				setHasFormRules(
					() -> {
						DDMForm ddmForm = ddmFormField.getDDMForm();

						List<DDMFormRule> ddmFormRules =
							ddmForm.getDDMFormRules();

						Stream<DDMFormRule> stream = ddmFormRules.stream();

						return stream.map(
							DDMFormRule::getCondition
						).anyMatch(
							condition -> condition.contains(
								ddmFormField.getName())
						);
					});
				setShowAsSwitcher(
					() -> {
						if (!DDMFormFieldType.CHECKBOX.equals(type) &&
							!DDMFormFieldType.CHECKBOX_MULTIPLE.equals(type)) {

							return null;
						}

						return GetterUtil.getBoolean(
							ddmFormField.getProperty("showAsSwitcher"));
					});
				setText(
					() -> {
						Object object = ddmFormField.getProperty("text");

						if (!(object instanceof LocalizedValue)) {
							return null;
						}

						return _toString(locale, (LocalizedValue)object);
					});
				setText_i18n(
					() -> {
						Object object = ddmFormField.getProperty("text");

						if (!(object instanceof LocalizedValue)) {
							return null;
						}

						LocalizedValue localizedValue = (LocalizedValue)object;

						return LocalizedMapUtil.getLocalizedMap(
							acceptAllLanguages, localizedValue.getValues());
					});
				setValidation(
					() -> {
						Object object = ddmFormField.getProperty("validation");

						if (!(object instanceof DDMFormFieldValidation)) {
							return null;
						}

						DDMFormFieldValidation ddmFormFieldValidation =
							(DDMFormFieldValidation)object;

						LocalizedValue errorMessageLocalizedValue =
							ddmFormFieldValidation.
								getErrorMessageLocalizedValue();

						return new Validation() {
							{
								errorMessage =
									errorMessageLocalizedValue.getString(
										locale);
								errorMessage_i18n =
									LocalizedMapUtil.getLocalizedMap(
										acceptAllLanguages,
										errorMessageLocalizedValue.getValues());
								expression =
									ddmFormFieldValidation.getExpression();
							}
						};
					});
			}
		};
	}

	private static FormFieldOption _toFormFieldOption(
		boolean acceptAllLanguages, Map.Entry<String, LocalizedValue> entry,
		Locale locale) {

		LocalizedValue localizedValue = entry.getValue();

		return new FormFieldOption() {
			{
				label = _toString(locale, localizedValue);
				label_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, localizedValue.getValues());
				value = entry.getKey();
			}
		};
	}

	private static FormPage _toFormPage(
		boolean acceptAllLanguages, DDMFormLayoutPage ddmFormLayoutPage,
		DDMStructure ddmStructure, Locale locale) {

		List<String> ddmFormFieldNames = Stream.of(
			ddmFormLayoutPage.getDDMFormLayoutRows()
		).flatMap(
			Collection::stream
		).map(
			DDMFormLayoutRow::getDDMFormLayoutColumns
		).flatMap(
			Collection::stream
		).map(
			DDMFormLayoutColumn::getDDMFormFieldNames
		).map(
			nestedDDMFormFieldNames -> _getNestedDDMFormFieldNames(
				nestedDDMFormFieldNames, ddmStructure)
		).flatMap(
			Collection::stream
		).collect(
			Collectors.toList()
		);

		List<DDMFormField> ddmFormFieldsList = ddmStructure.getDDMFormFields(
			true);

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFieldsList.stream();

		DDMFormField[] ddmFormFields = ddmFormFieldsStream.filter(
			ddmFormField -> ddmFormFieldNames.contains(ddmFormField.getName())
		).toArray(
			DDMFormField[]::new
		);

		LocalizedValue titleLocalizedValue = ddmFormLayoutPage.getTitle();

		LocalizedValue descriptionLocalizedValue =
			ddmFormLayoutPage.getDescription();

		return new FormPage() {
			{
				formFields = TransformUtil.transform(
					ddmFormFields,
					ddmFormField -> _toFormField(
						acceptAllLanguages, ddmFormField, locale),
					FormField.class);
				headline = _toString(locale, titleLocalizedValue);
				headline_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, titleLocalizedValue.getValues());
				text = _toString(locale, ddmFormLayoutPage.getDescription());
				text_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, descriptionLocalizedValue.getValues());
			}
		};
	}

	private static Map.Entry<String, LocalizedValue>[] _toMapEntry(
		DDMFormField ddmFormField, String name) {

		Object value = ddmFormField.getProperty(name);

		if (value == null) {
			return new Map.Entry[0];
		}

		DDMFormFieldOptions ddmFormFieldOptions = (DDMFormFieldOptions)value;

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		Set<Map.Entry<String, LocalizedValue>> set = options.entrySet();

		return set.toArray(new Map.Entry[0]);
	}

	private static String _toString(
		Locale locale, LocalizedValue localizedValue) {

		if (localizedValue == null) {
			return null;
		}

		return localizedValue.getString(locale);
	}

}