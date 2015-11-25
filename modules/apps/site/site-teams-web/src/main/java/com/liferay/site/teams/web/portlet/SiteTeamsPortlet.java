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
import com.liferay.portal.model.Team;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.TeamService;
import com.liferay.portal.service.UserGroupService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.site.teams.web.constants.SiteTeamsPortletKeys;

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
		"com.liferay.portlet.css-class-wrapper=portlet-communities",
		"com.liferay.portlet.icon=/icons/site_teams.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Site Teams",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteTeamsPortletKeys.SITE_TEAMS,
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

		_teamService.deleteTeam(teamId);
	}

	public void deleteTeams(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] teamIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		for (long teamId : teamIds) {
			_teamService.deleteTeam(teamId);
		}
	}

	public void editTeam(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (teamId <= 0) {

			// Add team

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Team.class.getName(), actionRequest);

			_teamService.addTeam(
				themeDisplay.getSiteGroupId(), name, description,
				serviceContext);
		}
		else {

			// Update team

			_teamService.updateTeam(teamId, name, description);
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

		_userGroupService.addTeamUserGroups(teamId, addUserGroupIds);
		_userGroupService.unsetTeamUserGroups(teamId, removeUserGroupIds);

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

		_userService.addTeamUsers(teamId, addUserIds);
		_userService.unsetTeamUsers(teamId, removeUserIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

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
	protected void setTeamService(TeamService teamService) {
		_teamService = teamService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupService(UserGroupService userGroupService) {
		_userGroupService = userGroupService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	private volatile TeamService _teamService;
	private volatile UserGroupService _userGroupService;
	private volatile UserService _userService;

}