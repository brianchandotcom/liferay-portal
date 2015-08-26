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

package com.liferay.site.teams.web.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.site.teams.web.constants.SiteTeamsPortletKeys;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + SiteTeamsPortletKeys.SITE_TEAMS},
	service = StagedModelDataHandler.class
)
public class TeamStagedModelDataHandler
	extends BaseStagedModelDataHandler<Team> {

	public static final String[] CLASS_NAMES = {Team.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Team team = TeamLocalServiceUtil.fetchTeamByUuidAndGroupId(
			uuid, groupId);

		if (team != null) {
			deleteStagedModel(team);
		}
	}

	@Override
	public void deleteStagedModel(Team team) throws PortalException {
		TeamLocalServiceUtil.deleteTeam(team);
	}

	@Override
	public void doExportStagedModel(
			PortletDataContext portletDataContext, Team team)
		throws Exception {

		Element teamElement = portletDataContext.getExportDataElement(team);

		List<User> teamUsers = UserLocalServiceUtil.getTeamUsers(
			team.getTeamId());

		if (ListUtil.isNotEmpty(teamUsers)) {
			for (User user : teamUsers) {
				portletDataContext.addReferenceElement(
					team, teamElement, user,
					PortletDataContext.REFERENCE_TYPE_WEAK, true);
			}
		}

		List<UserGroup> teamUserGroups =
			UserGroupLocalServiceUtil.getTeamUserGroups(team.getTeamId());

		if (ListUtil.isNotEmpty(teamUserGroups)) {
			for (UserGroup userGroup : teamUserGroups) {
				portletDataContext.addReferenceElement(
					team, teamElement, userGroup,
					PortletDataContext.REFERENCE_TYPE_WEAK, true);
			}
		}

		portletDataContext.addClassedModel(
			teamElement, ExportImportPathUtil.getModelPath(team), team);
	}

	@Override
	public List<Team> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return TeamLocalServiceUtil.getTeamsByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Team team)
		throws Exception {

		long userId = portletDataContext.getUserId(team.getUserUuid());

		Team existingTeam = TeamLocalServiceUtil.fetchTeamByUuidAndGroupId(
			team.getUuid(), portletDataContext.getScopeGroupId());

		Team importedTeam = null;

		if (existingTeam == null) {
			ServiceContext serviceContext =
				portletDataContext.createServiceContext(team);

			serviceContext.setUuid(team.getUuid());

			importedTeam = TeamLocalServiceUtil.addTeam(
				userId, portletDataContext.getScopeGroupId(), team.getName(),
				team.getDescription(), serviceContext);
		}
		else {
			importedTeam = TeamLocalServiceUtil.updateTeam(
				existingTeam.getTeamId(), team.getName(),
				team.getDescription());
		}

		List<Element> userElements = portletDataContext.getReferenceElements(
			team, User.class);

		for (Element userElement : userElements) {
			long companyId = GetterUtil.getLong(
				userElement.attributeValue("company-id"));
			String uuid = userElement.attributeValue("uuid");

			User user = UserLocalServiceUtil.fetchUserByUuidAndCompanyId(
				uuid, companyId);

			if ((user != null) &&
				!UserLocalServiceUtil.hasTeamUser(
					importedTeam.getTeamId(), user.getUserId())) {

				UserLocalServiceUtil.addTeamUser(
					importedTeam.getTeamId(), user);
			}
		}

		List<Element> userGroupElements =
			portletDataContext.getReferenceElements(team, UserGroup.class);

		for (Element userGroupElement : userGroupElements) {
			long companyId = GetterUtil.getLong(
				userGroupElement.attributeValue("company-id"));
			String uuid = userGroupElement.attributeValue("uuid");

			UserGroup userGroup =
				UserGroupLocalServiceUtil.fetchUserGroupByUuidAndCompanyId(
					uuid, companyId);

			if ((userGroup != null) &&
				!UserGroupLocalServiceUtil.hasTeamUserGroup(
					importedTeam.getTeamId(), userGroup.getUserGroupId())) {

				UserGroupLocalServiceUtil.addTeamUserGroup(
					importedTeam.getTeamId(), userGroup);
			}
		}

		portletDataContext.importClassedModel(team, importedTeam);
	}

	@Override
	protected void importReferenceStagedModels(
			PortletDataContext portletDataContext, Team team)
		throws PortletDataException {
	}

}