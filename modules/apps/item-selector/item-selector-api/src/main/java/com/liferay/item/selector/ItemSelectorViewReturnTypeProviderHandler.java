/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector;

import java.util.List;

/**
 * @author Lance Ji
 */
public interface ItemSelectorViewReturnTypeProviderHandler {

	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes(
		ItemSelectorView<?> itemSelectorView);

	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes(
		List<ItemSelectorReturnType> itemSelectorReturnTypes,
		String itemSelectorViewKey);

}