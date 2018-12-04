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
import com.liferay.data.engine.executor.DEGetRequestExecutor;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionLocalService;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
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
				_deDataDefinitionLocalService.save(deDataDefinitionSaveRequest);

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

			_deDataDefinitionLocalService.delete(deDataDefinitionDeleteRequest);
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

	@Inject(type = DEDataDefinitionLocalService.class)
	private DEDataDefinitionLocalService _deDataDefinitionLocalService;

	@Inject(type = DEGetRequestExecutor.class)
	private DEGetRequestExecutor _deGetRequestExecutor;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}