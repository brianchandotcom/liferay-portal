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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

/**
 * @author Michael C. Han
 */
public class ModelSearchSettingsImpl implements ModelSearchSettings {

	public ModelSearchSettingsImpl(String className) {
		_className = className;

		_searchClassNames = new String[] {className};
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return _defaultSelectedFieldNames;
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return _defaultSelectedLocalizedFieldNames;
	}

	@Override
	public String[] getSearchClassNames() {
		return _searchClassNames;
	}

	@Override
	public String getSearchEngineId() {
		return _searchEngineId;
	}

	@Override
	public boolean isCommitImmediately() {
		return _commitImmediately;
	}

	public boolean isSearchResultPermissionFilterSuppressed() {
		return _searchResultPermissionFilterSuppressed;
	}

	@Override
	public boolean isSelectAllLocales() {
		return _selectAllLocales;
	}

	@Override
	public boolean isStagingAware() {
		return _stagingAware;
	}

	public void setCommitImmediately(boolean commitImmediately) {
		_commitImmediately = commitImmediately;
	}

	public void setDefaultSelectedFieldNames(
		String... defaultSelectedFieldNames) {

		_defaultSelectedFieldNames = defaultSelectedFieldNames;
	}

	public void setDefaultSelectedLocalizedFieldNames(
		String... defaultSelectedLocalizedFieldNames) {

		_defaultSelectedLocalizedFieldNames =
			defaultSelectedLocalizedFieldNames;
	}

	public void setSearchClassNames(String... searchClassNames) {
		_searchClassNames = searchClassNames;
	}

	public void setSearchEngineId(String searchEngineId) {
		_searchEngineId = searchEngineId;
	}

	public void setSearchResultPermissionFilterSuppressed(
		boolean searchResultPermissionFilterSuppressed) {

		_searchResultPermissionFilterSuppressed =
			searchResultPermissionFilterSuppressed;
	}

	public void setSelectAllLocales(boolean selectAllLocales) {
		_selectAllLocales = selectAllLocales;
	}

	public void setStagingAware(boolean stagingAware) {
		_stagingAware = stagingAware;
	}

	private final String _className;
	private boolean _commitImmediately;
	private String[] _defaultSelectedFieldNames;
	private String[] _defaultSelectedLocalizedFieldNames;
	private String[] _searchClassNames;
	private String _searchEngineId = SearchEngineHelper.SYSTEM_ENGINE_ID;
	private boolean _searchResultPermissionFilterSuppressed;
	private boolean _selectAllLocales;
	private boolean _stagingAware = true;

}