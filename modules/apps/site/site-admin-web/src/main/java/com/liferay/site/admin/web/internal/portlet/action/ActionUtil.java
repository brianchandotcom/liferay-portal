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

package com.liferay.site.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.AvailableLocaleException;
import com.liferay.portal.kernel.exception.GroupNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;

/**
 * @author Jürgen Kappler
 */
public class ActionUtil {

	public static List<Group> getGroups(ResourceRequest request)
		throws Exception {

		long[] groupIds = ParamUtil.getLongValues(request, "rowIds");

		List<Group> groups = new ArrayList<>();

		for (long groupId : groupIds) {
			groups.add(GroupServiceUtil.getGroup(groupId));
		}

		return groups;
	}

	public static String getHistoryKey(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		return HttpUtil.getParameter(
			redirect, actionResponse.getNamespace() + "historyKey", false);
	}

	public static List<Long> getRoleIds(PortletRequest portletRequest) {
		List<Long> roleIds = new ArrayList<>();

		long[] siteRolesRoleIds = ArrayUtil.unique(
			ParamUtil.getLongValues(
				portletRequest, "siteRolesSearchContainerPrimaryKeys"));

		for (long siteRolesRoleId : siteRolesRoleIds) {
			if (siteRolesRoleId == 0) {
				continue;
			}

			roleIds.add(siteRolesRoleId);
		}

		return roleIds;
	}

	public static List<Long> getTeamIds(PortletRequest portletRequest) {
		List<Long> teamIds = new ArrayList<>();

		long[] teamsTeamIds = ArrayUtil.unique(
			ParamUtil.getLongValues(
				portletRequest, "teamsSearchContainerPrimaryKeys"));

		for (long teamsTeamId : teamsTeamIds) {
			if (teamsTeamId == 0) {
				continue;
			}

			teamIds.add(teamsTeamId);
		}

		return teamIds;
	}

	public static TreeMap<String, String> toTreeMap(
			ActionRequest actionRequest, String parameterPrefix,
			Set<Locale> availableLocales)
		throws AvailableLocaleException {

		TreeMap<String, String> treeMap = new TreeMap<>();

		String[] virtualHostnames = ParamUtil.getStringValues(
			actionRequest, parameterPrefix + "name[]");
		String[] virtualHostLanguageIds = ParamUtil.getStringValues(
			actionRequest, parameterPrefix + "LanguageId[]");

		for (int i = 0; i < virtualHostnames.length; i++) {
			String virtualHostname = virtualHostnames[i];

			String virtualHostLanguageId = (String)ArrayUtil.getValue(
				virtualHostLanguageIds, i);

			if (Validator.isNotNull(virtualHostLanguageId)) {
				Locale locale = LocaleUtil.fromLanguageId(
					virtualHostLanguageId);

				if (!availableLocales.contains(locale)) {
					throw new AvailableLocaleException(virtualHostLanguageId);
				}
			}

			treeMap.put(virtualHostname, virtualHostLanguageId);
		}

		return treeMap;
	}

	public static void validateDefaultLocaleGroupName(
			Map<Locale, String> nameMap, Locale defaultLocale)
		throws PortalException {

		if ((nameMap == null) || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new GroupNameException();
		}
	}

}