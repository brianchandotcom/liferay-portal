/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector;

import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Roberto Díaz
 */
public interface ItemSelectorReturnTypeResolver
	<T extends ItemSelectorReturnType, S> {

	public Class<T> getItemSelectorReturnTypeClass();

	public Class<S> getModelClass();

	public String getValue(S s, ThemeDisplay themeDisplay) throws Exception;

}