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
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ResourcePermissionTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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

		_upgradeProcess.upgrade();

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

		_upgradeProcess.upgrade();

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

		_upgradeProcess.upgrade();

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

		_upgradeProcess.upgrade();

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

		_upgradeProcess.upgrade();

		assertNewResourcePermissionExists();

		assertOldPermissionExists();
	}

	@Test
	public void testUpdateCalEventClassNameIdInVocabularies() throws Exception {
		_calEventClassName = addClassName(
			"com.liferay.portlet.calendar.model.CalEvent");

		addAssetVocabulary(_calEventClassName.getClassNameId());

		_upgradeProcess.upgrade();

		assertNewClassNameIdExists();
	}

	protected void addAssetVocabulary(long classNameId) throws Exception {
		Group group = GroupTestUtil.addGroup();

		StringBundler sb = new StringBundler(3);

		sb.append("multiValued=true\nselectedClassNameIds=");
		sb.append(classNameId);
		sb.append(":-1");

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), sb.toString(),
			ServiceContextTestUtil.getServiceContext());
	}

	protected ClassName addClassName(String value) {
		ClassName className = ClassNameLocalServiceUtil.createClassName(
			CounterLocalServiceUtil.increment());

		className.setValue(value);

		return ClassNameLocalServiceUtil.addClassName(className);
	}

	protected ResourcePermission addResourcePermission(
			long companyId, String name, String primKey, long roleId, int scope)
		throws Exception {

		return ResourcePermissionTestUtil.addResourcePermission(
			companyId, name, primKey, roleId, scope);
	}

	protected void assertNewClassNameIdExists() {
		long calBookingClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			"com.liferay.calendar.model.CalendarBooking");

		EntityCacheUtil.clearCache();

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
				_assetVocabulary.getVocabularyId());

		String settings = assetVocabulary.getSettings();

		boolean containsNewClassNameId = settings.contains(
			String.valueOf(calBookingClassNameId));

		Assert.assertTrue(containsNewClassNameId);
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
		_upgradeProcess = CalendarUpgradeTestUtil.getServiceUpgradeStep(
			"v1_0_4.UpgradeClassNames");
	}

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private ClassName _calEventClassName;

	@DeleteAfterTestRun
	private ResourcePermission _newResourcePermission;

	@DeleteAfterTestRun
	private ResourcePermission _oldResourcePermission;

	private UpgradeProcess _upgradeProcess;

}