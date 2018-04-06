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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class CreationMenu extends HashMap {

	public CreationMenu() {
		this(null);
	}

	public CreationMenu(HttpServletRequest request) {
		_request = request;

		_favoriteDropdownItemList = new DropdownItemList(_request);
		_primaryDropdownItemList = new DropdownItemList(_request);
		_restDropdownItemList = new DropdownItemList(_request);

		put("primaryItems", _primaryDropdownItemList);
	}

	public void addDropdownItem(Consumer<DropdownItem> consumer) {
		addPrimaryDropdownItem(consumer);
	}

	public void addFavoriteDropdownItem(Consumer<DropdownItem> consumer) {
		DropdownItem dropdownItem = new DropdownItem(_request);

		consumer.accept(dropdownItem);

		_favoriteDropdownItemList.add(dropdownItem);

		put("secondaryItems", _buildSecondaryDropdownItemList());
	}

	public void addPrimaryDropdownItem(Consumer<DropdownItem> consumer) {
		DropdownItem dropdownItem = new DropdownItem(_request);

		consumer.accept(dropdownItem);

		_primaryDropdownItemList.add(dropdownItem);
	}

	public void addRestDropdownItem(Consumer<DropdownItem> consumer) {
		DropdownItem dropdownItem = new DropdownItem(_request);

		consumer.accept(dropdownItem);

		_restDropdownItemList.add(dropdownItem);

		put("secondaryItems", _buildSecondaryDropdownItemList());
	}

	public void setCaption(String caption) {
		if (Validator.isNotNull(_request)) {
			caption = LanguageUtil.get(_request, caption);
		}

		put("caption", caption);
	}

	public void setHelpText(String helpText) {
		if (Validator.isNotNull(_request)) {
			helpText = LanguageUtil.get(_request, helpText);
		}

		put("helpText", helpText);
	}

	public void setViewMoreURL(String viewMoreURL) {
		put("viewMoreURL", viewMoreURL);
	}

	private DropdownItemList _buildSecondaryDropdownItemList() {
		DropdownItemList secondaryDropdownItemList = new DropdownItemList(
			_request);

		if (!_favoriteDropdownItemList.isEmpty()) {
			secondaryDropdownItemList.addGroup(
				dropdownGroupItem -> {
					dropdownGroupItem.setDropdownItemList(
						_favoriteDropdownItemList);
					dropdownGroupItem.setLabel(
						LanguageUtil.get(_request, "favorites"));

					if (!_restDropdownItemList.isEmpty()) {
						dropdownGroupItem.setSeparator(true);
					}
				});
		}

		if (!_restDropdownItemList.isEmpty()) {
			secondaryDropdownItemList.addGroup(
				dropdownGroupItem -> {
					dropdownGroupItem.setDropdownItemList(
						_restDropdownItemList);
				});
		}

		return secondaryDropdownItemList;
	}

	private final DropdownItemList _favoriteDropdownItemList;
	private final DropdownItemList _primaryDropdownItemList;
	private final HttpServletRequest _request;
	private final DropdownItemList _restDropdownItemList;

}