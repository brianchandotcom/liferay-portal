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

package com.liferay.project.templates.extensions.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gregory Amerson
 * @author Lawrence Lee
 */
public class VersionUtilTest {

	@Test
	public void testGetMajorVersion() throws Exception {
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.0.10"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.0.10.1"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.0.10.fp21"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.1.10"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.1.10.1"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.1.10.fp21"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.1.10.fp1-1"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.1.10.fp123-456"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.3.10.ep4"));
		Assert.assertEquals(8, VersionUtil.getMajorVersion("8.0.0"));
		Assert.assertEquals(10, VersionUtil.getMajorVersion("10.0.0"));
		Assert.assertEquals(100, VersionUtil.getMajorVersion("100.0.0"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.2"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.4.1-1"));
		Assert.assertEquals(7, VersionUtil.getMajorVersion("7.4.11.1-1"));
	}

	@Test
	public void testGetMicroVersion() throws Exception {
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.0.10"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.0.10.1"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.0.10.fp21"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.1.10"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.1.10.1"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.1.10.fp21"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.1.10.fp1-1"));
		Assert.assertEquals(
			10, VersionUtil.getMicroVersion("7.1.10.fp123-456"));
		Assert.assertEquals(10, VersionUtil.getMicroVersion("7.3.10.ep4"));
		Assert.assertEquals(0, VersionUtil.getMicroVersion("8.0.0"));
		Assert.assertEquals(0, VersionUtil.getMicroVersion("10.0.0"));
		Assert.assertEquals(0, VersionUtil.getMicroVersion("100.0.00"));
		Assert.assertEquals(1, VersionUtil.getMicroVersion("7.4.1-1"));
		Assert.assertEquals(11, VersionUtil.getMicroVersion("7.4.11.1-1"));
	}

	@Test(expected = NumberFormatException.class)
	public void testGetMicroVersionNull() throws Exception {
		Assert.assertEquals(7, VersionUtil.getMicroVersion("7.2"));
	}

	@Test
	public void testGetMinorVersion() throws Exception {
		Assert.assertEquals(0, VersionUtil.getMinorVersion("7.0.10"));
		Assert.assertEquals(0, VersionUtil.getMinorVersion("7.0.10.1"));
		Assert.assertEquals(0, VersionUtil.getMinorVersion("7.0.10.fp21"));
		Assert.assertEquals(1, VersionUtil.getMinorVersion("7.1.10"));
		Assert.assertEquals(1, VersionUtil.getMinorVersion("7.1.10.1"));
		Assert.assertEquals(100, VersionUtil.getMinorVersion("7.100.10.fp21"));
		Assert.assertEquals(10, VersionUtil.getMinorVersion("7.10.10.fp1-1"));
		Assert.assertEquals(
			1000, VersionUtil.getMinorVersion("7.1000.10.fp123-456"));
		Assert.assertEquals(3, VersionUtil.getMinorVersion("7.3.10.ep4"));
		Assert.assertEquals(0, VersionUtil.getMinorVersion("8.00.0"));
		Assert.assertEquals(0, VersionUtil.getMinorVersion("10.0.0"));
		Assert.assertEquals(0, VersionUtil.getMinorVersion("100.0.0"));
		Assert.assertEquals(2, VersionUtil.getMinorVersion("7.2"));
		Assert.assertEquals(4, VersionUtil.getMinorVersion("7.4.1-1"));
		Assert.assertEquals(4, VersionUtil.getMinorVersion("7.4.11.1-1"));
	}

	@Test
	public void testIsLiferayVersion() throws Exception {
		Assert.assertFalse("x", VersionUtil.isLiferayVersion("x"));
		Assert.assertFalse("6.2", VersionUtil.isLiferayVersion("6.2"));
		Assert.assertFalse("7.0test", VersionUtil.isLiferayVersion("7.0test"));
		Assert.assertFalse("07.1.0", VersionUtil.isLiferayVersion("07.1.0"));
		Assert.assertTrue("7.0.10", VersionUtil.isLiferayVersion("7.0.10"));
		Assert.assertTrue("7.0.10.1", VersionUtil.isLiferayVersion("7.0.10.1"));
		Assert.assertTrue(
			"7.0.10.fp21", VersionUtil.isLiferayVersion("7.0.10.fp21"));
		Assert.assertTrue("7.1.10", VersionUtil.isLiferayVersion("7.1.10"));
		Assert.assertTrue("7.1.10.1", VersionUtil.isLiferayVersion("7.1.10.1"));
		Assert.assertTrue(
			"7.1.10.fp21", VersionUtil.isLiferayVersion("7.1.10.fp21"));
		Assert.assertTrue(
			"7.1.10.fp1-1", VersionUtil.isLiferayVersion("7.1.10.fp1-1"));
		Assert.assertTrue(
			"7.1.10.fp123-456",
			VersionUtil.isLiferayVersion("7.1.10.fp123-456"));
		Assert.assertTrue(
			"7.3.10.ep4", VersionUtil.isLiferayVersion("7.3.10.ep4"));
		Assert.assertTrue("8.0.0", VersionUtil.isLiferayVersion("8.0.0"));
		Assert.assertTrue("10.0.0", VersionUtil.isLiferayVersion("10.0.0"));
		Assert.assertTrue("100.0.0", VersionUtil.isLiferayVersion("100.0.0"));
		Assert.assertTrue("7.2", VersionUtil.isLiferayVersion("7.2"));
		Assert.assertTrue("7.4.1-1", VersionUtil.isLiferayVersion("7.4.1-1"));
		Assert.assertTrue(
			"7.4.11.1-1", VersionUtil.isLiferayVersion("7.4.11.1-1"));
	}

}