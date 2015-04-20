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

package com.liferay.portal.tools.source.formatter;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.io.File;

import java.util.List;

import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Before;

/**
 * @author Hugo Huijser
 */
public class BaseSourceProcessorTestCase {

	@Before
	public void setUp() throws Exception {
		sourceFormatter = SourceFormatterUtil.create(
			false, false, false, false);
	}

	protected void test(String fileName) throws Exception {
		test(fileName, new String[0]);
	}

	protected void test(String fileName, String expectedErrorMessage)
		throws Exception {

		test(fileName, new String[] {expectedErrorMessage});
	}

	protected void test(
			String fileName, String expectedErrorMessage, int lineNumber)
		throws Exception {

		test(
			fileName, new String[] {expectedErrorMessage},
			new Integer[] {lineNumber});
	}

	protected void test(String fileName, String[] expectedErrorMessages)
		throws Exception {

		test(fileName, expectedErrorMessages, null);
	}

	protected void test(
			String fileName, String[] expectedErrorMessages,
			Integer[] lineNumbers)
		throws Exception {

		String fullFileName = _DIR_NAME + StringPool.SLASH + fileName;

		Tuple tuple = sourceFormatter.format(fullFileName);

		if (tuple == null) {
			throw new IllegalArgumentException(
				"The file name " + fullFileName +
					" does not end with a valid extension");
		}

		List<String> errorMessages = (List<String>)tuple.getObject(1);

		if (!errorMessages.isEmpty() || (expectedErrorMessages.length > 0)) {
			Assert.assertEquals(
				expectedErrorMessages.length, errorMessages.size());

			for (int i = 0; i < errorMessages.size(); i++) {
				String actualErrorMessage = errorMessages.get(i);
				String expectedErrorMessage = expectedErrorMessages[i];

				StringBundler sb = new StringBundler(5);

				sb.append(expectedErrorMessage);
				sb.append(StringPool.SPACE);
				sb.append(fullFileName);

				if (lineNumbers != null) {
					sb.append(StringPool.SPACE);
					sb.append(lineNumbers[i]);
				}

				Assert.assertEquals(sb.toString(), actualErrorMessage);
			}
		}
		else {
			String actualFormattedContent = (String)tuple.getObject(0);

			File file = new File(_DIR_NAME + "/expected/" + fileName);

			String expectedFormattedContent = FileUtils.readFileToString(
				file, StringPool.UTF8);

			expectedFormattedContent = StringUtil.replace(
				expectedFormattedContent, StringPool.RETURN_NEW_LINE,
				StringPool.NEW_LINE);

			Assert.assertEquals(
				expectedFormattedContent, actualFormattedContent);
		}
	}

	protected SourceFormatter sourceFormatter;

	private static final String _DIR_NAME =
		"portal-impl/test/integration/com/liferay/portal/tools/" +
			"sourceformatter/dependencies";

}