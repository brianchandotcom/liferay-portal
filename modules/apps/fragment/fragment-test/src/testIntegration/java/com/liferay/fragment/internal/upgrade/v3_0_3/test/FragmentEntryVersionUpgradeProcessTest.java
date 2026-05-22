/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

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

		_createDateTimestamp = new Timestamp(
			System.currentTimeMillis() + 3600000L);
		_modifiedDateCounter = System.currentTimeMillis() + 3600000L;
		_versionCounter = 1000;
	}

	@Test
	public void testUpgrade() throws Exception {
		_testUpgradeDeletesOldVersions();
		_testUpgradeSkipsCleanup();
	}

	private FragmentEntry _addFragmentEntry() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		return _fragmentEntryLocalService.addFragmentEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
			false, false,
			com.liferay.fragment.constants.FragmentConstants.TYPE_COMPONENT,
			null, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private long _countFragmentEntryVersions(FragmentEntry fragmentEntry)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) as count from FragmentEntryVersion where " +
					"fragmentEntryId = ?")) {

			preparedStatement.setLong(1, fragmentEntry.getFragmentEntryId());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("count");
				}

				return 0;
			}
		}
	}

	private List<Integer> _getFragmentEntryVersions(FragmentEntry fragmentEntry)
		throws Exception {

		List<Integer> versions = new ArrayList<>();

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select version from FragmentEntryVersion where " +
					"fragmentEntryId = ? order by version")) {

			preparedStatement.setLong(1, fragmentEntry.getFragmentEntryId());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					versions.add(resultSet.getInt("version"));
				}
			}
		}

		return versions;
	}

	private void _insertFragmentEntryVersions(
			int count, FragmentEntry fragmentEntry)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"insert into FragmentEntryVersion (mvccVersion, ",
						"ctCollectionId, fragmentEntryVersionId, version, ",
						"fragmentEntryId, groupId, companyId, userId, ",
						"createDate, modifiedDate, name, status) values (0, ",
						"0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			for (int i = 0; i < count; i++) {
				Timestamp modifiedDateTimestamp = new Timestamp(
					_modifiedDateCounter);

				_modifiedDateCounter += 1000L;

				preparedStatement.setLong(
					1,
					_counterLocalService.increment(
						FragmentEntryVersion.class.getName()));
				preparedStatement.setInt(2, _versionCounter++);
				preparedStatement.setLong(
					3, fragmentEntry.getFragmentEntryId());
				preparedStatement.setLong(4, fragmentEntry.getGroupId());
				preparedStatement.setLong(5, fragmentEntry.getCompanyId());
				preparedStatement.setLong(6, fragmentEntry.getUserId());
				preparedStatement.setTimestamp(7, _createDateTimestamp);
				preparedStatement.setTimestamp(8, modifiedDateTimestamp);
				preparedStatement.setString(9, RandomTestUtil.randomString());
				preparedStatement.setInt(10, WorkflowConstants.STATUS_APPROVED);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
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

	private void _testUpgradeDeletesOldVersions() throws Exception {
		FragmentEntry fragmentEntry = _addFragmentEntry();

		_insertFragmentEntryVersions(20, fragmentEntry);

		_runUpgrade();

		List<Integer> expectedVersions = new ArrayList<>(
			FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT);

		for (int version =
				_versionCounter -
					FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT;
			 version < _versionCounter; version++) {

			expectedVersions.add(version);
		}

		Assert.assertEquals(
			expectedVersions, _getFragmentEntryVersions(fragmentEntry));
	}

	private void _testUpgradeSkipsCleanup() throws Exception {
		int versionsCount = 5;

		FragmentEntry fragmentEntry = _addFragmentEntry();

		long initialCount = _countFragmentEntryVersions(fragmentEntry);

		_insertFragmentEntryVersions(versionsCount, fragmentEntry);

		_runUpgrade();

		Assert.assertEquals(
			versionsCount + initialCount,
			_countFragmentEntryVersions(fragmentEntry));
	}

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CounterLocalService _counterLocalService;

	private Timestamp _createDateTimestamp;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private long _modifiedDateCounter;

	@Inject
	private MultiVMPool _multiVMPool;

	private int _versionCounter;

}