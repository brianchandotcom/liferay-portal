/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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