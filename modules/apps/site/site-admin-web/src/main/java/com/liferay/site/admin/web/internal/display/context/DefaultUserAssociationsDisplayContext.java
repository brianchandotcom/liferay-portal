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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DefaultUserAssociationsDisplayContext {

	public DefaultUserAssociationsDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_groupTypeSettingsUnicodeProperties =
			(UnicodeProperties)httpServletRequest.getAttribute(
				"site.groupTypeSettings");
		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE));
		_liveGroupId = (long)httpServletRequest.getAttribute(
			"site.liveGroupId");
	}

	public String getSelectSiteRolePortletNamespace() {
		String selectSiteRolePortletId = PortletProviderUtil.getPortletId(
			Role.class.getName(), PortletProvider.Action.BROWSE);

		return PortalUtil.getPortletNamespace(selectSiteRolePortletId);
	}

	public String getSelectSiteRoleURL() throws PortalException {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				_httpServletRequest, Role.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectSiteRole"
		).setParameter(
			"groupId", _liveGroupId
		).setParameter(
			"roleType", RoleConstants.TYPE_SITE
		).setParameter(
			"step", "2"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getSelectTeamPortletNamespace() {
		String selectTeamPortletId = PortletProviderUtil.getPortletId(
			Team.class.getName(), PortletProvider.Action.BROWSE);

		return PortalUtil.getPortletNamespace(selectTeamPortletId);
	}

	public String getSelectTeamURL() throws PortalException {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				_httpServletRequest, Team.class.getName(),
				PortletProvider.Action.BROWSE)
		).setParameter(
			"eventName", _liferayPortletResponse.getNamespace() + "selectTeam"
		).setParameter(
			"groupId", _liveGroupId
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public SearchContainer<Role> getSiteRolesSearchContainer() {
		SearchContainer<Role> siteRolesSearchContainer =
			new SearchContainer<>();

		siteRolesSearchContainer.setResultsAndTotal(
			TransformUtil.transformToList(
				StringUtil.split(
					_groupTypeSettingsUnicodeProperties.getProperty(
						"defaultSiteRoleIds"),
					0L),
				RoleLocalServiceUtil::fetchRole));

		return siteRolesSearchContainer;
	}

	public SearchContainer<Team> getTeamsSearchContainer() {
		SearchContainer<Team> teamsSearchContainer = new SearchContainer<>();

		teamsSearchContainer.setResultsAndTotal(
			TransformUtil.transformToList(
				StringUtil.split(
					_groupTypeSettingsUnicodeProperties.getProperty(
						"defaultTeamIds"),
					0L),
				TeamLocalServiceUtil::fetchTeam));

		return teamsSearchContainer;
	}

	private final UnicodeProperties _groupTypeSettingsUnicodeProperties;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final long _liveGroupId;

}