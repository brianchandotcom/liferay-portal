/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.registrar;

import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface ModelSearchDefinition {

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
	 * Allows backwards compatibility to disable permission filtering in search
	 * queries. By default, permission awareness is controlled by the presence
	 * of a ModelResourcePermission implementation for the class. It is
	 * generally not suggested to set this value to false.
	 *
	 * @param permissionAware false if the indexer should not add permission
	 *        clauses to the pre-filter of the search query
	 */
	public void setPermissionAware(boolean permissionAware);

	public void setSearchResultPermissionFilterSuppressed(
		boolean searchResultPermissionFilterSuppressed);

	public void setSelectAllLocales(boolean selectAllLocales);

	public void setStagingAware(boolean stagingAware);

}