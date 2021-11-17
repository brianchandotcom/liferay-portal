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
public class FrontendDataSetSortItemList
	extends ArrayList<FrontendDataSetSortItem> {

	public static FrontendDataSetSortItemList of(
		FrontendDataSetSortItem... frontendDataSetSortItems) {

		FrontendDataSetSortItemList frontendDataSetSortItemList =
			new FrontendDataSetSortItemList();

		for (FrontendDataSetSortItem frontendDataSetSortItem :
				frontendDataSetSortItems) {

			if (frontendDataSetSortItem != null) {
				frontendDataSetSortItemList.add(frontendDataSetSortItem);
			}
		}

		return frontendDataSetSortItemList;
	}

	public static FrontendDataSetSortItemList of(
		UnsafeSupplier<FrontendDataSetSortItem, Exception>... unsafeSuppliers) {

		FrontendDataSetSortItemList frontendDataSetSortItemList =
			new FrontendDataSetSortItemList();

		for (UnsafeSupplier<FrontendDataSetSortItem, Exception> unsafeSupplier :
				unsafeSuppliers) {

			try {
				FrontendDataSetSortItem frontendDataSetSortItem =
					unsafeSupplier.get();

				if (frontendDataSetSortItem != null) {
					frontendDataSetSortItemList.add(frontendDataSetSortItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return frontendDataSetSortItemList;
	}

	public void add(
		UnsafeConsumer<FrontendDataSetSortItem, Exception> unsafeConsumer) {

		FrontendDataSetSortItem frontendDataSetSortItem =
			new FrontendDataSetSortItem();

		try {
			unsafeConsumer.accept(frontendDataSetSortItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(frontendDataSetSortItem);
	}

}