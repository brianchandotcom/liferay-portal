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

package com.liferay.item.selector.taglib;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Set;

/**
 * @author Roberto Díaz
 */
public enum ReturnType implements ItemSelectorReturnType {

	BASE_64 {
		@Override
		public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay) {
			return StringPool.BLANK;
		}
	},
	URL {
		@Override
		public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
			throws Exception {

			return DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
		}
	};

	public static ReturnType parse(
		ItemSelectorReturnType itemSelectorReturnType) {

		if (BASE_64.name().equals(itemSelectorReturnType.getName())) {
			return BASE_64;
		}

		if (URL.name().equals(itemSelectorReturnType.getName())) {
			return URL;
		}

		throw new IllegalArgumentException(
			"Invalid itemSelectorReturnType " +
				itemSelectorReturnType.getName());
	}

	public static ReturnType parseFirst(Set<ItemSelectorReturnType> values)
		throws Exception {

		for (ItemSelectorReturnType value : values) {
			try {
				return parse(value);
			}
			catch (IllegalArgumentException iae) {
			}
		}

		throw new IllegalArgumentException("Invalid values " + values);
	}

	@Override
	public String getName() {
		return name();
	}

	public abstract String getValue(
		FileEntry fileEntry, ThemeDisplay themeDisplay) throws Exception;

}