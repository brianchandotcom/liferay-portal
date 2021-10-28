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