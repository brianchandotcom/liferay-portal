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

package com.liferay.site.teams.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class TeamLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteTeam() throws Exception {
		_group = GroupTestUtil.addGroup();

		Team team = _teamLocalService.addTeam(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"defaultTeamIds", String.valueOf(team.getTeamId()));

		_groupLocalService.updateGroup(_group);

		_teamLocalService.deleteTeam(team);

		_group = _groupLocalService.getGroup(_group.getGroupId());

		typeSettingsProperties = _group.getTypeSettingsProperties();

		List<Long> defaultTeamIds = ListUtil.toList(
			StringUtil.split(
				typeSettingsProperties.getProperty("defaultTeamIds"), 0L));

		Assert.assertFalse(defaultTeamIds.contains(team.getTeamId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private TeamLocalService _teamLocalService;

}