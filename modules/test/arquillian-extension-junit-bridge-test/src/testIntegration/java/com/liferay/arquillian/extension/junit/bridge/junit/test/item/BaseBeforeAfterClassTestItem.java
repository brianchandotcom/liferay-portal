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

package com.liferay.arquillian.extension.junit.bridge.junit.test.item;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Matthew Tambara
 */
public class BaseBeforeAfterClassTestItem {

	@BeforeClass
	public static void setUpClassBase() throws IOException {
		testItemHelper.write("setUpClassBase");
	}

	@BeforeClass
	public static void setUpClassOverridden() throws IOException {
		testItemHelper.write("setUpClassOverriddenBase");
	}

	@AfterClass
	public static void tearDownClassBase() throws IOException {
		testItemHelper.write("tearDownClassBase");
	}

	@AfterClass
	public static void tearDownClassOverridden() throws IOException {
		testItemHelper.write("tearDownClassOverriddenBase");
	}

	protected static final TestItemHelper testItemHelper = new TestItemHelper(
		BaseBeforeAfterClassTestItem.class);

}