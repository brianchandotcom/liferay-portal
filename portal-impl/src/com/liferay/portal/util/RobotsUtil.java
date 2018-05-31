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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author David Truong
 * @author Jesse Rao
 */
public class RobotsUtil {

	public static String getDefaultRobots() {
		return getDefaultRobots(false, null, 8080);
	}

	public static String getDefaultRobots(
		boolean https, String virtualHost, int port) {

		if (Validator.isNotNull(virtualHost)) {
			String content = ContentUtil.get(
				RobotsUtil.class.getClassLoader(),
				PropsValues.ROBOTS_TXT_WITH_SITEMAP);

			if (https) {
				content = StringUtil.replace(content, "[$PROTOCOL$]", "https");
			}
			else {
				content = StringUtil.replace(content, "[$PROTOCOL$]", "http");
			}

			content = StringUtil.replace(content, "[$HOST$]", virtualHost);

			content = StringUtil.replace(
				content, "[$PORT$]", String.valueOf(port));

			return content;
		}

		return ContentUtil.get(
			RobotsUtil.class.getClassLoader(),
			PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
	}

	public static String getRobots(LayoutSet layoutSet) throws PortalException {
		String webServerProtocol = PropsValues.WEB_SERVER_PROTOCOL;

		boolean https = false;

		if ((webServerProtocol != null) && webServerProtocol.equals("https")) {
			https = true;
		}

		int portalServerPort;

		if (https) {
			portalServerPort = PortalUtil.getPortalServerPort(true);
		}
		else {
			portalServerPort = PortalUtil.getPortalServerPort(false);
		}

		if (layoutSet == null) {
			return getDefaultRobots(https, null, portalServerPort);
		}

		return GetterUtil.get(
			layoutSet.getSettingsProperty(
				layoutSet.isPrivateLayout() + "-robots.txt"),
			getDefaultRobots(
				https, PortalUtil.getVirtualHostname(layoutSet),
				portalServerPort));
	}

}