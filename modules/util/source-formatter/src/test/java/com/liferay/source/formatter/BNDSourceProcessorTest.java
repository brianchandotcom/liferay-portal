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
 * @author Alan Huang
 */
public class BNDSourceProcessorTest extends BaseSourceProcessorTestCase {

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