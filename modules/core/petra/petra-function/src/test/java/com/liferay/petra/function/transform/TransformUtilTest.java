/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.function.transform;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class TransformUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testTransformToBooleanArray() {
		Assert.assertArrayEquals(
			new boolean[] {true, false},
			TransformUtil.transformToBooleanArray(
				new TestClass[] {
					TestClass.getBooleanValue(true),
					TestClass.getBooleanValue(false)
				},
				testClass -> testClass.booleanValue));
		Assert.assertArrayEquals(
			new boolean[] {true, false},
			TransformUtil.transformToBooleanArray(
				Arrays.asList(
					TestClass.getBooleanValue(true),
					TestClass.getBooleanValue(false)),
				testClass -> testClass.booleanValue));
	}

	@Test
	public void testTransformToByteArray() {
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			TransformUtil.transformToByteArray(
				new TestClass[] {
					TestClass.getByteValue(1), TestClass.getByteValue(2),
					TestClass.getByteValue(3)
				},
				testClass -> testClass.byteValue));
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			TransformUtil.transformToByteArray(
				Arrays.asList(
					TestClass.getByteValue(1), TestClass.getByteValue(2),
					TestClass.getByteValue(3)),
				testClass -> testClass.byteValue));
	}

	@Test
	public void testTransformToDoubleArray() {
		Assert.assertArrayEquals(
			new double[] {1, 2, 3},
			TransformUtil.transformToDoubleArray(
				new TestClass[] {
					TestClass.getDoubleValue(1), TestClass.getDoubleValue(2),
					TestClass.getDoubleValue(3)
				},
				testClass -> testClass.doubleValue),
			Double.MIN_VALUE);
		Assert.assertArrayEquals(
			new double[] {1, 2, 3},
			TransformUtil.transformToDoubleArray(
				Arrays.asList(
					TestClass.getDoubleValue(1), TestClass.getDoubleValue(2),
					TestClass.getDoubleValue(3)),
				testClass -> testClass.doubleValue),
			Double.MIN_VALUE);
	}

	@Test
	public void testTransformToFloatArray() {
		Assert.assertArrayEquals(
			new float[] {1, 2, 3},
			TransformUtil.transformToFloatArray(
				new TestClass[] {
					TestClass.getFloatValue(1), TestClass.getFloatValue(2),
					TestClass.getFloatValue(3)
				},
				testClass -> testClass.floatValue),
			Float.MIN_VALUE);
		Assert.assertArrayEquals(
			new float[] {1, 2, 3},
			TransformUtil.transformToFloatArray(
				Arrays.asList(
					TestClass.getFloatValue(1), TestClass.getFloatValue(2),
					TestClass.getFloatValue(3)),
				testClass -> testClass.floatValue),
			Float.MIN_VALUE);
	}

	@Test
	public void testTransformToIntArray() {
		Assert.assertArrayEquals(
			new int[] {1, 2, 3},
			TransformUtil.transformToIntArray(
				new TestClass[] {
					TestClass.getIntValue(1), TestClass.getIntValue(2),
					TestClass.getIntValue(3)
				},
				testClass -> testClass.intValue));
		Assert.assertArrayEquals(
			new int[] {1, 2, 3},
			TransformUtil.transformToIntArray(
				Arrays.asList(
					TestClass.getIntValue(1), TestClass.getIntValue(2),
					TestClass.getIntValue(3)),
				testClass -> testClass.intValue));
	}

	@Test
	public void testTransformToLongArray() {
		Assert.assertArrayEquals(
			new long[] {1, 2, 3},
			TransformUtil.transformToLongArray(
				new TestClass[] {
					TestClass.getLongValue(1), TestClass.getLongValue(2),
					TestClass.getLongValue(3)
				},
				testClass -> testClass.longValue));
		Assert.assertArrayEquals(
			new long[] {1, 2, 3},
			TransformUtil.transformToLongArray(
				Arrays.asList(
					TestClass.getLongValue(1), TestClass.getLongValue(2),
					TestClass.getLongValue(3)),
				testClass -> testClass.longValue));
	}

	@Test
	public void testTransformToShortArray() {
		Assert.assertArrayEquals(
			new short[] {1, 2, 3},
			TransformUtil.transformToShortArray(
				new TestClass[] {
					TestClass.getShortValue(1), TestClass.getShortValue(2),
					TestClass.getShortValue(3)
				},
				testClass -> testClass.shortValue));
		Assert.assertArrayEquals(
			new short[] {1, 2, 3},
			TransformUtil.transformToShortArray(
				Arrays.asList(
					TestClass.getShortValue(1), TestClass.getShortValue(2),
					TestClass.getShortValue(3)),
				testClass -> testClass.shortValue));
	}

	private static class TestClass {

		public static TestClass getBooleanValue(boolean b) {
			return new TestClass() {
				{
					booleanValue = b;
				}
			};
		}

		public static TestClass getByteValue(int b) {
			return new TestClass() {
				{
					byteValue = (byte)b;
				}
			};
		}

		public static TestClass getDoubleValue(double d) {
			return new TestClass() {
				{
					doubleValue = d;
				}
			};
		}

		public static TestClass getFloatValue(float f) {
			return new TestClass() {
				{
					floatValue = f;
				}
			};
		}

		public static TestClass getIntValue(int i) {
			return new TestClass() {
				{
					intValue = i;
				}
			};
		}

		public static TestClass getLongValue(long l) {
			return new TestClass() {
				{
					longValue = l;
				}
			};
		}

		public static TestClass getShortValue(int s) {
			return new TestClass() {
				{
					shortValue = (short)s;
				}
			};
		}

		protected boolean booleanValue;
		protected byte byteValue;
		protected double doubleValue;
		protected float floatValue;
		protected int intValue;
		protected long longValue;
		protected short shortValue;

	}

}