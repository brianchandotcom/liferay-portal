/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.GroupCompanyGroupKeyUpgradeProcess;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class GroupCompanyGroupKeyUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_updateGroupKey(String.valueOf(TestPropsValues.getCompanyId()));
	}

	@Test
	@TestInfo("LPD-105635")
	public void testUpgrade() throws Exception {
		_updateGroupKey(GroupConstants.GLOBAL);

		_group = GroupTestUtil.addGroup();

		UpgradeProcess upgradeProcess =
			new GroupCompanyGroupKeyUpgradeProcess();

		upgradeProcess.upgrade();

		CacheRegistryUtil.clear();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		String companyIdString = String.valueOf(TestPropsValues.getCompanyId());

		Assert.assertEquals(companyIdString, companyGroup.getGroupKey());
		Assert.assertEquals(
			companyGroup,
			_groupLocalService.loadFetchGroup(
				TestPropsValues.getCompanyId(), companyIdString));

		Group regularGroup = _groupLocalService.getGroup(_group.getGroupId());

		Assert.assertEquals(_group.getGroupKey(), regularGroup.getGroupKey());
	}

	private void _updateGroupKey(String groupKey) throws Exception {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			StringBundler.concat(
				"update Group_ set groupKey = '", groupKey,
				"' where groupId = ", companyGroup.getGroupId()));

		CacheRegistryUtil.clear();
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}