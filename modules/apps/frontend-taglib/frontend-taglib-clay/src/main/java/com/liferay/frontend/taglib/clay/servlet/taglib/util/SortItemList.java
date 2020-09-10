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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

import java.util.ArrayList;

/**
 * @author Luca Pellizzon
 */
public class SortItemList extends ArrayList<SortItem> {

	public static SortItemList of(SortItem... sortItems) {
		SortItemList sortItemList = new SortItemList();

		for (SortItem sortItem : sortItems) {
			if (sortItem != null) {
				sortItemList.add(sortItem);
			}
		}

		return sortItemList;
	}

	public static SortItemList of(
		UnsafeSupplier<SortItem, Exception>... unsafeSuppliers) {

		SortItemList sortItemList = new SortItemList();

		for (UnsafeSupplier<SortItem, Exception> unsafeSupplier :
				unsafeSuppliers) {

			try {
				SortItem sortItem = unsafeSupplier.get();

				if (sortItem != null) {
					sortItemList.add(sortItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return sortItemList;
	}

	public void add(UnsafeConsumer<SortItem, Exception> unsafeConsumer) {
		SortItem sortItem = new SortItem();

		try {
			unsafeConsumer.accept(sortItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(sortItem);
	}

}