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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.util.ContentUtil;

/**
 * @author David Truong
 */
public class RobotsUtil {

	public static String getDefaultRobots() {
		return getDefaultRobots(null);
	}

	public static String getDefaultRobots(String virtualHost) {
		if (Validator.isNotNull(virtualHost)) {
			String content = ContentUtil.get(
				PropsValues.ROBOTS_TXT_WITH_SITEMAP);

			content = StringUtil.replace(content, "[$HOST$]", virtualHost);

			return content;
		}

		return ContentUtil.get(PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
	}

	public static String getRobots(LayoutSet layoutSet) throws PortalException {
		if (layoutSet == null) {
			return getDefaultRobots(null);
		}

		Group group = layoutSet.getGroup();

		return GetterUtil.get(
			group.getTypeSettingsProperty(
				layoutSet.isPrivateLayout() + "-robots.txt"),
			getDefaultRobots(PortalUtil.getVirtualHostname(layoutSet)));
	}

}