/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.action;

import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;

import java.util.List;

/**
 * @author Stefan Tanasie
 */
public interface ContentDashboardItemVersionActionProviderRegistry {

	public List<ContentDashboardItemVersionActionProvider>
		getContentDashboardItemVersionActionProviders(String className);

}