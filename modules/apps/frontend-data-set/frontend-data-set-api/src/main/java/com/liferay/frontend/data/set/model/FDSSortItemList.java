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

package com.liferay.frontend.data.set.model;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

import java.util.ArrayList;

/**
 * @author Luca Pellizzon
 */
public class FDSSortItemList extends ArrayList<FDSSortItem> {

	public static FDSSortItemList of(FDSSortItem... fdsSortItems) {
		FDSSortItemList fdsSortItemList = new FDSSortItemList();

		for (FDSSortItem fdsSortItem : fdsSortItems) {
			if (fdsSortItem != null) {
				fdsSortItemList.add(fdsSortItem);
			}
		}

		return fdsSortItemList;
	}

	public static FDSSortItemList of(
		UnsafeSupplier<FDSSortItem, Exception>... unsafeSuppliers) {

		FDSSortItemList fdsSortItemList = new FDSSortItemList();

		for (UnsafeSupplier<FDSSortItem, Exception> unsafeSupplier :
				unsafeSuppliers) {

			try {
				FDSSortItem fdsSortItem = unsafeSupplier.get();

				if (fdsSortItem != null) {
					fdsSortItemList.add(fdsSortItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return fdsSortItemList;
	}

	public void add(UnsafeConsumer<FDSSortItem, Exception> unsafeConsumer) {
		FDSSortItem fdsSortItem = new FDSSortItem();

		try {
			unsafeConsumer.accept(fdsSortItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(fdsSortItem);
	}

}