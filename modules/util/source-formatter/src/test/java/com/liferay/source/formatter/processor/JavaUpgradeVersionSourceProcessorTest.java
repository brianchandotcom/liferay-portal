/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class JavaUpgradeVersionSourceProcessorTest
	extends BaseSourceProcessorTestCase {

	@Test
	public void testMajorUpgradeByAlterColumnName() throws Exception {
		test("MajorUpgradeByAlterColumnName.testjava", "2.0.0", 23);
	}

	@Test
	public void testMajorUpgradeByAlterTableDropColumn() throws Exception {
		test("MajorUpgradeByAlterTableDropColumn.testjava", "2.0.0", 24);
	}

	@Test
	public void testMajorUpgradeByAlterTableDropColumnClause()
		throws Exception {

		test("MajorUpgradeByAlterTableDropColumnClause.testjava", "2.0.0", 24);
	}

	@Test
	public void testMajorUpgradeByDropTable() throws Exception {
		test("MajorUpgradeByDropTable.testjava", "2.0.0", 23);
	}

	@Test
	public void testMinorUpgradeByAlterTableAddColumn() throws Exception {
		test("MinorUpgradeByAlterTableAddColumn.testjava", "1.1.0", 24);
	}

	@Test
	public void testMinorUpgradeByAlterTableAddColumnClause() throws Exception {
		test("MinorUpgradeByAlterTableAddColumnClause.testjava", "1.1.0", 24);
	}

	@Test
	public void testMissingRegisterInitialization() throws Exception {
		test("MissingRegisterInitialization1.testjava");
		test("MissingRegisterInitialization2.testjava");
		test(
			"MissingRegisterInitialization3.testjava",
			"The upgrade process from version 0.0.0 should be replaced by " +
				"'registry.registerInitialization()'");
	}

	@Override
	protected void test(
			String fileName, String expectedSchemaVersion, int lineNumber)
		throws Exception {

		super.test(
			fileName, "Expected new schema version: " + expectedSchemaVersion,
			lineNumber);
	}

}