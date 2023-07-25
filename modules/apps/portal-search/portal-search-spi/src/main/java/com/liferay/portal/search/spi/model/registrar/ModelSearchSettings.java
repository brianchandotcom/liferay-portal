/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.registrar;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface ModelSearchSettings {

	public String getClassName();

	public String[] getDefaultSelectedFieldNames();

	public String[] getDefaultSelectedLocalizedFieldNames();

	public String[] getSearchClassNames();

	public boolean isCommitImmediately();

	public boolean isPermissionAware();

	public boolean isSearchResultPermissionFilterSuppressed();

	public boolean isSelectAllLocales();

	public boolean isStagingAware();

}