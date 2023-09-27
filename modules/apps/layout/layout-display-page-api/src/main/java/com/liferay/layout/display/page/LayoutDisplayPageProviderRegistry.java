/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public interface LayoutDisplayPageProviderRegistry {

	public LayoutDisplayPageProvider<?> getLayoutDisplayPageProviderByClassName(
		String className);

	public LayoutDisplayPageProvider<?>
		getLayoutDisplayPageProviderByURLSeparator(String urlSeparator);

	public List<LayoutDisplayPageProvider<?>> getLayoutDisplayPageProviders();

}