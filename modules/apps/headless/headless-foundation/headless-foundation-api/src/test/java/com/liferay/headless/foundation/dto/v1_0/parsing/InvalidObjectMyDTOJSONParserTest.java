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

package com.liferay.headless.foundation.dto.v1_0.parsing;

import com.liferay.headless.foundation.dto.v1_0.MyDTO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class InvalidObjectMyDTOJSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testNoClosingBracket() {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("Expected '}'; got '{'");

		MyDTO.toMyDto("{");
	}

	@Test
	public void testNoOpeningBracket() {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("Expected '{'; got '}'");

		MyDTO.toMyDto("}");
	}

	@Test
	public void testTwoClosingBrackets() {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("Expected end of JSON; got '}'");

		MyDTO.toMyDto("{}}");
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

}