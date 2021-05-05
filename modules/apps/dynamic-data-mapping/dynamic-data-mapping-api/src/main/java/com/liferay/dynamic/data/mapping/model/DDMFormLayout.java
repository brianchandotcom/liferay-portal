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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayout implements Serializable {

	public static final String MULTI_PAGES = "multi-pages";

	public static final String SETTINGS_MODE = "settings";

	public static final String SINGLE_PAGE_MODE = "single-page";

	public static final String TABBED_MODE = "tabbed";

	public static final String WIZARD_MODE = "wizard";

	public DDMFormLayout() {
	}

	public DDMFormLayout(DDMFormLayout ddmFormLayout) {
		_defaultLocale = ddmFormLayout._defaultLocale;
		_paginationMode = ddmFormLayout._paginationMode;
		_definitionSchemaVersion = ddmFormLayout._definitionSchemaVersion;

		for (DDMFormField ddmFormField : ddmFormLayout._ddmFormFields) {
			_ddmFormFields.add(new DDMFormField(ddmFormField));
		}

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout._ddmFormLayoutPages) {

			addDDMFormLayoutPage(new DDMFormLayoutPage(ddmFormLayoutPage));
		}

		for (DDMFormRule ddmFormRule : ddmFormLayout._ddmFormRules) {
			_ddmFormRules.add(new DDMFormRule(ddmFormRule));
		}
	}

	public void addDDMFormLayoutPage(DDMFormLayoutPage ddmFormLayoutPage) {
		_ddmFormLayoutPages.add(ddmFormLayoutPage);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormLayout)) {
			return false;
		}

		DDMFormLayout ddmFormLayout = (DDMFormLayout)object;

		if (Objects.equals(
				_availableLocales, ddmFormLayout._availableLocales) &&
			Objects.equals(_ddmFormFields, ddmFormLayout._ddmFormFields) &&
			Objects.equals(
				_ddmFormLayoutPages, ddmFormLayout._ddmFormLayoutPages) &&
			Objects.equals(_ddmFormRules, ddmFormLayout._ddmFormRules) &&
			Objects.equals(_defaultLocale, ddmFormLayout._defaultLocale) &&
			Objects.equals(_paginationMode, ddmFormLayout._paginationMode) &&
			Objects.equals(
				_definitionSchemaVersion,
				ddmFormLayout._definitionSchemaVersion)) {

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

	public DDMFormLayoutPage getDDMFormLayoutPage(int index) {
		return _ddmFormLayoutPages.get(index);
	}

	public List<DDMFormLayoutPage> getDDMFormLayoutPages() {
		return _ddmFormLayoutPages;
	}

	public List<DDMFormRule> getDDMFormRules() {
		return _ddmFormRules;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public String getDefinitionSchemaVersion() {
		return _definitionSchemaVersion;
	}

	public Integer getNextPage() {
		return _nextPage;
	}

	public String getPaginationMode() {
		return _paginationMode;
	}

	public Integer getPreviousPage() {
		return _previousPage;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _availableLocales);

		hash = HashUtil.hash(hash, _ddmFormFields);
		hash = HashUtil.hash(hash, _ddmFormLayoutPages);
		hash = HashUtil.hash(hash, _ddmFormRules);
		hash = HashUtil.hash(hash, _defaultLocale);
		hash = HashUtil.hash(hash, _paginationMode);

		return HashUtil.hash(hash, _definitionSchemaVersion);
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDDMFormFields(List<DDMFormField> ddmFormFields) {
		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmFormField.setDDMFormLayout(this);
		}

		_ddmFormFields = ddmFormFields;
	}

	public void setDDMFormLayoutPages(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		_ddmFormLayoutPages = ddmFormLayoutPages;
	}

	public void setDDMFormRules(List<DDMFormRule> ddmFormRules) {
		_ddmFormRules = ddmFormRules;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	public void setDefinitionSchemaVersion(String definitionSchemaVersion) {
		_definitionSchemaVersion = definitionSchemaVersion;
	}

	public void setNextPage(Integer nextPage) {
		_nextPage = nextPage;
	}

	public void setPaginationMode(String paginationMode) {
		_paginationMode = paginationMode;
	}

	public void setPreviousPage(Integer previousPage) {
		_previousPage = previousPage;
	}

	private Set<Locale> _availableLocales = new LinkedHashSet<>();
	private List<DDMFormField> _ddmFormFields = new ArrayList<>();
	private List<DDMFormLayoutPage> _ddmFormLayoutPages = new ArrayList<>();
	private List<DDMFormRule> _ddmFormRules = new ArrayList<>();
	private Locale _defaultLocale;
	private String _definitionSchemaVersion;
	private Integer _nextPage;
	private String _paginationMode;
	private Integer _previousPage;

}