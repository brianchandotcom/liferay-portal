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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class SocialInteractionsConfigurationUtil {

	public static SocialInteractionsConfiguration
			getSocialInteractionsConfiguration(long companyId)
		throws SystemException {

		boolean socialInteractionsAnyUserEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsAnyUserEnabled", true);
		boolean socialInteractionsEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsEnabled", true);
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsSitesEnabled", true);
		String socialInteractionsSocialRelationTypes =
			PrefsPropsUtil.getString(
				companyId, "socialInteractionsSocialRelationTypes",
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				companyId, "socialInteractionsSocialRelationTypesEnabled",
				true);

		return new SocialInteractionsConfiguration(
			socialInteractionsAnyUserEnabled, socialInteractionsEnabled,
			socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypes,
			socialInteractionsSocialRelationTypesEnabled);
	}

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(
			long companyId, HttpServletRequest request)
		throws SystemException {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId, true);

		boolean socialInteractionsAnyUserEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, request, "socialInteractionsAnyUserEnabled",
			true);
		boolean socialInteractionsEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, request, "socialInteractionsEnabled", true);
		boolean socialInteractionsSitesEnabled = PrefsParamUtil.getBoolean(
			portletPreferences, request, "socialInteractionsSitesEnabled",
			true);
		String socialInteractionsSocialRelationTypes =
			portletPreferences.getValue(
				"socialInteractionsSocialRelationTypes", StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsParamUtil.getBoolean(
				portletPreferences, request,
				"socialInteractionsSocialRelationTypesEnabled", true);

		return new SocialInteractionsConfiguration(
			socialInteractionsAnyUserEnabled, socialInteractionsEnabled,
			socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypes,
			socialInteractionsSocialRelationTypesEnabled);
	}

}