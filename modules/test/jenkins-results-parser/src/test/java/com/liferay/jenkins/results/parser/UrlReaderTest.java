/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class UrlReaderTest extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testToInputStream() throws Exception {
		UrlReader urlReader = mockUrlReader();

		setUrlReaderOutput(_STANDARD_OUT, _URL, urlReader);

		try (InputStream inputStream = JenkinsResultsParserUtil.toInputStream(
				_URL, false)) {

			Assert.assertEquals(
				_STANDARD_OUT,
				JenkinsResultsParserUtil.readInputStream(inputStream));
		}
	}

	@Test
	public void testToString() throws Exception {
		UrlReader urlReader = mockUrlReader();

		setUrlReaderOutput(_STANDARD_OUT, _URL, urlReader);

		Assert.assertEquals(
			_STANDARD_OUT, JenkinsResultsParserUtil.toString(_URL, false));
	}

	private static final String _STANDARD_OUT = "Hello, World!\n";

	private static final String _URL = "http://test.liferay.com";

}