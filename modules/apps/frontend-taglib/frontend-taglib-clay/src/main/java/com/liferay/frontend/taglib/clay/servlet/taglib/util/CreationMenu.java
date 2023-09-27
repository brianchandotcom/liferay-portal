/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class CreationMenu extends HashMap<String, Object> {

	public CreationMenu() {
		put("primaryItems", _primaryDropdownItems);
	}

	public CreationMenu addDropdownItem(DropdownItem dropdownItem) {
		return addPrimaryDropdownItem(dropdownItem);
	}

	public CreationMenu addDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		addPrimaryDropdownItem(unsafeConsumer);

		return this;
	}

	public CreationMenu addFavoriteDropdownItem(DropdownItem dropdownItem) {
		_favoriteDropdownItems.add(dropdownItem);

		put("secondaryItems", _buildSecondaryDropdownItems());

		return this;
	}

	public CreationMenu addFavoriteDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return addFavoriteDropdownItem(dropdownItem);
	}

	public CreationMenu addPrimaryDropdownItem(DropdownItem dropdownItem) {
		_primaryDropdownItems.add(dropdownItem);

		return this;
	}

	public CreationMenu addPrimaryDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return addPrimaryDropdownItem(dropdownItem);
	}

	public CreationMenu addRestDropdownItem(DropdownItem dropdownItem) {
		_restDropdownItems.add(dropdownItem);

		put("secondaryItems", _buildSecondaryDropdownItems());

		return this;
	}

	public CreationMenu addRestDropdownItem(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return addRestDropdownItem(dropdownItem);
	}

	@Override
	public boolean isEmpty() {
		if (_favoriteDropdownItems.isEmpty() &&
			_primaryDropdownItems.isEmpty() && _restDropdownItems.isEmpty()) {

			return true;
		}

		return super.isEmpty();
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