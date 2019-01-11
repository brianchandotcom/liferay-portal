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
import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class DEDataRecordCollectionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();

		_group = GroupTestUtil.addGroup();

		_siteMember = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		_adminUser = UserTestUtil.addOmniAdminUser();
	}

	@Test
	public void testInsert() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_siteMember, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithBadRequest() throws Exception {
		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).inGroup(
						_group.getGroupId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithBadRequest2() throws Exception {
		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_siteMember.getUserId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithNoDataDefinition() throws Exception {
		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_siteMember.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithNoSuchGroup() throws Exception {
		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, _deDataDefinitionService);

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_siteMember.getUserId()
					).inGroup(
						1
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithNoSuchUser() throws Exception {
		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, _deDataDefinitionService);

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						1
					).inGroup(
						_group.getGroupId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testUpdate() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_siteMember, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				_siteMember, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());
	}

	@Test
	public void testUpdateAfterUpdate() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_siteMember, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			_siteMember, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 3");

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				_siteMember, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEDataRecordCollectionService.class)
	private DEDataRecordCollectionService _deDataRecordCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteMember;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}