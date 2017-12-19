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

package com.liferay.social.activity.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.kernel.model.SocialActivityDefinition;
import com.liferay.social.kernel.service.SocialActivitySettingLocalServiceUtil;
import com.liferay.social.kernel.util.SocialConfigurationUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class SocialActivitySettingLocalServiceTest
	extends BaseSocialActivityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetActivityDefinition() throws Exception {
		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			group.getGroupId(), TEST_MODEL, true);

		SocialActivityDefinition defaultActivityDefinition =
			SocialConfigurationUtil.getActivityDefinition(TEST_MODEL, 1);

		SocialActivityDefinition activityDefinition =
			SocialActivitySettingLocalServiceUtil.getActivityDefinition(
				group.getGroupId(), TEST_MODEL, 1);

		Assert.assertEquals(defaultActivityDefinition, activityDefinition);

		List<SocialActivityDefinition> defaultActivityDefinitions =
			SocialConfigurationUtil.getActivityDefinitions(TEST_MODEL);

		Assert.assertNotNull(defaultActivityDefinitions);
		Assert.assertFalse(
			defaultActivityDefinitions.toString(),
			defaultActivityDefinitions.isEmpty());

		List<SocialActivityDefinition> activityDefinitions =
			SocialActivitySettingLocalServiceUtil.getActivityDefinitions(
				group.getGroupId(), TEST_MODEL);

		Assert.assertNotNull(activityDefinitions);
		Assert.assertFalse(
			activityDefinitions.toString(), activityDefinitions.isEmpty());
		Assert.assertEquals(
			activityDefinitions.toString(), defaultActivityDefinitions.size(),
			activityDefinitions.size());
		Assert.assertTrue(
			activityDefinitions.toString(),
			activityDefinitions.contains(defaultActivityDefinition));
	}

	@Test
	public void testUpdateActivitySettings() throws Exception {
		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			group.getGroupId(), TEST_MODEL, true);

		long classNameId = PortalUtil.getClassNameId(TEST_MODEL);

		Assert.assertTrue(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				group.getGroupId(), classNameId));

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			group.getGroupId(), TEST_MODEL, false);

		Assert.assertFalse(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				group.getGroupId(), classNameId));
		Assert.assertTrue(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				group.getGroupId(), classNameId, 1));

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			group.getGroupId(), TEST_MODEL, 1, false);

		Assert.assertFalse(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				group.getGroupId(), classNameId, 1));
	}

}