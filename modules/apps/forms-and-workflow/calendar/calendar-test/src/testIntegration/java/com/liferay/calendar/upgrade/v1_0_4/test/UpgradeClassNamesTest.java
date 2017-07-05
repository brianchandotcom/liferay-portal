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

package com.liferay.calendar.upgrade.v1_0_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class UpgradeClassNamesTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpUpgradeCalendarResource();
	}

	@Test
	public void testDeletesDuplicatedOldResourcePermissions() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		String primKey = RandomTestUtil.randomString();
		long roleId = RandomTestUtil.randomLong();
		int scope = RandomTestUtil.nextInt();

		_newResourcePermission = addResourcePermission(
			companyId, "com.liferay.calendar", primKey, roleId, scope);
		_oldResourcePermission = addResourcePermission(
			companyId, "com.liferay.portlet.calendar", primKey, roleId, scope);

		_upgradeClassNames.upgrade();

		assertNewResourcePermissionExists();

		assertOldResourcePermissionDoesNotExist();
	}

	@Test
	public void testDoesNotDeleteOldResourcePermissionsWithDifferentCompanyId()
		throws Exception {

		String primKey = RandomTestUtil.randomString();
		long roleId = RandomTestUtil.randomLong();
		int scope = RandomTestUtil.nextInt();

		_newResourcePermission = addResourcePermission(
			RandomTestUtil.randomLong(), "com.liferay.calendar", primKey,
			roleId, scope);
		_oldResourcePermission = addResourcePermission(
			RandomTestUtil.randomLong(), "com.liferay.portlet.calendar",
			primKey, roleId, scope);

		_upgradeClassNames.upgrade();

		assertNewResourcePermissionExists();

		assertOldResourcePermissionDoesNotExist();
	}

	@Test
	public void testDoesNotDeleteOldResourcePermissionsWithDifferentPrimKey()
		throws Exception {

		long companyId = RandomTestUtil.randomLong();
		long roleId = RandomTestUtil.randomLong();
		int scope = RandomTestUtil.nextInt();

		_newResourcePermission = addResourcePermission(
			companyId, "com.liferay.calendar", RandomTestUtil.randomString(),
			roleId, scope);
		_oldResourcePermission = addResourcePermission(
			companyId, "com.liferay.portlet.calendar",
			RandomTestUtil.randomString(), roleId, scope);

		_upgradeClassNames.upgrade();

		assertNewResourcePermissionExists();

		assertOldPermissionExists();
	}

	@Test
	public void testDoesNotDeleteOldResourcePermissionsWithDifferentRoleId()
		throws Exception {

		long companyId = RandomTestUtil.randomLong();
		String primKey = RandomTestUtil.randomString();
		int scope = RandomTestUtil.nextInt();

		_newResourcePermission = addResourcePermission(
			companyId, "com.liferay.calendar", primKey,
			RandomTestUtil.randomLong(), scope);
		_oldResourcePermission = addResourcePermission(
			companyId, "com.liferay.portlet.calendar", primKey,
			RandomTestUtil.randomLong(), scope);

		_upgradeClassNames.upgrade();

		assertNewResourcePermissionExists();

		assertOldPermissionExists();
	}

	@Test
	public void testDoesNotDeleteOldResourcePermissionsWithDifferentScope()
		throws Exception {

		long companyId = RandomTestUtil.randomLong();
		String primKey = RandomTestUtil.randomString();
		long roleId = RandomTestUtil.randomLong();

		_newResourcePermission = addResourcePermission(
			companyId, "com.liferay.calendar", primKey, roleId,
			RandomTestUtil.nextInt());
		_oldResourcePermission = addResourcePermission(
			companyId, "com.liferay.portlet.calendar", primKey, roleId,
			RandomTestUtil.nextInt());

		_upgradeClassNames.upgrade();

		assertNewResourcePermissionExists();

		assertOldPermissionExists();
	}

	protected ResourcePermission addResourcePermission(
			long companyId, String name, String primKey, long roleId, int scope)
		throws Exception {

		return ResourcePermissionTestUtil.addResourcePermission(
			companyId, name, primKey, roleId, scope);
	}

	protected void assertNewResourcePermissionExists() {
		EntityCacheUtil.clearCache(ResourcePermissionImpl.class);

		_newResourcePermission =
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				_newResourcePermission.getResourcePermissionId());

		Assert.assertNotNull(_newResourcePermission);
	}

	protected void assertOldPermissionExists() {
		EntityCacheUtil.clearCache(ResourcePermissionImpl.class);

		_oldResourcePermission =
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				_oldResourcePermission.getResourcePermissionId());

		Assert.assertNotNull(_oldResourcePermission);
	}

	protected void assertOldResourcePermissionDoesNotExist() {
		EntityCacheUtil.clearCache(ResourcePermissionImpl.class);

		_oldResourcePermission =
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				_oldResourcePermission.getResourcePermissionId());

		Assert.assertNull(_oldResourcePermission);
	}

	protected void setUpUpgradeCalendarResource() {
		_upgradeClassNames = CalendarUpgradeTestUtil.getUpgradeStep(
			"v1_0_4.UpgradeClassNames");
	}

	@DeleteAfterTestRun
	private ResourcePermission _newResourcePermission;

	@DeleteAfterTestRun
	private ResourcePermission _oldResourcePermission;

	private UpgradeProcess _upgradeClassNames;

}