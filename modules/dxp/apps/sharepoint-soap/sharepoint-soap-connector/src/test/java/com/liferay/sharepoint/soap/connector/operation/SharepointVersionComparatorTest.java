/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.sharepoint.soap.connector.SharepointVersion;

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