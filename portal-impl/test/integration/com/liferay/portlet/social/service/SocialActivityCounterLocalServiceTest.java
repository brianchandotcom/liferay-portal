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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;
import com.liferay.portlet.social.util.test.SocialActivityTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
@Sync
public class SocialActivityCounterLocalServiceTest
	extends BaseSocialActivityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, true);
	}

	@Test
	public void testAddActivity() throws Exception {
		SocialActivityTestUtil.addActivity(
			_creatorUser, _group, _assetEntry, 1);

		SocialActivityCounter contribution =
			SocialActivityTestUtil.getActivityCounter(
				_group.getGroupId(),
				SocialActivityCounterConstants.NAME_CONTRIBUTION, _creatorUser);

		Assert.assertNull(contribution);

		SocialActivityCounter participation =
			SocialActivityTestUtil.getActivityCounter(
				_group.getGroupId(),
				SocialActivityCounterConstants.NAME_PARTICIPATION,
				_creatorUser);

		Assert.assertEquals(2, participation.getCurrentValue());

		SocialActivityTestUtil.addActivity(_actorUser, _group, _assetEntry, 2);

		contribution = SocialActivityTestUtil.getActivityCounter(
			_group.getGroupId(),
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _creatorUser);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(1, contribution.getCurrentValue());

		participation = SocialActivityTestUtil.getActivityCounter(
			_group.getGroupId(),
			SocialActivityCounterConstants.NAME_PARTICIPATION, _actorUser);

		Assert.assertNotNull(participation);
		Assert.assertEquals(1, participation.getCurrentValue());

		SocialActivityLimit activityLimit =
			SocialActivityTestUtil.getActivityLimit(
				_group.getGroupId(), _actorUser, _assetEntry, 2,
				SocialActivityCounterConstants.NAME_PARTICIPATION);

		Assert.assertNotNull(activityLimit);
		Assert.assertEquals(1, activityLimit.getCount());

		SocialActivityTestUtil.addActivity(_actorUser, _group, _assetEntry, 2);

		activityLimit = SocialActivityTestUtil.getActivityLimit(
			_group.getGroupId(), _actorUser, _assetEntry, 2,
			SocialActivityCounterConstants.NAME_PARTICIPATION);

		Assert.assertNotNull(activityLimit);
		Assert.assertEquals(2, activityLimit.getCount());
	}

	@Test
	public void testToggleActivities() throws Exception {
		SocialActivityTestUtil.addActivity(
			_creatorUser, _group, _assetEntry, 1);

		SocialActivityTestUtil.addActivity(_actorUser, _group, _assetEntry, 2);

		SocialActivityCounter contribution =
			SocialActivityTestUtil.getActivityCounter(
				_group.getGroupId(),
				SocialActivityCounterConstants.NAME_CONTRIBUTION, _creatorUser);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(1, contribution.getCurrentValue());

		List<SocialActivityCounter> counters =
			SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(
				_group.getGroupId(), "asset.test.2",
				SocialCounterPeriodUtil.getStartPeriod(), -1);

		Assert.assertEquals(1, counters.size());

		SocialActivityCounterLocalServiceUtil.disableActivityCounters(
			_assetEntry.getClassName(), _assetEntry.getClassPK());

		contribution = SocialActivityTestUtil.getActivityCounter(
			_group.getGroupId(),
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _creatorUser);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(0, contribution.getCurrentValue());

		SocialActivityCounter counter =
			SocialActivityTestUtil.getActivityCounter(
				_group.getGroupId(), "asset.test.2", _assetEntry);

		Assert.assertNotNull(counter);
		Assert.assertEquals(false, counter.isActive());

		counters =
			SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(
				_group.getGroupId(), "asset.test.2",
				SocialCounterPeriodUtil.getStartPeriod(), -1);

		Assert.assertEquals(0, counters.size());

		SocialActivityCounterLocalServiceUtil.enableActivityCounters(
			_assetEntry.getClassName(), _assetEntry.getClassPK());

		contribution = SocialActivityTestUtil.getActivityCounter(
			_group.getGroupId(),
			SocialActivityCounterConstants.NAME_CONTRIBUTION, _creatorUser);

		Assert.assertNotNull(contribution);
		Assert.assertEquals(1, contribution.getCurrentValue());

		counter = SocialActivityTestUtil.getActivityCounter(
			_group.getGroupId(), "asset.test.2", _assetEntry);

		Assert.assertNotNull(counter);
		Assert.assertEquals(true, counter.isActive());

		counters =
			SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(
				_group.getGroupId(), "asset.test.2",
				SocialCounterPeriodUtil.getStartPeriod(), -1);

		Assert.assertEquals(1, counters.size());
	}

}