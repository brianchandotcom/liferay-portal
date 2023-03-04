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

package com.liferay.poshi.core.script;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.PropsUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PoshiScriptParserTest extends TestCase {

	@Test
	public void testInvalidSyntax() throws Exception {
		String poshiFileDir =
			"src/test/resources/com/liferay/poshi/core/dependencies/script";

		PropsUtil.clear();

		PropsUtil.set("test.base.dir.name", poshiFileDir);

		try {
			PoshiContext.readFiles();
		}
		catch (Exception exception) {
			System.out.println(exception);
		}

		Map<String, Integer> expectedErrorMessages =
			new TreeMap<String, Integer>() {
				{
					put(
						"Invalid variable assignment syntax, please use " +
							"triple quotes (''') to wrap a multiline string " +
								"instead of double quotes",
						4);
					put("Unescaped quotes in list value: \"item 1, item2\"", 7);
				}
			};

		List<PoshiScriptParserException> poshiScriptParserExceptions =
			PoshiScriptParserException.getExceptions();

		Iterator<PoshiScriptParserException>
			poshiScriptParserExceptionIterator =
				poshiScriptParserExceptions.iterator();

		Set<Map.Entry<String, Integer>> expectedErrorMessagesEntries =
			expectedErrorMessages.entrySet();

		Iterator<Map.Entry<String, Integer>> expectedErrorMessagesIterator =
			expectedErrorMessagesEntries.iterator();

		while (poshiScriptParserExceptionIterator.hasNext() &&
			   expectedErrorMessagesIterator.hasNext()) {

			Map.Entry<String, Integer> expectedErrorMessagesEntry =
				expectedErrorMessagesIterator.next();

			PoshiScriptParserException poshiScriptParserException =
				poshiScriptParserExceptionIterator.next();

			String actualErrorMessage = poshiScriptParserException.getMessage();

			Assert.assertTrue(
				actualErrorMessage.contains(
					expectedErrorMessagesEntry.getKey()));

			assertEquals(
				expectedErrorMessagesEntry.getValue(),
				Integer.valueOf(
					poshiScriptParserException.getErrorLineNumber()));
		}
	}

}