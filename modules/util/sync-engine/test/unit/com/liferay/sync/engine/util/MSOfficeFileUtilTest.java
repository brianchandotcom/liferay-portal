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

package com.liferay.sync.engine.util;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class MSOfficeFileUtilTest {

	@Test
	public void testIsExcelFile() throws Exception {
		String[] fileNames = {
			"TEST.CSV", "test.csv", "test.xls", "test.xlsb", "test.xlsm",
			"test.xlsx", "test.xltx"
		};

		for (String fileName : fileNames) {
			Assert.assertTrue(
				MSOfficeFileUtil.isExcelFile(Paths.get(fileName)));
		}
	}

	@Test
	public void testIsTempCreatedFile() throws Exception {
		String[] fileNames = {"~$st.doc", "~wrf0000.tmp"};

		for (String fileName : fileNames) {
			Assert.assertTrue(
				MSOfficeFileUtil.isTempCreatedFile(Paths.get(fileName)));
		}

		Assert.assertFalse(
			MSOfficeFileUtil.isTempCreatedFile(Paths.get("test.tmp")));
	}

	@Test
	public void testIsTempRenamedFile() throws Exception {
		String[] fileNames = {"CF19E4A8.tmp", "6CFEC200", "E1ECC200"};

		for (String fileName : fileNames) {
			Assert.assertTrue(
				MSOfficeFileUtil.isTempRenamedFile(Paths.get(fileName)));
		}

		Assert.assertFalse(
			MSOfficeFileUtil.isTempCreatedFile(Paths.get("6cfec200")));
	}

}