/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v2_8_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcos Martins
 */
@RunWith(Arquillian.class)
public class SegmentsExperimentUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(_group.getGroupId(), 1);

		_addSegmentsExperiment(
			_group.getGroupId(), 1,
			segmentsExperience.getSegmentsExperienceId(), 1);

		_addSegmentsExperiment(
			_group.getGroupId(), 2,
			segmentsExperience.getSegmentsExperienceId(), 2);

		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(_CLASS_NAME)) {
							_upgradeProcess = (UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	@Test
	public void testUpgrade() throws Exception {
		_upgradeProcess.upgrade();

		Assert.assertEquals(
			0, _segmentsExperimentLocalService.getSegmentsExperimentsCount());
	}

	private void _addSegmentsExperiment(
		long groupId, long plid, long segmentsExperienceId,
		long segmentsExperimentId) {

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.createSegmentsExperiment(
				segmentsExperimentId);

		segmentsExperiment.setGroupId(groupId);
		segmentsExperiment.setCreateDate(new Date());
		segmentsExperiment.setSegmentsExperienceId(segmentsExperienceId);
		segmentsExperiment.setPlid(plid);
		segmentsExperiment.setStatus(
			SegmentsExperimentConstants.STATUS_TERMINATED);

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperiment);
	}

	private static final String _CLASS_NAME =
		"com.liferay.segments.internal.upgrade.v2_8_1." +
			"SegmentsExperimentUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.segments.internal.upgrade.registry.SegmentsServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	private UpgradeProcess _upgradeProcess;

}