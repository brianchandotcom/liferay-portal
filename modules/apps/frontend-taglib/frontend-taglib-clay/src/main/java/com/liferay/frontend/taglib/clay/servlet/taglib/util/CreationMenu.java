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

import java.util.HashMap;
import java.util.List;

/**
 * @author Carlos Lancha
 */
public class CreationMenu extends HashMap {

	public CreationMenu() {
		put("primaryItems", _primaryDropdownItems);
	}

	public void addDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		addPrimaryDropdownItem(unsafeConsumer);
	}

	public void addFavoriteDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_favoriteDropdownItems.add(dropdownItem);

		put("secondaryItems", _buildSecondaryDropdownItems());
	}

	public void addPrimaryDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_primaryDropdownItems.add(dropdownItem);
	}

	public void addRestDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_restDropdownItems.add(dropdownItem);

		put("secondaryItems", _buildSecondaryDropdownItems());
	}

	public void setCaption(String caption) {
		put("caption", caption);
	}

	public void setHelpText(String helpText) {
		put("helpText", helpText);
	}

	public void setItemsIconAlignment(String itemsIconAlignment) {
		put("itemsIconAlignment", itemsIconAlignment);
	}

	public void setViewMoreURL(String viewMoreURL) {
		put("viewMoreURL", viewMoreURL);
	}

	private List<DropdownItem> _buildSecondaryDropdownItems() {
		DropdownItemList secondaryDropdownItemList = new DropdownItemList();

		if (!_favoriteDropdownItems.isEmpty()) {
			secondaryDropdownItemList.addGroup(
				dropdownGroupItem -> {
					dropdownGroupItem.setDropdownItems(_favoriteDropdownItems);
					dropdownGroupItem.setLabel(
						LanguageUtil.get(
							LocaleUtil.getMostRelevantLocale(), "favorites"));

					if (!_restDropdownItems.isEmpty()) {
						dropdownGroupItem.setSeparator(true);
					}
				});
		}

		if (!_restDropdownItems.isEmpty()) {
			secondaryDropdownItemList.addGroup(
				dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
					_restDropdownItems));
		}

		return secondaryDropdownItemList;
	}

	private final List<DropdownItem> _favoriteDropdownItems =
		new DropdownItemList();
	private final List<DropdownItem> _primaryDropdownItems =
		new DropdownItemList();
	private final List<DropdownItem> _restDropdownItems =
		new DropdownItemList();

}