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

package com.liferay.flags.taglib.servlet.taglib.util;

import com.liferay.flags.configuration.FlagsGroupServiceConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class FlagsTagUtil {

	public static String getCurrentURL(HttpServletRequest httpServletRequest) {
		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return PortalUtil.getCurrentURL(httpServletRequest);
		}

		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			PortalUtil.getLiferayPortletRequest(portletRequest),
			PortalUtil.getLiferayPortletResponse(portletResponse));

		return currentURLObj.toString();
	}

	public static Map<String, String> getReasons(
			long companyId, HttpServletRequest httpServletRequest)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class, companyId);

		Map<String, String> reasons = new HashMap<>();

		for (String reason : flagsGroupServiceConfiguration.reasons()) {
			reasons.put(reason, LanguageUtil.get(httpServletRequest, reason));
		}

		return reasons;
	}

	public static String getURI(HttpServletRequest httpServletRequest) {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.FLAGS, PortletRequest.ACTION_PHASE);

		portletURL.setParameter(ActionRequest.ACTION_NAME, "/flags/edit_entry");

		return portletURL.toString();
	}

	public static boolean isFlagsEnabled(ThemeDisplay themeDisplay)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class,
				themeDisplay.getCompanyId());

		if (flagsGroupServiceConfiguration.guestUsersEnabled() ||
			themeDisplay.isSignedIn()) {

			return true;
		}

		return false;
	}

}