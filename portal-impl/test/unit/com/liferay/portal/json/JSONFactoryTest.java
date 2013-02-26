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

import com.liferay.portal.dao.orm.common.EntityCacheImpl;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONIncludesManagerUtil;
import com.liferay.portal.kernel.json.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class JSONFactoryTest {

	@Before
	public void setUp() throws Exception {
		JSONInit.init();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		JSONIncludesManagerUtil jsonIncludesManagerUtil =
			new JSONIncludesManagerUtil();

		jsonIncludesManagerUtil.setJSONIncludesManager(
			new JSONIncludesManagerImpl());
	}

	@Test
	public void testHasProperty() {
		Three three = new Three();

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("class");

		String jsonString = jsonSerializer.serialize(three);

		Assert.assertEquals("{\"flag\":true}", jsonString);
	}

	@Test
	public void testLooseDeserialize() {
		try {
			JSONFactoryUtil.looseDeserialize(
				"{\"class\":\"" + EntityCacheUtil.class.getName() + "\"}}");

			Assert.fail();
		}
		catch (Exception e) {
		}

		try {
			Object object = JSONFactoryUtil.looseDeserialize(
				"{\"class\":\"java.lang.Thread\"}}");

			Assert.assertEquals(Thread.class, object.getClass());
		}
		catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void testLooseDeserializeSafe() {
		Object object = JSONFactoryUtil.looseDeserializeSafe(
			"{\"class\":\"java.lang.Thread\"}}");

		Assert.assertEquals(HashMap.class, object.getClass());

		object = JSONFactoryUtil.looseDeserializeSafe(
			"{\"\u0063lass\":\"java.lang.Thread\"}}");

		Assert.assertEquals(HashMap.class, object.getClass());
		Assert.assertTrue(((Map<?, ?>)object).containsKey("class"));

		try {
			JSONFactoryUtil.looseDeserializeSafe(
				"{\"class\":\"" + EntityCacheUtil.class.getName() + "\"}}");
		}
		catch (Exception e) {
			Assert.fail(e.toString());
		}

		Map<?, ?> map = (Map<?, ?>)JSONFactoryUtil.looseDeserializeSafe(
			"{\"class\":\"" + EntityCacheUtil.class.getName() +
				"\",\"foo\": \"boo\"}");

		Assert.assertNotNull(map);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(
			"com.liferay.portal.kernel.dao.orm.EntityCacheUtil",
			map.get("class"));
		Assert.assertEquals("boo", map.get("foo"));

		map = (Map<?, ?>)JSONFactoryUtil.looseDeserializeSafe(
			"{\"class\":\"" + EntityCacheUtil.class.getName() +
				"\",\"foo\": \"boo\",\"entityCache\":{\"class\":\"" +
					EntityCacheImpl.class.getName() + "\"}}");

		Assert.assertNotNull(map);
		Assert.assertEquals(3, map.size());
		Assert.assertEquals( EntityCacheUtil.class.getName(), map.get("class"));
		Assert.assertEquals("boo", map.get("foo"));

		map = (Map<?, ?>)map.get("entityCache");

		Assert.assertNotNull(map);
		Assert.assertEquals(1, map.size());
		Assert.assertEquals(EntityCacheImpl.class.getName(), map.get("class"));
	}

}