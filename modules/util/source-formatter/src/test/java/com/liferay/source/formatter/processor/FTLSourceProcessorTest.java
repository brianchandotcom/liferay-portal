/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Qi Zhang
 */
public class FTLSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectComments() throws Exception {
		test("MacroTagAttributes.testftl");
	}

	@Test
	public void testWhitespace() throws Exception {
		test("Whitespace.testftl");
	}

}