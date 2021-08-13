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
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sites.kernel.util.SitesUtil;

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

	public static long getRefererGroupId(ThemeDisplay themeDisplay)
		throws Exception {

		long refererGroupId = 0;

		try {
			Layout refererLayout = LayoutLocalServiceUtil.getLayout(
				themeDisplay.getRefererPlid());

			refererGroupId = refererLayout.getGroupId();
		}
		catch (NoSuchLayoutException noSuchLayoutException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchLayoutException, noSuchLayoutException);
			}
		}

		return refererGroupId;
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

	public static void updateLayoutSetPrototypesLinks(
			ActionRequest actionRequest, Group liveGroup)
		throws Exception {

		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled");
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled");

		if ((privateLayoutSetPrototypeId == 0) &&
			(publicLayoutSetPrototypeId == 0) &&
			!privateLayoutSetPrototypeLinkEnabled &&
			!publicLayoutSetPrototypeLinkEnabled) {

			long layoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "layoutSetPrototypeId");
			int layoutSetVisibility = ParamUtil.getInteger(
				actionRequest, "layoutSetVisibility");
			boolean layoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "layoutSetPrototypeLinkEnabled",
				layoutSetPrototypeId > 0);
			boolean layoutSetVisibilityPrivate = ParamUtil.getBoolean(
				actionRequest, "layoutSetVisibilityPrivate");

			if ((layoutSetVisibility == _LAYOUT_SET_VISIBILITY_PRIVATE) ||
				layoutSetVisibilityPrivate) {

				privateLayoutSetPrototypeId = layoutSetPrototypeId;
				privateLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
			else {
				publicLayoutSetPrototypeId = layoutSetPrototypeId;
				publicLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
		}

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();
		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		if ((privateLayoutSetPrototypeId ==
				privateLayoutSet.getLayoutSetPrototypeId()) &&
			(privateLayoutSetPrototypeLinkEnabled ==
				privateLayoutSet.isLayoutSetPrototypeLinkEnabled()) &&
			(publicLayoutSetPrototypeId ==
				publicLayoutSet.getLayoutSetPrototypeId()) &&
			(publicLayoutSetPrototypeLinkEnabled ==
				publicLayoutSet.isLayoutSetPrototypeLinkEnabled())) {

			return;
		}

		Group group = liveGroup.getStagingGroup();

		if (!liveGroup.isStaged() || liveGroup.isStagedRemotely()) {
			group = liveGroup;
		}

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, publicLayoutSetPrototypeId, privateLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled,
			privateLayoutSetPrototypeLinkEnabled);
	}

	public static void updateWorkflowDefinitionLinks(
			ActionRequest actionRequest, Group liveGroup)
		throws PortalException {

		long groupId = liveGroup.getGroupId();
		long companyId = liveGroup.getCompanyId();
		long userId = liveGroup.getCreatorUserId();

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		Group layoutSetPrototypeGroup =
			GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
				companyId, layoutSetPrototypeId);

		List<WorkflowDefinitionLink> workflowDefinitionLinks =
			WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLinks(
				companyId, layoutSetPrototypeGroup.getGroupId(), 0);

		for (WorkflowDefinitionLink workflowDefinitionLink :
				workflowDefinitionLinks) {

			WorkflowDefinitionLinkLocalServiceUtil.addWorkflowDefinitionLink(
				userId, companyId, groupId,
				workflowDefinitionLink.getClassName(),
				workflowDefinitionLink.getClassPK(),
				workflowDefinitionLink.getTypePK(),
				workflowDefinitionLink.getWorkflowDefinitionName(),
				workflowDefinitionLink.getWorkflowDefinitionVersion());
		}
	}

	public static void validateDefaultLocaleGroupName(
			Map<Locale, String> nameMap, Locale defaultLocale)
		throws PortalException {

		if ((nameMap == null) || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new GroupNameException();
		}
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	private static final Log _log = LogFactoryUtil.getLog(ActionUtil.class);

}