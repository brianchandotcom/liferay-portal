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

package com.liferay.portal.empty.temp.dir;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tom Wang
 */
public class EmptyTempDirTest {

	@Test
	public void testEmptyTempDir() {
		File tempDirFile = new File(
			System.getProperty("liferay.portal.temp.dir"));

		List<String> tempDirFileList = Arrays.asList(tempDirFile.list());

		Assert.assertTrue(
			"Unexpected file(s) detected in AppServer temp directory: " +
				tempDirFileList,
			tempDirFileList.isEmpty());
	}

}