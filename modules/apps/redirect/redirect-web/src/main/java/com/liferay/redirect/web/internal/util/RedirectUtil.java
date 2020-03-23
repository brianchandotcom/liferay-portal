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

package com.liferay.redirect.web.internal.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;

import java.util.TreeMap;

/**
 * @author Alejandro Tardín
 */
public class RedirectUtil {

	public static String getGroupBaseURL(ThemeDisplay themeDisplay) {
		StringBuilder groupBaseURL = new StringBuilder();

		groupBaseURL.append(themeDisplay.getPortalURL());

		Group group = themeDisplay.getScopeGroup();

		LayoutSet layoutSet = group.getPublicLayoutSet();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!_matchesHostname(groupBaseURL, virtualHostnames)) {

			groupBaseURL.append(group.getPathFriendlyURL(false, themeDisplay));
			groupBaseURL.append(HttpUtil.decodeURL(group.getFriendlyURL()));
		}

		return groupBaseURL.toString();
	}

	private static boolean _matchesHostname(
		StringBuilder friendlyURLBase,
		TreeMap<String, String> virtualHostnames) {

		for (String virtualHostname : virtualHostnames.keySet()) {
			if (friendlyURLBase.indexOf(virtualHostname) != -1) {
				return true;
			}
		}

		return false;
	}

}