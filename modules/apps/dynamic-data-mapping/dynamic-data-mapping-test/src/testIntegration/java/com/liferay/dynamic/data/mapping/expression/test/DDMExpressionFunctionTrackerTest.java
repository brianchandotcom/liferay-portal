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

package com.liferay.dynamic.data.mapping.expression.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMExpressionFunctionTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		setUpDDMExpressionFunctionFactory();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetCustomDDMExpressionFunctions() {
		Map<String, DDMExpressionFunction> customDDMExpressionFunctions =
			_ddmExpressionFunctionTracker.getCustomDDMExpressionFunctions();

		Assert.assertNotNull(
			customDDMExpressionFunctions.get("binaryFunction"));
		Assert.assertNull(customDDMExpressionFunctions.get("setRequired"));
	}

	@Test
	public void testGetDDMExpressionFunctionsShouldReturnNewInstances() {
		Set<String> functionNames = new HashSet<>();

		functionNames.add("setRequired");
		functionNames.add("setValue");

		Map<String, DDMExpressionFunction> ddmExpressionFunctions1 =
			_ddmExpressionFunctionTracker.getDDMExpressionFunctions(
				functionNames);
		Map<String, DDMExpressionFunction> ddmExpressionFunctions2 =
			_ddmExpressionFunctionTracker.getDDMExpressionFunctions(
				functionNames);

		for (Map.Entry<String, DDMExpressionFunction> entry :
				ddmExpressionFunctions1.entrySet()) {

			Assert.assertNotEquals(
				entry.getValue(), ddmExpressionFunctions2.get(entry.getKey()));
		}

		_ddmExpressionFunctionTracker.ungetDDMExpressionFunctions(
			ddmExpressionFunctions1);
		_ddmExpressionFunctionTracker.ungetDDMExpressionFunctions(
			ddmExpressionFunctions2);
	}

	protected static void setUpDDMExpressionFunctionFactory() {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMExpressionFunctionTrackerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			DDMExpressionFunctionFactory.class, new BinaryFunctionFactory(),
			MapUtil.singletonDictionary("name", "binaryFunction"));
	}

	private static ServiceRegistration<DDMExpressionFunctionFactory>
		_serviceRegistration;

	@Inject(type = DDMExpressionFunctionTracker.class)
	private DDMExpressionFunctionTracker _ddmExpressionFunctionTracker;

	private static class BinaryFunction
		implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

		@Override
		public Boolean apply(Object object1, Object object2) {
			return Objects.equals(object1, object2);
		}

		@Override
		public String getName() {
			return "binaryFunction";
		}

		@Override
		public boolean isCustomDDMExpressionFunction() {
			return true;
		}

	}

	private static class BinaryFunctionFactory
		implements DDMExpressionFunctionFactory {

		@Override
		public DDMExpressionFunction create() {
			return new BinaryFunction();
		}

	}

}