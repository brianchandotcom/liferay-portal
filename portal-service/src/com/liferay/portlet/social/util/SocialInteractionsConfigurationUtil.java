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
		getSocialInteractionsConfiguration(
				long companyId, HttpServletRequest request)
			throws SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		boolean socialInteractionsEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "socialInteractionsEnabled",
			true);

		boolean socialInteractionsAnyUserEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request,
			"socialInteractionsAnyUserEnabled", true);

		boolean socialInteractionsSitesEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request,
			"socialInteractionsSitesEnabled", true);

		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsParamUtil.getBoolean(
				companyPortletPreferences, request,
				"socialInteractionsSocialRelationTypesEnabled", true);

		String socialInteractionsSocialRelationTypes =
			companyPortletPreferences.getValue(
				"socialInteractionsSocialRelationTypes", StringPool.BLANK);

		return new SocialInteractionsConfiguration(
			socialInteractionsEnabled, socialInteractionsAnyUserEnabled,
			socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypesEnabled,
			socialInteractionsSocialRelationTypes);
	}

}