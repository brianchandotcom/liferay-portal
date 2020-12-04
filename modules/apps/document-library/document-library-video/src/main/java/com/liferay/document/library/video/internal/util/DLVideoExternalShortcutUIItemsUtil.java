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
import com.liferay.portal.kernel.servlet.taglib.ui.UIItem;

import java.util.List;

/**
 * @author Iván Zaera
 * @author Alejandro Tardín
 */
public class DLVideoExternalShortcutUIItemsUtil {

	public static void processUIItems(List<? extends UIItem> uiItems) {
		_removeUIItem(uiItems, DLUIItemKeys.CANCEL_CHECKOUT);
		_removeUIItem(uiItems, DLUIItemKeys.CHECKIN);
		_removeUIItem(uiItems, DLUIItemKeys.CHECKOUT);
		_removeUIItem(uiItems, DLUIItemKeys.DOWNLOAD);
		_removeUIItem(uiItems, DLUIItemKeys.OPEN_IN_MS_OFFICE);
	}

	private static int _getIndex(List<? extends UIItem> uiItems, String key) {
		for (int i = 0; i < uiItems.size(); i++) {
			UIItem uiItem = uiItems.get(i);

			if (key.equals(uiItem.getKey())) {
				return i;
			}
		}

		return -1;
	}

	private static void _removeUIItem(
		List<? extends UIItem> uiItems, String key) {

		int index = _getIndex(uiItems, key);

		if (index != -1) {
			uiItems.remove(index);
		}
	}

}