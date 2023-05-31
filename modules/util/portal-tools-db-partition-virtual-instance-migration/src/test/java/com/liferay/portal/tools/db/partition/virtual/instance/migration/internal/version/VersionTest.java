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

package com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.version;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class VersionTest {

	@Test
	public void testCompareFinalToStepVersion() {
		Version version1 = Version.parseVersion("1.2.3");
		Version version2 = Version.parseVersion("1.2.3.step-2");

		Assert.assertTrue(version1.compareTo(version2) > 0);
	}

	@Test
	public void testCompareSameFinalVersions() {
		Version version1 = Version.parseVersion("1.2.3");
		Version version2 = Version.parseVersion("1.2.3");

		Assert.assertTrue(version1.compareTo(version2) == 0);
	}

	@Test
	public void testCompareSameStepVersions() {
		Version version1 = Version.parseVersion("1.2.3.step-3");
		Version version2 = Version.parseVersion("1.2.3.step-3");

		Assert.assertTrue(version1.compareTo(version2) == 0);
	}

	@Test
	public void testCompareSteps() {
		Version version1 = Version.parseVersion("1.2.3.step-1");
		Version version2 = Version.parseVersion("1.2.3.step-2");

		Assert.assertTrue(version1.compareTo(version2) < 0);
	}

	@Test
	public void testCompareStepToFinalVersion() {
		Version version1 = Version.parseVersion("1.2.3.step-2");
		Version version2 = Version.parseVersion("1.2.3");

		Assert.assertTrue(version1.compareTo(version2) < 0);
	}

	@Test
	public void testEqualSameFinalVersions() {
		Version version1 = Version.parseVersion("1.2.3");
		Version version2 = Version.parseVersion("1.2.3");

		Assert.assertTrue(version1.equals(version2));
	}

	@Test
	public void testEqualSameStepVersions() {
		Version version1 = Version.parseVersion("1.2.3.step-1");
		Version version2 = Version.parseVersion("1.2.3.step-1");

		Assert.assertTrue(version1.equals(version2));
	}

	@Test
	public void testNotEqualVersions() {
		Version version1 = Version.parseVersion("1.2.3");
		Version version2 = Version.parseVersion("1.2.3.step-4");

		Assert.assertTrue(!version1.equals(version2));
	}

	@Test
	public void testParseEmptyVersion() {
		Version version = Version.parseVersion("");

		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(0, version.getMinor());
		Assert.assertEquals(0, version.getMicro());
		Assert.assertEquals("", version.getQualifier());
	}

	@Test
	public void testParseNullVersion() {
		Version version = Version.parseVersion(null);

		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(0, version.getMinor());
		Assert.assertEquals(0, version.getMicro());
		Assert.assertEquals("", version.getQualifier());
	}

	@Test
	public void testParseStepVersion() {
		Version version = Version.parseVersion("1.2.3.step-4");

		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(3, version.getMicro());
		Assert.assertEquals("step-4", version.getQualifier());
	}

	@Test
	public void testParseVersion() {
		Version version = Version.parseVersion("1.2.3");

		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(3, version.getMicro());
		Assert.assertEquals("", version.getQualifier());
	}

	@Test
	public void testParseWrongFormatVersion() {
		Version version = Version.parseVersion("V1.4.3");

		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(0, version.getMinor());
		Assert.assertEquals(0, version.getMicro());
		Assert.assertEquals("", version.getQualifier());
	}

	@Test
	public void testToStringFinalVersion() {
		Version version = Version.parseVersion("1.2.3");

		Assert.assertEquals("1.2.3", version.toString());
	}

	@Test
	public void testToStringStepVersion() {
		Version version = Version.parseVersion("1.2.3.step-4");

		Assert.assertEquals("1.2.3.step-4", version.toString());
	}

}