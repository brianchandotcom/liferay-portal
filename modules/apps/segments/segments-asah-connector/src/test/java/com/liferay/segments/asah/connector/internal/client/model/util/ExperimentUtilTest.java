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

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentStatus;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentType;
import com.liferay.segments.asah.connector.internal.client.model.Goal;
import com.liferay.segments.asah.connector.internal.client.model.GoalMetric;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class ExperimentUtilTest {

	@Test
	public void testToExperimentWithSegmentsExperimentWithDefaultExperience()
		throws PortalException {

		long classPK = RandomTestUtil.randomLong();
		Date createDate = RandomTestUtil.nextDate();
		String dataSourceId = RandomTestUtil.randomString();
		String defaultSegmentsEntryName = RandomTestUtil.randomString();
		String defaultSegmentsExperienceName = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		Date modifiedDate = RandomTestUtil.nextDate();
		String name = RandomTestUtil.randomString();
		String layoutFriendlyURL = RandomTestUtil.randomString();
		String layoutTitle = RandomTestUtil.randomString();
		String layoutUuid = RandomTestUtil.randomString();
		String pageURL = RandomTestUtil.randomString();
		String segmentsExperimentKey = RandomTestUtil.randomString();

		Locale locale = LocaleUtil.ENGLISH;

		Layout layout = _createLayout(
			layoutFriendlyURL, locale, layoutTitle, layoutUuid);

		SegmentsExperiment segmentsExperiment = _createSegmentsExperiment(
			classPK, createDate, modifiedDate, name, description,
			SegmentsExperienceConstants.ID_DEFAULT, segmentsExperimentKey,
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK, SegmentsExperimentConstants.STATUS_DRAFT);

		Experiment experiment = ExperimentUtil.toExperiment(
			dataSourceId, defaultSegmentsEntryName,
			defaultSegmentsExperienceName, layout, locale, pageURL,
			_segmentsEntryLocalService, _segmentsExperienceLocalService,
			segmentsExperiment);

		Assert.assertEquals(createDate, experiment.getCreateDate());
		Assert.assertEquals(dataSourceId, experiment.getDataSourceId());
		Assert.assertEquals(description, experiment.getDescription());
		Assert.assertEquals(
			SegmentsExperienceConstants.KEY_DEFAULT,
			experiment.getDXPExperienceId());
		Assert.assertEquals(
			defaultSegmentsExperienceName, experiment.getDXPExperienceName());
		Assert.assertEquals(layoutUuid, experiment.getDXPLayoutId());
		Assert.assertEquals(
			SegmentsEntryConstants.KEY_DEFAULT, experiment.getDXPSegmentId());
		Assert.assertEquals(
			defaultSegmentsEntryName, experiment.getDXPSegmentName());
		Assert.assertEquals(
			ExperimentStatus.DRAFT, experiment.getExperimentStatus());
		Assert.assertEquals(ExperimentType.AB, experiment.getExperimentType());

		Goal goal = experiment.getGoal();

		GoalMetric goalMetric = goal.getGoalMetric();

		Assert.assertEquals(
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.name(),
			goalMetric.name());

		Assert.assertEquals(segmentsExperimentKey, experiment.getId());
		Assert.assertEquals(modifiedDate, experiment.getModifiedDate());
		Assert.assertEquals(name, experiment.getName());
		Assert.assertEquals(
			layoutFriendlyURL, experiment.getPageRelativePath());
		Assert.assertEquals(layoutTitle, experiment.getPageTitle());
		Assert.assertEquals(pageURL, experiment.getPageURL());
	}

	@Test
	public void testToExperimentWithSegmentsExperimentWithNondefaultExperience()
		throws PortalException {

		long classPK = RandomTestUtil.randomLong();
		Date createDate = RandomTestUtil.nextDate();
		String dataSourceId = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		Date modifiedDate = RandomTestUtil.nextDate();
		String name = RandomTestUtil.randomString();
		String layoutFriendlyURL = RandomTestUtil.randomString();
		String layoutTitle = RandomTestUtil.randomString();
		String layoutUuid = RandomTestUtil.randomString();
		String pageURL = RandomTestUtil.randomString();
		long segmentsEntryId = RandomTestUtil.randomLong();
		String segmentsEntryKey = RandomTestUtil.randomString();
		String segmentsEntryName = RandomTestUtil.randomString();
		long segmentsExperienceId = RandomTestUtil.randomLong();
		String segmentsExperienceKey = RandomTestUtil.randomString();
		String segmentsExperienceName = RandomTestUtil.randomString();
		String segmentsExperimentKey = RandomTestUtil.randomString();

		Locale locale = LocaleUtil.ENGLISH;

		Layout layout = _createLayout(
			layoutFriendlyURL, locale, layoutTitle, layoutUuid);

		SegmentsEntry segmentsEntry = _createSegmentsEntry(
			locale, segmentsEntryKey, segmentsEntryName);

		Mockito.when(
			_segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId)
		).thenReturn(
			segmentsEntry
		);

		SegmentsExperience segmentsExperience = _createSegmentsExperience(
			locale, segmentsExperienceName, segmentsEntryId,
			segmentsExperienceKey);

		Mockito.when(
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperienceId)
		).thenReturn(
			segmentsExperience
		);

		SegmentsExperiment segmentsExperiment = _createSegmentsExperiment(
			classPK, createDate, modifiedDate, name, description,
			segmentsExperienceId, segmentsExperimentKey,
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK, SegmentsExperimentConstants.STATUS_DRAFT);

		Experiment experiment = ExperimentUtil.toExperiment(
			dataSourceId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), layout, LocaleUtil.ENGLISH, pageURL,
			_segmentsEntryLocalService, _segmentsExperienceLocalService,
			segmentsExperiment);

		Assert.assertEquals(createDate, experiment.getCreateDate());
		Assert.assertEquals(dataSourceId, experiment.getDataSourceId());
		Assert.assertEquals(description, experiment.getDescription());
		Assert.assertEquals(
			segmentsExperienceKey, experiment.getDXPExperienceId());
		Assert.assertEquals(
			segmentsExperienceName, experiment.getDXPExperienceName());
		Assert.assertEquals(layoutUuid, experiment.getDXPLayoutId());
		Assert.assertEquals(segmentsEntryKey, experiment.getDXPSegmentId());
		Assert.assertEquals(segmentsEntryName, experiment.getDXPSegmentName());
		Assert.assertEquals(
			ExperimentStatus.DRAFT, experiment.getExperimentStatus());
		Assert.assertEquals(ExperimentType.AB, experiment.getExperimentType());

		Goal goal = experiment.getGoal();

		GoalMetric goalMetric = goal.getGoalMetric();

		Assert.assertEquals(
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.name(),
			goalMetric.name());

		Assert.assertEquals(segmentsExperimentKey, experiment.getId());
		Assert.assertEquals(modifiedDate, experiment.getModifiedDate());
		Assert.assertEquals(name, experiment.getName());
		Assert.assertEquals(
			layoutFriendlyURL, experiment.getPageRelativePath());
		Assert.assertEquals(layoutTitle, experiment.getPageTitle());
		Assert.assertEquals(pageURL, experiment.getPageURL());
	}

	private Layout _createLayout(
		String friendlyURL, Locale locale, String title, String uuid) {

		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			friendlyURL
		).when(
			layout
		).getFriendlyURL();

		Mockito.doReturn(
			title
		).when(
			layout
		).getTitle(
			locale
		);

		Mockito.doReturn(
			uuid
		).when(
			layout
		).getUuid();

		return layout;
	}

	private SegmentsEntry _createSegmentsEntry(
		Locale locale, Object segmentsEntryKey, String segmentsEntryName) {

		SegmentsEntry segmentsEntry = Mockito.mock(SegmentsEntry.class);

		Mockito.doReturn(
			segmentsEntryKey
		).when(
			segmentsEntry
		).getSegmentsEntryKey();

		Mockito.doReturn(
			segmentsEntryName
		).when(
			segmentsEntry
		).getName(
			locale
		);

		return segmentsEntry;
	}

	private SegmentsExperience _createSegmentsExperience(
		Locale locale, String name, long segmentsEntryId,
		String segmentsExperienceKey) {

		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.doReturn(
			segmentsExperienceKey
		).when(
			segmentsExperience
		).getSegmentsExperienceKey();

		Mockito.doReturn(
			name
		).when(
			segmentsExperience
		).getName(
			locale
		);

		Mockito.doReturn(
			segmentsEntryId
		).when(
			segmentsExperience
		).getSegmentsEntryId();

		return segmentsExperience;
	}

	private SegmentsExperiment _createSegmentsExperiment(
		long classPK, Date createDate, Date modifiedDate, String name,
		String description, long segmentsExperienceId,
		String segmentsExperimentKey, String goal, String goalTarget,
		int status) {

		SegmentsExperiment segmentsExperiment = Mockito.mock(
			SegmentsExperiment.class);

		Mockito.doReturn(
			classPK
		).when(
			segmentsExperiment
		).getClassPK();

		Mockito.doReturn(
			createDate
		).when(
			segmentsExperiment
		).getCreateDate();

		Mockito.doReturn(
			modifiedDate
		).when(
			segmentsExperiment
		).getModifiedDate();

		Mockito.doReturn(
			name
		).when(
			segmentsExperiment
		).getName();

		Mockito.doReturn(
			description
		).when(
			segmentsExperiment
		).getDescription();

		Mockito.doReturn(
			segmentsExperienceId
		).when(
			segmentsExperiment
		).getSegmentsExperienceId();

		Mockito.doReturn(
			segmentsExperimentKey
		).when(
			segmentsExperiment
		).getSegmentsExperimentKey();

		Mockito.doReturn(
			goal
		).when(
			segmentsExperiment
		).getGoal();

		Mockito.doReturn(
			goalTarget
		).when(
			segmentsExperiment
		).getGoalTarget();

		Mockito.doReturn(
			status
		).when(
			segmentsExperiment
		).getStatus();

		return segmentsExperiment;
	}

	@Mock
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Mock
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}