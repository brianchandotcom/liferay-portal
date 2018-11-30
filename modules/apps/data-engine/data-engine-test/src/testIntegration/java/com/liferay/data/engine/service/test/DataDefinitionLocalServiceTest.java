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

package com.liferay.data.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.exception.DataDefinitionException;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionField;
import com.liferay.data.engine.service.DataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DataDefinitionGetRequest;
import com.liferay.data.engine.service.DataDefinitionGetResponse;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
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
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DataDefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupOwnerUser(_group);
	}

	@Test(expected = DataDefinitionException.class)
	public void testDelete() throws Exception {
		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionField dataDefinitionField =
			DataDefinitionField.Builder.newBuilder(
				"name", "string"
			).label(
				expectedNameLabels
			).build();

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Arrays.asList(dataDefinitionField)
		).name(
			LocaleUtil.US, "Definition 1"
		).storageType(
			"json"
		).build();

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), dataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.get(dataDefinitionGetRequest);
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

		DataDefinitionField dataDefinitionField1 =
			DataDefinitionField.Builder.newBuilder(
				"field1", "string"
			).label(
				field1Labels
			).build();

		Map<String, String> field2Labels = new HashMap() {
			{
				put("en_US", "Field 2");
			}
		};

		DataDefinitionField dataDefinitionField2 =
			DataDefinitionField.Builder.newBuilder(
				"field2", "number"
			).label(
				field2Labels
			).build();

		Map<String, String> field3Labels = new HashMap() {
			{
				put("en_US", "Field 3");
			}
		};

		DataDefinitionField dataDefinitionField3 =
			DataDefinitionField.Builder.newBuilder(
				"field3", "date"
			).label(
				field3Labels
			).build();

		DataDefinition expectedDataDefinition =
			DataDefinition.Builder.newBuilder(
				Arrays.asList(
					dataDefinitionField1, dataDefinitionField2,
					dataDefinitionField3)
			).name(
				LocaleUtil.US, "Definition 2"
			).storageType(
				"json"
			).build();

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			expectedDataDefinition.setPrimaryKeyObj(dataDefinitionId);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DataDefinitionGetResponse dataDefinitionGetResponse =
				_dataDefinitionLocalService.get(dataDefinitionGetRequest);

			Assert.assertEquals(
				expectedDataDefinition,
				dataDefinitionGetResponse.getDataDefinition());

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
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

		DataDefinitionField expectedDataDefinitionField1 =
			DataDefinitionField.Builder.newBuilder(
				"name", "string"
			).label(
				expectedNameLabels
			).build();

		Map<String, String> expectedEmailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		DataDefinitionField expectedDataDefinitionField2 =
			DataDefinitionField.Builder.newBuilder(
				"email", "string"
			).label(
				expectedEmailLabels
			).build();

		DataDefinition expectedDataDefinition =
			DataDefinition.Builder.newBuilder(
				Arrays.asList(
					expectedDataDefinitionField1, expectedDataDefinitionField2)
			).description(
				LocaleUtil.US, "Contact description"
			).description(
				LocaleUtil.BRAZIL, "Descrição do contato"
			).name(
				LocaleUtil.US, "Contact"
			).name(
				LocaleUtil.BRAZIL, "Contato"
			).storageType(
				"json"
			).build();

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			expectedDataDefinition.setPrimaryKeyObj(dataDefinitionId);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DataDefinitionGetResponse dataDefinitionGetResponse =
				_dataDefinitionLocalService.get(dataDefinitionGetRequest);

			DataDefinition dataDefinition =
				dataDefinitionGetResponse.getDataDefinition();

			Assert.assertEquals(expectedDataDefinition, dataDefinition);

			Role ownerRole = _roleLocalService.getRole(
				_group.getCompanyId(), RoleConstants.OWNER);

			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					_group.getCompanyId(), DataDefinition.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dataDefinitionId), ownerRole.getRoleId());

			Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
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

		DataDefinitionField dataDefinitionField1 =
			DataDefinitionField.Builder.newBuilder(
				"title", "string"
			).label(
				expectedTitleLabels
			).localizable(
				true
			).build();

		DataDefinition expectedDataDefinition =
			DataDefinition.Builder.newBuilder(
				Arrays.asList(dataDefinitionField1)
			).name(
				LocaleUtil.US, "Story"
			).name(
				LocaleUtil.BRAZIL, "Estória"
			).storageType(
				"json"
			).build();

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			expectedDataDefinition.setPrimaryKeyObj(dataDefinitionId);

			Map<String, String> expectedDescriptionLabels = new HashMap() {
				{
					put("pt_BR", "Descrição");
					put("en_US", "Description");
				}
			};

			DataDefinitionField dataDefinitionField2 =
				DataDefinitionField.Builder.newBuilder(
					"description", "string"
				).label(
					expectedDescriptionLabels
				).localizable(
					true
				).build();

			expectedDataDefinition = DataDefinition.Builder.newBuilder(
				Arrays.asList(dataDefinitionField1, dataDefinitionField2)
			).dataDefinitionId(
				dataDefinitionId
			).name(
				LocaleUtil.US, "Story"
			).name(
				LocaleUtil.BRAZIL, "Estória"
			).storageType(
				"json"
			).build();

			dataDefinitionSaveRequest = DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

			_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DataDefinitionGetResponse dataDefinitionGetResponse =
				_dataDefinitionLocalService.get(dataDefinitionGetRequest);

			DataDefinition dataDefinition =
				dataDefinitionGetResponse.getDataDefinition();

			Assert.assertEquals(expectedDataDefinition, dataDefinition);

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
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

	@Inject(type = DataDefinitionLocalService.class)
	private DataDefinitionLocalService _dataDefinitionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(type = ResourcePermissionLocalService.class)
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}