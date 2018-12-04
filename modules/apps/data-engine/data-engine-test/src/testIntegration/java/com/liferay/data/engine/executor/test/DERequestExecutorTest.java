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

package com.liferay.data.engine.executor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.executor.DEDeleteRequestExecutor;
import com.liferay.data.engine.executor.DEGetRequestExecutor;
import com.liferay.data.engine.executor.DESaveRequestExecutor;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DERequestExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupOwnerUser(_group);
	}

	@Test(expected = DEDataDefinitionException.class)
	public void testDelete() throws Exception {
		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			"name", "string");

		deDataDefinitionField.addLabels(expectedNameLabels);

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField));

		deDataDefinition.addName(LocaleUtil.US, "Definition 1");
		deDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), deDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deSaveRequestExecutor.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionDeleteRequest.Builder.of(deDataDefinitionId);

			_deDeleteRequestExecutor.execute(deDataDefinitionDeleteRequest);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionGetRequest.Builder.of(deDataDefinitionId);

			_deGetRequestExecutor.execute(deDataDefinitionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGet() throws Exception {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Field 1");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "string");

		deDataDefinitionField1.addLabels(field1Labels);

		Map<String, String> field2Labels = new HashMap() {
			{
				put("en_US", "Field 2");
			}
		};

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "number");

		deDataDefinitionField2.addLabels(field2Labels);

		Map<String, String> field3Labels = new HashMap() {
			{
				put("en_US", "Field 3");
			}
		};

		DEDataDefinitionField deDataDefinitionField3 =
			new DEDataDefinitionField("field3", "date");

		deDataDefinitionField3.addLabels(field3Labels);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(
				deDataDefinitionField1, deDataDefinitionField2,
				deDataDefinitionField3));

		expectedDEDataDefinition.addName(LocaleUtil.US, "Definition 2");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDEDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deSaveRequestExecutor.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(deDataDefinitionId);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionGetRequest.Builder.of(deDataDefinitionId);

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deGetRequestExecutor.execute(deDataDefinitionGetRequest);

			Assert.assertEquals(
				expectedDEDataDefinition,
				deDataDefinitionGetResponse.getDeDataDefinition());

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionDeleteRequest.Builder.of(deDataDefinitionId);

			_deDeleteRequestExecutor.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testInsert() throws Exception {
		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField expectedDEDataDefinitionField1 =
			new DEDataDefinitionField("name", "string");

		expectedDEDataDefinitionField1.addLabels(expectedNameLabels);

		Map<String, String> expectedEmailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		DEDataDefinitionField expectedDEDataDefinitionField2 =
			new DEDataDefinitionField("email", "string");

		expectedDEDataDefinitionField1.addLabels(expectedEmailLabels);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(
				expectedDEDataDefinitionField1,
				expectedDEDataDefinitionField2));

		expectedDEDataDefinition.addDescription(
			LocaleUtil.US, "Contact description");
		expectedDEDataDefinition.addDescription(
			LocaleUtil.BRAZIL, "Descrição do contato");
		expectedDEDataDefinition.addName(LocaleUtil.US, "Contact");
		expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDEDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deSaveRequestExecutor.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(deDataDefinitionId);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionGetRequest.Builder.of(deDataDefinitionId);

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deGetRequestExecutor.execute(deDataDefinitionGetRequest);

			DEDataDefinition deDataDefinition =
				deDataDefinitionGetResponse.getDeDataDefinition();

			Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);

			Role ownerRole = _roleLocalService.getRole(
				_group.getCompanyId(), RoleConstants.OWNER);

			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					_group.getCompanyId(), DEDataDefinition.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(deDataDefinitionId), ownerRole.getRoleId());

			Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionDeleteRequest.Builder.of(deDataDefinitionId);

			_deDeleteRequestExecutor.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testUpdate() throws Exception {
		Map<String, String> expectedTitleLabels = new HashMap() {
			{
				put("pt_BR", "Título");
				put("en_US", "Title");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("title", "string");

		deDataDefinitionField1.addLabels(expectedTitleLabels);
		deDataDefinitionField1.setLocalizable(true);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1));

		expectedDEDataDefinition.addName(LocaleUtil.US, "Story");
		expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Estória");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDEDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deSaveRequestExecutor.execute(deDataDefinitionSaveRequest);

			long dataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(dataDefinitionId);

			Map<String, String> expectedDescriptionLabels = new HashMap() {
				{
					put("pt_BR", "Descrição");
					put("en_US", "Description");
				}
			};

			DEDataDefinitionField deDataDefinitionField2 =
				new DEDataDefinitionField("description", "string");

			deDataDefinitionField2.addLabels(expectedDescriptionLabels);
			deDataDefinitionField2.setLocalizable(true);

			expectedDEDataDefinition = new DEDataDefinition(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));

			expectedDEDataDefinition.setDataDefinitionId(dataDefinitionId);
			expectedDEDataDefinition.addName(LocaleUtil.US, "Story");
			expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Estória");
			expectedDEDataDefinition.setStorageType("json");

			deDataDefinitionSaveRequest =
				DEDataDefinitionSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(),
					expectedDEDataDefinition
				);

			_deSaveRequestExecutor.execute(deDataDefinitionSaveRequest);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deGetRequestExecutor.execute(deDataDefinitionGetRequest);

			DEDataDefinition deDataDefinition =
				deDataDefinitionGetResponse.getDeDataDefinition();

			Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_deDeleteRequestExecutor.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected ModelPermissions createModelPermissions() {
		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, ActionKeys.VIEW);

		return modelPermissions;
	}

	protected ServiceContext createServiceContext(
		Group group, User user, ModelPermissions modelPermissions) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setModelPermissions(modelPermissions);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Inject(type = DEDeleteRequestExecutor.class)
	private DEDeleteRequestExecutor _deDeleteRequestExecutor;

	@Inject(type = DEGetRequestExecutor.class)
	private DEGetRequestExecutor _deGetRequestExecutor;

	@Inject(type = DESaveRequestExecutor.class)
	private DESaveRequestExecutor _deSaveRequestExecutor;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(type = ResourcePermissionLocalService.class)
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}