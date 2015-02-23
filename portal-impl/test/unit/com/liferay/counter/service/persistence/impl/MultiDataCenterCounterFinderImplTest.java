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

package com.liferay.counter.service.persistence.impl;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class MultiDataCenterCounterFinderImplTest {

	@Test
	public void testIncrement2DataCenters() {
		MultiDataCenterCounterFinderImpl multiDataCenterCounterFinderImpl =
			new MultiDataCenterCounterFinderImpl(2, 0);

		Assert.assertEquals(
			0l, multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			4611686018427387903l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			2, 1);

		Assert.assertEquals(
			4611686018427387904l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			9223372036854775807l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));
	}

	@Test
	public void testIncrement5DataCenters() {
		MultiDataCenterCounterFinderImpl multiDataCenterCounterFinderImpl =
			new MultiDataCenterCounterFinderImpl(5, 0);

		Assert.assertEquals(
			0l, multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			1152921504606846975l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 1);

		Assert.assertEquals(
			1152921504606846976l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			2305843009213693951l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 2);

		Assert.assertEquals(
			2305843009213693952l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			3458764513820540927l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 3);

		Assert.assertEquals(
			3458764513820540928l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			4611686018427387903l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 4);

		Assert.assertEquals(
			4611686018427387904l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			5764607523034234879l,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));
	}

	@Test
	public void testIncrementSingleDataCenter() {
		MultiDataCenterCounterFinderImpl multiDataCenterCounterFinderImpl =
			new MultiDataCenterCounterFinderImpl(1, 0);

		Assert.assertEquals(
			0, multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			Long.MAX_VALUE,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));
	}

	@Test
	public void testInvalidConfiguration() {
		try {
			new MultiDataCenterCounterFinderImpl(2, 2);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Invalid data center count 2 or data center deployment ID 2",
				iae.getMessage());
		}

		try {
			new MultiDataCenterCounterFinderImpl(1 << 8, 2);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Unable to shift more than 8 bits", iae.getMessage());
		}
	}

}