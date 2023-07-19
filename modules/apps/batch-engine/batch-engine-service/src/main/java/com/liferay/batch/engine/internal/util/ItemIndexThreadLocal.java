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

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Matija Petanjek
 */
public class ItemIndexThreadLocal {

	public static void add(int itemIndex) {
		Queue<Integer> indexQueue = _indexQueue.get();

		indexQueue.add(itemIndex);
	}

	public static void clear() {
		_indexQueue.remove();
	}

	public static int get() {
		Queue<Integer> indexQueue = _indexQueue.get();

		return indexQueue.peek();
	}

	public static int remove() {
		Queue<Integer> indexQueue = _indexQueue.get();

		return indexQueue.remove();
	}

	private static final ThreadLocal<Queue<Integer>> _indexQueue =
		new CentralizedThreadLocal<>(
			ItemIndexThreadLocal.class + "._indexQueue", LinkedList::new);

}