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

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.exception.LockedSegmentsExperimentException;
import com.liferay.segments.exception.SegmentsExperimentGoalException;
import com.liferay.segments.exception.SegmentsExperimentNameException;
import com.liferay.segments.exception.SegmentsExperimentStatusException;
import com.liferay.segments.exception.WinnerSegmentsExperienceException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentModel;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class SegmentsExperimentLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddSegmentsExperiment() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment expectedSegmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment actualSegmentsExperiment =
			_segmentsExperimentLocalService.getSegmentsExperiment(
				expectedSegmentsExperiment.getSegmentsExperimentId());

		Assert.assertNotNull(actualSegmentsExperiment);
		Assert.assertEquals(
			expectedSegmentsExperiment.getSegmentsExperimentKey(),
			actualSegmentsExperiment.getSegmentsExperimentKey());
		Assert.assertEquals(
			expectedSegmentsExperiment.getSegmentsExperienceId(),
			actualSegmentsExperiment.getSegmentsExperienceId());
		Assert.assertEquals(
			expectedSegmentsExperiment.getSegmentsEntryId(),
			actualSegmentsExperiment.getSegmentsEntryId());
		Assert.assertEquals(
			expectedSegmentsExperiment.getName(),
			actualSegmentsExperiment.getName());
		Assert.assertEquals(
			expectedSegmentsExperiment.getDescription(),
			actualSegmentsExperiment.getDescription());
		Assert.assertEquals(
			expectedSegmentsExperiment.getStatus(),
			actualSegmentsExperiment.getStatus());
		Assert.assertEquals(0, actualSegmentsExperiment.getStatus());
		Assert.assertEquals(
			expectedSegmentsExperiment.getTypeSettings(),
			actualSegmentsExperiment.getTypeSettings());

		List<SegmentsExperimentRel> segmentsExperimentRels =
			_segmentsExperimentRelLocalService.getSegmentsExperimentRels(
				actualSegmentsExperiment.getSegmentsExperimentId());

		Assert.assertEquals(
			segmentsExperimentRels.toString(), 1,
			segmentsExperimentRels.size());

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRels.get(0);

		Assert.assertEquals(
			actualSegmentsExperiment.getSegmentsExperienceId(),
			segmentsExperimentRel.getSegmentsExperienceId());
	}

	@Test(expected = SegmentsExperimentNameException.class)
	public void testAddSegmentsExperimentWithEmptyName() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), StringPool.BLANK,
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testAddSegmentsExperimentWithExistingExperimentInDraft()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testAddSegmentsExperimentWithExistingExperimentInPaused()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_PAUSED);

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testAddSegmentsExperimentWithExistingExperimentInRunning()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentGoalException.class)
	public void testAddSegmentsExperimentWithInvalidGoal() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentNameException.class)
	public void testAddSegmentsExperimentWithNullName() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), null,
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testDeleteSegmentsExperiments() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_addSegmentsExperiment(segmentsExperience);

		_segmentsExperimentLocalService.deleteSegmentsExperiments(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK());

		Assert.assertTrue(
			ListUtil.isNull(
				_segmentsExperimentLocalService.getSegmentsExperiments(
					segmentsExperience.getSegmentsExperienceId(),
					segmentsExperience.getClassNameId(),
					segmentsExperience.getClassPK())));
	}

	@Test
	public void testFetchSegmentsExperiment() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				segmentsExperiment.getSegmentsEntryId(),
				segmentsExperiment.getClassNameId(),
				segmentsExperiment.getClassPK(),
				RandomTestUtil.randomLocaleStringMap(), false,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperience.getSegmentsExperienceId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNull(
			_segmentsExperimentLocalService.fetchSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				new int[] {SegmentsExperimentConstants.STATUS_RUNNING}));
		Assert.assertNotNull(
			_segmentsExperimentLocalService.fetchSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				new int[] {SegmentsExperimentConstants.STATUS_DRAFT}));
	}

	@Test
	public void testGetSegmentsEntrySegmentsExperiments() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		Layout layout = LayoutTestUtil.addLayout(_group);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), segmentsEntry.getSegmentsEntryId(),
				classNameId, layout.getPlid());

		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), segmentsEntry.getSegmentsEntryId(),
				classNameId, layout.getPlid());

		SegmentsExperiment segmentsExperiment1 = _addSegmentsExperiment(
			segmentsExperience1);

		SegmentsExperiment segmentsExperiment2 = _addSegmentsExperiment(
			segmentsExperience2);

		List<SegmentsExperiment> segmentsExperiments =
			_segmentsExperimentLocalService.getSegmentsEntrySegmentsExperiments(
				segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			segmentsExperiments.toString(), 2, segmentsExperiments.size());

		Stream<SegmentsExperiment> stream = segmentsExperiments.stream();

		long[] segmentsExperimentIds = stream.mapToLong(
			SegmentsExperimentModel::getSegmentsExperimentId
		).toArray();

		Assert.assertTrue(
			ArrayUtil.containsAll(
				segmentsExperimentIds,
				new long[] {
					segmentsExperiment1.getSegmentsExperimentId(),
					segmentsExperiment2.getSegmentsExperimentId()
				}));
	}

	@Test
	public void testGetSegmentsExperiments() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
		Layout layout = LayoutTestUtil.addLayout(_group);

		SegmentsExperiment segmentsExperimentDefault =
			SegmentsTestUtil.addSegmentsExperiment(
				_group.getGroupId(), SegmentsExperienceConstants.ID_DEFAULT,
				classNameId, layout.getPlid());

		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), classNameId, layout.getPlid());

		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), classNameId, layout.getPlid());

		SegmentsExperiment segmentsExperiment1 = _addSegmentsExperiment(
			segmentsExperience1);

		SegmentsExperiment segmentsExperiment2 = _addSegmentsExperiment(
			segmentsExperience2);

		List<SegmentsExperiment> segmentsExperiments =
			_segmentsExperimentLocalService.getSegmentsExperiments(
				layout.getGroupId(), classNameId, layout.getPlid());

		Assert.assertEquals(
			segmentsExperiments.toString(), 3, segmentsExperiments.size());

		Stream<SegmentsExperiment> stream = segmentsExperiments.stream();

		long[] segmentsExperimentIds = stream.mapToLong(
			SegmentsExperimentModel::getSegmentsExperimentId
		).toArray();

		Assert.assertTrue(
			ArrayUtil.containsAll(
				segmentsExperimentIds,
				new long[] {
					segmentsExperimentDefault.getSegmentsExperimentId(),
					segmentsExperiment1.getSegmentsExperimentId(),
					segmentsExperiment2.getSegmentsExperimentId()
				}));
	}

	@Test
	public void testHasSegmentsExperiment() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				segmentsExperiment.getSegmentsEntryId(),
				segmentsExperiment.getClassNameId(),
				segmentsExperiment.getClassPK(),
				RandomTestUtil.randomLocaleStringMap(), false,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperience.getSegmentsExperienceId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertFalse(
			_segmentsExperimentLocalService.hasSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				new int[] {SegmentsExperimentConstants.STATUS_RUNNING}));
		Assert.assertTrue(
			_segmentsExperimentLocalService.hasSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				new int[] {SegmentsExperimentConstants.STATUS_DRAFT}));
	}

	@Test(expected = LockedSegmentsExperimentException.class)
	public void testUpdateSegmentsExperimentNameInStatusRunning()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment.getSegmentsExperimentId(),
			RandomTestUtil.randomString(), null, null, null);
	}

	@Test
	public void testUpdateSegmentsExperimentStatusToCompletedWithWinnerSegmentsExperience()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperience variantSegmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK());

		variantSegmentsExperience.setActive(false);

		_segmentsExperienceLocalService.updateSegmentsExperience(
			variantSegmentsExperience);

		_segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			variantSegmentsExperience.getSegmentsExperienceId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			variantSegmentsExperience.getSegmentsExperienceId(),
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			variantSegmentsExperience.getSegmentsExperienceId(),
			SegmentsExperimentConstants.STATUS_COMPLETED);

		segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());

		Assert.assertFalse(segmentsExperience.isActive());

		variantSegmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				variantSegmentsExperience.getSegmentsExperienceId());

		Assert.assertTrue(variantSegmentsExperience.isActive());
	}

	@Test(expected = WinnerSegmentsExperienceException.class)
	public void testUpdateSegmentsExperimentStatusToFinishedWithNonexistingWinnerSegmentsExperience()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			RandomTestUtil.nextLong(),
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testUpdateSegmentsExperimentStatusToFinishedWithoutWinnerSegmentsExperience()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperience variantSegmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK());

		variantSegmentsExperience.setActive(false);

		_segmentsExperienceLocalService.updateSegmentsExperience(
			variantSegmentsExperience);

		_segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			variantSegmentsExperience.getSegmentsExperienceId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test
	public void testUpdateSegmentsExperimentStatusToFinishedWithWinnerSegmentsExperience()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperience variantSegmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				segmentsExperience.getGroupId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK());

		variantSegmentsExperience.setActive(false);

		_segmentsExperienceLocalService.updateSegmentsExperience(
			variantSegmentsExperience);

		_segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			variantSegmentsExperience.getSegmentsExperienceId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		segmentsExperiment =
			_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
				segmentsExperiment.getSegmentsExperimentId(),
				variantSegmentsExperience.getSegmentsExperienceId(),
				SegmentsExperimentConstants.STATUS_FINISHED_WINNER);

		Assert.assertEquals(
			segmentsExperiment.getWinnerSegmentsExperienceId(),
			variantSegmentsExperience.getSegmentsExperienceId());
		Assert.assertEquals(
			segmentsExperiment.getWinnerSegmentsExperienceKey(),
			variantSegmentsExperience.getSegmentsExperienceKey());
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testUpdateSegmentsExperimentStatusToPausedWithExistingExperimentInPaused()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_PAUSED);

		SegmentsExperiment newSegmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			newSegmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			newSegmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testUpdateSegmentsExperimentStatusToRunningWithExistingExperimentInRunning()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		SegmentsExperiment newSegmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			newSegmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testUpdateSegmentsExperimentToRunningWithExistingExperimentInRunning()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment segmentsExperiment1 =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		segmentsExperiment1.setStatus(
			SegmentsExperimentConstants.STATUS_TERMINATED);

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment1);

		SegmentsExperiment segmentsExperiment2 =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment2.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		segmentsExperiment1.setStatus(SegmentsExperimentConstants.STATUS_DRAFT);

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment1);

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment1.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentGoalException.class)
	public void testUpdateSegmentsExperimentWithInvalidGoal() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		String invalidGoal =
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel() +
				"_INVALID";

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment.getSegmentsExperimentId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			invalidGoal, StringPool.BLANK);
	}

	@Test(expected = SegmentsExperimentNameException.class)
	public void testUpdateSegmentsExperimentWithInvalidName() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment.getSegmentsExperimentId(), StringPool.BLANK,
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testUpdateSegmentsExperimentWithInvalidStatus()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(), Integer.MIN_VALUE);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testUpdateSegmentsExperimentWithInvalidStatusTransition()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test
	public void testUpdateSegmentsExperimentWithValidGoal() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		SegmentsExperiment updatedSegmentsExperiment =
			_segmentsExperimentLocalService.updateSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentId(),
				segmentsExperiment.getName(),
				segmentsExperiment.getDescription(),
				SegmentsExperimentConstants.Goal.MAX_SCROLL_DEPTH.getLabel(),
				StringPool.BLANK);

		UnicodeProperties typeSettingsProperties =
			updatedSegmentsExperiment.getTypeSettingsProperties();

		String goal = typeSettingsProperties.getProperty("goal");

		Assert.assertEquals(
			SegmentsExperimentConstants.Goal.MAX_SCROLL_DEPTH.getLabel(), goal);
	}

	@Test
	public void testUpdateSegmentsExperimentWithValidName() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();

		SegmentsExperiment updatedSegmentsExperiment =
			_segmentsExperimentLocalService.updateSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentId(), name, description,
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK);

		Assert.assertEquals(name, updatedSegmentsExperiment.getName());
		Assert.assertEquals(
			description, updatedSegmentsExperiment.getDescription());
	}

	@Test
	public void testUpdateSegmentsExperimentWithValidStatus() throws Exception {
		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		SegmentsExperiment updatedSegmentsExperiment =
			_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
				segmentsExperiment.getSegmentsExperimentId(),
				SegmentsExperimentConstants.STATUS_RUNNING);

		Assert.assertEquals(
			SegmentsExperimentConstants.STATUS_RUNNING,
			updatedSegmentsExperiment.getStatus());
	}

	private SegmentsExperience _addSegmentsExperience() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
		Layout layout = LayoutTestUtil.addLayout(_group);

		return SegmentsTestUtil.addSegmentsExperience(
			_group.getGroupId(), classNameId, layout.getPlid());
	}

	private SegmentsExperiment _addSegmentsExperiment() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		return _addSegmentsExperiment(segmentsExperience);
	}

	private SegmentsExperiment _addSegmentsExperiment(
			SegmentsExperience segmentsExperience)
		throws Exception {

		return SegmentsTestUtil.addSegmentsExperiment(
			_group.getGroupId(), segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Inject
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}