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

package unit.com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ArrayUtilTest extends TestCase {

	public void testToDoubleArray() throws Exception {
		List<Double> doubleList = new ArrayList<Double>();

		doubleList.add(1.0);
		doubleList.add(2.0);

		double[] doubleArray = ArrayUtil.toDoubleArray(doubleList);

		assertEquals(doubleArray.length, doubleList.size());

		for (int i = 0; i < doubleList.size(); i++) {
			Double value = doubleList.get(i);

			assertEquals(value.doubleValue(), doubleArray[i]);
		}
	}

	public void testToFloatArray() throws Exception {
		List<Float> floatList = new ArrayList<Float>();

		floatList.add(1.0F);
		floatList.add(2.0F);

		float[] floatArray = ArrayUtil.toFloatArray(floatList);

		assertEquals(floatArray.length, floatList.size());

		for (int i = 0; i < floatList.size(); i++) {
			Float value = floatList.get(i);

			assertEquals(value.floatValue(), floatArray[i]);
		}
	}

	public void testToIntArray() throws Exception {
		List<Integer> intList = new ArrayList<Integer>();

		intList.add(100);
		intList.add(200);

		int[] intArray = ArrayUtil.toIntArray(intList);

		assertEquals(intArray.length, intList.size());

		for (int i = 0; i < intList.size(); i++) {
			Integer value = intList.get(i);

			assertEquals(value.intValue(), intArray[i]);
		}
	}

	public void testToLongArray() throws Exception {
		List<Long> longList = new ArrayList<Long>();

		longList.add(100L);
		longList.add(200L);

		long[] longArray = ArrayUtil.toLongArray(longList);

		assertEquals(longArray.length, longList.size());

		for (int i = 0; i < longList.size(); i++) {
			Long value = longList.get(i);

			assertEquals(value.longValue(), longArray[i]);
		}
	}

}