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

package com.liferay.dynamic.data.mapping.internal.security.permission.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.test.BaseDDMServiceTestCase;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
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
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceRecordModelResourcePermissionTest
	extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Class<?> formInstanceClass = DDMFormInstance.class;

		_classNameId = PortalUtil.getClassNameId(formInstanceClass.getName());

		_siteUser = UserTestUtil.addUser(group.getGroupId());

		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testUpdateApprovedFormInstanceRecordByOwnerShouldBeFalse()
		throws Exception {

		DDMFormInstance formInstance = createFormInstance();

		DDMStructure structure = formInstance.getStructure();

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			structure.getDDMForm());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, _siteUser.getUserId());

		DDMFormInstanceRecord formInstanceRecord =
			DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
				_siteUser.getUserId(), group.getGroupId(),
				formInstance.getFormInstanceId(), ddmFormValues,
				serviceContext);

		Assert.assertFalse(
			_ddmFormInstanceRecordModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				formInstanceRecord, ActionKeys.UPDATE));
	}

	@Test
	public void testUpdateDraftFormInstanceRecordByOwnerShouldBeTrue()
		throws Exception {

		DDMFormInstance formInstance = createFormInstance();

		DDMStructure structure = formInstance.getStructure();

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			structure.getDDMForm());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, _siteUser.getUserId());

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);
		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DDMFormInstanceRecord formInstanceRecord =
			DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
				_siteUser.getUserId(), group.getGroupId(),
				formInstance.getFormInstanceId(), ddmFormValues,
				serviceContext);

		Assert.assertTrue(
			_ddmFormInstanceRecordModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				formInstanceRecord, ActionKeys.UPDATE));
	}

	protected DDMFormInstance createFormInstance() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return DDMFormInstanceLocalServiceUtil.addFormInstance(
			structure.getUserId(), structure.getGroupId(),
			structure.getStructureId(), structure.getNameMap(),
			structure.getNameMap(), settingsDDMFormValues, serviceContext);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteUser));
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(_siteUser.getUserId());
	}

	private static long _classNameId;

	@Inject(
		filter = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
		type = ModelResourcePermission.class
	)
	private ModelResourcePermission<DDMFormInstanceRecord>
		_ddmFormInstanceRecordModelResourcePermission;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteUser;

}