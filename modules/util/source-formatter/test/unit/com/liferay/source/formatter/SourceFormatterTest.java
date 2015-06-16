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

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterTest {

	@Test
	public void testFileNameWithIncorrectExtension() throws Exception {
		SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

		sourceFormatterArgs.setAutoFix(false);
		sourceFormatterArgs.setPrintErrors(false);
		sourceFormatterArgs.setThrowException(false);
		sourceFormatterArgs.setUseProperties(false);

		String fileName =
			"test/unit/com/liferay/source/formatter/dependencies/wrong.foo";

		sourceFormatterArgs.setFileNames(Collections.singletonList(fileName));

		SourceFormatter sourceFormatter = new SourceFormatter(
			sourceFormatterArgs);

		sourceFormatter.format();

		List<String> modifiedFileNames = sourceFormatter.getModifiedFileNames();

		Assert.assertTrue(modifiedFileNames.isEmpty());
	}

	@Test
	public void testSourceFormatter() throws Exception {
		SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

		sourceFormatterArgs.setAutoFix(false);
		sourceFormatterArgs.setBaseDirName("../../../");
		sourceFormatterArgs.setPrintErrors(false);
		sourceFormatterArgs.setThrowException(true);
		sourceFormatterArgs.setUseProperties(false);

		SourceFormatter sourceFormatter = new SourceFormatter(
			sourceFormatterArgs);

		try {
			sourceFormatter.format();
		}
		catch (SourceMismatchException sme) {
			try {
				Assert.assertEquals(
					sme.getFileName(), sme.getFormattedSource(),
					sme.getOriginalSource());
			}
			catch (AssertionError ae) {
				String message = ae.getMessage();

				if (message.length() >= _MAX_MESSAGE_SIZE) {
					message =
						"Truncated message :\n" +
						message.substring(0, _MAX_MESSAGE_SIZE);

					throw new AssertionError(message, ae.getCause());
				}

				throw ae;
			}
		}
	}

	private static final int _MAX_MESSAGE_SIZE = 10000;

}