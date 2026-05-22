/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

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
public class FragmentEntryVersionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Throwable {
		_testUpgradeDeletesFragmentEntryVersions();
		_testUpgradePreservesFragmentEntryVersions();
	}

	private void _runUpgrade() throws Exception {
		_entityCache.clearCache();
		_multiVMPool.clear();

		for (UpgradeProcess upgradeProcess :
				UpgradeTestUtil.getUpgradeSteps(
					_upgradeStepRegistrator, new Version(3, 0, 3))) {

			upgradeProcess.upgrade();
		}

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	private void _testUpgradeDeletesFragmentEntryVersions() throws Throwable {
		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		List<Integer> insertedVersions =
			FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
				20, CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry);

		_runUpgrade();

		List<Integer> expectedVersions = insertedVersions.subList(
			insertedVersions.size() -
				FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT,
			insertedVersions.size());

		Assert.assertEquals(
			expectedVersions,
			FragmentEntryVersionTestUtil.getFragmentEntryVersions(
				fragmentEntry));
	}

	private void _testUpgradePreservesFragmentEntryVersions() throws Exception {
		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		int initialCount =
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry);

		int versionsCount = 5;

		FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
			versionsCount, CTConstants.CT_COLLECTION_ID_PRODUCTION,
			fragmentEntry);

		_runUpgrade();

		Assert.assertEquals(
			versionsCount + initialCount,
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry));
	}

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

}