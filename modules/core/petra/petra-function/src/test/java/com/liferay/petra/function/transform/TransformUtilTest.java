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
					new TestClass() {
						{
							booleanValue = true;
						}
					},
					new TestClass() {
						{
							booleanValue = false;
						}
					}
				},
				testClass -> testClass.booleanValue));
		Assert.assertArrayEquals(
			new boolean[] {true, false},
			TransformUtil.transformToBooleanArray(
				Arrays.asList(
					new TestClass() {
						{
							booleanValue = true;
						}
					},
					new TestClass() {
						{
							booleanValue = false;
						}
					}),
				testClass -> testClass.booleanValue));
	}

	@Test
	public void testTransformToByteArray() {
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			TransformUtil.transformToByteArray(
				new TestClass[] {
					new TestClass() {
						{
							byteValue = 1;
						}
					},
					new TestClass() {
						{
							byteValue = 2;
						}
					},
					new TestClass() {
						{
							byteValue = 3;
						}
					}
				},
				testClass -> testClass.byteValue));
		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			TransformUtil.transformToByteArray(
				Arrays.asList(
					new TestClass() {
						{
							byteValue = 1;
						}
					},
					new TestClass() {
						{
							byteValue = 2;
						}
					},
					new TestClass() {
						{
							byteValue = 3;
						}
					}),
				testClass -> testClass.byteValue));
	}

	@Test
	public void testTransformToDoubleArray() {
		Assert.assertArrayEquals(
			new double[] {1, 2, 3},
			TransformUtil.transformToDoubleArray(
				new TestClass[] {
					new TestClass() {
						{
							doubleValue = 1;
						}
					},
					new TestClass() {
						{
							doubleValue = 2;
						}
					},
					new TestClass() {
						{
							doubleValue = 3;
						}
					}
				},
				testClass -> testClass.doubleValue),
			Double.MIN_VALUE);
		Assert.assertArrayEquals(
			new double[] {1, 2, 3},
			TransformUtil.transformToDoubleArray(
				Arrays.asList(
					new TestClass() {
						{
							doubleValue = 1;
						}
					},
					new TestClass() {
						{
							doubleValue = 2;
						}
					},
					new TestClass() {
						{
							doubleValue = 3;
						}
					}),
				testClass -> testClass.doubleValue),
			Double.MIN_VALUE);
	}

	@Test
	public void testTransformToFloatArray() {
		Assert.assertArrayEquals(
			new float[] {1, 2, 3},
			TransformUtil.transformToFloatArray(
				new TestClass[] {
					new TestClass() {
						{
							floatValue = 1;
						}
					},
					new TestClass() {
						{
							floatValue = 2;
						}
					},
					new TestClass() {
						{
							floatValue = 3;
						}
					}
				},
				testClass -> testClass.floatValue),
			Float.MIN_VALUE);
		Assert.assertArrayEquals(
			new float[] {1, 2, 3},
			TransformUtil.transformToFloatArray(
				Arrays.asList(
					new TestClass() {
						{
							floatValue = 1;
						}
					},
					new TestClass() {
						{
							floatValue = 2;
						}
					},
					new TestClass() {
						{
							floatValue = 3;
						}
					}),
				testClass -> testClass.floatValue),
			Float.MIN_VALUE);
	}

	@Test
	public void testTransformToIntArray() {
		Assert.assertArrayEquals(
			new int[] {1, 2, 3},
			TransformUtil.transformToIntArray(
				new TestClass[] {
					new TestClass() {
						{
							intValue = 1;
						}
					},
					new TestClass() {
						{
							intValue = 2;
						}
					},
					new TestClass() {
						{
							intValue = 3;
						}
					}
				},
				testClass -> testClass.intValue));
		Assert.assertArrayEquals(
			new int[] {1, 2, 3},
			TransformUtil.transformToIntArray(
				Arrays.asList(
					new TestClass() {
						{
							intValue = 1;
						}
					},
					new TestClass() {
						{
							intValue = 2;
						}
					},
					new TestClass() {
						{
							intValue = 3;
						}
					}),
				testClass -> testClass.intValue));
	}

	@Test
	public void testTransformToLongArray() {
		Assert.assertArrayEquals(
			new long[] {1, 2, 3},
			TransformUtil.transformToLongArray(
				new TestClass[] {
					new TestClass() {
						{
							longValue = 1;
						}
					},
					new TestClass() {
						{
							longValue = 2;
						}
					},
					new TestClass() {
						{
							longValue = 3;
						}
					}
				},
				testClass -> testClass.longValue));
		Assert.assertArrayEquals(
			new long[] {1, 2, 3},
			TransformUtil.transformToLongArray(
				Arrays.asList(
					new TestClass() {
						{
							longValue = 1;
						}
					},
					new TestClass() {
						{
							longValue = 2;
						}
					},
					new TestClass() {
						{
							longValue = 3;
						}
					}),
				testClass -> testClass.longValue));
	}

	@Test
	public void testTransformToShortArray() {
		Assert.assertArrayEquals(
			new short[] {1, 2, 3},
			TransformUtil.transformToShortArray(
				new TestClass[] {
					new TestClass() {
						{
							shortValue = 1;
						}
					},
					new TestClass() {
						{
							shortValue = 2;
						}
					},
					new TestClass() {
						{
							shortValue = 3;
						}
					}
				},
				testClass -> testClass.shortValue));
		Assert.assertArrayEquals(
			new short[] {1, 2, 3},
			TransformUtil.transformToShortArray(
				Arrays.asList(
					new TestClass() {
						{
							shortValue = 1;
						}
					},
					new TestClass() {
						{
							shortValue = 2;
						}
					},
					new TestClass() {
						{
							shortValue = 3;
						}
					}),
				testClass -> testClass.shortValue));
	}

	private static class TestClass {

		protected boolean booleanValue;
		protected byte byteValue;
		protected double doubleValue;
		protected float floatValue;
		protected int intValue;
		protected long longValue;
		protected short shortValue;

	}

}