/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

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
				"moved to the archived folder");
		test(
			SourceProcessorTestParameters.create(
				"FormatBndInstructions3/app.testbnd"
			).addExpectedMessage(
				"The 'Liferay-Releng-Restart-Required' can only be set to " +
					"false if a POSHI tests exists"
			).addExpectedMessage(
				StringBundler.concat(
					"The 'Liferay-Releng-Suite' can be blank or one of the ",
					"following values 'collaboration, commerce, ",
					"forms-and-workflow, foundation, static, web-experience'")
			));
	}

	@Test
	public void testFormatDefinitionKeys() throws Exception {
		test("FormatDefinitionKeys1/common.testbnd");
		test(
			SourceProcessorTestParameters.create(
				"FormatDefinitionKeys2/common.testbnd"
			).addExpectedMessage(
				"Unknown key \"-fixupmessagess\""
			).addExpectedMessage(
				"Unknown key \"Liferay-Portal-ServerInfo\""
			));
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