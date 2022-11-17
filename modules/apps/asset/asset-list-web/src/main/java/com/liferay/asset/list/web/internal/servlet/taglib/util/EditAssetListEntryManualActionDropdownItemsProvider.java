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

package com.liferay.asset.list.web.internal.servlet.taglib.util;

import com.liferay.asset.list.web.internal.display.context.EditAssetListDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;

import java.util.List;
import java.util.Map;

/**
 * @author Diego Hu
 */
public class EditAssetListEntryManualActionDropdownItemsProvider {

	public EditAssetListEntryManualActionDropdownItemsProvider(
		EditAssetListDisplayContext editAssetListDisplayContext) {

		_editAssetListDisplayContext = editAssetListDisplayContext;
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		Map<String, Map<String, Object>> manualAddIconDataMap =
			_editAssetListDisplayContext.getManualAddIconDataMap();

		DropdownItemList dropdownItemList = new DropdownItemList();

		for (Map.Entry<String, Map<String, Object>> entry :
				manualAddIconDataMap.entrySet()) {

			Map<String, Object> entryValue = entry.getValue();

			Object entryHref = entryValue.get("href");
			Object entryTitle = entryValue.get("title");
			Object entryType = entryValue.get("type");

			dropdownItemList.add(
				dropdownItem -> {
					dropdownItem.putData("href", entryHref.toString());
					dropdownItem.putData("title", entryTitle.toString());
					dropdownItem.setLabel(entryType.toString());
				});
		}

		return dropdownItemList;
	}

	private final EditAssetListDisplayContext _editAssetListDisplayContext;

}