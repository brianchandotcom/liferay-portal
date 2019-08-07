/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.portlet.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Cristina González
 */
public class DLOpenerTimestampUtil {

	public static void add(
		HttpServletRequest httpServletRequest, String timestamp) {

		List<String> timestamps = _getTimestamps(httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		timestamps.add(timestamp);

		httpSession.setAttribute(
			_SESSION_ATTRIBUTE_NAME_ONEDRIVE_TIMESTAMP, timestamps);
	}

	public static boolean contains(
		HttpServletRequest httpServletRequest, String timestamp) {

		List<String> timestamps = _getTimestamps(httpServletRequest);

		return timestamps.contains(timestamp);
	}

	private static List<String> _getTimestamps(
		HttpServletRequest httpServletRequest) {

		HttpSession httpSession = httpServletRequest.getSession();

		return Optional.ofNullable(
			(List<String>)httpSession.getAttribute(
				_SESSION_ATTRIBUTE_NAME_ONEDRIVE_TIMESTAMP)
		).orElse(
			new ArrayList<>()
		);
	}

	private static final String _SESSION_ATTRIBUTE_NAME_ONEDRIVE_TIMESTAMP =
		"onedrive-timestamps";

}