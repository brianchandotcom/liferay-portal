/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author David Zhang
 */
public class TLDSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectEmptyLines() throws Exception {
		test("IncorrectEmptyLines.testtld");
	}

	@Test
	public void testMissingCDATA() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"MissingCDATA.testtld"
			).addExpectedMessage(
				"Use CDATA to warp each '<code>' in the description", 14
			).addExpectedMessage(
				"Missing CDATA after 'replaced by' in the description", 19
			));
	}

	@Test
	public void testUnnecessaryCDATA() throws Exception {
		test("UnnecessaryCDATA.testtld");
	}

}