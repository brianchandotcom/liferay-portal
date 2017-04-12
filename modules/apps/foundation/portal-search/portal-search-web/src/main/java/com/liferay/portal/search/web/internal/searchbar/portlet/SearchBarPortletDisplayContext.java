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

package com.liferay.portal.search.web.internal.searchbar.portlet;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDisplayContext {

	public String getKeywords() {
		return _keywords;
	}

	public String getKeywordsParameterName() {
		return _keywordsParameterName;
	}

	public String getScopeParameterString() {
		return _scopeParameterString;
	}

	public boolean isScopeEverythingSelected() {
		return _scopeEverythingSelected;
	}

	public boolean isScopePreferenceEverythingAvailable() {
		return _scopePreferenceEverythingAvailable;
	}

	public boolean isScopePreferenceLetTheUserChoose() {
		return _scopePreferenceLetTheUserChoose;
	}

	public boolean isScopeSiteSelected() {
		return _scopeSiteSelected;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	public void setScopeEverythingSelected(boolean scopeEverythingSelected) {
		_scopeEverythingSelected = scopeEverythingSelected;
	}

	public void setScopeParameterString(String scopeParameterString) {
		_scopeParameterString = scopeParameterString;
	}

	public void setScopePreferenceEverythingAvailable(
		boolean scopePreferenceEverythingAvailable) {

		_scopePreferenceEverythingAvailable =
			scopePreferenceEverythingAvailable;
	}

	public void setScopePreferenceLetTheUserChoose(
		boolean scopePreferenceLetTheUserChoose) {

		_scopePreferenceLetTheUserChoose = scopePreferenceLetTheUserChoose;
	}

	public void setScopeSiteSelected(boolean scopeSiteSelected) {
		_scopeSiteSelected = scopeSiteSelected;
	}

	private String _keywords;
	private String _keywordsParameterName;
	private boolean _scopeEverythingSelected;
	private String _scopeParameterString;
	private boolean _scopePreferenceEverythingAvailable;
	private boolean _scopePreferenceLetTheUserChoose;
	private boolean _scopeSiteSelected;

}