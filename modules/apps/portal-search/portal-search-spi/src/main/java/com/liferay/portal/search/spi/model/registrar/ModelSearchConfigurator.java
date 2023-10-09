/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.registrar;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface ModelSearchConfigurator<T extends BaseModel<?>> {

	public String getClassName();

	public String[] getDefaultSelectedFieldNames();

	public String[] getDefaultSelectedLocalizedFieldNames();

	public ModelIndexerWriterContributor<T> getModelIndexerWriterContributor();

	public ModelSummaryContributor getModelSummaryBuilder();

	public ModelVisibilityContributor getModelVisibilityContributor();

	public boolean isPermissionAware();

	public boolean isSearchResultPermissionFilterSuppressed();

	public boolean isSelectAllLocales();

	public boolean isStagingAware();

	public void setDefaultSelectedFieldNames(
		String... defaultSelectedFieldNames);

	public void setDefaultSelectedLocalizedFieldNames(
		String... defaultSelectedLocalizedFieldNames);

	public void setModelIndexWriteContributor(
		ModelIndexerWriterContributor<?> modelIndexWriterContributor);

	public void setModelSummaryContributor(
		ModelSummaryContributor modelSummaryContributor);

	public void setModelVisibilityContributor(
		ModelVisibilityContributor modelVisibilityContributor);

	/**
	 * See LPS-192313.
	 */
	public void setPermissionAware(boolean permissionAware);

	public void setSearchResultPermissionFilterSuppressed(
		boolean searchResultPermissionFilterSuppressed);

	public void setSelectAllLocales(boolean selectAllLocales);

	public void setStagingAware(boolean stagingAware);

}