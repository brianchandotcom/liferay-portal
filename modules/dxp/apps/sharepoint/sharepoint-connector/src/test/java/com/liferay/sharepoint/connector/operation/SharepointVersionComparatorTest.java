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

package com.liferay.sharepoint.connector.operation;

import com.liferay.sharepoint.connector.SharepointVersion;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class SharepointVersionComparatorTest {

	@Test
	public void testCompareGreaterThanMajor() {
		SharepointVersion sharepointVersion1 = createSharepointVersion("8.1");
		SharepointVersion sharepointVersion2 = createSharepointVersion("9.0");

		Assert.assertEquals(
			1,
			_sharepointVersionComparator.compare(
				sharepointVersion1, sharepointVersion2));
	}

	@Test
	public void testCompareGreaterThanMinor() {
		SharepointVersion sharepointVersion1 = createSharepointVersion("9.0");
		SharepointVersion sharepointVersion2 = createSharepointVersion("9.1");

		Assert.assertEquals(
			1,
			_sharepointVersionComparator.compare(
				sharepointVersion1, sharepointVersion2));
	}

	@Test
	public void testCompareLessThanMajor() {
		SharepointVersion sharepointVersion1 = createSharepointVersion("9.0");
		SharepointVersion sharepointVersion2 = createSharepointVersion("8.1");

		Assert.assertEquals(
			-1,
			_sharepointVersionComparator.compare(
				sharepointVersion1, sharepointVersion2));
	}

	@Test
	public void testCompareLessThanMinor() {
		SharepointVersion sharepointVersion1 = createSharepointVersion("9.1");
		SharepointVersion sharepointVersion2 = createSharepointVersion("9.0");

		Assert.assertEquals(
			-1,
			_sharepointVersionComparator.compare(
				sharepointVersion1, sharepointVersion2));
	}

	@Test
	public void testEquals() {
		SharepointVersion sharepointVersion1 = createSharepointVersion("1.1");
		SharepointVersion sharepointVersion2 = createSharepointVersion("1.1");

		Assert.assertEquals(
			0,
			_sharepointVersionComparator.compare(
				sharepointVersion1, sharepointVersion2));
	}

	protected SharepointVersion createSharepointVersion(String version) {
		return new SharepointVersion(null, null, null, null, 0, null, version);
	}

	private final SharepointVersionComparator _sharepointVersionComparator =
		new SharepointVersionComparator();

}