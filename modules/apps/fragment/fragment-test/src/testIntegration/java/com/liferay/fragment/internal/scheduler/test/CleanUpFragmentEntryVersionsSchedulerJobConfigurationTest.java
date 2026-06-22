/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.fragment.constants.FragmentEntryVersionConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class CleanUpFragmentEntryVersionsSchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-75909")
	public void testCleanUpFragmentEntryVersions() throws Throwable {
		FragmentEntry fragmentEntry1 =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
			FragmentEntryVersionConstants.MAX_COUNT +
				RandomTestUtil.randomInt(1, 10),
			CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry1);

		long ctCollectionId = RandomTestUtil.randomLong();

		FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
			FragmentEntryVersionConstants.MAX_COUNT +
				RandomTestUtil.randomInt(1, 10),
			ctCollectionId, fragmentEntry1);

		FragmentEntry fragmentEntry2 =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
			FragmentEntryVersionConstants.MAX_COUNT - 1,
			CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry2);

		List<Integer> ctCollectionVersions =
			FragmentEntryVersionTestUtil.getVersions(
				ctCollectionId, fragmentEntry1);
		List<Integer> versions = FragmentEntryVersionTestUtil.getVersions(
			fragmentEntry1);

		UnsafeRunnable<Exception> jobExecutorUnsafeRunnable =
			_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

		jobExecutorUnsafeRunnable.run();

		Assert.assertEquals(
			ctCollectionVersions.subList(
				ctCollectionVersions.size() -
					FragmentEntryVersionConstants.MAX_COUNT,
				ctCollectionVersions.size()),
			FragmentEntryVersionTestUtil.getVersions(
				ctCollectionId, fragmentEntry1));
		Assert.assertEquals(
			versions.subList(
				versions.size() - FragmentEntryVersionConstants.MAX_COUNT,
				versions.size()),
			FragmentEntryVersionTestUtil.getVersions(fragmentEntry1));
		Assert.assertEquals(
			FragmentEntryVersionConstants.MAX_COUNT,
			FragmentEntryVersionTestUtil.countVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry2));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.fragment.internal.scheduler.CleanUpFragmentEntryVersionsSchedulerJobConfiguration"
	)
	private SchedulerJobConfiguration _schedulerJobConfiguration;

}