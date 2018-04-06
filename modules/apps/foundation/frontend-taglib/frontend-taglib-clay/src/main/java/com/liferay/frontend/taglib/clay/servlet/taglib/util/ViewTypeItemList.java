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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class ViewTypeItemList extends ArrayList<ViewTypeItem> {

	public ViewTypeItemList() {
		_request = null;
	}

	public ViewTypeItemList(HttpServletRequest request) {
		_request = request;
	}

	public void add(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addCardViewTypeItem(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		viewTypeItem.setIcon("cards2");

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addListViewTypeItem(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		viewTypeItem.setIcon("list");

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addTableViewTypeItem(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		viewTypeItem.setIcon("table");

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	private final HttpServletRequest _request;

}