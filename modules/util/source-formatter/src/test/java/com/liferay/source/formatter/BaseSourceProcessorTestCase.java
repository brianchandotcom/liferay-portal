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

package com.liferay.source.formatter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * @author Hugo Huijser
 */
public abstract class BaseSourceProcessorTestCase {

	@BeforeClass
	public static void setUpClass() {
		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(StringPool.SLASH);
		sb.append(StringUtil.randomString());
		sb.append(StringPool.SLASH);

		_temporaryRootFolder = new File(sb.toString());

		sb.append(_DIR_NAME);

		_temporaryFolder = new File(sb.toString());
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		FileUtils.deleteDirectory(_temporaryRootFolder);
	}

	protected SourceFormatterArgs getSourceFormatterArgs() {
		SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

		sourceFormatterArgs.setAutoFix(true);
		sourceFormatterArgs.setPrintErrors(false);
		sourceFormatterArgs.setThrowException(false);

		return sourceFormatterArgs;
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
			String fileName, String[] expectedMessages, Integer[] lineNumbers)
		throws Exception {

		int pos = fileName.lastIndexOf(CharPool.PERIOD);

		if (pos == -1) {
			throw new IllegalArgumentException(
				"The file name " + fileName +
					" does not end with a valid extension");
		}

		String originalExtension = fileName.substring(pos + 1);

		String extension = originalExtension;

		fileName = fileName.substring(0, pos);

		if (originalExtension.startsWith("test")) {
			extension = extension.substring(4);
		}

		String fullFileName = StringBundler.concat(
			_DIR_NAME, StringPool.SLASH, fileName, ".", originalExtension);

		URL url = classLoader.getResource(fullFileName);

		if (url == null) {
			throw new FileNotFoundException(fullFileName);
		}

		File newFile = new File(_temporaryFolder, fileName + "." + extension);

		try (InputStream inputStream = url.openStream()) {
			FileUtils.copyInputStreamToFile(inputStream, newFile);
		}

		SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

		sourceFormatterArgs.setFileNames(
			Collections.singletonList(newFile.getAbsolutePath()));

		SourceFormatter sourceFormatter = new SourceFormatter(
			sourceFormatterArgs);

		sourceFormatter.format();

		List<String> modifiedFileNames = sourceFormatter.getModifiedFileNames();

		if (modifiedFileNames.isEmpty()) {
			throw new IllegalArgumentException(
				"The file name " + newFile.getAbsolutePath() +
					" does not end with a valid extension");
		}

		List<SourceFormatterMessage> sourceFormatterMessages =
			ListUtil.fromCollection(
				sourceFormatter.getSourceFormatterMessages());

		if (!sourceFormatterMessages.isEmpty() ||
			(expectedMessages.length > 0)) {

			Assert.assertEquals(
				sourceFormatterMessages.toString(), expectedMessages.length,
				sourceFormatterMessages.size());

			for (int i = 0; i < sourceFormatterMessages.size(); i++) {
				SourceFormatterMessage sourceFormatterMessage =
					sourceFormatterMessages.get(i);

				Assert.assertEquals(
					expectedMessages[i], sourceFormatterMessage.getMessage());

				int lineNumber = sourceFormatterMessage.getLineNumber();

				if (lineNumber > -1) {
					Assert.assertEquals(
						String.valueOf(lineNumbers[i]),
						String.valueOf(lineNumber));
				}

				String absolutePath = StringUtil.replace(
					newFile.getAbsolutePath(), CharPool.BACK_SLASH,
					CharPool.SLASH);

				Assert.assertEquals(
					absolutePath, sourceFormatterMessage.getFileName());
			}
		}
		else {
			String actualFormattedContent = FileUtil.read(
				new File(modifiedFileNames.get(0)));

			String expectedFileName = StringBundler.concat(
				_DIR_NAME, "/expected/", fileName, ".", originalExtension);

			URL expectedURL = classLoader.getResource(expectedFileName);

			if (expectedURL == null) {
				throw new FileNotFoundException(expectedFileName);
			}

			String expectedFormattedContent = IOUtils.toString(
				expectedURL, StringPool.UTF8);

			expectedFormattedContent = StringUtil.replace(
				expectedFormattedContent, StringPool.RETURN_NEW_LINE,
				StringPool.NEW_LINE);

			Assert.assertEquals(
				expectedFormattedContent, actualFormattedContent);
		}
	}

	protected final ClassLoader classLoader =
		BaseSourceProcessorTestCase.class.getClassLoader();

	private static final String _DIR_NAME =
		"com/liferay/source/formatter/dependencies";

	private static File _temporaryFolder;
	private static File _temporaryRootFolder;

}