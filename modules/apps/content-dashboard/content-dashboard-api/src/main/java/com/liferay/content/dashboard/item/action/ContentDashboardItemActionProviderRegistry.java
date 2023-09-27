/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.action;

import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;

import java.util.List;

/**
 * @author Cristina González
 */
public interface ContentDashboardItemActionProviderRegistry {

	public ContentDashboardItemActionProvider
		getContentDashboardItemActionProvider(
			String className, ContentDashboardItemAction.Type type);

	public List<ContentDashboardItemActionProvider>
		getContentDashboardItemActionProviders(
			String className, ContentDashboardItemAction.Type... types);

}