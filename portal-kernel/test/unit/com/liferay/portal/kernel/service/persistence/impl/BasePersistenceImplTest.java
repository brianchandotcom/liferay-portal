/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.Date;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BasePersistenceImplTest {

	@Test
	public void testConvertCaseFunctionReturnsEmptyStringForNullValue() {
		Function<TestModel, Object> function =
			BasePersistenceImpl.convertCaseFunction(testModel -> null);

		Assert.assertEquals("", function.apply(_TEST_MODEL));
	}

	@Test
	public void testConvertCaseFunctionReturnsLowercaseForPopulatedValue() {
		Function<TestModel, Object> function =
			BasePersistenceImpl.convertCaseFunction(testModel -> "Alice");

		Assert.assertEquals("alice", function.apply(_TEST_MODEL));
	}

	@Test
	public void testConvertDateFunctionReturnsLongForPopulatedValue() {
		Date date = new Date(1_234_567_890L);

		Function<TestModel, Object> function =
			BasePersistenceImpl.convertDateFunction(testModel -> date);

		Assert.assertEquals(1_234_567_890L, function.apply(_TEST_MODEL));
	}

	@Test
	public void testConvertDateFunctionReturnsNullForNullValue() {
		Function<TestModel, Object> function =
			BasePersistenceImpl.convertDateFunction(testModel -> null);

		Assert.assertNull(function.apply(_TEST_MODEL));
	}

	@Test
	public void testConvertNullFunctionReturnsEmptyStringForEmptyValue() {
		Function<TestModel, Object> function =
			BasePersistenceImpl.convertNullFunction(testModel -> "");

		Assert.assertEquals("", function.apply(_TEST_MODEL));
	}

	@Test
	public void testConvertNullFunctionReturnsEmptyStringForNullValue() {
		Function<TestModel, Object> function =
			BasePersistenceImpl.convertNullFunction(testModel -> null);

		Assert.assertEquals("", function.apply(_TEST_MODEL));
	}

	@Test
	public void testConvertNullFunctionReturnsValueForPopulatedValue() {
		Function<TestModel, Object> function =
			BasePersistenceImpl.convertNullFunction(testModel -> "Alice");

		Assert.assertEquals("Alice", function.apply(_TEST_MODEL));
	}

	private static final TestModel _TEST_MODEL =
		(TestModel)ProxyUtil.newProxyInstance(
			BasePersistenceImplTest.class.getClassLoader(),
			new Class<?>[] {TestModel.class}, (proxy, method, args) -> null);

	private interface TestModel extends BaseModel<TestModel> {
	}

}