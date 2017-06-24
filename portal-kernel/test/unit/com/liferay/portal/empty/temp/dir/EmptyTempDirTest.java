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

import com.liferay.portal.kernel.util.OSDetector;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tom Wang
 */
public class EmptyTempDirTest {

	@Test
	public void testEmptyTempDir() throws IOException {
		File tempDir = new File(System.getProperty("liferay.temp.dir"));

		List<String> fileNames = Arrays.asList(tempDir.list());

		try {
			Assert.assertTrue(
				"Unexpected files found in the application server's temp " +
					"directory: " + fileNames,
				fileNames.isEmpty());
		}
		catch (AssertionError ae) {
			if (OSDetector.isWindows()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				Calendar calendar = Calendar.getInstance();

				Date today = calendar.getTime();

				Path logFilePath = Paths.get(
					System.getProperty("liferay.log.dir"),
					"liferay." + dateFormat.format(today) + ".xml");

				if (Files.exists(logFilePath)) {
					Files.write(
						logFilePath,
						Arrays.asList(
							"<log4j:event level=\"ERROR\">\n<log4j:message>",
							ae.getMessage(),
							"</log4j:message>\n</log4j:event>\n\n"),
						StandardOpenOption.APPEND);
				}
			}

			throw ae;
		}
	}

}