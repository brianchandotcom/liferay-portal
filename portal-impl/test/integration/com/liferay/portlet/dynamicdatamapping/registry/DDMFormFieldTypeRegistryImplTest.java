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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.dynamicdatamapping.registry.bundle.ddmformfieldtyperegistryimpl.TestDDMFormFieldType;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class DDMFormFieldTypeRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.ddmformfieldtyperegistryimpl"));

	@Test
	public void testGetDDMFormFieldType() {
		String className = TestDDMFormFieldType.class.getName();

		DDMFormFieldType dDMFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(className);

		Class<?> clazz = dDMFormFieldType.getClass();

		Assert.assertEquals(className, clazz.getName());
	}

	@Test
	public void testGetDDMFormFieldTypeNames() {
		String className = TestDDMFormFieldType.class.getName();

		Set<String> dDMFormFieldTypeNames =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldTypeNames();

		for (String dDMFormFieldTypeName : dDMFormFieldTypeNames) {
			if (className.equals(dDMFormFieldTypeName)) {
				return;
			}
		}

		Assert.fail();
	}

	@Test
	public void testInstanceGetDDMFormFieldType() {
		String className = TestDDMFormFieldType.class.getName();

		DDMFormFieldTypeRegistry dDMFormFieldTypeRegistry =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldTypeRegistry();

		DDMFormFieldType dDMFormFieldType =
			dDMFormFieldTypeRegistry.getDDMFormFieldType(className);

		Class<?> clazz = dDMFormFieldType.getClass();

		Assert.assertEquals(className, clazz.getName());
	}

	@Test
	public void testInstanceGetDDMFormFieldTypeNames() {
		String className = TestDDMFormFieldType.class.getName();

		DDMFormFieldTypeRegistry dDMFormFieldTypeRegistry =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldTypeRegistry();

		Set<String> dDMFormFieldTypeNames =
			dDMFormFieldTypeRegistry.getDDMFormFieldTypeNames();

		for (String dDMFormFieldTypeName : dDMFormFieldTypeNames) {
			if (className.equals(dDMFormFieldTypeName)) {
				return;
			}
		}

		Assert.fail();
	}

}