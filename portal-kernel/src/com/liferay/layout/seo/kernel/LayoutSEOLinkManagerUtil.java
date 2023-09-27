/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.kernel;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author     Cristina González
 * @deprecated As of Mueller (7.2.x), replaced by {@link LayoutSEOLinkManager}
 */
@Deprecated
public class LayoutSEOLinkManagerUtil {

	public static LayoutSEOLinkManager getLayoutSEOLinkManager() {
		return _layoutSEOLinkManager;
	}

	private static volatile LayoutSEOLinkManager _layoutSEOLinkManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			LayoutSEOLinkManager.class, LayoutSEOLinkManagerUtil.class,
			"_layoutSEOLinkManager", false);

}