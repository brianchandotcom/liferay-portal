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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portlet.social.util.SocialInteractionsConfiguration.SocialInteractionsType;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class SocialInteractionsConfigurationUtil {

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(
			long companyId, HttpServletRequest request, String serviceName) {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId, true);

		SocialInteractionsType socialInteractionsType =
			SocialInteractionsType.parse(
				PrefsParamUtil.getString(
					portletPreferences, request,
					"socialInteractionsType" + serviceName,
					SocialInteractionsType.ALL_USERS.toString()));
		boolean socialInteractionsSitesEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, request,
			"socialInteractionsSitesEnabled" + serviceName, true);
		boolean socialInteractionsFriendsEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, request,
			"socialInteractionsFriendsEnabled" + serviceName, true);

		return new SocialInteractionsConfiguration(
			socialInteractionsType, socialInteractionsSitesEnabled,
			socialInteractionsFriendsEnabled);
	}

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(long companyId, String serviceName) {

		SocialInteractionsType socialInteractionsType =
			SocialInteractionsType.parse(
				PrefsPropsUtil.getString(
					companyId, "socialInteractionsType" + serviceName,
					SocialInteractionsType.ALL_USERS.toString()));
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsSitesEnabled" + serviceName, true);
		boolean socialInteractionsFriendsEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsFriendsEnabled" + serviceName, true);

		return new SocialInteractionsConfiguration(
			socialInteractionsType, socialInteractionsSitesEnabled,
			socialInteractionsFriendsEnabled);
	}

}