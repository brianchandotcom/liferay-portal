/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

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
	public void testAnnotations() {
		FooBean fooBean = new FooBean();

		String json = removeQuotes(JSONFactoryUtil.looseSerialize(fooBean));

		Assert.assertEquals(
			"{class:com.liferay.portal.json.FooBean,name:bar,value:173}",
			json);
	}

	@Test
	public void testCollection() {
		FooBean1 fooBean1 = new FooBean1();

		String json = removeQuotes(JSONFactoryUtil.looseSerialize(fooBean1));

		Assert.assertEquals(
			"{class:com.liferay.portal.json.FooBean1,collection:[element]," +
			"value:173}",
			json);
	}

	@Test
	public void testDeserializePrimitiveArrays() {
		String json = buildPrimitiveArraysJSON();

		Object object = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(object instanceof FooBean3);

		checkPrimitiveArrays((FooBean3)object);
	}

	@Test
	public void testDeserializePrimitiveArraysSerializable() {
		String json = buildPrimitiveArraysSerializableJSON();

		Object object = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(object instanceof FooBean4);

		checkPrimitiveArrays((FooBean4)object);
	}

	@Test
	public void testDeserializePrimitives() {
		String json = buildPrimitivesJSON();

		Object object = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(object instanceof FooBean5);

		checkPrimitives((FooBean5)object);
	}

	@Test
	public void testDeserializePrimitivesSerializable() {
		String json = buildPrimitivesSerializableJSON();

		Object object = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(object instanceof FooBean6);

		checkPrimitives((FooBean6)object);
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

	@Test
	public void testSerializePrimitiveArrays() {
		String json = buildPrimitiveArraysJSON();

		Assert.assertNotNull(json);

		checkJSONPrimitiveArrays(json);
	}

	@Test
	public void testSerializePrimitiveArraysSerializable() {
		String json = buildPrimitiveArraysSerializableJSON();

		Assert.assertNotNull(json);

		checkJSONPrimitiveArrays(json);
		checkJSONSerializableArgument(json);
	}

	@Test
	public void testSerializePrimitives() {
		String json = buildPrimitivesJSON();

		Assert.assertNotNull(json);

		checkJSONPrimitives(json);
	}

	@Test
	public void testSerializePrimitivesSerializable() {
		String json = buildPrimitivesSerializableJSON();

		Assert.assertNotNull(json);

		checkJSONPrimitives(json);
		checkJSONSerializableArgument(json);
	}

	@Test
	public void testStrictMode() {
		FooBean2 fooBean2 = new FooBean2();

		String json = removeQuotes(JSONFactoryUtil.looseSerialize(fooBean2));

		Assert.assertEquals("{value:173}", json);
	}

	protected String buildPrimitiveArraysJSON() {
		FooBean3 fooBean3 = new FooBean3();

		initializePrimitiveArrays(fooBean3);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(fooBean3);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("Unable to serialize " + fooBean3);
		}

		return json;
	}

	protected String buildPrimitiveArraysSerializableJSON() {
		FooBean4 fooBean4 = new FooBean4();

		initializePrimitiveArrays(fooBean4);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(fooBean4);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("Unable to serialize " + fooBean4);
		}

		return json;
	}

	protected String buildPrimitivesJSON() {
		FooBean5 fooBean5 = new FooBean5();

		initializePrimitives(fooBean5);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(fooBean5);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("Unable to serialize " + fooBean5);
		}

		return json;
	}

	protected String buildPrimitivesSerializableJSON() {
		FooBean6 fooBean6 = new FooBean6();

		initializePrimitives(fooBean6);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(fooBean6);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("Unable to serialize " + fooBean6);
		}

		return json;
	}

	protected void checkJSONPrimitiveArrays(String json) {
		Assert.assertTrue(
			json.contains("\"doubleArray\":" + _DOUBLE_ARRAY_STRING));
		Assert.assertTrue(json.contains("\"longArray\":" + _LONG_ARRAY_STRING));
		Assert.assertTrue(
			json.contains("\"integerArray\":" + _INTEGER_ARRAY_STRING));
	}

	protected void checkJSONPrimitives(String json) {
		Assert.assertTrue(json.contains("\"longValue\":" + _LONG_VALUE));
		Assert.assertTrue(json.contains("\"integerValue\":" + _INTEGER_VALUE));
		Assert.assertTrue(json.contains("\"doubleValue\":" + _DOUBLE_VALUE));
	}

	protected void checkJSONSerializableArgument(String json) {
		Assert.assertTrue(json.contains("serializable"));
	}

	protected void checkPrimitiveArrays(FooBean3 fooBean3) {
		AssertUtils.assertEquals(_DOUBLE_ARRAY, fooBean3.getDoubleArray());
		Assert.assertArrayEquals(_INTEGER_ARRAY, fooBean3.getIntegerArray());
		Assert.assertArrayEquals(_LONG_ARRAY, fooBean3.getLongArray());
	}

	protected void checkPrimitives(FooBean5 fooBean5) {
		Assert.assertEquals(_INTEGER_VALUE, fooBean5.getIntegerValue());
		Assert.assertEquals(_LONG_VALUE, fooBean5.getLongValue());
		AssertUtils.assertEquals(_DOUBLE_VALUE, fooBean5.getDoubleValue());
	}

	protected void initializePrimitiveArrays(FooBean3 fooBean3) {
		fooBean3.setDoubleArray(_DOUBLE_ARRAY);
		fooBean3.setIntegerArray(_INTEGER_ARRAY);
		fooBean3.setLongArray(_LONG_ARRAY);
	}

	protected void initializePrimitives(FooBean5 fooBean5) {
		fooBean5.setDoubleValue(_DOUBLE_VALUE);
		fooBean5.setIntegerValue(_INTEGER_VALUE);
		fooBean5.setLongValue(_LONG_VALUE);
	}

	protected String removeQuotes(String string) {
		return StringUtil.replace(string, StringPool.QUOTE, StringPool.BLANK);
	}

	private static final double[] _DOUBLE_ARRAY = {1.2345, 2.3456, 5.6789};

	private static final String _DOUBLE_ARRAY_STRING = "[1.2345,2.3456,5.6789]";

	private static final double _DOUBLE_VALUE = 3.1425927;

	private static final int[] _INTEGER_ARRAY = {1, 2, 3, 4, 5};

	private static final String _INTEGER_ARRAY_STRING = "[1,2,3,4,5]";

	private static final int _INTEGER_VALUE = 5;

	private static final long[] _LONG_ARRAY = {
		10000000000000L, 20000000000000L, 30000000000000L
	};

	private static final String _LONG_ARRAY_STRING =
		"[10000000000000,20000000000000,30000000000000]";

	private static final long _LONG_VALUE = 50000000000000L;

}