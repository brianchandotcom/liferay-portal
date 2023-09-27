/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.type;

import java.util.Collection;

/**
 * @author Cristina González
 */
public interface ContentDashboardItemSubtypeFactoryRegistry {

	public Collection<String> getClassNames();

	public ContentDashboardItemSubtypeFactory
		getContentDashboardItemSubtypeFactory(String className);

}