/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.configuration;

/**
 * @author Keven Leone
 */
public class MarketplaceConfigurationValues {

	public static final String MARKETPLACE_CLIENT_ID =
		MarketplaceConfigurationUtil.getConfiguration("marketplace.client.id");

	public static final String MARKETPLACE_REDIRECT =
		MarketplaceConfigurationUtil.getConfiguration("marketplace.redirect");

	public static final String MARKETPLACE_URL =
		MarketplaceConfigurationUtil.getConfiguration("marketplace.url");

}