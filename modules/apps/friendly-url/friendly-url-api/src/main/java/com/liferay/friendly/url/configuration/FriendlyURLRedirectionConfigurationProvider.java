/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Mijarra
 */
@ProviderType
public interface FriendlyURLRedirectionConfigurationProvider {

	public FriendlyURLRedirectionConfiguration
		getCompanyFriendlyURLRedirectionConfiguration(long companyId);

	public FriendlyURLRedirectionConfiguration
		getSystemFriendlyURLRedirectionConfiguration();

}