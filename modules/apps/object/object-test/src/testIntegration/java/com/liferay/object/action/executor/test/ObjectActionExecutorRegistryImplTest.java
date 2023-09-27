/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.action.executor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Guilherme Camacho
 */
@RunWith(Arquillian.class)
public class ObjectActionExecutorRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldNotReturnObjectActionExecutorScopedByAnotherCompany() {
		ServiceRegistration<ObjectActionExecutor>
			objectActionExecutorServiceRegistration1 = null;
		ServiceRegistration<ObjectActionExecutor>
			objectActionExecutorServiceRegistration2 = null;

		try {
			objectActionExecutorServiceRegistration1 = _register(
				new TestObjectActionExecutor(1, Collections.emptyList()));

			ObjectActionExecutor objectActionExecutor =
				new TestObjectActionExecutor(2, Collections.emptyList());

			objectActionExecutorServiceRegistration2 = _register(
				objectActionExecutor);

			List<ObjectActionExecutor> objectActionExecutors =
				_objectActionExecutorRegistry.getObjectActionExecutors(
					1, StringUtil.randomId());

			Assert.assertFalse(
				objectActionExecutors.contains(objectActionExecutor));
		}
		finally {
			_unregister(objectActionExecutorServiceRegistration1);
			_unregister(objectActionExecutorServiceRegistration2);
		}
	}

	private ServiceRegistration<ObjectActionExecutor> _register(
		ObjectActionExecutor objectActionExecutor) {

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectActionExecutorRegistryImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			ObjectActionExecutor.class, objectActionExecutor, null);
	}

	private void _unregister(
		ServiceRegistration<ObjectActionExecutor>
			objectActionExecutorServiceRegistration) {

		if (objectActionExecutorServiceRegistration == null) {
			return;
		}

		objectActionExecutorServiceRegistration.unregister();
	}

	@Inject
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	private static class TestObjectActionExecutor
		implements CompanyScoped, ObjectActionExecutor, ObjectDefinitionScoped {

		public TestObjectActionExecutor(
			long allowedCompanyId, List<String> allowedObjectDefinitionNames) {

			_allowedCompanyId = allowedCompanyId;
			_allowedObjectDefinitionNames = allowedObjectDefinitionNames;
		}

		@Override
		public void execute(
				long companyId, long objectActionId,
				UnicodeProperties parametersUnicodeProperties,
				JSONObject payloadJSONObject, long userId)
			throws Exception {
		}

		@Override
		public long getAllowedCompanyId() {
			return _allowedCompanyId;
		}

		@Override
		public List<String> getAllowedObjectDefinitionNames() {
			return _allowedObjectDefinitionNames;
		}

		@Override
		public String getKey() {
			return "test";
		}

		private final long _allowedCompanyId;
		private final List<String> _allowedObjectDefinitionNames;

	}

}