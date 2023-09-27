/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 */
@ProviderType
public interface SiteInitializerRegistry {

	public SiteInitializer getSiteInitializer(String key);

	public List<SiteInitializer> getSiteInitializers(long companyId);

	public List<SiteInitializer> getSiteInitializers(
		long companyId, boolean activeOnly);

}