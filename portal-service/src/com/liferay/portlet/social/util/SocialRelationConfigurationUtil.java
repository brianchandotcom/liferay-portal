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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationConfigurationUtil {

	public static SocialRelationConfiguration getSocialRelationConfiguration(
			long companyId)
		throws SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		boolean interactionsEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsEnabled", Boolean.TRUE.toString()));

		boolean interactionsAnyUserEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsAnyUserEnabled", Boolean.TRUE.toString()));

		boolean interactionsSitesEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsSitesEnabled", Boolean.TRUE.toString()));

		boolean interactionsSocialRelationTypesEnabled = GetterUtil.getBoolean(
			companyPortletPreferences.getValue(
				"interactionsSocialRelationTypesEnabled",
				Boolean.TRUE.toString()));

		String interactionsSocialRelationTypes =
			companyPortletPreferences.getValue(
				"interactionsSocialRelationTypes", StringPool.BLANK);

		return new SocialRelationConfiguration(
			interactionsEnabled, interactionsAnyUserEnabled,
			interactionsSitesEnabled, interactionsSocialRelationTypesEnabled,
			interactionsSocialRelationTypes);
	}

	public static SocialRelationConfiguration getSocialRelationConfiguration(
			long companyId, HttpServletRequest request)
		throws SystemException {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		boolean interactionsEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "interactionsEnabled", true);

		boolean interactionsAnyUserEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "interactionsAnyUserEnabled",
			true);

		boolean interactionsSitesEnabled = PrefsParamUtil.getBoolean(
			companyPortletPreferences, request, "interactionsSitesEnabled",
			true);

		boolean interactionsSocialRelationTypesEnabled =
			PrefsParamUtil.getBoolean(
				companyPortletPreferences, request,
				"interactionsSocialRelationTypesEnabled", true);

		String interactionsSocialRelationTypes =
			companyPortletPreferences.getValue(
				"interactionsSocialRelationTypes", StringPool.BLANK);

		return new SocialRelationConfiguration(
			interactionsEnabled, interactionsAnyUserEnabled,
			interactionsSitesEnabled, interactionsSocialRelationTypesEnabled,
			interactionsSocialRelationTypes);
	}

}