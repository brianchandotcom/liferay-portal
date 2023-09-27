/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.web.internal.util;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alejandro Tardín
 */
public class ItemSelectorKeyUtil {

	public static String getItemSelectorCriterionKey(
		Class<? extends ItemSelectorCriterion> clazz) {

		return _getKey(clazz, ItemSelectorCriterion.class.getSimpleName());
	}

	public static String getItemSelectorReturnTypeKey(
		Class<? extends ItemSelectorReturnType> clazz) {

		return _getKey(clazz, ItemSelectorReturnType.class.getSimpleName());
	}

	private static String _getKey(Class<?> clazz, String suffix) {
		String key = _itemSelectorKeysMap.get(clazz.getName());

		if (key == null) {
			key = StringUtil.lowerCase(
				StringUtil.removeSubstring(clazz.getSimpleName(), suffix));

			if (_itemSelectorKeysMap.containsValue(key)) {
				key = clazz.getName();
			}

			String oldKey = _itemSelectorKeysMap.putIfAbsent(
				clazz.getName(), key);

			if (oldKey != null) {
				key = oldKey;
			}
		}

		return key;
	}

	private static final Map<String, String> _itemSelectorKeysMap =
		new ConcurrentHashMap<>();

}