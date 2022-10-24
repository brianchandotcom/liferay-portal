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

package com.liferay.site.teams.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.site.teams.web.internal.constants.SiteTeamsPortletKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = CTDisplayRenderer.class)
public class TeamCTDisplayRenderer extends BaseCTDisplayRenderer<Team> {

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, Team team)
		throws Exception {

		Group group = _groupLocalService.getGroup(team.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, SiteTeamsPortletKeys.SITE_TEAMS, 0,
				0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_team.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"teamId", team.getTeamId()
		).buildString();
	}

	@Override
	public Class<Team> getModelClass() {
		return Team.class;
	}

	@Override
	public String getTitle(Locale locale, Team team) {
		return team.getName();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Team> displayBuilder) {
		Team team = displayBuilder.getModel();

		displayBuilder.display(
			"name", team.getName()
		).display(
			"description", team.getDescription()
		).display(
			"users",
			() -> _userLocalService.searchCount(
				team.getCompanyId(), StringPool.BLANK,
				WorkflowConstants.STATUS_ANY,
				LinkedHashMapBuilder.<String, Object>put(
					"usersTeams", team.getTeamId()
				).build())
		).display(
			"user-groups",
			() -> _userGroupLocalService.searchCount(
				team.getCompanyId(), StringPool.BLANK,
				LinkedHashMapBuilder.<String, Object>put(
					UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_TEAMS,
					team.getTeamId()
				).build())
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}