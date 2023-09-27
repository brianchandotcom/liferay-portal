/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TeamLocalService;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(service = {EntityModelListener.class, ModelListener.class})
public class TeamModelListener extends BaseEntityModelListener<Team> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) {
		return user.getTeamIds();
	}

	@Override
	public String getModelClassName() {
		return Team.class.getName();
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return _teamLocalService.getActionableDynamicQuery();
	}

	@Override
	protected Team getModel(long id) throws Exception {
		return _teamLocalService.getTeam(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "teamId";
	}

	private static final List<String> _attributeNames = Arrays.asList(
		"groupId", "name");

	@Reference
	private TeamLocalService _teamLocalService;

}