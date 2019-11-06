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

package com.liferay.depot.web.internal.servlet.taglib.util;

import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class DepotActionDropdownItemsProvider {

	public DepotActionDropdownItemsProvider(
		Group group, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_group = group;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				if (_hasUpdatePermission()) {
					add(
						dropdownItem -> {
							dropdownItem.setHref(
								DepotEntryURLUtil.getEditDepotEntryPortletURL(
									_group, _themeDisplay.getURLCurrent(),
									_liferayPortletRequest));

							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						});
				}

				if (_hasDeletePermission()) {
					add(
						dropdownItem -> {
							ActionURL deleteDepotEntryActionURL =
								DepotEntryURLUtil.getDeleteDepotEntryActionURL(
									_group.getClassPK(),
									_themeDisplay.getURLCurrent(),
									_liferayPortletResponse);

							dropdownItem.putData("action", "deleteDepotEntry");

							dropdownItem.putData(
								"deleteDepotEntryURL",
								deleteDepotEntryActionURL.toString());

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						});
				}
			}
		};
	}

	private boolean _hasDeletePermission() {
		try {
			if (!GroupPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(), _group,
					ActionKeys.DELETE)) {

				return false;
			}

			return true;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private boolean _hasUpdatePermission() {
		try {
			return GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _group,
				ActionKeys.UPDATE);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private final Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}