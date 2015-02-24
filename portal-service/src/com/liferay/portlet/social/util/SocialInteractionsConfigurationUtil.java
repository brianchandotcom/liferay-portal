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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.social.util.SocialInteractionsConfiguration.SocialInteractionsType;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class SocialInteractionsConfigurationUtil {

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(long companyId) {

		SocialInteractionsType socialInteractionsType =
			SocialInteractionsType.parse(
				PrefsPropsUtil.getString(
					companyId, "socialInteractionsType",
					SocialInteractionsType.ALL_USERS.toString()));
		boolean socialInteractionsSitesEnabled = PrefsPropsUtil.getBoolean(
			companyId, "socialInteractionsSitesEnabled", true);
		String socialInteractionsSocialRelationTypes = PrefsPropsUtil.getString(
			companyId, "socialInteractionsSocialRelationTypes",
			StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				companyId, "socialInteractionsSocialRelationTypesEnabled",
				true);

		return new SocialInteractionsConfiguration(
			socialInteractionsType, socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypes,
			socialInteractionsSocialRelationTypesEnabled, null);
	}

	public static SocialInteractionsConfiguration
		getSocialInteractionsConfiguration(
			long companyId, HttpServletRequest request) {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId, true);

		SocialInteractionsType socialInteractionsType =
			SocialInteractionsType.parse(
				PrefsParamUtil.getString(
					portletPreferences, request, "socialInteractionsType",
					SocialInteractionsType.ALL_USERS.toString()));
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
			socialInteractionsType, socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypes,
			socialInteractionsSocialRelationTypesEnabled, null);
	}

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
		String socialInteractionsSocialRelationTypes =
			portletPreferences.getValue(
				"socialInteractionsSocialRelationTypes" + serviceName,
				StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsParamUtil.getBoolean(
				portletPreferences, request,
				"socialInteractionsSocialRelationTypesEnabled" + serviceName,
				true);

		SocialInteractionsConfiguration defaultSocialInteractionsConfiguration =
			SocialInteractionsConfigurationUtil.
				getSocialInteractionsConfiguration(companyId, request);

		return new SocialInteractionsConfiguration(
			socialInteractionsType, socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypes,
			socialInteractionsSocialRelationTypesEnabled,
			defaultSocialInteractionsConfiguration);
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
		String socialInteractionsSocialRelationTypes = PrefsPropsUtil.getString(
			companyId, "socialInteractionsSocialRelationTypes" + serviceName,
			StringPool.BLANK);
		boolean socialInteractionsSocialRelationTypesEnabled =
			PrefsPropsUtil.getBoolean(
				companyId,
				"socialInteractionsSocialRelationTypesEnabled" + serviceName,
				true);

		SocialInteractionsConfiguration defaultSocialInteractionsConfiguration =
			SocialInteractionsConfigurationUtil.
				getSocialInteractionsConfiguration(companyId);

		return new SocialInteractionsConfiguration(
			socialInteractionsType, socialInteractionsSitesEnabled,
			socialInteractionsSocialRelationTypes,
			socialInteractionsSocialRelationTypesEnabled,
			defaultSocialInteractionsConfiguration);
	}

	public static boolean isInheritSocialInteractionsConfiguration(
		long companyId, HttpServletRequest request, String serviceName) {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId, true);

		return PrefsParamUtil.getBoolean(
			portletPreferences, request,
			"inheritSocialInteractionsConfiguration" + serviceName, true);
	}

}