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

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.constants.AssetListFormConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "screen.navigation.entry.order:Integer=25",
	service = ScreenNavigationEntry.class
)
public class AssetListCustomAttributesScreenNavigationEntry
	extends BaseAssetListScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return AssetListFormConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getEntryKey() {
		return AssetListFormConstants.ENTRY_KEY_CUSTOM_ATTRIBUTES;
	}

	@Override
	public String getJspPath() {
		return "/asset_list/custom_user_attributes.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "custom-attributes");
	}

	@Override
	public boolean isVisible(User user, AssetListEntry assetListEntry) {
		if (assetListEntry == null) {
			return false;
		}

		if (assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_DYNAMIC) {

			return true;
		}

		return false;
	}

}