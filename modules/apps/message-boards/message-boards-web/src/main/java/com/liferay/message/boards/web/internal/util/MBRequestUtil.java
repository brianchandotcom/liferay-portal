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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dante Wang
 */
public class MBRequestUtil {

	public static MBGroupServiceSettings getMBGroupServiceSettings(
			HttpServletRequest httpServletRequest, long groupId)
		throws PortalException {

		MBGroupServiceSettings mbGroupServiceSettings =
			(MBGroupServiceSettings)httpServletRequest.getAttribute(
				_MB_GROUP_SERVICE_SETTINGS);

		if (mbGroupServiceSettings != null) {
			return mbGroupServiceSettings;
		}

		mbGroupServiceSettings = MBGroupServiceSettings.getInstance(groupId);

		httpServletRequest.setAttribute(
			_MB_GROUP_SERVICE_SETTINGS, mbGroupServiceSettings);

		return mbGroupServiceSettings;
	}

	private static final String _MB_GROUP_SERVICE_SETTINGS =
		"MB_GROUP_SERVICE_SETTINGS";

}