/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.taglib.internal.info.display.contributor;

import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Alejandro Tardín
 */
public class LayoutDisplayPageProviderRegistryUtil {

	public static LayoutDisplayPageProviderRegistry
		getLayoutDisplayPageProviderRegistry() {

		return _layoutDisplayPageProviderRegistrySnapshot.get();
	}

	private static final Snapshot<LayoutDisplayPageProviderRegistry>
		_layoutDisplayPageProviderRegistrySnapshot = new Snapshot<>(
			LayoutDisplayPageProviderRegistryUtil.class,
			LayoutDisplayPageProviderRegistry.class);

}