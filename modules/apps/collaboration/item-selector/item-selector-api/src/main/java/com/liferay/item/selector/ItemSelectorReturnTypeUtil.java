/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.item.selector;

import com.liferay.portal.kernel.util.ClassUtil;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorReturnTypeUtil {

	public static ItemSelectorReturnType
		getFirstAvailableItemSelectorReturnType(
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes,
			List<ItemSelectorReturnType> supportedItemSelectorReturnTypes) {

		Iterator<ItemSelectorReturnType> iterator =
			desiredItemSelectorReturnTypes.iterator();

		List<String> supportedItemSelectorReturnTypeNames =
			supportedItemSelectorReturnTypes.stream().map(
				ClassUtil::getClassName).collect(Collectors.toList());

		while (iterator.hasNext()) {
			ItemSelectorReturnType itemSelectorReturnType = iterator.next();

			String className = ClassUtil.getClassName(itemSelectorReturnType);

			if (supportedItemSelectorReturnTypeNames.contains(className)) {
				return itemSelectorReturnType;
			}
		}

		return null;
	}

}