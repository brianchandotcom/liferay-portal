/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class EmptyResultTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testEmptyConstructor() {
		new EmptyResult();
	}

	@Test
	public void testPlainMatch() {

		// 3 args and last is not an OrderByComparator

		Object[] args = {1, "test", new Date()};

		EmptyResult emptyResult = new EmptyResult(args);

		Assert.assertTrue(emptyResult.matches(args));
		Assert.assertFalse(emptyResult.matches(Arrays.copyOf(args, 2)));

		args = Arrays.copyOf(args, args.length);

		args[0] = 2;

		Assert.assertFalse(emptyResult.matches(args));

		// 2 args

		args = new Object[] {1, "test"};

		emptyResult = new EmptyResult(args);

		Assert.assertTrue(emptyResult.matches(args));

		args = Arrays.copyOf(args, args.length);

		args[0] = 2;

		Assert.assertFalse(emptyResult.matches(args));

		// Start is not an intenger

		args = new Object[] {
			1, "test", 1L, 2,
			new OrderByComparator<Object>() {

				@Override
				public int compare(Object object1, Object object2) {
					return 0;
				}

			}
		};

		emptyResult = new EmptyResult(args);

		Object[] savedArgs = ReflectionTestUtil.getFieldValue(
			emptyResult, "_args");

		Assert.assertEquals(Arrays.toString(savedArgs), 5, savedArgs.length);

		Assert.assertTrue(emptyResult.matches(args));

		// End is not an intenger

		args = new Object[] {
			1, "test", 1, 2L,
			new OrderByComparator<Object>() {

				@Override
				public int compare(Object object1, Object object2) {
					return 0;
				}

			}
		};

		emptyResult = new EmptyResult(args);

		savedArgs = ReflectionTestUtil.getFieldValue(emptyResult, "_args");

		Assert.assertEquals(Arrays.toString(savedArgs), 5, savedArgs.length);

		Assert.assertTrue(emptyResult.matches(args));
	}

	@Test
	public void testSerialization() throws Exception {
		Object[] args = {
			1, "test", new Date(), 1, 2,
			new OrderByComparator<Object>() {

				@Override
				public int compare(Object object1, Object object2) {
					return 0;
				}

			}
		};

		EmptyResult emptyResult = new EmptyResult(args);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream)) {

			objectOutputStream.writeObject(emptyResult);
		}

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			byteArrayOutputStream.toByteArray());

		try (ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream)) {

			EmptyResult deserializedEmptyResult =
				(EmptyResult)objectInputStream.readObject();

			Assert.assertTrue(deserializedEmptyResult.matches(args));
			Assert.assertTrue(
				deserializedEmptyResult.matches(Arrays.copyOf(args, 4)));
		}
	}

	@Test
	public void testStripPaginationDefenseEmptyPageMatch() {

		// Start 2 and end 2 stripped to start 0 and end 0

		Object[] args = {
			1, "test", new Date(), 2, 2,
			new OrderByComparator<Object>() {

				@Override
				public int compare(Object object1, Object object2) {
					return 0;
				}

			}
		};

		EmptyResult emptyResult = new EmptyResult(args);

		Object[] savedArgs = ReflectionTestUtil.getFieldValue(
			emptyResult, "_args");

		Assert.assertEquals(Arrays.toString(savedArgs), 5, savedArgs.length);
		Assert.assertEquals(0, savedArgs[3]);
		Assert.assertEquals(0, savedArgs[4]);

		Assert.assertTrue(emptyResult.matches(args));

		// Start 5 and end 5

		args = Arrays.copyOf(args, args.length);

		args[3] = 5;
		args[4] = 5;

		Assert.assertTrue(emptyResult.matches(args));

		// No start and end

		Assert.assertFalse(emptyResult.matches(Arrays.copyOf(args, 3)));
	}

	@Test
	public void testStripPaginationMatch() {

		// Strip pagination

		Object[] args = {
			1, "test", new Date(), 1, 2,
			new OrderByComparator<Object>() {

				@Override
				public int compare(Object object1, Object object2) {
					return 0;
				}

			}
		};

		EmptyResult emptyResult = new EmptyResult(args);

		Object[] savedArgs = ReflectionTestUtil.getFieldValue(
			emptyResult, "_args");

		Assert.assertEquals(Arrays.toString(savedArgs), 4, savedArgs.length);

		Assert.assertTrue(emptyResult.matches(args));

		// Different OrderByComparator

		args = Arrays.copyOf(args, args.length);

		args[5] = new OrderByComparator<Object>() {

			@Override
			public int compare(Object object1, Object object2) {
				return 0;
			}

		};

		Assert.assertTrue(emptyResult.matches(args));

		// Different end

		args[4] = 3;

		Assert.assertTrue(emptyResult.matches(args));

		// Different start

		args = Arrays.copyOf(args, args.length);

		args[3] = 2;

		Assert.assertFalse(emptyResult.matches(args));

		// Different first parameter

		args[0] = 2;

		Assert.assertFalse(emptyResult.matches(args));

		// No pagination

		args[0] = 1;

		Assert.assertFalse(emptyResult.matches(Arrays.copyOf(args, 3)));

		// Strip start QueryUtil.ALL_POS and end QueryUtil.ALL_POS

		args = new Object[] {
			1, "test", new Date(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new OrderByComparator<Object>() {

				@Override
				public int compare(Object object1, Object object2) {
					return 0;
				}

			}
		};

		emptyResult = new EmptyResult(args);

		savedArgs = ReflectionTestUtil.getFieldValue(emptyResult, "_args");

		Assert.assertEquals(Arrays.toString(savedArgs), 3, savedArgs.length);

		Assert.assertTrue(emptyResult.matches(args));

		// No pagination

		Assert.assertTrue(emptyResult.matches(Arrays.copyOf(args, 3)));
	}

}