/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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