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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.headless.form.dto.v1_0.Column;
import com.liferay.headless.form.dto.v1_0.Field;
import com.liferay.headless.form.dto.v1_0.FormPage;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.Grid;
import com.liferay.headless.form.dto.v1_0.Option;
import com.liferay.headless.form.dto.v1_0.Row;
import com.liferay.headless.form.dto.v1_0.SuccessPage;
import com.liferay.headless.form.dto.v1_0.Validation;
import com.liferay.headless.form.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Victor Oliveira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/form-structure.properties",
	scope = ServiceScope.PROTOTYPE, service = FormStructureResource.class
)
public class FormStructureResourceImpl extends BaseFormStructureResourceImpl {

	@Override
	public FormStructure getFormStructure(Long formStructureId)
		throws Exception {

		return _toFormStructure(
			_ddmStructureLocalService.getStructure(formStructureId));
	}

	@Override
	public Page<FormStructure> getSiteFormStructuresPage(
			Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureLocalService.getStructures(
					siteId, _getClassNameId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toFormStructure),
			pagination,
			_ddmStructureLocalService.getStructuresCount(
				siteId, _getClassNameId()));
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(DDMFormInstance.class.getName());
	}

	private List<String> _getNestedDDMFormFieldNames(
		List<String> ddmFormFieldNames, DDMStructure ddmStructure) {

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(true);

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.filter(
			ddmFormField -> ddmFormFieldNames.contains(ddmFormField.getName())
		).map(
			ddmFormField -> transform(
				ddmFormField.getNestedDDMFormFields(),
				nestedDDMFormField -> nestedDDMFormField.getName())
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

	private Field _toField(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		return new Field() {
			{
				immutable = ddmFormField.isTransient();
				inputControl = type;
				label = _toString(ddmFormField.getLabel());
				localizable = ddmFormField.isLocalizable();
				multiple = ddmFormField.isMultiple();
				name = ddmFormField.getName();

				options = Optional.ofNullable(
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
					entry -> new Option() {
						{
							label = _toString(entry.getValue());
							value = entry.getKey();
						}
					}
				).toArray(
					Option[]::new
				);

				predefinedValue = _toString(ddmFormField.getPredefinedValue());
				repeatable = ddmFormField.isRepeatable();
				required = ddmFormField.isRequired();
				showLabel = ddmFormField.isShowLabel();

				setDataType(
					() -> {
						if (Objects.equals("document_library", type)) {
							return "document";
						}

						if (Objects.equals("date", type) ||
							Objects.equals("paragraph", type)) {

							return type;
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
									entry -> new Column() {
										{
											label = _toString(entry.getValue());
											value = entry.getKey();
										}
									},
									Column.class);
								rows = TransformUtil.transform(
									_toMapEntry(ddmFormField, "rows"),
									entry -> new Row() {
										{
											label = _toString(entry.getValue());
											value = entry.getKey();
										}
									},
									Row.class);
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

						return _toString((LocalizedValue)object);
					});
				setValidation(
					() -> {
						Object object = ddmFormField.getProperty("validation");

						if (!(object instanceof DDMFormFieldValidation)) {
							return null;
						}

						DDMFormFieldValidation ddmFormFieldValidation =
							(DDMFormFieldValidation)object;

						return new Validation() {
							{
								errorMessage =
									ddmFormFieldValidation.getErrorMessage();
								expression =
									ddmFormFieldValidation.getExpression();
							}
						};
					});
			}
		};
	}

	private FormPage _toFormPage(
		DDMFormLayoutPage ddmFormLayoutPage, DDMStructure ddmStructure) {

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

		DDMFormField[] ddmFormFields = ddmStructure.getDDMFormFields(
			true
		).stream(
		).filter(
			ddmFormField -> ddmFormFieldNames.contains(ddmFormField.getName())
		).toArray(
			DDMFormField[]::new
		);

		return new FormPage() {
			{
				headline = _toString(ddmFormLayoutPage.getTitle());
				fields = TransformUtil.transform(
					ddmFormFields, ddmFormField -> _toField(ddmFormField),
					Field.class);
				text = _toString(ddmFormLayoutPage.getDescription());
			}
		};
	}

	private FormStructure _toFormStructure(DDMStructure ddmStructure)
		throws Exception {

		DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			ddmForm.getDDMFormSuccessPageSettings();

		return new FormStructure() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmStructure.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(ddmStructure.getUserId()));
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				description = ddmStructure.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				formPages = TransformUtil.transformToArray(
					ddmFormLayout.getDDMFormLayoutPages(),
					ddmFormLayoutPage -> _toFormPage(
						ddmFormLayoutPage, ddmStructure),
					FormPage.class);
				id = ddmStructure.getStructureId();
				name = ddmStructure.getName(
					contextAcceptLanguage.getPreferredLocale());
				siteId = ddmStructure.getGroupId();

				setSuccessPage(
					() -> {
						if (!ddmFormSuccessPageSettings.isEnabled()) {
							return null;
						}

						return new SuccessPage() {
							{
								description = _toString(
									ddmFormSuccessPageSettings.getBody());
								headline = _toString(
									ddmFormSuccessPageSettings.getTitle());
							}
						};
					});
			}
		};
	}

	private Map.Entry<String, LocalizedValue>[] _toMapEntry(
		DDMFormField ddmFormField, String name) {

		Object value = ddmFormField.getProperty(name);

		if (value == null) {
			return new Map.Entry[0];
		}

		DDMFormFieldOptions ddmFormFieldOptions = (DDMFormFieldOptions)value;

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		Set<Map.Entry<String, LocalizedValue>> set = options.entrySet();

		return set.toArray(new Map.Entry[set.size()]);
	}

	private String _toString(LocalizedValue localizedValue) {
		if (localizedValue == null) {
			return null;
		}

		return localizedValue.getString(
			contextAcceptLanguage.getPreferredLocale());
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}