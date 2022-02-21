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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDestinationUtil {

	public static boolean isSameDestination(
		PortletPreferences portletPreferences, ThemeDisplay themeDisplay) {

		String destination = GetterUtil.getString(
			portletPreferences.getValue("destination", StringPool.BLANK));

		if (Validator.isNull(destination) ||
			isSameDestination(
				destination,
				themeDisplay.getLayoutFriendlyURL(themeDisplay.getLayout()))) {

			return true;
		}

		return false;
	}

	public static boolean isSameDestination(
		SearchBarPortletPreferences searchBarPortletPreferences,
		ThemeDisplay themeDisplay) {

		Optional<String> optional =
			searchBarPortletPreferences.getDestination();

		if (!optional.isPresent() ||
			isSameDestination(
				optional.get(),
				themeDisplay.getLayoutFriendlyURL(themeDisplay.getLayout()))) {

			return true;
		}

		return false;
	}

	protected static boolean isSameDestination(
		String destination, String friendlyURL) {

		int offset = 0;

		if (destination.charAt(0) != '/') {
			offset = 1;
		}

		if ((destination.length() == (friendlyURL.length() - offset)) &&
			destination.regionMatches(
				0, friendlyURL, offset, friendlyURL.length() - offset)) {

			return true;
		}

		return false;
	}

}