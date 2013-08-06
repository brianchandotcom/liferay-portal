/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author Hugo Huijser
 */
public class BaseSourceProcessorTestCase {

	@Before
	public void setUp() throws Exception {
		_sourceFormatter = SourceFormatterUtil.create(
			false, false, false, false);
	}

	protected void test(String fileName) throws Exception {
		test(fileName, null, -1);
	}

	protected void test(String fileName, String expectedErrorMessage)
		throws Exception {

		test(fileName, expectedErrorMessage, -1);
	}

	protected void test(
			String fileName, String expectedErrorMessage, int lineNumber)
		throws Exception {

		String fullFileName = _DIR_NAME + StringPool.SLASH + fileName;

		String[] result = _sourceFormatter.format(fullFileName);

		if (result == null) {
			return;
		}

		String actualErrorMessage = result[1];

		if (Validator.isNotNull(actualErrorMessage) ||
			Validator.isNotNull(expectedErrorMessage)) {

			StringBundler sb = new StringBundler(5);

			sb.append(expectedErrorMessage);
			sb.append(StringPool.SPACE);
			sb.append(fullFileName);

			if (lineNumber > 0) {
				sb.append(StringPool.SPACE);
				sb.append(lineNumber);
			}

			Assert.assertEquals(sb.toString(), actualErrorMessage);
		}
		else {
			String actualFormattedContent = result[0];

			try {
				File file = new File(_DIR_NAME + "/expected/" + fileName);

				String expectedFormattedContent = FileUtil.read(file.getPath());

				Assert.assertEquals(
					expectedFormattedContent, actualFormattedContent);
			}
			catch (FileNotFoundException fnfe) {
				Assert.fail();
			}
		}
	}

	private static final String _DIR_NAME =
		"portal-impl/test/integration/com/liferay/portal/tools/" +
			"sourceformatter/dependencies";

	private SourceFormatter _sourceFormatter;

}