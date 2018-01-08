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

package com.liferay.frontend.taglib.servlet.taglib.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Eudaldo Alonso
 */
public class AddMenuKeys {

	public static final int MAX_ITEMS = Integer.MAX_VALUE;

	public static String getAddMenuTypeLabel(AddMenuType addMenuType) {
		if (addMenuType == AddMenuType.FAVORITE) {
			return "favorite";
		}
		else if (addMenuType == AddMenuType.PRIMARY) {
			return "primary";
		}
		else if (addMenuType == AddMenuType.RECENT) {
			return "recent";
		}
		else if (addMenuType == AddMenuType.DEFAULT) {
			return "default";
		}

		return StringPool.BLANK;
	}

	public enum AddMenuType {

		DEFAULT, FAVORITE, PRIMARY, RECENT

	}

}