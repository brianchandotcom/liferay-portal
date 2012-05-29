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

package com.liferay.portal.json.serializer;

import com.liferay.portal.json.serializer.dependencies.PrimitiveArrays;
import com.liferay.portal.json.serializer.dependencies.PrimitiveArraysSerializable;
import com.liferay.portal.json.serializer.dependencies.Primitives;
import com.liferay.portal.json.serializer.dependencies.PrimitivesSerializable;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio Sánchez
 */

@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JSONFactoryUtilTest {
	
	public static final double[] DOUBLE_ARRAY = {1.2345, 2.3456, 5.6789};
	public static final double DOUBLE_VALUE = 3.1425927;
	public static final String DOUBLE_ARRAY_STRING = "[1.2345,2.3456,5.6789]";
	public static final int[] INTEGER_ARRAY = {1, 2, 3, 4, 5};
	public static final String INTEGER_ARRAY_STRING = "[1,2,3,4,5]";
	public static final int INTEGER_VALUE = 5;
	public static final long[] LONG_ARRAY =
		{10000000000000L, 20000000000000L, 30000000000000L};
	public static final String LONG_ARRAY_STRING =
		"[10000000000000,20000000000000,30000000000000]";
	public static final long LONG_VALUE = 50000000000000L;
	
	@Test
	public void testDeserializePrimitiveArrays() {
		String json = buildPrimitiveArraysJson();

		Object primitiveArrays = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(primitiveArrays instanceof PrimitiveArrays);

		checkPrimitiveArrays((PrimitiveArrays) primitiveArrays);
	}

	@Test
	public void testDeserializePrimitiveArraysSerializable() {
		String json = buildPrimitiveArraysSerializableJson();

		Object primitiveArraysSerializable = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(
			primitiveArraysSerializable instanceof PrimitiveArraysSerializable);

		checkPrimitiveArrays(
			(PrimitiveArraysSerializable) primitiveArraysSerializable);
	}

	@Test
	public void testDeserializePrimitives() {
		String json = buildPrimitivesJson();

		Object primitives = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(primitives instanceof Primitives);

		checkPrimitives((Primitives) primitives);
	}

	@Test
	public void testDeserializePrimitivesSerializable() {
		String json = buildPrimitivesSerializableJson();

		Object primitivesSerializable = JSONFactoryUtil.deserialize(json);

		Assert.assertTrue(
				primitivesSerializable instanceof PrimitivesSerializable);

		checkPrimitives((PrimitivesSerializable)primitivesSerializable);
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

	protected String buildPrimitiveArraysJson() {
		PrimitiveArrays primitiveArrays = new PrimitiveArrays();

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
		PrimitiveArraysSerializable primitiveArraysSerializable =
				new PrimitiveArraysSerializable();

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
		Primitives primitives = new Primitives();

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
		PrimitivesSerializable primitivesSerializable =
				new PrimitivesSerializable();

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
		Assert.assertTrue(json.contains(
				"\"longArray\":" + LONG_ARRAY_STRING));
		Assert.assertTrue(json.contains(
				"\"integerArray\":" + INTEGER_ARRAY_STRING));
	}

	protected void checkJsonPrimitives(String json) {
		Assert.assertTrue(json.contains(
				"\"longValue\":" + LONG_VALUE));
		Assert.assertTrue(json.contains(
				"\"integerValue\":" + INTEGER_VALUE));
		Assert.assertTrue(json.contains(
				"\"doubleValue\":" + DOUBLE_VALUE));
	}

	protected void checkJsonSerializableArgument(String json) {
		Assert.assertTrue(json.contains("serializable"));
	}

	protected void checkPrimitiveArrays(PrimitiveArrays primitiveArrays) {
		Assert.assertTrue(primitiveArrays.getIntegerArray().equals(
				primitiveArrays.getIntegerArray()));
		Assert.assertTrue(primitiveArrays.getLongArray().equals(
				primitiveArrays.getLongArray()));
		Assert.assertTrue(primitiveArrays.getDoubleArray().equals(
				primitiveArrays.getDoubleArray()));
	}

	protected void checkPrimitives(Primitives primitives) {
		Assert.assertTrue(
				primitives.getIntegerValue() == INTEGER_VALUE);
		Assert.assertTrue(primitives.getLongValue() == LONG_VALUE);
		Assert.assertTrue(
				primitives.getDoubleValue() == DOUBLE_VALUE);
	}
	
	protected void initializePrimitiveArrays(PrimitiveArrays primitiveArrays) {
		primitiveArrays.setIntegerArray(INTEGER_ARRAY);
		primitiveArrays.setLongArray(LONG_ARRAY);
		primitiveArrays.setDoubleArray(DOUBLE_ARRAY);
	}
	
	protected void initializePrimitives(Primitives primitives) {
		primitives.setIntegerValue(INTEGER_VALUE);
		primitives.setLongValue(LONG_VALUE);
		primitives.setDoubleValue(DOUBLE_VALUE);
	}

}