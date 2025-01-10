/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.configuration;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Keven Leone
 */
public class MarketplaceConfigurationValues {

	public static final String MARKETPLACE_CLIENT_ID = PropsUtil.get(
		PropsKeys.MARKETPLACE_CLIENT_ID);

	public static final String MARKETPLACE_REDIRECT = PropsUtil.get(
		PropsKeys.MARKETPLACE_REDIRECT);

	public static final String MARKETPLACE_URL = PropsUtil.get(
		PropsKeys.MARKETPLACE_URL);

}