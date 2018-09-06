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

package com.liferay.asset.list.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.asset.list.constants.AssetListFormConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class AssetListDetailsScreenNavigationEntry
	extends BaseAssetListScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return AssetListFormConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getEntryKey() {
		return AssetListFormConstants.ENTRY_KEY_DETAILS;
	}

	@Override
	public String getJspPath() {
		return "/asset_list/details.jsp";
	}

}