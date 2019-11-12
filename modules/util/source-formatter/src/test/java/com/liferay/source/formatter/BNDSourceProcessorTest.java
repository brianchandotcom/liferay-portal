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

import com.liferay.petra.string.StringBundler;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class BNDSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testFormatBndInstructions() throws Exception {
		test("FormatBndInstructions1/app.testbnd");
		test(
			"FormatBndInstructions2/app.testbnd",
			"Deprecated apps that are not published on Marketplace should be " +
				"moved to the deprecated folder");
		test(
			"FormatBndInstructions3/app.testbnd",
			StringBundler.concat(
				"The 'Liferay-Releng-Suite' can be blank or one of the ",
				"following values collaboration, forms-and-workflow, ",
				"foundation, static, web-experience"));
	}

	@Test
	public void testFormatDefinitionKeys() throws Exception {
		test("FormatDefinitionKeys1/common.testbnd");
		test(
			"FormatDefinitionKeys2/common.testbnd",
			new String[] {
				"Unknown key \"-fixupmessagess\"",
				"Unknown key \"Liferay-Portal-ServerInfo\""
			});
	}

	@Test
	public void testIncorrectBundleActivator() throws Exception {
		test(
			"IncorrectBundleActivator1/bnd.testbnd",
			"Incorrect Bundle-Activator, it should match " +
				"'Bundle-SymbolicName'");
		test(
			"IncorrectBundleActivator2/bnd.testbnd",
			"Incorrect Bundle-Activator, it should end with 'BundleActivator'");
		test(
			"IncorrectBundleActivator3/bnd.testbnd",
			"Incorrect Bundle-Activator, it should match " +
				"'Bundle-SymbolicName'");
	}

	@Test
	public void testRemoveInternalPrivatePackages() throws Exception {
		test("RemoveInternalPrivatePackages.testbnd");
	}

	@Test
	public void testRemoveNoValueDefinitionKey() throws Exception {
		test("RemoveNoValueDefinitionKey1.testbnd");
		test("RemoveNoValueDefinitionKey2.testbnd");
	}

}