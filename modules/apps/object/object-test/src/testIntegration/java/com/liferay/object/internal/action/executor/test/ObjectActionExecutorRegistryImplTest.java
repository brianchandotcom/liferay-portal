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
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.test.util.TestObjectActionExecutor;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

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

		String objectDefinitionName1 = "A" + RandomTestUtil.randomString();

		_objectDefinition1 = _addObjectDefinition(
			objectDefinitionName1, TestPropsValues.getUser());

		String objectDefinitionName2 = "A" + RandomTestUtil.randomString();

		_objectDefinition2 = _addObjectDefinition(
			objectDefinitionName2, TestPropsValues.getUser());

		_objectDefinition3 = _addObjectDefinition(
			objectDefinitionName1, UserTestUtil.addCompanyAdminUser(company));
		_objectDefinition4 = _addObjectDefinition(
			objectDefinitionName2, UserTestUtil.addCompanyAdminUser(company));
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
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, _objectDefinition1.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		// Available for all companies' restricted object definitions

		ObjectActionExecutor objectActionExecutor1 =
			_registerObjectActionExecutor(
				Arrays.asList(_objectDefinition1.getName()), 0,
				StringUtil.randomId());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor1.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, _objectDefinition1.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, _objectDefinition2.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		_unregisterObjectActionExecutors();

		// Available for a restricted company's object definitions

		ObjectActionExecutor objectActionExecutor2 =
			_registerObjectActionExecutor(
				Collections.emptyList(), _companyId1, StringUtil.randomId());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor2.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, _objectDefinition1.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, _objectDefinition3.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		_unregisterObjectActionExecutors();

		// Available for a restricted company's restricted object definitions

		ObjectActionExecutor objectActionExecutor3 =
			_registerObjectActionExecutor(
				Arrays.asList(_objectDefinition1.getName()), _companyId1,
				StringUtil.randomId());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(
				ArrayUtil.append(
					new String[] {objectActionExecutor3.getKey()},
					_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS)),
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, _objectDefinition1.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));

		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId1, _objectDefinition2.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));
		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, _objectDefinition3.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));
		Assert.assertArrayEquals(
			_DEFAULT_OBJECT_ACTION_EXECUTOR_KEYS,
			TransformUtil.transformToArray(
				_objectActionExecutorRegistry.getObjectActionExecutors(
					_companyId2, _objectDefinition4.getName()),
				ObjectActionExecutor::getKey, ObjectActionExecutor.class));
	}

	private static ObjectDefinition _addObjectDefinition(String name, User user)
		throws Exception {

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			user.getUserId(), false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					StringUtil.randomId()
				).objectFieldSettings(
					Collections.emptyList()
				).build()));
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

	private static ObjectDefinition _objectDefinition1;
	private static ObjectDefinition _objectDefinition2;
	private static ObjectDefinition _objectDefinition3;
	private static ObjectDefinition _objectDefinition4;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static final List<ServiceRegistration<ObjectActionExecutor>>
		_serviceRegistrations = new ArrayList<>();

}