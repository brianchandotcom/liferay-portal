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
import com.liferay.object.service.test.util.TestObjectActionExecutor;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
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

		_companyId1 = RandomTestUtil.nextLong();
		_companyId2 = RandomTestUtil.nextLong();
	}

	@AfterClass
	public static void tearDownClass() {
		_unregisterObjectActionExecutors();
	}

	@Test
	public void testGetObjectActionExecutorsWithCompanyAndObjectDefinitionRestriction() {
		ObjectActionExecutor objectActionExecutor =
			_registerObjectActionExecutor(
				Collections.singletonList("objectDefinitionName1"),
				_companyId1, StringUtil.randomId());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "objectDefinitionName1"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "objectDefinitionName2"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));
		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "objectDefinitionName1"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));
		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "objectDefinitionName2"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));
	}

	@Test
	public void testGetObjectActionExecutorsWithCompanyRestriction() {
		ObjectActionExecutor objectActionExecutor =
			_registerObjectActionExecutor(
				Collections.emptyList(), _companyId1, StringUtil.randomId());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "objectDefinitionName1"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "objectDefinitionName2"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "objectDefinitionName1"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		_unregisterObjectActionExecutors();
	}

	@Test
	public void testGetObjectActionExecutorsWithObjectDefinitionRestriction() {
		ObjectActionExecutor objectActionExecutor =
			_registerObjectActionExecutor(
				Collections.singletonList("objectDefinitionName1"), 0,
				StringUtil.randomId());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "objectDefinitionName1"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, "objectDefinitionName2"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, "objectDefinitionName1"),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		_unregisterObjectActionExecutors();
	}

	private static void _unregisterObjectActionExecutors() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	private ObjectActionExecutor _registerObjectActionExecutor(
		List<String> allowedObjectDefinitionNames, long companyId, String key) {

		ServiceRegistration<ObjectActionExecutor> serviceRegistration =
			_bundleContext.registerService(
				ObjectActionExecutor.class,
				new TestObjectActionExecutor(
					allowedObjectDefinitionNames, companyId, key),
				new HashMapDictionary<>());

		_serviceRegistrations.add(serviceRegistration);

		return _bundleContext.getService(serviceRegistration.getReference());
	}

	private static final String[] _DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS = {
		ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
		ObjectActionExecutorConstants.KEY_GROOVY,
		ObjectActionExecutorConstants.KEY_NOTIFICATION,
		ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
		ObjectActionExecutorConstants.KEY_WEBHOOK
	};

	private static BundleContext _bundleContext;
	private static long _companyId1;
	private static long _companyId2;

	@Inject
	private static ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	private static final List<ServiceRegistration<ObjectActionExecutor>>
		_serviceRegistrations = new ArrayList<>();

}