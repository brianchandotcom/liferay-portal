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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Rodrigo Paulino
 * @author André de Oliveira
 */
public class LiferayTypeMappingsDateDetectionEmptyStringTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		_liferayIndexFixture = new LiferayIndexFixture(
			clazz.getSimpleName(), new IndexName(testName.getMethodName()));

		_liferayIndexFixture.setUp();
	}

	@Test
	public void testEmptyStringInSecondDocument() throws Exception {
		String field1 = _randomField();
		String field2 = _randomField();
		String field3 = _randomField();
		String field4 = _randomField();
		String field5 = _randomField();
		String field6 = _randomField();
		String field7 = _randomField();
		String field8 = _randomField();
		String field9 = _randomField();

		index(
			HashMapBuilder.<String, Object>put(
				field1, RandomTestUtil.randomString()
			).put(
				field2, StringPool.BLANK
			).put(
				field3, new Date()
			).put(
				field4, "2011-07-01T01:32:33"
			).put(
				field5, 321231312321L
			).put(
				field6, "321231312321"
			).put(
				field7, true
			).put(
				field8, "true"
			).put(
				field9, "NULL"
			).build());

		index(
			HashMapBuilder.<String, Object>put(
				field1, StringPool.BLANK
			).put(
				field2, StringPool.BLANK
			).put(
				field3, StringPool.BLANK
			).put(
				field4, StringPool.BLANK
			).put(
				field5, StringPool.BLANK
			).put(
				field6, StringPool.BLANK
			).put(
				field7, StringPool.BLANK
			).put(
				field8, StringPool.BLANK
			).put(
				field9, StringPool.BLANK
			).build());

		index(
			HashMapBuilder.<String, Object>put(
				field1, RandomTestUtil.randomString()
			).put(
				field2, RandomTestUtil.randomString()
			).put(
				field3, RandomTestUtil.randomString()
			).put(
				field4, RandomTestUtil.randomString()
			).put(
				field5, String.valueOf(RandomTestUtil.randomLong())
			).put(
				field6, RandomTestUtil.randomString()
			).put(
				field7, StringPool.FALSE
			).put(
				field8, RandomTestUtil.randomString()
			).put(
				field9, RandomTestUtil.randomString()
			).build());

		assertType(field1, "text");
		assertType(field2, "text");
		assertType(field3, "text");
		assertType(field4, "text");
		assertType(field5, "long");
		assertType(field6, "text");
		assertType(field7, "boolean");
		assertType(field8, "text");
		assertType(field9, "text");
	}

	@Rule
	public TestName testName = new TestName();

	protected void assertType(String field, String type) throws Exception {
		_liferayIndexFixture.assertType(field, type);
	}

	protected void index(Map<String, Object> map) {
		_liferayIndexFixture.index(map);
	}

	private static String _randomField() {
		return "randomField__" + RandomTestUtil.randomString();
	}

	private LiferayIndexFixture _liferayIndexFixture;

}