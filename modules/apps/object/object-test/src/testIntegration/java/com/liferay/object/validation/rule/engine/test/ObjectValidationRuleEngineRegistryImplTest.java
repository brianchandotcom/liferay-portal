/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.validation.rule.engine.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineRegistry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
 * @author Mateus Santana
 */
@RunWith(Arquillian.class)
public class ObjectValidationRuleEngineRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() {

		// Scope by company

		ServiceRegistration<ObjectValidationRuleEngine>
			objectValidationRuleEngineServiceRegistration1 = _register(
				new TestObjectValidationRuleEngine(1, Collections.emptyList()));

		ObjectValidationRuleEngine objectValidationRuleEngine =
			new TestObjectValidationRuleEngine(2, Collections.emptyList());

		ServiceRegistration<ObjectValidationRuleEngine>
			objectValidationRuleEngineServiceRegistration2 = _register(
				objectValidationRuleEngine);

		List<ObjectValidationRuleEngine> objectValidationRuleEngines =
			_objectValidationRuleEngineRegistry.getObjectValidationRuleEngines(
				1, StringUtil.randomId());

		Assert.assertFalse(
			objectValidationRuleEngines.contains(objectValidationRuleEngine));

		_unregister(objectValidationRuleEngineServiceRegistration1);
		_unregister(objectValidationRuleEngineServiceRegistration2);

		// Scope by object definition

		objectValidationRuleEngine = new TestObjectValidationRuleEngine(
			1, Collections.singletonList("C_Department"));

		objectValidationRuleEngineServiceRegistration1 = _register(
			objectValidationRuleEngine);

		objectValidationRuleEngines =
			_objectValidationRuleEngineRegistry.getObjectValidationRuleEngines(
				1, "C_Department");

		Assert.assertTrue(
			objectValidationRuleEngines.contains(objectValidationRuleEngine));

		objectValidationRuleEngine = new TestObjectValidationRuleEngine(
			1, Collections.emptyList());

		objectValidationRuleEngineServiceRegistration2 = _register(
			objectValidationRuleEngine);

		objectValidationRuleEngines =
			_objectValidationRuleEngineRegistry.getObjectValidationRuleEngines(
				1, "C_Employee");

		Assert.assertFalse(
			objectValidationRuleEngines.contains(objectValidationRuleEngine));

		_unregister(objectValidationRuleEngineServiceRegistration1);
		_unregister(objectValidationRuleEngineServiceRegistration2);
	}

	private ServiceRegistration<ObjectValidationRuleEngine> _register(
		ObjectValidationRuleEngine objectValidationRuleEngine) {

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectValidationRuleEngineRegistryImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			ObjectValidationRuleEngine.class, objectValidationRuleEngine, null);
	}

	private void _unregister(
		ServiceRegistration<ObjectValidationRuleEngine>
			objectValidationRuleEngineServiceRegistration) {

		if (objectValidationRuleEngineServiceRegistration == null) {
			return;
		}

		objectValidationRuleEngineServiceRegistration.unregister();
	}

	@Inject
	private ObjectValidationRuleEngineRegistry
		_objectValidationRuleEngineRegistry;

	private static class TestObjectValidationRuleEngine
		implements CompanyScoped, ObjectDefinitionScoped,
				   ObjectValidationRuleEngine {

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

		@Override
		public String getLabel(Locale locale) {
			return "Test";
		}

		private TestObjectValidationRuleEngine(
			long allowedCompanyId, List<String> allowedObjectDefinitionNames) {

			_allowedCompanyId = allowedCompanyId;
			_allowedObjectDefinitionNames = allowedObjectDefinitionNames;
		}

		private final long _allowedCompanyId;
		private final List<String> _allowedObjectDefinitionNames;

	}

}