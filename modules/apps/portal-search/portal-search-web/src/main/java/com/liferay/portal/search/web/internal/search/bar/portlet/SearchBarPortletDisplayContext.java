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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDisplayContext {

	public String getCurrentSiteSearchScopeParameterString() {
		return _currentSiteSearchScopeParameterString;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public String getEverythingSearchScopeParameterString() {
		return _everythingSearchScopeParameterString;
	}

	public String getInputPlaceholder() {
		return _inputPlaceholder;
	}

	public String getKeywords() {
		return _keywords;
	}

	public String getKeywordsParameterName() {
		return _keywordsParameterName;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	public String getScopeParameterName() {
		return _scopeParameterName;
	}

	public String getScopeParameterValue() {
		return _scopeParameterValue;
	}

	public SearchBarPortletInstanceConfiguration
		getSearchBarPortletInstanceConfiguration() {

		return _searchBarPortletInstanceConfiguration;
	}

	public String getSearchURL() {
		return _searchURL;
	}

	public boolean isAvailableEverythingSearchScope() {
		return _availableEverythingSearchScope;
	}

	public boolean isDestinationUnreachable() {
		return _destinationUnreachable;
	}

	public boolean isDisplayWarningIgnoredConfiguration() {
		return _displayWarningIgnoredConfiguration;
	}

	public boolean isEmptySearchEnabled() {
		return _emptySearchEnabled;
	}

	public boolean isLetTheUserChooseTheSearchScope() {
		return _letTheUserChooseTheSearchScope;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public boolean isSelectedCurrentSiteSearchScope() {
		return _selectedCurrentSiteSearchScope;
	}

	public boolean isSelectedEverythingSearchScope() {
		return _selectedEverythingSearchScope;
	}

	public void setAvailableEverythingSearchScope(
		boolean availableEverythingSearchScope) {

		_availableEverythingSearchScope = availableEverythingSearchScope;
	}

	public void setCurrentSiteSearchScopeParameterString(
		String searchScopeCurrentSiteParameterString) {

		_currentSiteSearchScopeParameterString =
			searchScopeCurrentSiteParameterString;
	}

	public void setDestinationUnreachable(boolean destinationUnreachable) {
		_destinationUnreachable = destinationUnreachable;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setDisplayWarningIgnoredConfiguration(
		boolean displayWarningIgnoredConfiguration) {

		_displayWarningIgnoredConfiguration =
			displayWarningIgnoredConfiguration;
	}

	public void setEmptySearchEnabled(boolean emptySearchEnabled) {
		_emptySearchEnabled = emptySearchEnabled;
	}

	public void setEverythingSearchScopeParameterString(
		String searchScopeEverythingParameterString) {

		_everythingSearchScopeParameterString =
			searchScopeEverythingParameterString;
	}

	public void setInputPlaceholder(String inputPlaceholder) {
		_inputPlaceholder = inputPlaceholder;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	public void setLetTheUserChooseTheSearchScope(
		boolean letTheUserChooseTheSearchScope) {

		_letTheUserChooseTheSearchScope = letTheUserChooseTheSearchScope;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setScopeParameterName(String scopeParameterName) {
		_scopeParameterName = scopeParameterName;
	}

	public void setScopeParameterValue(String scopeParameterValue) {
		_scopeParameterValue = scopeParameterValue;
	}

	public void setSearchBarPortletInstanceConfiguration(
		SearchBarPortletInstanceConfiguration
			searchBarPortletInstanceConfiguration) {

		_searchBarPortletInstanceConfiguration =
			searchBarPortletInstanceConfiguration;
	}

	public void setSearchURL(String searchURL) {
		_searchURL = searchURL;
	}

	public void setSelectedCurrentSiteSearchScope(
		boolean selectedCurrentSiteSearchScope) {

		_selectedCurrentSiteSearchScope = selectedCurrentSiteSearchScope;
	}

	public void setSelectedEverythingSearchScope(
		boolean selectedEverythingSearchScope) {

		_selectedEverythingSearchScope = selectedEverythingSearchScope;
	}

	private boolean _availableEverythingSearchScope;
	private String _currentSiteSearchScopeParameterString;
	private boolean _destinationUnreachable;
	private long _displayStyleGroupId;
	private boolean _displayWarningIgnoredConfiguration;
	private boolean _emptySearchEnabled;
	private String _everythingSearchScopeParameterString;
	private String _inputPlaceholder;
	private String _keywords;
	private String _keywordsParameterName;
	private boolean _letTheUserChooseTheSearchScope;
	private String _paginationStartParameterName;
	private boolean _renderNothing;
	private String _scopeParameterName;
	private String _scopeParameterValue;
	private SearchBarPortletInstanceConfiguration
		_searchBarPortletInstanceConfiguration;
	private String _searchURL;
	private boolean _selectedCurrentSiteSearchScope;
	private boolean _selectedEverythingSearchScope;

}