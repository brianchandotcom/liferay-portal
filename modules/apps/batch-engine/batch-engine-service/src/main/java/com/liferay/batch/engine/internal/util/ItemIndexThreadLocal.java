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

package com.liferay.batch.engine.internal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matija Petanjek
 */
public class ItemIndexThreadLocal {

	public static int get(Object item) {
		Map<Object, Integer> itemIndexMap = _itemIndexMap.get();

		return itemIndexMap.get(item);
	}

	public static void put(Object item, int itemIndex) {
		Map<Object, Integer> itemIndexMap = _itemIndexMap.get();

		itemIndexMap.put(item, itemIndex);
	}

	public static void remove() {
		_itemIndexMap.remove();
	}

	private static final ThreadLocal<Map<Object, Integer>> _itemIndexMap =
		new CentralizedThreadLocal<>(
			ItemIndexThreadLocal.class + "._itemIndexMap", HashMap::new);

}