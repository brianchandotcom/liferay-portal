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

package com.liferay.lcs.rest;

import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Igor Beslic
 */
public class LCSProjectServiceUtil {

	public static List<LCSProject> getUserManageableLCSProjects(
		PortletRequest portletRequest) {

		try {
			return _lcsProjectService.getUserManageableLCSProjects();
		}
		catch (Exception e) {
			_log.error("LCS project discovery failed", e);

			if (OAuthUtil.hasOAuthException(e)) {
				OAuthUtil.processOAuthException(portletRequest, e);
			}
			else {
				SessionErrors.add(portletRequest, "generalPluginAccess");
			}
		}

		return Collections.emptyList();
	}

	public void setLCSProjectService(LCSProjectService lcsProjectService) {
		_lcsProjectService = lcsProjectService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSProjectServiceUtil.class);

	private static LCSProjectService _lcsProjectService;

}