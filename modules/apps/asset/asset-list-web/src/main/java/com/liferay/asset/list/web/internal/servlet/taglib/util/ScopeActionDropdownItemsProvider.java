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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

/**
 * @author Diego Hu
 */
public class ScopeActionDropdownItemsProvider {

	public ScopeActionDropdownItemsProvider(
		EditAssetListDisplayContext editAssetListDisplayContext,
		LiferayPortletRequest liferayPortletRequest) {

		_editAssetListDisplayContext = editAssetListDisplayContext;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		DropdownItemList dropdownItemList = new DropdownItemList();

		List<Group> selectedGroups =
			_editAssetListDisplayContext.getSelectedGroups();

		for (Group group : _editAssetListDisplayContext.getAvailableGroups()) {
			if (!selectedGroups.contains(group)) {
				dropdownItemList.add(
					dropdownItem -> {
						dropdownItem.putData("action", "addRow");
						dropdownItem.putData(
							"groupId", String.valueOf(group.getGroupId()));
						dropdownItem.setLabel(
							group.getScopeDescriptiveName(_themeDisplay));
					});
			}
		}

		dropdownItemList.add(
			dropdownItem -> {
				dropdownItem.putData("action", "selectManageableGroup");
				dropdownItem.putData(
					"groupItemSelectorURL",
					_editAssetListDisplayContext.getGroupItemSelectorURL());
				dropdownItem.putData(
					"selectEventName",
					_editAssetListDisplayContext.getSelectGroupEventName());

				String otherSiteLabelLocalized = LanguageUtil.get(
					_themeDisplay.getLocale(), "other-site-or-asset-library");

				dropdownItem.setLabel(
					otherSiteLabelLocalized + StringPool.TRIPLE_PERIOD);
			});

		return dropdownItemList;
	}

	private final EditAssetListDisplayContext _editAssetListDisplayContext;
	private final ThemeDisplay _themeDisplay;

}