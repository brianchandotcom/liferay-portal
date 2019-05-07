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

package com.liferay.social.kernel.util;

import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration.SocialInteractionsType;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class SocialInteractionsConfigurationUtil {

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(
			long companyId, HttpServletRequest httpServletRequest,
			String serviceName) {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId, true);

		boolean socialInteractionsFriendsEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, httpServletRequest,
			"socialInteractionsFriendsEnabled" + serviceName, true);
		boolean socialInteractionsSitesEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, httpServletRequest,
			"socialInteractionsSitesEnabled" + serviceName, true);
		SocialInteractionsType socialInteractionsType =
			SocialInteractionsType.parse(
				PrefsParamUtil.getString(
					portletPreferences, httpServletRequest,
					"socialInteractionsType" + serviceName,
					SocialInteractionsType.ALL_USERS.toString()));

		return new SocialInteractionsConfiguration(
			socialInteractionsFriendsEnabled, socialInteractionsSitesEnabled,
			socialInteractionsType);
	}

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(long companyId, String serviceName) {

		boolean socialInteractionsFriendsEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsFriendsEnabled" + serviceName, true);
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsSitesEnabled" + serviceName, true);
		SocialInteractionsType socialInteractionsType =
			SocialInteractionsType.parse(
				PrefsPropsUtil.getString(
					companyId, "socialInteractionsType" + serviceName,
					SocialInteractionsType.ALL_USERS.toString()));

		return new SocialInteractionsConfiguration(
			socialInteractionsFriendsEnabled, socialInteractionsSitesEnabled,
			socialInteractionsType);
	}

}