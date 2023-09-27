/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.test;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Roberto Díaz
 */
public class TestItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<TestItemSelectorReturnType, String> {

	@Override
	public Class<TestItemSelectorReturnType> getItemSelectorReturnTypeClass() {
		return TestItemSelectorReturnType.class;
	}

	@Override
	public Class<String> getModelClass() {
		return String.class;
	}

	@Override
	public String getValue(String s, ThemeDisplay themeDisplay)
		throws Exception {

		return "Success Value";
	}

}