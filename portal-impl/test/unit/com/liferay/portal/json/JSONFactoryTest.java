/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author Igor Spasic
 */
public class JSONFactoryTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	public void testLooseDeserializationOfForbiddenTypes() {
		try {
			JSONFactoryUtil.looseDeserialize(
				"{\"class\":" +
					"\"com.liferay.portal.kernel.dao.orm.EntityCacheUtil\"}}");
			fail();
		}
		catch (Exception e) {
		}

		try {
			Object object = JSONFactoryUtil.looseDeserialize(
				"{\"class\":\"java.lang.Thread\"}}");

			assertEquals(Thread.class, object.getClass());
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testLooseDeserializationSafe() {

		Object object = JSONFactoryUtil.looseDeserializeSafe(
			"{\"class\":\"java.lang.Thread\"}}");

		assertEquals(HashMap.class, object.getClass());

		object = JSONFactoryUtil.looseDeserializeSafe(
			"{\"\u0063lass\":\"java.lang.Thread\"}}");

		assertEquals(HashMap.class, object.getClass());
		assertTrue(((Map)object).containsKey("class"));

		try {
			JSONFactoryUtil.looseDeserializeSafe(
				"{\"class\":" +
					"\"com.liferay.portal.kernel.dao.orm.EntityCacheUtil\"}}");
		}
		catch (Exception e) {
			fail(e.toString());
		}

		Map map = (Map)JSONFactoryUtil.looseDeserializeSafe(
			"{\"class\":" +
				"\"com.liferay.portal.kernel.dao.orm.EntityCacheUtil\"," +
					"\"foo\": \"boo\"}");

		assertNotNull(map);
		assertEquals(2, map.size());
		assertEquals(
			"com.liferay.portal.kernel.dao.orm.EntityCacheUtil",
			map.get("class"));
		assertEquals("boo", map.get("foo"));

		map = (Map)JSONFactoryUtil.looseDeserializeSafe(
			"{\"class\":\"com.liferay.portal.kernel.dao.orm.EntityCacheUtil\"" +
				",\"foo\": \"boo\",\"entityCache\":{\"class\":" +
					"\"com.liferay.portal.dao.orm.common.EntityCacheImpl\"}}");

		assertNotNull(map);
		assertEquals(3, map.size());
		assertEquals(
			"com.liferay.portal.kernel.dao.orm.EntityCacheUtil",
			map.get("class"));
		assertEquals("boo", map.get("foo"));

		map = (Map)map.get("entityCache");
		assertNotNull(map);
		assertEquals(1, map.size());
		assertEquals(
			"com.liferay.portal.dao.orm.common.EntityCacheImpl",
			map.get("class"));
	}

}