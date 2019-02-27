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

import org.junit.After;
import org.junit.Before;

/**
 * @author Matthew Tambara
 */
public class BaseBeforeAfterTestItem {

	@Before
	public static void setUpOverridden() throws IOException {
		testItemHelper.write("setUpOverriddenBase");
	}

	@After
	public static void tearDownOverridden() throws IOException {
		testItemHelper.write("tearDownOverriddenBase");
	}

	@Before
	public void setUpBase() throws IOException {
		testItemHelper.write("setUpBase");
	}

	@After
	public void tearDownBase() throws IOException {
		testItemHelper.write("tearDownBase");
	}

	protected static final TestItemHelper testItemHelper = new TestItemHelper(
		BeforeAfterTestItem.class);

}