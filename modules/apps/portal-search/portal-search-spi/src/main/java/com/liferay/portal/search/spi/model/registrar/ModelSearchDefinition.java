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

	public void setSearchResultPermissionFilterSuppressed(
		boolean searchResultPermissionFilterSuppressed);

	public void setSelectAllLocales(boolean selectAllLocales);

}