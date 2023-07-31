/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.HashMap;

/**
 * @author Stefan Tanasie
 */
public class IconItem extends HashMap<String, Object> {

	public static IconItem of(String symbol, String title) {
		IconItem iconItem = new IconItem();

		iconItem.setSymbol(symbol);
		iconItem.setTitle(title);

		return iconItem;
	}

	public void setSymbol(String symbol) {
		put("symbol", symbol);
	}

	public void setTitle(String title) {
		put("title", title);
	}

}