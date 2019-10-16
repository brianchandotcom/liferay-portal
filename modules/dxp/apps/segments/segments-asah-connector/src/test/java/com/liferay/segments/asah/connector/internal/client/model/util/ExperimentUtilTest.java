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

		Locale locale = LocaleUtil.ENGLISH;

		Layout layout = _createLayout(locale);

		SegmentsExperiment segmentsExperiment = _createSegmentsExperiment(
			SegmentsExperienceConstants.ID_DEFAULT,
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			SegmentsExperimentConstants.STATUS_DRAFT);

		String dataSourceId = RandomTestUtil.randomString();
		String defaultSegmentsEntryName = RandomTestUtil.randomString();
		String defaultSegmentsExperienceName = RandomTestUtil.randomString();
		String pageURL = RandomTestUtil.randomString();

		Experiment experiment = ExperimentUtil.toExperiment(
			dataSourceId, defaultSegmentsEntryName,
			defaultSegmentsExperienceName, layout, locale, pageURL,
			_segmentsEntryLocalService, _segmentsExperienceLocalService,
			segmentsExperiment);

		Assert.assertEquals(
			segmentsExperiment.getCreateDate(), experiment.getCreateDate());
		Assert.assertEquals(dataSourceId, experiment.getDataSourceId());
		Assert.assertEquals(
			segmentsExperiment.getDescription(), experiment.getDescription());
		Assert.assertEquals(
			SegmentsExperienceConstants.KEY_DEFAULT,
			experiment.getDXPExperienceId());
		Assert.assertEquals(
			defaultSegmentsExperienceName, experiment.getDXPExperienceName());
		Assert.assertEquals(layout.getUuid(), experiment.getDXPLayoutId());
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

		Assert.assertEquals(
			segmentsExperiment.getSegmentsExperimentKey(), experiment.getId());
		Assert.assertEquals(
			segmentsExperiment.getModifiedDate(), experiment.getModifiedDate());
		Assert.assertEquals(segmentsExperiment.getName(), experiment.getName());
		Assert.assertEquals(
			layout.getFriendlyURL(), experiment.getPageRelativePath());
		Assert.assertEquals(layout.getTitle(locale), experiment.getPageTitle());
		Assert.assertEquals(pageURL, experiment.getPageURL());
		Assert.assertNull(experiment.getPublishedDXPVariantId());
	}

	@Test
	public void testToExperimentWithSegmentsExperimentWithNondefaultExperience()
		throws PortalException {

		Locale locale = LocaleUtil.ENGLISH;

		Layout layout = _createLayout(locale);

		SegmentsEntry segmentsEntry = _createSegmentsEntry(locale);

		long segmentsEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			_segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId)
		).thenReturn(
			segmentsEntry
		);

		SegmentsExperience segmentsExperience = _createSegmentsExperience(
			locale, segmentsEntryId);

		long segmentsExperienceId = RandomTestUtil.randomLong();

		Mockito.when(
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperienceId)
		).thenReturn(
			segmentsExperience
		);

		SegmentsExperiment segmentsExperiment = _createSegmentsExperiment(
			segmentsExperienceId,
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			SegmentsExperimentConstants.STATUS_DRAFT);

		String dataSourceId = RandomTestUtil.randomString();
		String pageURL = RandomTestUtil.randomString();

		Experiment experiment = ExperimentUtil.toExperiment(
			dataSourceId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), layout, LocaleUtil.ENGLISH, pageURL,
			_segmentsEntryLocalService, _segmentsExperienceLocalService,
			segmentsExperiment);

		Assert.assertEquals(
			segmentsExperiment.getCreateDate(), experiment.getCreateDate());
		Assert.assertEquals(dataSourceId, experiment.getDataSourceId());
		Assert.assertEquals(
			segmentsExperiment.getDescription(), experiment.getDescription());
		Assert.assertEquals(
			segmentsExperience.getSegmentsExperienceKey(),
			experiment.getDXPExperienceId());
		Assert.assertEquals(
			segmentsExperience.getName(locale),
			experiment.getDXPExperienceName());
		Assert.assertEquals(layout.getUuid(), experiment.getDXPLayoutId());
		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryKey(), experiment.getDXPSegmentId());
		Assert.assertEquals(
			segmentsEntry.getName(locale), experiment.getDXPSegmentName());
		Assert.assertEquals(
			ExperimentStatus.DRAFT, experiment.getExperimentStatus());
		Assert.assertEquals(ExperimentType.AB, experiment.getExperimentType());

		Goal goal = experiment.getGoal();

		GoalMetric goalMetric = goal.getGoalMetric();

		Assert.assertEquals(
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.name(),
			goalMetric.name());

		Assert.assertEquals(
			segmentsExperiment.getSegmentsExperimentKey(), experiment.getId());
		Assert.assertEquals(
			segmentsExperiment.getModifiedDate(), experiment.getModifiedDate());
		Assert.assertEquals(segmentsExperiment.getName(), experiment.getName());
		Assert.assertEquals(
			layout.getFriendlyURL(), experiment.getPageRelativePath());
		Assert.assertEquals(layout.getTitle(locale), experiment.getPageTitle());
		Assert.assertEquals(pageURL, experiment.getPageURL());
		Assert.assertNull(experiment.getPublishedDXPVariantId());
	}

	@Test
	public void testToExperimentWithSegmentsExperimentWithStatusCompleted()
		throws PortalException {

		Locale locale = LocaleUtil.ENGLISH;

		Layout layout = _createLayout(locale);

		SegmentsEntry segmentsEntry = _createSegmentsEntry(locale);

		long segmentsEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			_segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId)
		).thenReturn(
			segmentsEntry
		);

		SegmentsExperience segmentsExperience = _createSegmentsExperience(
			locale, segmentsEntryId);

		long segmentsExperienceId = RandomTestUtil.randomLong();

		Mockito.when(
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperienceId)
		).thenReturn(
			segmentsExperience
		);

		SegmentsExperiment segmentsExperiment = _createSegmentsExperiment(
			segmentsExperienceId,
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			SegmentsExperimentConstants.STATUS_COMPLETED);

		String dataSourceId = RandomTestUtil.randomString();
		String pageURL = RandomTestUtil.randomString();

		Experiment experiment = ExperimentUtil.toExperiment(
			dataSourceId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), layout, LocaleUtil.ENGLISH, pageURL,
			_segmentsEntryLocalService, _segmentsExperienceLocalService,
			segmentsExperiment);

		Assert.assertEquals(
			segmentsExperiment.getCreateDate(), experiment.getCreateDate());
		Assert.assertEquals(dataSourceId, experiment.getDataSourceId());
		Assert.assertEquals(
			segmentsExperiment.getDescription(), experiment.getDescription());
		Assert.assertEquals(
			segmentsExperience.getSegmentsExperienceKey(),
			experiment.getDXPExperienceId());
		Assert.assertEquals(
			segmentsExperience.getName(locale),
			experiment.getDXPExperienceName());
		Assert.assertEquals(layout.getUuid(), experiment.getDXPLayoutId());
		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryKey(), experiment.getDXPSegmentId());
		Assert.assertEquals(
			segmentsEntry.getName(locale), experiment.getDXPSegmentName());
		Assert.assertEquals(
			ExperimentStatus.COMPLETED, experiment.getExperimentStatus());
		Assert.assertEquals(ExperimentType.AB, experiment.getExperimentType());

		Goal goal = experiment.getGoal();

		GoalMetric goalMetric = goal.getGoalMetric();

		Assert.assertEquals(
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.name(),
			goalMetric.name());

		Assert.assertEquals(
			segmentsExperiment.getSegmentsExperimentKey(), experiment.getId());
		Assert.assertEquals(
			segmentsExperiment.getModifiedDate(), experiment.getModifiedDate());
		Assert.assertEquals(segmentsExperiment.getName(), experiment.getName());
		Assert.assertEquals(
			layout.getFriendlyURL(), experiment.getPageRelativePath());
		Assert.assertEquals(layout.getTitle(locale), experiment.getPageTitle());
		Assert.assertEquals(pageURL, experiment.getPageURL());
		Assert.assertEquals(
			segmentsExperiment.getWinnerSegmentsExperienceKey(),
			experiment.getPublishedDXPVariantId());
	}

	private Layout _createLayout(Locale locale) {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			layout
		).getFriendlyURL();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			layout
		).getTitle(
			locale
		);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			layout
		).getUuid();

		return layout;
	}

	private SegmentsEntry _createSegmentsEntry(Locale locale) {
		SegmentsEntry segmentsEntry = Mockito.mock(SegmentsEntry.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsEntry
		).getSegmentsEntryKey();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsEntry
		).getName(
			locale
		);

		return segmentsEntry;
	}

	private SegmentsExperience _createSegmentsExperience(
		Locale locale, long segmentsEntryId) {

		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.doReturn(
			segmentsEntryId
		).when(
			segmentsExperience
		).getSegmentsEntryId();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperience
		).getSegmentsExperienceKey();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperience
		).getName(
			locale
		);

		return segmentsExperience;
	}

	private SegmentsExperiment _createSegmentsExperiment(
		long segmentsExperienceId, String goal, int status) {

		SegmentsExperiment segmentsExperiment = Mockito.mock(
			SegmentsExperiment.class);

		Mockito.doReturn(
			segmentsExperienceId
		).when(
			segmentsExperiment
		).getSegmentsExperienceId();

		Mockito.doReturn(
			RandomTestUtil.nextDate()
		).when(
			segmentsExperiment
		).getCreateDate();

		Mockito.doReturn(
			RandomTestUtil.nextDate()
		).when(
			segmentsExperiment
		).getModifiedDate();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperiment
		).getSegmentsExperimentKey();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperiment
		).getWinnerSegmentsExperienceKey();

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			segmentsExperiment
		).getClassPK();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperiment
		).getName();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			segmentsExperiment
		).getDescription();

		Mockito.doReturn(
			goal
		).when(
			segmentsExperiment
		).getGoal();

		Mockito.doReturn(
			StringPool.BLANK
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