/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.sharepoint.soap.repository.connector.SharepointVersion;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class SharepointVersionComparatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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