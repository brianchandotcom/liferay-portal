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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMForm implements Serializable {

	public DDMForm() {
		_availableLocales = new LinkedHashSet<>();
		_ddmFormFields = new ArrayList<>();
		_ddmFormRules = new ArrayList<>();
		_ddmFormSuccessPageSettings = new DDMFormSuccessPageSettings();
	}

	public DDMForm(DDMForm ddmForm) {
		_availableLocales = new LinkedHashSet<>(ddmForm._availableLocales);
		_defaultLocale = ddmForm._defaultLocale;

		_ddmFormFields = new ArrayList<>(ddmForm._ddmFormFields.size());

		for (DDMFormField ddmFormField : ddmForm._ddmFormFields) {
			addDDMFormField(new DDMFormField(ddmFormField));
		}

		_ddmFormRules = new ArrayList<>(ddmForm._ddmFormRules.size());

		for (DDMFormRule ddmFormRule : ddmForm._ddmFormRules) {
			addDDMFormRule(new DDMFormRule(ddmFormRule));
		}

		_ddmFormSuccessPageSettings = new DDMFormSuccessPageSettings(
			ddmForm.getDDMFormSuccessPageSettings());
	}

	public void addAvailableLocale(Locale locale) {
		_availableLocales.add(locale);
	}

	public void addDDMFormField(DDMFormField ddmFormField) {
		ddmFormField.setDDMForm(this);

		_ddmFormFields.add(ddmFormField);
	}

	public void addDDMFormRule(DDMFormRule ddmFormRule) {
		_ddmFormRules.add(ddmFormRule);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMForm)) {
			return false;
		}

		DDMForm ddmForm = (DDMForm)obj;

		if (Objects.equals(_availableLocales, ddmForm._availableLocales) &&
			Objects.equals(_defaultLocale, ddmForm._defaultLocale) &&
			Objects.equals(_ddmFormFields, ddmForm._ddmFormFields) &&
			Objects.equals(_ddmFormRules, ddmForm._ddmFormRules) &&
			Objects.equals(
				_ddmFormSuccessPageSettings,
				ddmForm._ddmFormSuccessPageSettings)) {

			return true;
		}

		return false;
	}

	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public List<DDMFormField> getDDMFormFields() {
		return _ddmFormFields;
	}

	public Map<String, DDMFormField> getDDMFormFieldsMap(
		boolean includeNestedDDMFormFields) {

		Map<String, DDMFormField> ddmFormFieldsMap = new LinkedHashMap<>();

		for (DDMFormField ddmFormField : _ddmFormFields) {
			ddmFormFieldsMap.put(ddmFormField.getName(), ddmFormField);

			if (includeNestedDDMFormFields) {
				ddmFormFieldsMap.putAll(
					ddmFormField.getNestedDDMFormFieldsMap());
			}
		}

		return ddmFormFieldsMap;
	}

	public List<DDMFormRule> getDDMFormRules() {
		return _ddmFormRules;
	}

	public DDMFormSuccessPageSettings getDDMFormSuccessPageSettings() {
		return _ddmFormSuccessPageSettings;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public Map<String, DDMFormField> getNontransientDDMFormFieldsMap(
		boolean includeNestedDDMFormFields) {

		Map<String, DDMFormField> ddmFormFieldsMap = new LinkedHashMap<>();

		for (DDMFormField ddmFormField : _ddmFormFields) {
			if (!ddmFormField.isTransient()) {
				ddmFormFieldsMap.put(ddmFormField.getName(), ddmFormField);
			}

			if (includeNestedDDMFormFields) {
				ddmFormFieldsMap.putAll(
					ddmFormField.getNontransientNestedDDMFormFieldsMap());
			}
		}

		return ddmFormFieldsMap;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _availableLocales);

		hash = HashUtil.hash(hash, _defaultLocale);

		hash = HashUtil.hash(hash, _ddmFormFields);

		hash = HashUtil.hash(hash, _ddmFormRules);

		return HashUtil.hash(hash, _ddmFormSuccessPageSettings);
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDDMFormFields(List<DDMFormField> ddmFormFields) {
		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmFormField.setDDMForm(this);
		}

		_ddmFormFields = ddmFormFields;
	}

	public void setDDMFormRules(List<DDMFormRule> ddmFormRules) {
		_ddmFormRules = ddmFormRules;
	}

	public void setDDMFormSuccessPageSettings(
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		_ddmFormSuccessPageSettings = new DDMFormSuccessPageSettings(
			ddmFormSuccessPageSettings);
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	private Set<Locale> _availableLocales;
	private List<DDMFormField> _ddmFormFields;
	private List<DDMFormRule> _ddmFormRules;
	private DDMFormSuccessPageSettings _ddmFormSuccessPageSettings;
	private Locale _defaultLocale;

}