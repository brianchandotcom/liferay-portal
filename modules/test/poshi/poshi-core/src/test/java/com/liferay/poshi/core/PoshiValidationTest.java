/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core;

import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsUtil;
import com.liferay.poshi.core.util.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

import org.junit.After;
import org.junit.Before;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiValidationTest extends TestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		String[] poshiFileNames = ArrayUtils.addAll(
			PoshiContext.POSHI_SUPPORT_FILE_INCLUDES,
			PoshiContext.POSHI_TEST_FILE_INCLUDES);

		String poshiTestDirName =
			"src/test/resources/com/liferay/poshi/core/dependencies/test";

		String poshiValidationDirName =
			"src/test/resources/com/liferay/poshi/core/dependencies/validation";

		PoshiContext.readFiles(
			true, poshiFileNames, poshiTestDirName, poshiValidationDirName);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		PoshiContext.clear();
	}

	protected String getExceptionMessage() {
		Set<Exception> exceptions = PoshiValidation.getExceptions();

		StringBuilder sb = new StringBuilder();

		for (Exception exception : exceptions) {
			String message = exception.getMessage();

			int x = message.indexOf("\n");

			if (x == -1) {
				sb.append(message);
			}
			else {
				sb.append(message.substring(0, x));
			}
		}

		PoshiValidation.clearExceptions();

		return sb.toString();
	}

	protected String getFilePath(String fileName) {
		String filePath = FileUtil.getCanonicalPath(
			PropsUtil.get("test.base.dir.name") +
				"resources/com/liferay/poshi/core/dependencies/validation/" +
					fileName);

		if (OSDetector.isWindows()) {
			filePath = StringUtil.replace(filePath, "/", "\\");
		}

		return filePath;
	}

	protected URL getURL(String fileName) throws MalformedURLException {
		return new URL("file:" + getFilePath(fileName));
	}

}