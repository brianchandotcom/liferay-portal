/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.security.permission;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Hugo Huijser
 * @author Angelo Jefferson
 */
public class WikiPermissionPropagatorImpl extends BasePermissionPropagator {

	@Override
	public void propagateRolePermissions(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<String> nodeResourceActions =
			ResourceActionsUtil.getModelResourceActions(
				WikiNode.class.getName());

		List<String> pageResourceActions =
			ResourceActionsUtil.getModelResourceActions(
				WikiPage.class.getName());

		List<String> sharedResourceActions = new ArrayList<String>();

		for (String resourceAction : pageResourceActions) {
			if (!nodeResourceActions.contains(resourceAction)) {
				continue;
			}

			sharedResourceActions.add(resourceAction);
		}

		long nodeId = ParamUtil.getLong(actionRequest, "resourcePrimKey");

		List<WikiPage> wikiPages = WikiPageLocalServiceUtil.getPages(
			nodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (WikiPage wikiPage : wikiPages) {
			String resourcePrimKey = String.valueOf(
				wikiPage.getResourcePrimKey());

			String rolesSearchContainerPrimaryKeys = ParamUtil.getString(
				actionRequest, "rolesSearchContainerPrimaryKeys");

			long[] roleIds = StringUtil.split(
				rolesSearchContainerPrimaryKeys, 0L);

			for (long roleId : roleIds) {
				List<String> nodeActionIds =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							themeDisplay.getCompanyId(),
							WikiNode.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							String.valueOf(nodeId), roleId,
							nodeResourceActions);

				List<String> pageActionIds =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							themeDisplay.getCompanyId(),
							WikiPage.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
							roleId, pageResourceActions);

				for (String actionId : nodeActionIds) {
					if (!sharedResourceActions.contains(actionId)) {
						continue;
					}

					pageActionIds.add(actionId);
				}

				ResourcePermissionServiceUtil.setIndividualResourcePermissions(
					themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(),
					WikiPage.class.getName(), resourcePrimKey, roleId,
					pageActionIds.toArray(new String[pageActionIds.size()]));
			}
		}
	}

}