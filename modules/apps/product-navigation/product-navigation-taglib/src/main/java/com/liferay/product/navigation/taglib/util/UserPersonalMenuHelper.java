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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.user.personal.menu.UserPersonalMenuEntry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class UserPersonalMenuHelper {

	public static List<DropdownItem> getDropdownItems(
		HttpServletRequest request) {

		List<List<UserPersonalMenuEntry>> groupedPersonalMenuEntries =
			_userPersonalMenuEntryRegistry.getGroupedUserPersonalMenuEntries();

		int size = groupedPersonalMenuEntries.size();

		return new DropdownItemList() {
			{
				for (int i = 0; i < size; i++) {
					int index = i;

					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getDropdownItems(
									request,
									groupedPersonalMenuEntries.get(index)));

							if (index < (size - 1)) {
								dropdownGroupItem.setSeparator(true);
							}
						});
				}
			}
		};
	}

	@Reference(unbind = "-")
	protected void setUserPersonalMenuEntryRegistry(
		UserPersonalMenuEntryRegistry userPersonalMenuEntryRegistry) {

		_userPersonalMenuEntryRegistry = userPersonalMenuEntryRegistry;
	}

	private static List<DropdownItem> _getDropdownItems(
		HttpServletRequest request,
		List<UserPersonalMenuEntry> userPersonalMenuEntries) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				for (UserPersonalMenuEntry entry : userPersonalMenuEntries) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									entry.getPortletURL(request));
								dropdownItem.setLabel(
									entry.getLabel(themeDisplay.getLocale()));
							}));
				}
			}
		};
	}

	private static UserPersonalMenuEntryRegistry _userPersonalMenuEntryRegistry;

}