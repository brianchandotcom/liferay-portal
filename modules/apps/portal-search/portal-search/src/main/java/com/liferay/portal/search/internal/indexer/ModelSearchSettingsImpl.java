/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

/**
 * @author Michael C. Han
 */
public class ModelSearchSettingsImpl implements ModelSearchSettings {

	public ModelSearchSettingsImpl(
		ModelSearchConfigurator<?> modelSearchConfigurator) {

		_className = modelSearchConfigurator.getClassName();
		_companyId = modelSearchConfigurator.getCompanyId();
		_defaultSelectedFieldNames =
			modelSearchConfigurator.getDefaultSelectedFieldNames();
		_defaultSelectedLocalizedFieldNames =
			modelSearchConfigurator.getDefaultSelectedLocalizedFieldNames();
		_permissionAware = modelSearchConfigurator.isPermissionAware();
		_searchClassNames = new String[] {
			modelSearchConfigurator.getClassName()
		};
		_searchResultPermissionFilterSuppressed =
			modelSearchConfigurator.isSearchResultPermissionFilterSuppressed();
		_selectAllLocales = modelSearchConfigurator.isSelectAllLocales();
		_stagingAware = modelSearchConfigurator.isStagingAware();
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
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

	private final String _className;
	private final long _companyId;
	private final String[] _defaultSelectedFieldNames;
	private final String[] _defaultSelectedLocalizedFieldNames;
	private final boolean _permissionAware;
	private final String[] _searchClassNames;
	private final boolean _searchResultPermissionFilterSuppressed;
	private final boolean _selectAllLocales;
	private final boolean _stagingAware;

}