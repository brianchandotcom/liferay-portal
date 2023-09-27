/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector;

/**
 * @author Lance Ji
 */
public interface ItemSelectorReturnTypeResolverHandler {

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver(
			Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass,
			Class<?> modelClass);

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver(
			ItemSelectorCriterion itemSelectorCriterion,
			ItemSelectorView<?> itemSelectorView, Class<?> modelClass);

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver(
			String itemSelectorReturnTypeClassName, String modelClassName);

}