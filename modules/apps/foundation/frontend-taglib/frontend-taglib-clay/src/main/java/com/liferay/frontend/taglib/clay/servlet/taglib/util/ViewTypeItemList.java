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

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class ViewTypeItemList extends ArrayList<ViewTypeItem> {

	public ViewTypeItemList() {
		_request = null;
		_portletURL = null;
		_selectedType = null;
	}

	public ViewTypeItemList(
		HttpServletRequest request, PortletURL portletURL,
		String selectedType) {

		_request = request;
		_portletURL = portletURL;
		_selectedType = selectedType;
	}

	public void add(Consumer<ViewTypeItem> consumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		consumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public ViewTypeItem addCardViewTypeItem() {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		if (Validator.isNotNull(_selectedType)) {
			viewTypeItem.setActive(Objects.equals(_selectedType, "icon"));
		}

		if (Validator.isNotNull(_portletURL)) {
			viewTypeItem.setHref(_portletURL, "displayStyle", "icon");
		}

		viewTypeItem.setIcon("cards2");
		viewTypeItem.setLabel("cards");

		add(viewTypeItem);

		return viewTypeItem;
	}

	public void addCardViewTypeItem(Consumer<ViewTypeItem> consumer) {
		consumer.accept(addCardViewTypeItem());
	}

	public ViewTypeItem addListViewTypeItem() {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		if (Validator.isNotNull(_selectedType)) {
			viewTypeItem.setActive(
				Objects.equals(_selectedType, "descriptive"));
		}

		if (Validator.isNotNull(_portletURL)) {
			viewTypeItem.setHref(_portletURL, "displayStyle", "descriptive");
		}

		viewTypeItem.setIcon("list");
		viewTypeItem.setLabel("list");

		add(viewTypeItem);

		return viewTypeItem;
	}

	public void addListViewTypeItem(Consumer<ViewTypeItem> consumer) {
		consumer.accept(addListViewTypeItem());
	}

	public ViewTypeItem addTableViewTypeItem() {
		ViewTypeItem viewTypeItem = new ViewTypeItem(_request);

		if (Validator.isNotNull(_selectedType)) {
			viewTypeItem.setActive(Objects.equals(_selectedType, "list"));
		}

		if (Validator.isNotNull(_portletURL)) {
			viewTypeItem.setHref(_portletURL, "displayStyle", "list");
		}

		viewTypeItem.setIcon("table");
		viewTypeItem.setLabel("table");

		add(viewTypeItem);

		return viewTypeItem;
	}

	public void addTableViewTypeItem(Consumer<ViewTypeItem> consumer) {
		consumer.accept(addTableViewTypeItem());
	}

	private final PortletURL _portletURL;
	private final HttpServletRequest _request;
	private final String _selectedType;

}