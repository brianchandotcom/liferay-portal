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

import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SocialActivityCounterLocalServiceTest
	extends BaseSocialActivityTest {

	@BeforeClass
	public static void setUp() throws Exception {
		BaseSocialActivityTest.setUp();
	}

	@After
	public void afterTest() throws Exception {
		BaseSocialActivityTest.tearDown();
	}

	@Before
	public void beforeTest() throws Exception {
		createGroup(TEST_GROUP);

		createUsers();

		createAsset();

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, true);
	}

	@Test
	public void testAddActivity() throws Exception {
		SocialActivityCounterLocalServiceUtil.addActivityCounters(
			createActivity(_userCreator, 1));

		SocialActivityCounter contribution = getActivityCounter(
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _userCreator);

		Assert.assertNull(contribution);

		SocialActivityCounter participation = getActivityCounter(
			SocialActivityCounterConstants.NAME_PARTICIPATION, _userCreator);

		Assert.assertEquals(2, participation.getCurrentValue());

		SocialActivityCounterLocalServiceUtil.addActivityCounters(
			createActivity(_userActor, 2));

		contribution = getActivityCounter(
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _userCreator);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(1, contribution.getCurrentValue());

		participation = getActivityCounter(
			SocialActivityCounterConstants.NAME_PARTICIPATION, _userActor);

		Assert.assertNotNull(participation);
		Assert.assertEquals(1, participation.getCurrentValue());

		SocialActivityLimit activityLimit =  getActivityLimit(
			_userActor, _assetEntry, 2,
			SocialActivityCounterConstants.NAME_PARTICIPATION);

		Assert.assertNotNull(activityLimit);
		Assert.assertEquals(1, activityLimit.getCount());

		SocialActivityCounterLocalServiceUtil.addActivityCounters(
			createActivity(_userActor, 2));

		activityLimit =  getActivityLimit(
			_userActor, _assetEntry, 2,
			SocialActivityCounterConstants.NAME_PARTICIPATION);

		Assert.assertNotNull(activityLimit);
		Assert.assertEquals(2, activityLimit.getCount());
	}

	@Test
	public void testToggleActivities() throws Exception {
		SocialActivityCounterLocalServiceUtil.addActivityCounters(
			createActivity(_userCreator, 1));

		SocialActivityCounterLocalServiceUtil.addActivityCounters(
			createActivity(_userActor, 2));

		SocialActivityCounter contribution = getActivityCounter(
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _userCreator);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(1, contribution.getCurrentValue());

		List<SocialActivityCounter> counters =
			SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(
				_group.getGroupId(), "asset.test.2",
				SocialCounterPeriodUtil.getStartPeriod(), -1);

		Assert.assertEquals(1, counters.size());

		SocialActivityCounterLocalServiceUtil.disableActivityCounters(
			_assetEntry.getClassName(), _assetEntry.getClassPK());

		contribution = getActivityCounter(
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _userCreator);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(0, contribution.getCurrentValue());

		SocialActivityCounter counter = getActivityCounter(
			"asset.test.2", _assetEntry);

		Assert.assertNotNull(counter);
		Assert.assertEquals(false, counter.isActive());

		counters =
			SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(
				_group.getGroupId(), "asset.test.2",
				SocialCounterPeriodUtil.getStartPeriod(), -1);

		Assert.assertEquals(0, counters.size());

		SocialActivityCounterLocalServiceUtil.enableActivityCounters(
			_assetEntry.getClassName(), _assetEntry.getClassPK());

		contribution = getActivityCounter(
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _userCreator);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(1, contribution.getCurrentValue());

		counter = getActivityCounter("asset.test.2", _assetEntry);

		Assert.assertNotNull(counter);
		Assert.assertEquals(true, counter.isActive());

		counters =
			SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(
				_group.getGroupId(), "asset.test.2",
				SocialCounterPeriodUtil.getStartPeriod(), -1);

		Assert.assertEquals(1, counters.size());
	}

}