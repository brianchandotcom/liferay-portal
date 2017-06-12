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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ConsoleTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class PortalClientBuilderTest {

	@Test
	public void testPortalClientBuilder() throws Exception {
		Path outputPath = Files.createTempDirectory(null);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			ConsoleTestUtil.hijackStdOut();

		try {
			PortalClientBuilder.main(
				new String[] {
					"portal-web/docroot/WEB-INF/server-config.wsdd",
					outputPath.toString(),
					"portal-client/namespace-mapping.properties",
					"http://localhost:8080"
				});
		}
		finally {
			FileUtil.deltree(outputPath.toFile());

			String output = ConsoleTestUtil.restoreStdOut(
				unsyncByteArrayOutputStream);

			for (String line : StringUtil.splitLines(output)) {
				line = line.trim();

				Assert.assertTrue(
					"Unexpected output " + output,
					line.startsWith("Loading ") ||
					line.startsWith("WSDL2Java "));
			}
		}
	}

}