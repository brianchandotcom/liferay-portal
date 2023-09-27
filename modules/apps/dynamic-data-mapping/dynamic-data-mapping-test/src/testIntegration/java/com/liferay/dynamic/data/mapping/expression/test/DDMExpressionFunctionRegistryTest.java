/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionRegistry;
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
public class DDMExpressionFunctionRegistryTest {

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
			_ddmExpressionFunctionRegistry.getCustomDDMExpressionFunctions();

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
			_ddmExpressionFunctionRegistry.getDDMExpressionFunctions(
				functionNames);
		Map<String, DDMExpressionFunction> ddmExpressionFunctions2 =
			_ddmExpressionFunctionRegistry.getDDMExpressionFunctions(
				functionNames);

		for (Map.Entry<String, DDMExpressionFunction> entry :
				ddmExpressionFunctions1.entrySet()) {

			Assert.assertNotEquals(
				entry.getValue(), ddmExpressionFunctions2.get(entry.getKey()));
		}

		_ddmExpressionFunctionRegistry.ungetDDMExpressionFunctions(
			ddmExpressionFunctions1);
		_ddmExpressionFunctionRegistry.ungetDDMExpressionFunctions(
			ddmExpressionFunctions2);
	}

	protected static void setUpDDMExpressionFunctionFactory() {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMExpressionFunctionRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			DDMExpressionFunctionFactory.class, new BinaryFunctionFactory(),
			MapUtil.singletonDictionary("name", "binaryFunction"));
	}

	private static ServiceRegistration<DDMExpressionFunctionFactory>
		_serviceRegistration;

	@Inject(type = DDMExpressionFunctionRegistry.class)
	private DDMExpressionFunctionRegistry _ddmExpressionFunctionRegistry;

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