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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Objects;

import javax.portlet.PortletURL;

/**
 * @author Carlos Lancha
 */
public class ViewTypeItemList extends ArrayList<ViewTypeItem> {

	public ViewTypeItemList() {
		_portletURL = null;
		_selectedType = null;
	}

	public ViewTypeItemList(PortletURL portletURL, String selectedType) {
		_portletURL = portletURL;
		_selectedType = selectedType;
	}

	public void add(UnsafeConsumer<ViewTypeItem, Exception> unsafeConsumer) {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		try {
			unsafeConsumer.accept(viewTypeItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(viewTypeItem);
	}

	public ViewTypeItem addCardViewTypeItem() {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		if (Validator.isNotNull(_selectedType)) {
			viewTypeItem.setActive(Objects.equals(_selectedType, "icon"));
		}

		if (_portletURL != null) {
			viewTypeItem.setHref(_portletURL, "displayStyle", "icon");
		}

		viewTypeItem.setIcon("cards2");
		viewTypeItem.setLabel(
			LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "cards"));

		add(viewTypeItem);

		return viewTypeItem;
	}

	public void addCardViewTypeItem(
		UnsafeConsumer<ViewTypeItem, Exception> unsafeConsumer) {

		try {
			unsafeConsumer.accept(addCardViewTypeItem());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public ViewTypeItem addListViewTypeItem() {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		if (Validator.isNotNull(_selectedType)) {
			viewTypeItem.setActive(
				Objects.equals(_selectedType, "descriptive"));
		}

		if (_portletURL != null) {
			viewTypeItem.setHref(_portletURL, "displayStyle", "descriptive");
		}

		viewTypeItem.setIcon("list");
		viewTypeItem.setLabel(
			LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "list"));

		add(viewTypeItem);

		return viewTypeItem;
	}

	public void addListViewTypeItem(
		UnsafeConsumer<ViewTypeItem, Exception> unsafeConsumer) {

		try {
			unsafeConsumer.accept(addListViewTypeItem());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public ViewTypeItem addTableViewTypeItem() {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		if (Validator.isNotNull(_selectedType)) {
			viewTypeItem.setActive(Objects.equals(_selectedType, "list"));
		}

		if (_portletURL != null) {
			viewTypeItem.setHref(_portletURL, "displayStyle", "list");
		}

		viewTypeItem.setIcon("table");
		viewTypeItem.setLabel(
			LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "table"));

		add(viewTypeItem);

		return viewTypeItem;
	}

	public void addTableViewTypeItem(
		UnsafeConsumer<ViewTypeItem, Exception> unsafeConsumer) {

		try {
			unsafeConsumer.accept(addTableViewTypeItem());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final PortletURL _portletURL;
	private final String _selectedType;

}