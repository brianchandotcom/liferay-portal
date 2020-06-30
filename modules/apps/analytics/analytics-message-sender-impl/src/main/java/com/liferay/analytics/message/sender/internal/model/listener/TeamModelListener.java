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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.EntityModelListener;
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
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class TeamModelListener extends BaseEntityModelListener<Team> {

	@Override
	public List<String> getAttributeNames() {
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