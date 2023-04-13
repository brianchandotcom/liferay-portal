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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
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

		_ootbObjectActionExecutors = Arrays.asList(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY),
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_GROOVY),
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_NOTIFICATION),
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY),
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_WEBHOOK));
	}

	@AfterClass
	public static void tearDownClass() {
		CompanyThreadLocal.setCompanyId(_companyId1);

		_serviceRegistrations.forEach(ServiceRegistration::unregister);
	}

	@Test
	public void testGetObjectActionExecutors() throws Exception {

		// Available for all companies' object definitions

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Account"),
			_ootbObjectActionExecutors);

		CompanyThreadLocal.setCompanyId(_companyId2);

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Address"),
			_ootbObjectActionExecutors);

		// Available for all companies' restricted object definitions

		CompanyThreadLocal.setCompanyId(_companyId1);

		ObjectActionExecutor objectActionExecutor1 =
			_registerObjectActionExecutor(
				ObjectActionExecutor.UNRESTRICTED_BY_COMPANY, "_objectActionExecutor1",
				Arrays.asList("Account", "Address"));

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Account"),
			_concatOOTBObjectActionExecutors(objectActionExecutor1));

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("User"),
			_ootbObjectActionExecutors);

		// Available for a restricted company's object definitions

		ObjectActionExecutor objectActionExecutor2 =
			_registerObjectActionExecutor(
				_companyId1, "_objectActionExecutor2",
				ObjectActionExecutor.UNRESTRICTED_BY_OBJECT_DEFINITIONS);

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Account"),
			_concatOOTBObjectActionExecutors(
				objectActionExecutor1, objectActionExecutor2));

		CompanyThreadLocal.setCompanyId(_companyId2);

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Account"),
			_concatOOTBObjectActionExecutors(objectActionExecutor1));

		// Available for a restricted company's restricted object definitions

		CompanyThreadLocal.setCompanyId(_companyId1);

		ObjectActionExecutor objectActionExecutor3 =
			_registerObjectActionExecutor(
				_companyId1, "_objectActionExecutor3",
				Arrays.asList("Account", "Address"));

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Account"),
			_concatOOTBObjectActionExecutors(
				objectActionExecutor1, objectActionExecutor2,
				objectActionExecutor3));

		CompanyThreadLocal.setCompanyId(_companyId2);

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("Account"),
			_concatOOTBObjectActionExecutors(objectActionExecutor1));

		_assertObjectActionExecutors(
			_objectActionExecutorRegistry.getObjectActionExecutors("User"),
			_ootbObjectActionExecutors);
	}

	private void _assertObjectActionExecutors(
		List<ObjectActionExecutor> expectedObjectActionExecutors,
		List<ObjectActionExecutor> actualObjectActionExecutors) {

		Assert.assertEquals(
			actualObjectActionExecutors.toString(),
			expectedObjectActionExecutors.size(),
			actualObjectActionExecutors.size());

		for (int i = 0; i < expectedObjectActionExecutors.size(); i++) {
			ObjectActionExecutor actualObjectActionExecutor =
				actualObjectActionExecutors.get(i);
			ObjectActionExecutor expectedObjectActionExecutor =
				expectedObjectActionExecutors.get(i);

			Assert.assertEquals(
				expectedObjectActionExecutor.getKey(),
				actualObjectActionExecutor.getKey());
		}
	}

	private List<ObjectActionExecutor> _concatOOTBObjectActionExecutors(
		ObjectActionExecutor... objectActionExecutors) {

		return ListUtil.concat(
			Arrays.asList(objectActionExecutors), _ootbObjectActionExecutors);
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

	@Inject
	private static ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	private static List<ObjectActionExecutor> _ootbObjectActionExecutors;
	private static final List<ServiceRegistration<ObjectActionExecutor>>
		_serviceRegistrations = new ArrayList<>();

}