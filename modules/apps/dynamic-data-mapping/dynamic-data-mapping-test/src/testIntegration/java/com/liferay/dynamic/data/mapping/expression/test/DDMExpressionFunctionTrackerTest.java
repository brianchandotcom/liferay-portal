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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

	@Inject(type = DDMExpressionFunctionTracker.class)
	private DDMExpressionFunctionTracker _ddmExpressionFunctionTracker;

}