/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item;

import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Cristina González
 */
public interface ContentDashboardItemFactory<T> {

	public ContentDashboardItem<T> create(long classPK) throws PortalException;

	public ContentDashboardItemSubtypeFactory
		getContentDashboardItemSubtypeFactory();

}