/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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