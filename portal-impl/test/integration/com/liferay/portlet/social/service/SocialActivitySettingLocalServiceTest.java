/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SocialActivitySettingLocalServiceTest {

	@Before
	public void setUp() throws Exception {

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/test-activity.xml");

		String xml = new String(FileUtil.getBytes(inputStream));

		SocialConfigurationUtil.read(
			clazz.getClassLoader(), new String[] {xml});

		defaultActivityDefinition =
			SocialConfigurationUtil.getActivityDefinition(TEST_MODEL, 1);

		guestGroup = GroupLocalServiceUtil.fetchGroup(
			PortalUtil.getDefaultCompanyId(), GroupConstants.GUEST);
	}

	@Test
	public void testGetActivityDefinition() throws SystemException {
		SocialActivityDefinition activityDefinition =
			SocialActivitySettingLocalServiceUtil.getActivityDefinition(
				guestGroup.getGroupId(), TEST_MODEL, 1);

		Assert.assertEquals(defaultActivityDefinition, activityDefinition);

		List<SocialActivityDefinition> activityDefinitions =
			SocialActivitySettingLocalServiceUtil.getActivityDefinitions(
				guestGroup.getGroupId(), TEST_MODEL);

		Assert.assertNotNull(activityDefinitions);
		Assert.assertEquals(1, activityDefinitions.size());
		Assert.assertEquals(
			defaultActivityDefinition, activityDefinitions.get(0));
	}

	@Test
	public void testUpdateActivitySettings()
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(TEST_MODEL);

		boolean enabled = SocialActivitySettingLocalServiceUtil.isEnabled(
			guestGroup.getGroupId(), classNameId);

		Assert.assertFalse(enabled);

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			guestGroup.getGroupId(), TEST_MODEL, true);

		enabled = SocialActivitySettingLocalServiceUtil.isEnabled(
			guestGroup.getGroupId(), classNameId);

		Assert.assertTrue(enabled);
	}

	private static final String TEST_MODEL = "TEST_MODEL";

	private SocialActivityDefinition defaultActivityDefinition;

	private Group guestGroup;

}