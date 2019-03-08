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

package com.liferay.portal.service;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TeamTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jonathan McCann
 */
public class TeamLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteTeam() throws Exception {
		_group = GroupTestUtil.addGroup();

		Team team = TeamTestUtil.addTeam();

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"defaultTeamIds", String.valueOf(team.getTeamId()));

		GroupLocalServiceUtil.updateGroup(_group);

		TeamLocalServiceUtil.deleteTeam(team);

		_group = GroupLocalServiceUtil.getGroup(_group.getGroupId());

		typeSettingsProperties = _group.getTypeSettingsProperties();

		List<Long> defaultTeamIds = ListUtil.toList(
			StringUtil.split(
				typeSettingsProperties.getProperty("defaultTeamIds"), 0L));

		Assert.assertFalse(defaultTeamIds.contains(team.getTeamId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}