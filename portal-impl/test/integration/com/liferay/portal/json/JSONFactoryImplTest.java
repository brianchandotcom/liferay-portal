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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.AssertUtils;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Spasic
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JSONFactoryImplTest {

	public static final double[] DOUBLE_ARRAY = {1.2345, 2.3456, 5.6789};
	public static final String DOUBLE_ARRAY_STRING = "[1.2345,2.3456,5.6789]";
	public static final double DOUBLE_VALUE = 3.1425927;
	public static final int[] INTEGER_ARRAY = {1, 2, 3, 4, 5};
	public static final String INTEGER_ARRAY_STRING = "[1,2,3,4,5]";
	public static final int INTEGER_VALUE = 5;
	public static final long[] LONG_ARRAY =
		{10000000000000L, 20000000000000L, 30000000000000L};
	public static final String LONG_ARRAY_STRING =
		"[10000000000000,20000000000000,30000000000000]";
	public static final long LONG_VALUE = 50000000000000L;

	@Test
	public void testAnnotations() {
		FooBean fooBean = new FooBean();

		String json = _removeQuotationMarks(
			JSONFactoryUtil.looseSerialize(fooBean));

		Assert.assertEquals(
			"{class:com.liferay.portal.json.FooBean,name:bar,value:173}",
			json);
	}

	@Test
	public void testCollection() {
		FooBean1 fooBean1 = new FooBean1();

		String json = _removeQuotationMarks(
			JSONFactoryUtil.looseSerialize(fooBean1));

		Assert.assertEquals(
			"{class:com.liferay.portal.json.FooBean1,collection:[element]," +
			"value:173}",
			json);
	}

	@Test
	public void testDeserializePrimitiveArrays() {
		String json = buildPrimitiveArraysJson();

		Object primitiveArrays = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(primitiveArrays instanceof FooBean3);

		checkPrimitiveArrays((FooBean3) primitiveArrays);
	}

	@Test
	public void testDeserializePrimitiveArraysSerializable() {
		String json = buildPrimitiveArraysSerializableJson();

		Object primitiveArraysSerializable = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(primitiveArraysSerializable instanceof FooBean4);

		checkPrimitiveArrays((FooBean4) primitiveArraysSerializable);
	}

	@Test
	public void testDeserializePrimitives() {
		String json = buildPrimitivesJson();

		Object primitives = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(primitives instanceof FooBean5);

		checkPrimitives((FooBean5) primitives);
	}

	@Test
	public void testDeserializePrimitivesSerializable() {
		String json = buildPrimitivesSerializableJson();

		Object primitivesSerializable = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(primitivesSerializable instanceof FooBean6);

		checkPrimitives((FooBean6)primitivesSerializable);
	}

	@Test
	public void testSerializePrimitiveArrays() {
		String json = buildPrimitiveArraysJson();

		Assert.assertNotNull(json);

		checkJsonPrimitiveArrays(json);
	}

	@Test
	public void testSerializePrimitiveArraysSerializable() {
		String json = buildPrimitiveArraysSerializableJson();

		Assert.assertNotNull(json);

		checkJsonPrimitiveArrays(json);

		checkJsonSerializableArgument(json);
	}

	@Test
	public void testSerializePrimitives() {
		String json = buildPrimitivesJson();

		Assert.assertNotNull(json);

		checkJsonPrimitives(json);
	}

	@Test
	public void testSerializePrimitivesSerializable() {
		String json = buildPrimitivesSerializableJson();

		Assert.assertNotNull(json);

		checkJsonPrimitives(json);

		checkJsonSerializableArgument(json);
	}

	@Test
	public void testStrictMode() {
		FooBean2 fooBean2 = new FooBean2();

		String json = _removeQuotationMarks(
			JSONFactoryUtil.looseSerialize(fooBean2));

		Assert.assertEquals("{value:173}", json);
	}

	protected String buildPrimitiveArraysJson() {
		FooBean3 primitiveArrays = new FooBean3();

		initializePrimitiveArrays(primitiveArrays);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(primitiveArrays);
		} catch(IllegalArgumentException iae) {
			Assert.fail("Cannot serialize " + primitiveArrays + " object");
		}

		return json;
	}

	protected String buildPrimitiveArraysSerializableJson() {
		FooBean4 primitiveArraysSerializable = new FooBean4();

		initializePrimitiveArrays(primitiveArraysSerializable);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(primitiveArraysSerializable);
		} catch(IllegalArgumentException iae) {
			Assert.fail(
				"Cannot serialize " + primitiveArraysSerializable + " object");
		}

		return json;
	}

	protected String buildPrimitivesJson() {
		FooBean5 primitives = new FooBean5();

		initializePrimitives(primitives);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(primitives);
		} catch(IllegalArgumentException iae) {
			Assert.fail("Cannot serialize " + primitives + " object");
		}

		return json;
	}

	protected String buildPrimitivesSerializableJson() {
		FooBean6 primitivesSerializable = new FooBean6();

		initializePrimitives(primitivesSerializable);

		String json = null;

		try {
			json = JSONFactoryUtil.serialize(primitivesSerializable);
		} catch(IllegalArgumentException iae) {
			Assert.fail(
				"Cannot serialize " + primitivesSerializable + " object");
		}

		return json;
	}

	protected void checkJsonPrimitiveArrays(String json) {
		Assert.assertTrue(json.contains(
			"\"doubleArray\":" + DOUBLE_ARRAY_STRING));
		Assert.assertTrue(json.contains("\"longArray\":" + LONG_ARRAY_STRING));
		Assert.assertTrue(json.contains(
			"\"integerArray\":" + INTEGER_ARRAY_STRING));
	}

	protected void checkJsonPrimitives(String json) {
		Assert.assertTrue(json.contains("\"longValue\":" + LONG_VALUE));
		Assert.assertTrue(json.contains("\"integerValue\":" + INTEGER_VALUE));
		Assert.assertTrue(json.contains("\"doubleValue\":" + DOUBLE_VALUE));
	}

	protected void checkJsonSerializableArgument(String json) {
		Assert.assertTrue(json.contains("serializable"));
	}

	protected void checkPrimitiveArrays(FooBean3 primitiveArrays) {
		AssertUtils.assertArrayEquals(
			DOUBLE_ARRAY, primitiveArrays.getDoubleArray());
		Assert.assertArrayEquals(
			INTEGER_ARRAY, primitiveArrays.getIntegerArray());
		Assert.assertArrayEquals(LONG_ARRAY, primitiveArrays.getLongArray());
	}

	protected void checkPrimitives(FooBean5 primitives) {
		Assert.assertEquals(INTEGER_VALUE, primitives.getIntegerValue());
		Assert.assertEquals(LONG_VALUE, primitives.getLongValue());
		AssertUtils.assertEquals(DOUBLE_VALUE, primitives.getDoubleValue());
	}

	protected void initializePrimitiveArrays(FooBean3 primitiveArrays) {
		primitiveArrays.setDoubleArray(DOUBLE_ARRAY);
		primitiveArrays.setIntegerArray(INTEGER_ARRAY);
		primitiveArrays.setLongArray(LONG_ARRAY);
	}

	protected void initializePrimitives(FooBean5 primitives) {
		primitives.setDoubleValue(DOUBLE_VALUE);
		primitives.setIntegerValue(INTEGER_VALUE);
		primitives.setLongValue(LONG_VALUE);
	}

	private String _removeQuotationMarks(String string) {
		return StringUtil.replace(string, StringPool.QUOTE, StringPool.BLANK);
	}

}