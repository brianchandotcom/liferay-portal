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

package com.liferay.portal.upgrade.v7_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.dao.db.BaseDB;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.test.rule.ExpectedDBType;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_0_0.UpgradeResourcePermission;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sampsa Sohlman
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class UpgradeResourcePermissionTest extends UpgradeResourcePermission {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() throws Exception {
		for (long resourcePermissionId : _resourcePermissionIds) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermissionId);
		}
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog = "object name already exists:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog = "ORA-00955: name is already used by an existing object",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog = "ERROR: relation ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog = "There is already an index on table 'ResourcePermission' named ",
				expectedType = ExpectedType.PREFIX
			)
		},
		level = "WARN", loggerClass = BaseDB.class
	)
	@Test
	public void testUpgrade() throws Exception {
		String primKey1 = "123";
		long actionIds1 = 4;

		long resourcePermissionId1 = addResourcePermission(
			primKey1, actionIds1);

		String primKey2 = "987_INSTANCE_ABCD";
		long actionIds2 = 3;

		long resourcePermissionId2 = addResourcePermission(
			primKey2, actionIds2);

		upgrade();

		CacheRegistryUtil.clear();

		ResourcePermission resourcePermission1 =
			_resourcePermissionLocalService.getResourcePermission(
				resourcePermissionId1);

		Assert.assertEquals(
			(actionIds1 % 2) == 1, resourcePermission1.isViewActionId());
		Assert.assertEquals(
			resourcePermission1.getPrimKeyId(), GetterUtil.getLong(primKey1));

		ResourcePermission resourcePermission2 =
			_resourcePermissionLocalService.getResourcePermission(
				resourcePermissionId2);

		Assert.assertEquals(
			(actionIds2 % 2) == 1, resourcePermission2.isViewActionId());
		Assert.assertEquals(0, resourcePermission2.getPrimKeyId());
	}

	protected long addResourcePermission(String primKey, long actionIds) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		long resourcePermissionId = _counterLocalService.increment(
			ResourcePermission.class.getName());

		resourcePermission.setResourcePermissionId(resourcePermissionId);

		resourcePermission.setCompanyId(_user.getCompanyId());
		resourcePermission.setName(
			UpgradeResourcePermissionTest.class.getName());
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setPrimKeyId(-1);
		resourcePermission.setOwnerId(_user.getUserId());
		resourcePermission.setActionIds(actionIds);
		resourcePermission.setViewActionId((actionIds % 2) != 1);

		resourcePermission =
			_resourcePermissionLocalService.addResourcePermission(
				resourcePermission);

		_resourcePermissionIds.add(
			resourcePermission.getResourcePermissionId());

		return resourcePermission.getResourcePermissionId();
	}

	@Inject
	private CounterLocalService _counterLocalService;

	private final List<Long> _resourcePermissionIds = new ArrayList<>();

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private User _user;

}