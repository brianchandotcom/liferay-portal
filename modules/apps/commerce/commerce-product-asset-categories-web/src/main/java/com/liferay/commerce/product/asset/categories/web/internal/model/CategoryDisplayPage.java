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

package com.liferay.commerce.product.asset.categories.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class CategoryDisplayPage {

	public CategoryDisplayPage(
		long categoryDisplayPageId, String categoryName, String layout) {

		_categoryDisplayPageId = categoryDisplayPageId;
		_categoryName = categoryName;
		_layout = layout;
	}

	public long getCategoryDisplayPageId() {
		return _categoryDisplayPageId;
	}

	public String getCategoryName() {
		return _categoryName;
	}

	public String getLayout() {
		return _layout;
	}

	private final long _categoryDisplayPageId;
	private final String _categoryName;
	private final String _layout;

}