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

package com.liferay.product.navigation.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.user.personal.menu.UserPersonalMenuEntry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UserPersonalMenuDropdownItemsProvider {

	public UserPersonalMenuDropdownItemsProvider(HttpServletRequest request) {
		_request = request;
	}

	public List<DropdownItem> getDropdownItems() {
		List<List<UserPersonalMenuEntry>> groupedPersonalMenuEntries =
			UserPersonalMenuEntryRegistryUtil.
				getGroupedUserPersonalMenuEntries();

		int size = groupedPersonalMenuEntries.size();

		return new DropdownItemList() {
			{
				for (int i = 0; i < size; i++) {
					int index = i;

					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getDropdownItems(
									groupedPersonalMenuEntries.get(index)));

							if (index < (size - 1)) {
								dropdownGroupItem.setSeparator(true);
							}
						});
				}
			}
		};
	}

	private List<DropdownItem> _getDropdownItems(
		List<UserPersonalMenuEntry> userPersonalMenuEntries) {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		return new DropdownItemList() {
			{
				for (UserPersonalMenuEntry userPersonalMenuEntry :
						userPersonalMenuEntries) {

					try {
						if (!userPersonalMenuEntry.isShow(permissionChecker)) {
							continue;
						}

						add(
							SafeConsumer.ignore(
								dropdownItem -> {
									dropdownItem.setHref(
										userPersonalMenuEntry.getPortletURL(
											_request));
									dropdownItem.setLabel(
										userPersonalMenuEntry.getLabel(
											themeDisplay.getLocale()));
								}));
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(pe, pe);
						}
					}
				}
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPersonalMenuDropdownItemsProvider.class);

	private final HttpServletRequest _request;

}