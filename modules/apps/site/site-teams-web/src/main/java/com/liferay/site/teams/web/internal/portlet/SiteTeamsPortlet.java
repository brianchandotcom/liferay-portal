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

package com.liferay.site.teams.web.internal.portlet;

import com.liferay.portal.kernel.exception.DuplicateTeamException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchTeamException;
import com.liferay.portal.kernel.exception.TeamNameException;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.TeamService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.teams.web.internal.constants.SiteTeamsPortletKeys;

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
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteTeamsPortletKeys.SITE_TEAMS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class SiteTeamsPortlet extends MVCPortlet {

	public void addTeamUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long[] addUserGroupIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		_userGroupService.addTeamUserGroups(teamId, addUserGroupIds);
	}

	public void addTeamUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long[] addUserIds = ParamUtil.getLongValues(actionRequest, "rowIds");

		_userService.addTeamUsers(teamId, addUserIds);
	}

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

	public void deleteTeamUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long[] removeUserGroupIds = null;

		long removeUserGroupId = ParamUtil.getLong(
			actionRequest, "removeUserGroupId");

		if (removeUserGroupId > 0) {
			removeUserGroupIds = new long[] {removeUserGroupId};
		}
		else {
			removeUserGroupIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		_userGroupService.unsetTeamUserGroups(teamId, removeUserGroupIds);
	}

	public void deleteTeamUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long[] removeUserIds = null;

		long removeUserId = ParamUtil.getLong(actionRequest, "removeUserId");

		if (removeUserId > 0) {
			removeUserIds = new long[] {removeUserId};
		}
		else {
			removeUserIds = ParamUtil.getLongValues(actionRequest, "rowIds");
		}

		_userService.unsetTeamUsers(teamId, removeUserIds);
	}

	public void editTeam(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (teamId <= 0) {

			// Add team

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

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

	private TeamService _teamService;
	private UserGroupService _userGroupService;
	private UserService _userService;

}