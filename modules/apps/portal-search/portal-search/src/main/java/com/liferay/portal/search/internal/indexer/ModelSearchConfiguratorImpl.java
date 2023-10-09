/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

/**
 * @author Michael C. Han
 */
public class ModelSearchConfiguratorImpl<T extends BaseModel<?>>
	implements ModelSearchConfigurator<T> {

	public ModelSearchConfiguratorImpl(String className) {
		_className = className;
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
	public ModelIndexerWriterContributor<T> getModelIndexerWriterContributor() {
		return (ModelIndexerWriterContributor<T>)_modelIndexerWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
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

	@Override
	public void setDefaultSelectedFieldNames(
		String... defaultSelectedFieldNames) {

		_defaultSelectedFieldNames = defaultSelectedFieldNames;
	}

	@Override
	public void setDefaultSelectedLocalizedFieldNames(
		String... defaultSelectedLocalizedFieldNames) {

		_defaultSelectedLocalizedFieldNames =
			defaultSelectedLocalizedFieldNames;
	}

	@Override
	public void setModelIndexWriteContributor(
		ModelIndexerWriterContributor<?> modelIndexWriterContributor) {

		_modelIndexerWriterContributor = modelIndexWriterContributor;
	}

	@Override
	public void setModelSummaryContributor(
		ModelSummaryContributor modelSummaryContributor) {

		_modelSummaryContributor = modelSummaryContributor;
	}

	@Override
	public void setModelVisibilityContributor(
		ModelVisibilityContributor modelVisibilityContributor) {

		_modelVisibilityContributor = modelVisibilityContributor;
	}

	@Override
	public void setPermissionAware(boolean permissionAware) {
		_permissionAware = permissionAware;
	}

	@Override
	public void setSearchResultPermissionFilterSuppressed(
		boolean searchResultPermissionFilterSuppressed) {

		_searchResultPermissionFilterSuppressed =
			searchResultPermissionFilterSuppressed;
	}

	@Override
	public void setSelectAllLocales(boolean selectAllLocales) {
		_selectAllLocales = selectAllLocales;
	}

	@Override
	public void setStagingAware(boolean stagingAware) {
		_stagingAware = stagingAware;
	}

	private final String _className;
	private String[] _defaultSelectedFieldNames;
	private String[] _defaultSelectedLocalizedFieldNames;
	private ModelIndexerWriterContributor<?> _modelIndexerWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;
	private ModelVisibilityContributor _modelVisibilityContributor;
	private boolean _permissionAware = true;
	private boolean _searchResultPermissionFilterSuppressed;
	private boolean _selectAllLocales;
	private boolean _stagingAware = true;

}