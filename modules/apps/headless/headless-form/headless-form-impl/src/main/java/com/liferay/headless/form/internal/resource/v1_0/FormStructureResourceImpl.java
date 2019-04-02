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
	public Page<FormStructure> getContentSpaceFormStructuresPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureLocalService.getStructures(
					contentSpaceId, _getClassNameId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toFormStructure),
			pagination,
			_ddmStructureLocalService.getStructuresCount(
				contentSpaceId, _getClassNameId()));
	}

	@Override
	public FormStructure getFormStructure(Long formStructureId)
		throws Exception {

		return _toFormStructure(
			_ddmStructureLocalService.getStructure(formStructureId));
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(DDMFormInstance.class.getName());
	}

	private List<String> _getFieldNames(
		DDMFormLayoutPage ddmFormLayoutPage, DDMStructure ddmStructure) {

		return Stream.of(
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
			formFieldNames -> _getNestedFieldNames(formFieldNames, ddmStructure)
		).flatMap(
			Collection::stream
		).collect(
			Collectors.toList()
		);
	}

	private Grid _getGrid(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (!type.equals("grid")) {
			return null;
		}

		return new Grid() {
			{
				columns = _toColumn(ddmFormField);
				rows = TransformUtil.transform(
					_toLocalizedValueMapEntry(ddmFormField, "rows"),
					entry -> new Row() {
						{
							label = _toString(entry.getValue());
							value = entry.getKey();
						}
					},
					Row.class);
			}
		};
	}

	private List<String> _getNestedFieldNames(
		List<String> ddmFormFieldNames, DDMStructure ddmStructure) {

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(true);

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		return ddmFormFieldStream.filter(
			formField -> ddmFormFieldNames.contains(formField.getName())
		).map(
			formField -> formField.getNestedDDMFormFields(
			).stream(
			).map(
				DDMFormField::getName
			).collect(
				Collectors.toList()
			)
		).map(
			fieldNames -> _getNestedFieldNames(fieldNames, ddmStructure)
		).peek(
			fieldNames -> fieldNames.addAll(ddmFormFieldNames)
		).flatMap(
			Collection::stream
		).collect(
			Collectors.toList()
		);
	}

	private SuccessPage _getSuccessPage(
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		if (!ddmFormSuccessPageSettings.isEnabled()) {
			return null;
		}

		return new SuccessPage() {
			{
				description = _toString(ddmFormSuccessPageSettings.getBody());
				headline = _toString(ddmFormSuccessPageSettings.getTitle());
			}
		};
	}

	private String _getText(DDMFormField ddmFormField) {
		Object textProperty = ddmFormField.getProperty("text");

		if (!(textProperty instanceof LocalizedValue)) {
			return null;
		}

		return _toString((LocalizedValue)textProperty);
	}

	private Validation _getValidation(DDMFormField ddmFormField) {
		Object obj = ddmFormField.getProperty("validation");

		if (!(obj instanceof DDMFormFieldValidation)) {
			return null;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			(DDMFormFieldValidation)obj;

		return new Validation() {
			{
				expression = ddmFormFieldValidation.getExpression();
				errorMessage = ddmFormFieldValidation.getErrorMessage();
			}
		};
	}

	private Boolean _hasFormRulesFunction(DDMFormField ddmFormField) {
		DDMForm ddmForm = ddmFormField.getDDMForm();

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		return ddmFormRules.stream(
		).map(
			DDMFormRule::getCondition
		).anyMatch(
			ruleCondition -> ruleCondition.contains(ddmFormField.getName())
		);
	}

	private Boolean _showAsSwitcher(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (DDMFormFieldType.CHECKBOX.equals(type) ||
			DDMFormFieldType.CHECKBOX_MULTIPLE.equals(type)) {

			return GetterUtil.getBoolean(
				ddmFormField.getProperty("showAsSwitcher"));
		}

		return null;
	}

	private Column[] _toColumn(DDMFormField ddmFormField) {
		return TransformUtil.transform(
			_toLocalizedValueMapEntry(ddmFormField, "columns"),
			entry -> new Column() {
				{
					label = _toString(entry.getValue());
					value = entry.getKey();
				}
			},
			Column.class);
	}

	private String _toDataType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (type.equals("document_library")) {
			return "document";
		}
		else if (type.equals("date") || type.equals("paragraph")) {
			return type;
		}

		return ddmFormField.getDataType();
	}

	private Field _toField(DDMFormField ddmFormField) {
		return new Field() {
			{
				grid = _getGrid(ddmFormField);
				hasFormRules = _hasFormRulesFunction(ddmFormField);
				immutable = ddmFormField.isTransient();
				label = _toString(ddmFormField.getLabel());
				localizable = ddmFormField.isLocalizable();
				multiple = ddmFormField.isMultiple();
				name = ddmFormField.getName();
				predefinedValue = _toString(ddmFormField.getPredefinedValue());
				repeatable = ddmFormField.isRepeatable();
				required = ddmFormField.isRequired();
				showAsSwitcher = _showAsSwitcher(ddmFormField);
				showLabel = ddmFormField.isShowLabel();
				text = _getText(ddmFormField);
				validation = _getValidation(ddmFormField);

				setDataType(_toDataType(ddmFormField));
				setInputControl(ddmFormField.getType());
				setLabel(_toString(ddmFormField.getLabel()));
				setLocalizable(ddmFormField.isLocalizable());
				setMultiple(ddmFormField.isMultiple());
				setName(ddmFormField.getName());
				setOptions(
					() -> Optional.ofNullable(
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
								setLabel(_toString(entry.getValue()));
								setValue(entry.getKey());
							}
						}
					).toArray(
						Option[]::new
					));
			}
		};
	}

	private FormPage _toFormPage(
		DDMFormLayoutPage ddmFormLayoutPage, DDMStructure ddmStructure) {

		List<String> fieldNames = _getFieldNames(
			ddmFormLayoutPage, ddmStructure);

		DDMFormField[] ddmFormFields = ddmStructure.getDDMFormFields(
			true
		).stream(
		).filter(
			ddmFormField -> fieldNames.contains(ddmFormField.getName())
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

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			ddmForm.getDDMFormSuccessPageSettings();

		DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

		return new FormStructure() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmStructure.getAvailableLanguageIds());
				contentSpaceId = ddmStructure.getGroupId();
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
				successPage = _getSuccessPage(ddmFormSuccessPageSettings);
			}
		};
	}

	private Map.Entry<String, LocalizedValue>[] _toLocalizedValueMapEntry(
		DDMFormField ddmFormField, String element) {

		Object property = ddmFormField.getProperty(element);

		if (property != null) {
			DDMFormFieldOptions ddmFormFieldOptions =
				(DDMFormFieldOptions)property;

			Map<String, LocalizedValue> options =
				ddmFormFieldOptions.getOptions();

			Set<Map.Entry<String, LocalizedValue>> entries = options.entrySet();

			return entries.toArray(new Map.Entry[entries.size()]);
		}

		return new Map.Entry[0];
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