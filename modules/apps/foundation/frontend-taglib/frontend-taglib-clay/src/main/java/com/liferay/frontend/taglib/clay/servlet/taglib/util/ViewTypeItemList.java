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

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Carlos Lancha
 */
public class ViewTypeItemList extends ArrayList<ViewTypeItem> {

	public void add(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addCardViewType(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		viewTypeItem.setIcon("cards2");

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addListViewType(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		viewTypeItem.setIcon("list");

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addTableViewType(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		viewTypeItem.setIcon("table");

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

}