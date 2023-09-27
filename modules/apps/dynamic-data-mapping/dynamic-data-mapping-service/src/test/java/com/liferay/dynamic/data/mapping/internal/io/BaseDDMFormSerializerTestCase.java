/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

/**
 * @author Pablo Carvalho
 */
public abstract class BaseDDMFormSerializerTestCase extends BaseDDMTestCase {

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(
			DDMFormTestUtil.createAvailableLocales(
				LocaleUtil.BRAZIL, LocaleUtil.US));
		ddmForm.setDDMFormFields(_createDDMFormFields());
		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMFormField createTextDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setIndexType("keyword");
		ddmFormField.setLabel(_createTextDDMFormFieldLabel());
		ddmFormField.setLocalizable(false);
		ddmFormField.setPredefinedValue(
			_createTextDDMFormFieldPredefinedValue());
		ddmFormField.setReadOnly(false);
		ddmFormField.setRepeatable(true);
		ddmFormField.setRequired(false);
		ddmFormField.setShowLabel(true);
		ddmFormField.setVisibilityExpression("true");

		_createNotEmptyValidation(ddmFormField);

		return ddmFormField;
	}

	private DDMFormFieldOptions _createDDMFormFieldOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOption("Value 1");

		ddmFormFieldOptions.addOptionLabel(
			"Value 1", LocaleUtil.BRAZIL, "Opcao 1");
		ddmFormFieldOptions.addOptionLabel(
			"Value 1", LocaleUtil.US, "Option 1");

		ddmFormFieldOptions.addOption("Value 2");

		ddmFormFieldOptions.addOptionLabel(
			"Value 2", LocaleUtil.BRAZIL, "Opcao 2");
		ddmFormFieldOptions.addOptionLabel(
			"Value 2", LocaleUtil.US, "Option 2");

		return ddmFormFieldOptions;
	}

	private List<DDMFormField> _createDDMFormFields() {
		return ListUtil.fromArray(
			_createNestedDDMFormFields("ParentField", "ChildField"),
			_createRadioDDMFormField("BooleanField"),
			_createSelectDDMFormField("SelectField"),
			createTextDDMFormField("TextField"),
			_createHTMLDDMFormField("HTMLField"));
	}

	private DDMFormField _createHTMLDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(
			name, DDMFormFieldType.TEXT_HTML);

		ddmFormField.setDataType("html");
		ddmFormField.setFieldNamespace("ddm");
		ddmFormField.setIndexType("text");
		ddmFormField.setLabel(_createHTMLDDMFormFieldLabel());
		ddmFormField.setLocalizable(true);
		ddmFormField.setPredefinedValue(
			_createHTMLDDMFormFieldPredefinedValue());
		ddmFormField.setReadOnly(false);
		ddmFormField.setRepeatable(false);
		ddmFormField.setRequired(false);
		ddmFormField.setShowLabel(true);
		ddmFormField.setTip(_createHTMLDDMFormFieldTip());

		return ddmFormField;
	}

	private LocalizedValue _createHTMLDDMFormFieldLabel() {
		LocalizedValue label = new LocalizedValue();

		label.addString(LocaleUtil.BRAZIL, "HTML");
		label.addString(LocaleUtil.US, "HTML");

		return label;
	}

	private LocalizedValue _createHTMLDDMFormFieldPredefinedValue() {
		LocalizedValue predefinedValue = new LocalizedValue();

		predefinedValue.addString(LocaleUtil.BRAZIL, "");
		predefinedValue.addString(LocaleUtil.US, "");

		return predefinedValue;
	}

	private LocalizedValue _createHTMLDDMFormFieldTip() {
		LocalizedValue predefinedValue = new LocalizedValue();

		predefinedValue.addString(LocaleUtil.BRAZIL, "Dica");
		predefinedValue.addString(LocaleUtil.US, "Tip");

		return predefinedValue;
	}

	private DDMFormField _createNestedDDMFormFields(
		String parentName, String childName) {

		DDMFormField parentDDMFormField = createTextDDMFormField(parentName);

		parentDDMFormField.setNestedDDMFormFields(
			ListUtil.fromArray(_createSelectDDMFormField(childName)));

		return parentDDMFormField;
	}

	private void _createNotEmptyValidation(DDMFormField ddmFormField) {
		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("!" + ddmFormField.getName() + ".isEmpty()");
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			DDMFormValuesTestUtil.createLocalizedValue(
				StringBundler.concat(
					"Field ", ddmFormField.getName(), " must not be empty."),
				LocaleUtil.US));

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);
	}

	private DDMFormField _createRadioDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "radio");

		ddmFormField.setDataType("string");
		ddmFormField.setDDMFormFieldOptions(_createDDMFormFieldOptions());
		ddmFormField.setLocalizable(false);
		ddmFormField.setReadOnly(false);
		ddmFormField.setRepeatable(false);
		ddmFormField.setRequired(true);
		ddmFormField.setShowLabel(false);
		ddmFormField.setVisibilityExpression("false");

		_createNotEmptyValidation(ddmFormField);

		return ddmFormField;
	}

	private DDMFormField _createSelectDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "select");

		ddmFormField.setDataType("string");
		ddmFormField.setLocalizable(false);
		ddmFormField.setIndexType("");
		ddmFormField.setMultiple(true);
		ddmFormField.setReadOnly(false);
		ddmFormField.setRepeatable(false);
		ddmFormField.setRequired(false);
		ddmFormField.setShowLabel(true);
		ddmFormField.setVisibilityExpression("true");

		_createNotEmptyValidation(ddmFormField);

		ddmFormField.setDDMFormFieldOptions(_createDDMFormFieldOptions());

		return ddmFormField;
	}

	private LocalizedValue _createTextDDMFormFieldLabel() {
		LocalizedValue label = new LocalizedValue();

		label.addString(LocaleUtil.BRAZIL, "Texto");
		label.addString(LocaleUtil.US, "Text");

		return label;
	}

	private LocalizedValue _createTextDDMFormFieldPredefinedValue() {
		LocalizedValue predefinedValue = new LocalizedValue();

		predefinedValue.addString(LocaleUtil.BRAZIL, "Exemplo");
		predefinedValue.addString(LocaleUtil.US, "Example");

		return predefinedValue;
	}

}