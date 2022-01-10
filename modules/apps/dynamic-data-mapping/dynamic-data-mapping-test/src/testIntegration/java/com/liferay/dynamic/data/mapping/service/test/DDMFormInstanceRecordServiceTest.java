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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceRecordServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		DDMStructure ddmStructure = addStructure(
			0, PortalUtil.getClassNameId(DDMFormInstance.class), null,
			RandomTestUtil.randomString(), null,
			read("ddm-structure-text-field.xsd"),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		_ddmFormInstance = _ddmFormInstanceLocalService.addFormInstance(
			TestPropsValues.getUserId(), ddmStructure.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getNameMap(),
			ddmStructure.getNameMap(),
			DDMFormInstanceTestUtil.createSettingsDDMFormValues(),
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId()));

		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = TestPropsValues.getUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testGetFormInstanceRecords1() throws Exception {
		try {
			_setUser(_defaultUser);

			_ddmFormInstanceRecordService.getFormInstanceRecords(
				_ddmFormInstance.getFormInstanceId());

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_setUser(_user);

		_ddmFormInstanceRecordService.getFormInstanceRecords(
			_ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceRecords2() throws Exception {
		try {
			_setUser(_defaultUser);

			_ddmFormInstanceRecordService.getFormInstanceRecords(
				_ddmFormInstance.getFormInstanceId(),
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_setUser(_user);

		_ddmFormInstanceRecordService.getFormInstanceRecords(
			_ddmFormInstance.getFormInstanceId(), WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Test
	public void testGetFormInstanceRecordsCount() throws Exception {
		try {
			_setUser(_defaultUser);

			_ddmFormInstanceRecordService.getFormInstanceRecordsCount(
				_ddmFormInstance.getFormInstanceId());

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_setUser(_user);

		_ddmFormInstanceRecordService.getFormInstanceRecordsCount(
			_ddmFormInstance.getFormInstanceId());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	@DeleteAfterTestRun
	private DDMFormInstance _ddmFormInstance;

	@Inject
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Inject
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	private User _defaultUser;
	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}