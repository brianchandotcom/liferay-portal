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

package com.liferay.site.teams.web.portlet;

import com.liferay.portal.DuplicateTeamException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.TeamNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.TeamServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.site.teams.web.upgrade.SiteTeamsWebUpgrade;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.control-panel-entry-category=site_administration.users",
		"com.liferay.portlet.control-panel-entry-weight=2.0",
		"com.liferay.portlet.css-class-wrapper=portlet-communities",
		"com.liferay.portlet.icon=/icons/site_memberships_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Site Teams Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SiteTeamsPortlet extends MVCPortlet {

	public void deleteTeam(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		TeamServiceUtil.deleteTeam(teamId);
	}

	public void editTeam(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (teamId <= 0) {

			// Add team

			TeamServiceUtil.addTeam(groupId, name, description);
		}
		else {

			// Update team

			TeamServiceUtil.updateTeam(teamId, name, description);
		}
	}

	public void editTeamUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		UserGroupServiceUtil.addTeamUserGroups(teamId, addUserGroupIds);
		UserGroupServiceUtil.unsetTeamUserGroups(teamId, removeUserGroupIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void editTeamUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);
		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		UserServiceUtil.addTeamUsers(teamId, addUserIds);
		UserServiceUtil.unsetTeamUsers(teamId, removeUserIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicateTeamException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof NoSuchTeamException ||
			cause instanceof PrincipalException ||
			cause instanceof TeamNameException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setSiteTeamsWebUpgrade(
		SiteTeamsWebUpgrade siteTeamsWebUpgrade) {
	}

}