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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
			return ContentUtil.get(
				RobotsUtil.class.getClassLoader(),
				PropsValues.ROBOTS_TXT_WITH_SITEMAP);
		}

		return ContentUtil.get(
			RobotsUtil.class.getClassLoader(),
			PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
	}

	public static String getRobots(LayoutSet layoutSet, boolean secure)
		throws PortalException {

		if (layoutSet == null) {
			return ContentUtil.get(
				RobotsUtil.class.getClassLoader(),
				PropsValues.ROBOTS_TXT_WITH_SITEMAP);
		}

		int portalServerPort = PortalUtil.getPortalServerPort(secure);

		TreeMap<String, String> virtualHostnames =
			PortalUtil.getVirtualHostnames(layoutSet);

		String virtualHostname = StringPool.BLANK;

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
		}

		String robotsTxt = GetterUtil.getString(
			layoutSet.getSettingsProperty(
				layoutSet.isPrivateLayout() + "-robots.txt"),
			null);

		if (robotsTxt == null) {
			robotsTxt = ContentUtil.get(
				RobotsUtil.class.getClassLoader(),
				PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
		}

		robotsTxt = _replaceWildcards(
			robotsTxt, virtualHostname, secure, portalServerPort);

		return robotsTxt;
	}

	private static String _replaceWildcards(
		String robotsTxt, String virtualHostname, boolean secure, int port) {

		if (Validator.isNotNull(virtualHostname)) {
			robotsTxt = StringUtil.replace(
				robotsTxt, "[$HOST$]", virtualHostname);
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(
				"Wildcard [$HOST$] could not be replaced with the actual " +
					"virtualhost");
		}

		robotsTxt = StringUtil.replace(
			robotsTxt, "[$PORT$]", String.valueOf(port));

		if (secure) {
			return StringUtil.replace(robotsTxt, "[$PROTOCOL$]", "https");
		}

		return StringUtil.replace(robotsTxt, "[$PROTOCOL$]", "http");
	}

	private static final Log _log = LogFactoryUtil.getLog(RobotsUtil.class);

}