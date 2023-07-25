/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

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
	public boolean isCommitImmediately() {
		return _commitImmediately;
	}

	@Override
	public boolean isPermissionAware() {
		return _permissionAware;
	}

	@Override
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

	public void setPermissionAware(boolean permissionAware) {
		_permissionAware = permissionAware;
	}

	public void setSearchClassNames(String... searchClassNames) {
		_searchClassNames = searchClassNames;
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
	private boolean _permissionAware = true;
	private String[] _searchClassNames;
	private boolean _searchResultPermissionFilterSuppressed;
	private boolean _selectAllLocales;
	private boolean _stagingAware = true;

}