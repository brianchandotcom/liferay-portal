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

package com.liferay.portal.vulcan.internal.jaxrs;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class JaxRsResourceRegistryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPropertyValue() {
		String className = RandomTestUtil.randomString();
		String propertyKey = RandomTestUtil.randomString();
		String propertyValue = RandomTestUtil.randomString();

		Map<String, Map<String, Object>> resourceProperties =
			Collections.singletonMap(
				className,
				Collections.singletonMap(propertyKey, propertyValue));

		ReflectionTestUtil.setFieldValue(
			_jaxRsResourceRegistryImpl, "_jaxRsResourceProperties",
			resourceProperties);

		Assert.assertEquals(
			propertyValue,
			_jaxRsResourceRegistryImpl.getPropertyValue(
				className, propertyKey));
	}

	@Test
	public void testGetPropertyValueUnknownClassName() {
		String className = RandomTestUtil.randomString();
		String propertyKey = RandomTestUtil.randomString();
		String propertyValue = RandomTestUtil.randomString();

		Map<String, Map<String, Object>> resourceProperties =
			Collections.singletonMap(
				className,
				Collections.singletonMap(propertyKey, propertyValue));

		ReflectionTestUtil.setFieldValue(
			_jaxRsResourceRegistryImpl, "_jaxRsResourceProperties",
			resourceProperties);

		Assert.assertNull(
			_jaxRsResourceRegistryImpl.getPropertyValue(
				RandomTestUtil.randomString(), propertyKey));
	}

	@Test
	public void testGetPropertyValueUnknownPropertyKey() {
		String className = RandomTestUtil.randomString();
		String propertyKey = RandomTestUtil.randomString();
		String propertyValue = RandomTestUtil.randomString();

		Map<String, Map<String, Object>> resourceProperties =
			Collections.singletonMap(
				className,
				Collections.singletonMap(propertyKey, propertyValue));

		ReflectionTestUtil.setFieldValue(
			_jaxRsResourceRegistryImpl, "_jaxRsResourceProperties",
			resourceProperties);

		Assert.assertNull(
			_jaxRsResourceRegistryImpl.getPropertyValue(
				className, RandomTestUtil.randomString()));
	}

	private final JaxRsResourceRegistryImpl _jaxRsResourceRegistryImpl =
		new JaxRsResourceRegistryImpl();

}