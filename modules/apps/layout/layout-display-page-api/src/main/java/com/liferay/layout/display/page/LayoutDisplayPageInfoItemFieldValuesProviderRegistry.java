/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public interface LayoutDisplayPageInfoItemFieldValuesProviderRegistry {

	public LayoutDisplayPageInfoItemFieldValuesProvider<?>
		getLayoutDisplayPageInfoItemFieldValuesProvider(String className);

	public List<LayoutDisplayPageInfoItemFieldValuesProvider<?>>
		getLayoutDisplayPageInfoItemFieldValuesProviders();

}