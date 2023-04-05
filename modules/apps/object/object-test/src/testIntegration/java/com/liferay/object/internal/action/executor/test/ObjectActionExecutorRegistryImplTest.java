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

package com.liferay.object.internal.action.executor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.test.action.executor.TestObjectActionExecutorImpl;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class ObjectActionExecutorRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_objectDefinition = _addObjectDefinition(TestPropsValues.getUserId());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectActionExecutorRegistryImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			ObjectActionExecutor.class,
			new TestObjectActionExecutorImpl(
				SetUtil.fromArray(_objectDefinition.getName(), "AccountEntry"),
				CompanyThreadLocal.getCompanyId(),
				_CUSTOM_OBJECT_ACTION_EXECUTOR_KEY),
			new HashMapDictionary<>());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetObjectActionExecutor() {
		Assert.assertNotNull(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY));
		Assert.assertNotNull(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_GROOVY));
		Assert.assertNotNull(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_NOTIFICATION));
		Assert.assertNotNull(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY));
		Assert.assertNotNull(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_WEBHOOK));
		Assert.assertNotNull(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				_CUSTOM_OBJECT_ACTION_EXECUTOR_KEY));
	}

	@Test
	public void testGetObjectActionExecutors() throws Exception {
		_assertObjectActionExecutors(false, 5, "User");
		_assertObjectActionExecutors(true, 6, "AccountEntry");
		_assertObjectActionExecutors(true, 6, _objectDefinition.getName());

		Company company = CompanyTestUtil.addCompany();

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					company.getCompanyId())) {

			User user = UserTestUtil.getAdminUser(company.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
			PrincipalThreadLocal.setName(user.getUserId());

			_assertObjectActionExecutors(false, 5, "User");
			_assertObjectActionExecutors(false, 5, "AccountEntry");

			ObjectDefinition objectDefinition = _addObjectDefinition(
				user.getUserId());

			_assertObjectActionExecutors(false, 5, objectDefinition.getName());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private static ObjectDefinition _addObjectDefinition(long userId)
		throws PortalException {

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			userId, false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			_OBJECT_DEFINITION_NAME, null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, null);
	}

	private void _assertObjectActionExecutors(
		boolean existingCustomObjectExecutor, int expectSizeObjectExecutors,
		String objectDefinitionName) {

		List<ObjectActionExecutor> objectActionExecutors =
			_objectActionExecutorRegistry.getObjectActionExecutors(
				objectDefinitionName);

		Assert.assertEquals(
			objectActionExecutors.toString(), expectSizeObjectExecutors,
			objectActionExecutors.size());

		List<String> objectActionExecutorsKeys = ListUtil.toList(
			objectActionExecutors, ObjectActionExecutor::getKey);

		Assert.assertEquals(
			existingCustomObjectExecutor,
			objectActionExecutorsKeys.contains(
				_CUSTOM_OBJECT_ACTION_EXECUTOR_KEY));
	}

	private static final String _CUSTOM_OBJECT_ACTION_EXECUTOR_KEY =
		RandomTestUtil.randomString();

	private static final String _OBJECT_DEFINITION_NAME =
		"A" + RandomTestUtil.randomString();

	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static PermissionChecker _originalPermissionChecker;
	private static ServiceRegistration<ObjectActionExecutor>
		_serviceRegistration;

	@Inject
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

}