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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalService;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.site.teams.web.constants.SiteTeamsPortletKeys;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = {"javax.portlet.name=" + SiteTeamsPortletKeys.SITE_TEAMS},
	service = PortletDataHandler.class
)
public class SiteTeamsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "site_teams";

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "site-teams", true, true, null,
				Team.class.getName()));
		setPublishToLiveByDefault(true);
		setRank(80);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				SiteTeamsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_teamLocalService.deleteTeams(portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			_teamLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element teamsElement = portletDataContext.getImportDataGroupElement(
			Team.class);

		List<Element> teamElements = teamsElement.elements();

		for (Element teamElement : teamElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, teamElement);
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_teamLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performCount();
	}

	@Reference(unbind = "-")
	protected void setTeamLocalService(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	private volatile TeamLocalService _teamLocalService;

}