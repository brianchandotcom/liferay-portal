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

package com.liferay.document.library.video.internal.util;

import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.servlet.taglib.ui.UIItem;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
public class DLVideoExternalShortcutUIItemsUtil {

	public static void processDropdownItems(List<DropdownItem> dropdownItems) {
		_removeUIItems(
			dropdownItems, dropdownItem -> (String)dropdownItem.get("key"),
			SetUtil.fromArray(
				DLUIItemKeys.CANCEL_CHECKOUT, DLUIItemKeys.CHECKIN,
				DLUIItemKeys.CHECKOUT, DLUIItemKeys.DOWNLOAD,
				DLUIItemKeys.OPEN_IN_MS_OFFICE));
	}

	public static void processUIItems(List<? extends UIItem> uiItems) {
		_removeUIItems(
			uiItems, UIItem::getKey,
			SetUtil.fromArray(
				DLUIItemKeys.CANCEL_CHECKOUT, DLUIItemKeys.CHECKIN,
				DLUIItemKeys.CHECKOUT, DLUIItemKeys.DOWNLOAD,
				DLUIItemKeys.OPEN_IN_MS_OFFICE));
	}

	private static <T> void _removeUIItems(
		List<T> items, Function<T, String> function, Set<String> keys) {

		Iterator<T> iterator = items.iterator();

		while (iterator.hasNext()) {
			T item = iterator.next();

			if (keys.contains(function.apply(item))) {
				iterator.remove();
			}
		}
	}

}