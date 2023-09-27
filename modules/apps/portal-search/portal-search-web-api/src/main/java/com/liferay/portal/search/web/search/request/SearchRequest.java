/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.search.request;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author     André de Oliveira
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
@ProviderType
public interface SearchRequest {

	public void addSearchSettingsContributor(
		SearchSettingsContributor searchSettingsContributor);

	public void removeSearchSettingsContributor(
		SearchSettingsContributor searchSettingsContributor);

	public SearchResponse search();

}