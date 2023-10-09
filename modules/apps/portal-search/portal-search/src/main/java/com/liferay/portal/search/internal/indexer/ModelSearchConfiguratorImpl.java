/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

/**
 * @author Michael C. Han
 */
public class ModelSearchConfiguratorImpl<T extends BaseModel<?>>
	implements ModelSearchConfigurator<T> {

	public ModelSearchConfiguratorImpl(String className) {
		_className = className;

		_modelSearchSettingsImpl = new ModelSearchSettingsImpl(className);
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public ModelIndexerWriterContributor<T> getModelIndexerWriterContributor() {
		return (ModelIndexerWriterContributor<T>)_modelIndexerWriterContributor;
	}

	@Override
	public ModelSearchSettings getModelSearchSettings() {
		return _modelSearchSettingsImpl;
	}

	@Override
	public ModelSummaryContributor getModelSummaryBuilder() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	@Override
	public void setDefaultSelectedFieldNames(
		String... defaultSelectedFieldNames) {

		_modelSearchSettingsImpl.setDefaultSelectedFieldNames(
			defaultSelectedFieldNames);
	}

	@Override
	public void setDefaultSelectedLocalizedFieldNames(
		String... defaultSelectedLocalizedFieldNames) {

		_modelSearchSettingsImpl.setDefaultSelectedLocalizedFieldNames(
			defaultSelectedLocalizedFieldNames);
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
		_modelSearchSettingsImpl.setPermissionAware(permissionAware);
	}

	@Override
	public void setSearchResultPermissionFilterSuppressed(
		boolean searchResultPermissionFilterSuppressed) {

		_modelSearchSettingsImpl.setSearchResultPermissionFilterSuppressed(
			searchResultPermissionFilterSuppressed);
	}

	@Override
	public void setSelectAllLocales(boolean selectAllLocales) {
		_modelSearchSettingsImpl.setSelectAllLocales(selectAllLocales);
	}

	@Override
	public void setStagingAware(boolean stagingAware) {
		_modelSearchSettingsImpl.setStagingAware(stagingAware);
	}

	private final String _className;
	private ModelIndexerWriterContributor<?> _modelIndexerWriterContributor;
	private final ModelSearchSettingsImpl _modelSearchSettingsImpl;
	private ModelSummaryContributor _modelSummaryContributor;
	private ModelVisibilityContributor _modelVisibilityContributor;

}