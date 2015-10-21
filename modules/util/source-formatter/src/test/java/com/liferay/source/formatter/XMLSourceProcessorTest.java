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

import org.junit.Test;

/**
 * @author Karen Dang
 */
public class XMLSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testCharactersAfterDefinitionTag() throws Exception {
		test(
			"CharactersAfterDefinitionTag1.testmacro",
			"Characters found after definition element:");
		test(
			"CharactersAfterDefinitionTag2.testmacro",
			"Characters found after definition element:");
		test(
			"CharactersAfterDefinitionTag3.testmacro",
			"Characters found after definition element:");
		test(
			"CharactersAfterDefinitionTag4.testmacro",
			"Characters found after definition element:");
	}

	@Test
	public void testCharactersBeforeDefinitionTag() throws Exception {
		test(
			"CharactersBeforeDefinitionTag1.testmacro",
			"Characters found before definition element:");
		test(
			"CharactersBeforeDefinitionTag2.testmacro",
			"Characters found before definition element:");
		test(
			"CharactersBeforeDefinitionTag3.testmacro",
			"Characters found before definition element:");
		test(
			"CharactersBeforeDefinitionTag4.testmacro",
			"Characters found before definition element:");
	}

	@Test
	public void testIncorrectTabs() throws Exception {
		test("IncorrectTabs1.testaction");
		test("IncorrectTabs2.testaction");
		test("IncorrectTabs3.testaction");
	}

}