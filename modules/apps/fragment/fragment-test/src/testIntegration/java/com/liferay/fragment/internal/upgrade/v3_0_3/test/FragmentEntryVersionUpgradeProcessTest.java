/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.fragment.constants.FragmentEntryVersionConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
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
	@TestInfo("LPD-75909")
	public void testUpgrade() throws Exception {
		for (int count : new int[] {1, 10, 11, 20}) {
			_testUpgrade(0, count);
			_testUpgrade(count, 1);
		}
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

	private void _testUpgrade(
			int ctCollectionVersionsCount, int productionVersionsCount)
		throws Exception {

		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		List<Integer> productionVersions = new ArrayList<>(
			FragmentEntryVersionTestUtil.getVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry));

		long ctCollectionId = RandomTestUtil.randomLong();
		List<Integer> ctCollectionVersions = new ArrayList<>();

		if (ctCollectionVersionsCount > 0) {
			ctCollectionVersions =
				FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
					ctCollectionVersionsCount, ctCollectionId, fragmentEntry);
		}

		productionVersions.addAll(
			FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
				productionVersionsCount - 1,
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry));

		_runUpgrade();

		Assert.assertEquals(
			productionVersions.subList(
				Math.max(
					0,
					productionVersions.size() -
						FragmentEntryVersionConstants.
							FRAGMENT_ENTRY_VERSIONS_COUNT_MAX),
				productionVersions.size()),
			FragmentEntryVersionTestUtil.getVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry));

		if (ctCollectionVersionsCount > 0) {
			Assert.assertEquals(
				ctCollectionVersions.subList(
					Math.max(
						0,
						ctCollectionVersions.size() -
							FragmentEntryVersionConstants.
								FRAGMENT_ENTRY_VERSIONS_COUNT_MAX),
					ctCollectionVersions.size()),
				FragmentEntryVersionTestUtil.getVersions(
					ctCollectionId, fragmentEntry));
		}
	}

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}