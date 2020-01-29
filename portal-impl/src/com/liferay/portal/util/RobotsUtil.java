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

package com.liferay.portal.util;

import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.TreeMap;

/**
 * @author David Truong
 * @author Jesse Rao
 */
public class RobotsUtil {

	public static String getDefaultRobots() {
		int portalServerPort = PortalUtil.getPortalServerPort(false);

		return getDefaultRobots(null, false, portalServerPort);
	}

	public static String getDefaultRobots(
		String virtualHostname, boolean secure, int port) {

		if (Validator.isNotNull(virtualHostname)) {
			String content = ContentUtil.get(
				RobotsUtil.class.getClassLoader(),
				PropsValues.ROBOTS_TXT_WITH_SITEMAP);

			content = StringUtil.replace(content, "[$HOST$]", virtualHostname);
			content = StringUtil.replace(
				content, "[$PORT$]", String.valueOf(port));

			if (secure) {
				content = StringUtil.replace(content, "[$PROTOCOL$]", "https");
			}
			else {
				content = StringUtil.replace(content, "[$PROTOCOL$]", "http");
			}

			return content;
		}

		return ContentUtil.get(
			RobotsUtil.class.getClassLoader(),
			PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
	}

	public static String getRobots(LayoutSet layoutSet, boolean secure)
		throws PortalException {

		int portalServerPort = PortalUtil.getPortalServerPort(secure);

		if (layoutSet == null) {
			return getDefaultRobots(null, secure, portalServerPort);
		}

		TreeMap<String, String> virtualHostnames =
			PortalUtil.getVirtualHostnames(layoutSet);

		String virtualHostname = StringPool.BLANK;

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
		}

		return GetterUtil.get(
			layoutSet.getSettingsProperty(
				layoutSet.isPrivateLayout() + "-robots.txt"),
			getDefaultRobots(virtualHostname, secure, portalServerPort));
	}

}