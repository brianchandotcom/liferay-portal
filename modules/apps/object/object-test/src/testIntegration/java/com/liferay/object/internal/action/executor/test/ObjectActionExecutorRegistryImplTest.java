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
import com.liferay.object.service.test.util.ObjectActionTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
 * @author Feliphe Marinho, Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class ObjectActionExecutorRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ObjectActionExecutorRegistryImplTest.class);

		_bundleContext = bundle.getBundleContext();

		_companyId1 = CompanyThreadLocal.getCompanyId();

		Company company = CompanyTestUtil.addCompany();

		_companyId2 = company.getCompanyId();
	}

	@AfterClass
	public static void tearDownClass() {
		_unregisterObjectActionExecutors();
	}

	@Test
	public void testGetObjectActionExecutors() {

		// Available for all companies' object definitions

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "Account"),
				_objectActionExecutorKeyAccessor));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "Address"),
				_objectActionExecutorKeyAccessor));

		// Available for all companies' restricted object definitions

		CompanyThreadLocal.setCompanyId(_companyId1);

		ObjectActionExecutor objectActionExecutor1 =
			_registerObjectActionExecutor(
				0, "_objectActionExecutor1",
				Arrays.asList("Account", "Address"));

		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor1.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "Account"),
				_objectActionExecutorKeyAccessor));
		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor1.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "Address"),
				_objectActionExecutorKeyAccessor));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "User"),
				_objectActionExecutorKeyAccessor));

		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor1.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "Account"),
				_objectActionExecutorKeyAccessor));
		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor1.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "Address"),
				_objectActionExecutorKeyAccessor));

		_unregisterObjectActionExecutors();

		// Available for a restricted company's object definitions

		ObjectActionExecutor objectActionExecutor2 =
			_registerObjectActionExecutor(
				_companyId1, "_objectActionExecutor2", Collections.emptyList());

		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor2.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "Account"),
				_objectActionExecutorKeyAccessor));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "Account"),
				_objectActionExecutorKeyAccessor));

		_unregisterObjectActionExecutors();

		// Available for a restricted company's restricted object definitions

		ObjectActionExecutor objectActionExecutor3 =
			_registerObjectActionExecutor(
				_companyId1, "_objectActionExecutor3",
				Arrays.asList("Account", "Address"));

		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor3.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "Account"),
				_objectActionExecutorKeyAccessor));
		Assert.assertArrayEquals(
			ArrayUtil.append(
				new String[] {objectActionExecutor3.getKey()},
				_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS),
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "Address"),
				_objectActionExecutorKeyAccessor));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "User"),
				_objectActionExecutorKeyAccessor));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "Account"),
				_objectActionExecutorKeyAccessor));
		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			ListUtil.toArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "Address"),
				_objectActionExecutorKeyAccessor));
	}

	private static void _unregisterObjectActionExecutors() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	private ObjectActionExecutor _registerObjectActionExecutor(
		long companyId, String key, List<String> objectDefinitionNames) {

		ServiceRegistration<ObjectActionExecutor> serviceRegistration =
			_bundleContext.registerService(
				ObjectActionExecutor.class,
				ObjectActionTestUtil.createProxyObjectActionExecutor(
					companyId, key, objectDefinitionNames),
				new HashMapDictionary<>());

		_serviceRegistrations.add(serviceRegistration);

		return _bundleContext.getService(serviceRegistration.getReference());
	}

	private static BundleContext _bundleContext;
	private static long _companyId1;
	private static long _companyId2;
	private static final String[] _DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS = {
		ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
		ObjectActionExecutorConstants.KEY_GROOVY,
		ObjectActionExecutorConstants.KEY_NOTIFICATION,
		ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
		ObjectActionExecutorConstants.KEY_WEBHOOK
	};

	private static final Accessor<ObjectActionExecutor, String>
		_objectActionExecutorKeyAccessor =
			new Accessor<ObjectActionExecutor, String>() {

				@Override
				public String get(ObjectActionExecutor objectActionExecutor) {
					return objectActionExecutor.getKey();
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<ObjectActionExecutor> getTypeClass() {
					return ObjectActionExecutor.class;
				}

			};

	@Inject
	private static ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	private static final List<ServiceRegistration<ObjectActionExecutor>>
		_serviceRegistrations = new ArrayList<>();

}