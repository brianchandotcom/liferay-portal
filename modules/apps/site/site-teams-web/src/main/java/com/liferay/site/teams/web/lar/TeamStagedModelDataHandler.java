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
import com.liferay.portal.service.TeamLocalService;
import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.site.teams.web.constants.SiteTeamsPortletKeys;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		Team team = _teamLocalService.fetchTeamByUuidAndGroupId(uuid, groupId);

		if (team != null) {
			deleteStagedModel(team);
		}
	}

	@Override
	public void deleteStagedModel(Team team) throws PortalException {
		_teamLocalService.deleteTeam(team);
	}

	@Override
	public void doExportStagedModel(
			PortletDataContext portletDataContext, Team team)
		throws Exception {

		Element teamElement = portletDataContext.getExportDataElement(team);

		List<User> teamUsers = _userLocalService.getTeamUsers(team.getTeamId());

		if (ListUtil.isNotEmpty(teamUsers)) {
			for (User user : teamUsers) {
				portletDataContext.addReferenceElement(
					team, teamElement, user,
					PortletDataContext.REFERENCE_TYPE_WEAK, true);
			}
		}

		List<UserGroup> teamUserGroups =
			_userGroupLocalService.getTeamUserGroups(team.getTeamId());

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
	public Team fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return _teamLocalService.fetchTeamByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<Team> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _teamLocalService.getTeamsByUuidAndCompanyId(uuid, companyId);
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

		Team existingTeam = fetchExistingTeam(
			team.getUuid(), portletDataContext.getScopeGroupId(),
			team.getName());

		Team importedTeam = null;

		if (existingTeam == null) {
			ServiceContext serviceContext =
				portletDataContext.createServiceContext(team);

			serviceContext.setUuid(team.getUuid());

			importedTeam = _teamLocalService.addTeam(
				userId, portletDataContext.getScopeGroupId(), team.getName(),
				team.getDescription(), serviceContext);
		}
		else {
			importedTeam = _teamLocalService.updateTeam(
				existingTeam.getTeamId(), team.getName(),
				team.getDescription());
		}

		List<Element> userElements = portletDataContext.getReferenceElements(
			team, User.class);

		for (Element userElement : userElements) {
			long companyId = GetterUtil.getLong(
				userElement.attributeValue("company-id"));
			String uuid = userElement.attributeValue("uuid");

			User user = _userLocalService.fetchUserByUuidAndCompanyId(
				uuid, companyId);

			if ((user != null) &&
				!_userLocalService.hasTeamUser(
					importedTeam.getTeamId(), user.getUserId())) {

				_userLocalService.addTeamUser(importedTeam.getTeamId(), user);
			}
		}

		List<Element> userGroupElements =
			portletDataContext.getReferenceElements(team, UserGroup.class);

		for (Element userGroupElement : userGroupElements) {
			long companyId = GetterUtil.getLong(
				userGroupElement.attributeValue("company-id"));
			String uuid = userGroupElement.attributeValue("uuid");

			UserGroup userGroup =
				_userGroupLocalService.fetchUserGroupByUuidAndCompanyId(
					uuid, companyId);

			if ((userGroup != null) &&
				!_userGroupLocalService.hasTeamUserGroup(
					importedTeam.getTeamId(), userGroup.getUserGroupId())) {

				_userGroupLocalService.addTeamUserGroup(
					importedTeam.getTeamId(), userGroup);
			}
		}

		portletDataContext.importClassedModel(team, importedTeam);
	}

	protected Team fetchExistingTeam(String uuid, long groupId, String name) {
		Team team = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (team != null) {
			return team;
		}

		return _teamLocalService.fetchTeam(groupId, name);
	}

	@Override
	protected void importReferenceStagedModels(
			PortletDataContext portletDataContext, Team team)
		throws PortletDataException {
	}

	@Reference(unbind = "-")
	protected void setTeamLocalService(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupLocalService(
		UserGroupLocalService userGroupLocalService) {

		_userGroupLocalService = userGroupLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile TeamLocalService _teamLocalService;
	private volatile UserGroupLocalService _userGroupLocalService;
	private volatile UserLocalService _userLocalService;

}