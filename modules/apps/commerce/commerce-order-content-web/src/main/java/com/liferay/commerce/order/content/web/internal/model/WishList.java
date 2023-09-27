/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class WishList {

	public WishList(
		String author, String date, int itemsNumber, String title,
		long wishListId) {

		_author = author;
		_date = date;
		_itemsNumber = itemsNumber;
		_title = title;
		_wishListId = wishListId;
	}

	public String getAuthor() {
		return _author;
	}

	public String getDate() {
		return _date;
	}

	public int getItemsNumber() {
		return _itemsNumber;
	}

	public String getTitle() {
		return _title;
	}

	public long getWishListId() {
		return _wishListId;
	}

	private final String _author;
	private final String _date;
	private final int _itemsNumber;
	private final String _title;
	private final long _wishListId;

}