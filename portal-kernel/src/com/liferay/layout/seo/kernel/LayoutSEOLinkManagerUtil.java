/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.kernel;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author     Cristina González
 * @deprecated As of Mueller (7.2.x), replaced by {@link LayoutSEOLinkManager}
 */
@Deprecated
public class LayoutSEOLinkManagerUtil {

	public static LayoutSEOLinkManager getLayoutSEOLinkManager() {
		return _layoutSEOLinkManagerSnapshot.get();
	}

	private static final Snapshot<LayoutSEOLinkManager>
		_layoutSEOLinkManagerSnapshot = new Snapshot<>(
			LayoutSEOLinkManagerUtil.class, LayoutSEOLinkManager.class);

}