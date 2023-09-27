/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.web.internal.display.context;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;

import java.util.Locale;
import java.util.Set;

/**
 * @author Drew Brokke
 */
public class EditDisplayContext {

	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public String getBackURL() {
		return _backURL;
	}

	public String getKey() {
		return _key;
	}

	public LocalizedValuesMap getOriginalValuesLocalizedValuesMap() {
		return _originalValuesLocalizedValuesMap;
	}

	public String getPageTitle() {
		return _pageTitle;
	}

	public String getSelectedLanguageId() {
		return _selectedLanguageId;
	}

	public LocalizedValuesMap getValuesLocalizedValuesMap() {
		return _valuesLocalizedValuesMap;
	}

	public boolean isShowOriginalValues() {
		return _showOriginalValues;
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setOriginalValuesLocalizedValuesMap(
		LocalizedValuesMap originalValuesLocalizedValuesMap) {

		_originalValuesLocalizedValuesMap = originalValuesLocalizedValuesMap;
	}

	public void setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
	}

	public void setSelectedLanguageId(String selectedLanguageId) {
		_selectedLanguageId = selectedLanguageId;
	}

	public void setShowOriginalValues(boolean showOriginalValues) {
		_showOriginalValues = showOriginalValues;
	}

	public void setValuesLocalizedValuesMap(
		LocalizedValuesMap valuesLocalizedValuesMap) {

		_valuesLocalizedValuesMap = valuesLocalizedValuesMap;
	}

	private Set<Locale> _availableLocales;
	private String _backURL;
	private String _key;
	private LocalizedValuesMap _originalValuesLocalizedValuesMap;
	private String _pageTitle;
	private String _selectedLanguageId;
	private boolean _showOriginalValues;
	private LocalizedValuesMap _valuesLocalizedValuesMap;

}