/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.upgrade.v1_3_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class SharingEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_group = GroupTestUtil.addGroup();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-48130")
	public void testUpgrade() throws Exception {
		User toUser = UserTestUtil.addUser();

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), 0, 0,
			toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_runUpgrade();

		Assert.assertTrue(_dbInspector.hasColumn("SharingEntry", "toTicketId"));

		Assert.assertEquals(
			0L, _selectToTicketId(sharingEntry.getSharingEntryId()));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 3, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}
	}

	private long _selectToTicketId(long sharingEntryId) throws Exception {
		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select toTicketId from SharingEntry where sharingEntryId = " +
					"?")) {

			preparedStatement.setLong(1, sharingEntryId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Assert.assertTrue(resultSet.next());

				return resultSet.getLong("toTicketId");
			}
		}
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private Connection _connection;
	private DBInspector _dbInspector;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.sharing.internal.upgrade.registry.SharingServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}