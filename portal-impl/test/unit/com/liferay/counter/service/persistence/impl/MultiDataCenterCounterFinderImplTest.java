/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.counter.service.persistence.impl;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class MultiDataCenterCounterFinderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIncrement2DataCenters() {
		MultiDataCenterCounterFinderImpl multiDataCenterCounterFinderImpl =
			new MultiDataCenterCounterFinderImpl(2, 0);

		Assert.assertEquals(
			0L, multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			4611686018427387903L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			2, 1);

		Assert.assertEquals(
			4611686018427387904L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			9223372036854775807L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));
	}

	@Test
	public void testIncrement5DataCenters() {
		MultiDataCenterCounterFinderImpl multiDataCenterCounterFinderImpl =
			new MultiDataCenterCounterFinderImpl(5, 0);

		Assert.assertEquals(
			0L, multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			1152921504606846975L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 1);

		Assert.assertEquals(
			1152921504606846976L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			2305843009213693951L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 2);

		Assert.assertEquals(
			2305843009213693952L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			3458764513820540927L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 3);

		Assert.assertEquals(
			3458764513820540928L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			4611686018427387903L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(
				Long.MAX_VALUE));

		multiDataCenterCounterFinderImpl = new MultiDataCenterCounterFinderImpl(
			5, 4);

		Assert.assertEquals(
			4611686018427387904L,
			multiDataCenterCounterFinderImpl.getMultiClusterSafeValue(0));
		Assert.assertEquals(
			5764607523034234879L,
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
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Invalid data center count 2 or data center deployment ID 2",
				illegalArgumentException.getMessage());
		}

		try {
			new MultiDataCenterCounterFinderImpl(1 << 8, 2);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Unable to shift more than 8 bits",
				illegalArgumentException.getMessage());
		}
	}

}