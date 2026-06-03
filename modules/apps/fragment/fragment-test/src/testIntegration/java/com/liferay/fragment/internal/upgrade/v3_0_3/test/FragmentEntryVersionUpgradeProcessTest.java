/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.fragment.internal.constants.FragmentEntryVersionConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	public void testUpgrade() throws Exception {
		_testUpgradeDeletesFragmentEntryVersions();
		_testUpgradeDeletesFragmentEntryVersionsPerCtCollection();
		_testUpgradePreservesFragmentEntryVersions();
		_testUpgradePreservesFragmentEntryVersionsPerCtCollection();
	}

	private List<Integer> _getFragmentEntryVersions(
			long ctCollectionId, FragmentEntry fragmentEntry)
		throws Exception {

		List<Integer> versions = new ArrayList<>();

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select version from FragmentEntryVersion where " +
					"ctCollectionId = ? and fragmentEntryId = ? order by " +
						"version")) {

			preparedStatement.setLong(1, ctCollectionId);
			preparedStatement.setLong(2, fragmentEntry.getFragmentEntryId());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					versions.add(resultSet.getInt("version"));
				}
			}
		}

		return versions;
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
			int actualCtCollectionVersionsCount,
			int actualProductionVersionsCount,
			int expectedCtCollectionVersionsCount,
			int expectedProductionVersionsCount)
		throws Exception {

		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		List<Integer> initialProductionVersions = _getFragmentEntryVersions(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry);

		long ctCollectionId = RandomTestUtil.randomLong();
		List<Integer> insertedCtCollectionVersions = new ArrayList<>();

		if (actualCtCollectionVersionsCount > 0) {
			insertedCtCollectionVersions =
				FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
					actualCtCollectionVersionsCount, ctCollectionId,
					fragmentEntry);
		}

		List<Integer> insertedProductionVersions =
			FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
				actualProductionVersionsCount - 1,
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry);

		_runUpgrade();

		List<Integer> allProductionVersions = new ArrayList<>(
			initialProductionVersions.size() +
				insertedProductionVersions.size());

		allProductionVersions.addAll(initialProductionVersions);
		allProductionVersions.addAll(insertedProductionVersions);

		Assert.assertEquals(
			allProductionVersions.subList(
				allProductionVersions.size() - expectedProductionVersionsCount,
				allProductionVersions.size()),
			_getFragmentEntryVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry));

		if (actualCtCollectionVersionsCount > 0) {
			Assert.assertEquals(
				insertedCtCollectionVersions.subList(
					insertedCtCollectionVersions.size() -
						expectedCtCollectionVersionsCount,
					insertedCtCollectionVersions.size()),
				_getFragmentEntryVersions(ctCollectionId, fragmentEntry));
		}
	}

	private void _testUpgradeDeletesFragmentEntryVersions() throws Exception {
		_testUpgrade(
			0, 11, 0,
			FragmentEntryVersionConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX);
		_testUpgrade(
			0, 20, 0,
			FragmentEntryVersionConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX);
	}

	private void _testUpgradeDeletesFragmentEntryVersionsPerCtCollection()
		throws Exception {

		_testUpgrade(
			11, 1,
			FragmentEntryVersionConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX, 1);
		_testUpgrade(
			20, 10,
			FragmentEntryVersionConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
			10);
	}

	private void _testUpgradePreservesFragmentEntryVersions() throws Exception {
		_testUpgrade(0, 1, 0, 1);
		_testUpgrade(0, 10, 0, 10);
	}

	private void _testUpgradePreservesFragmentEntryVersionsPerCtCollection()
		throws Exception {

		_testUpgrade(1, 1, 1, 1);
		_testUpgrade(10, 10, 10, 10);
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