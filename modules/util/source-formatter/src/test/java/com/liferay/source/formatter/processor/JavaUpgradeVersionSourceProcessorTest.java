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

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class JavaUpgradeVersionSourceProcessorTest
	extends BaseSourceProcessorTestCase {

	@Test
	public void testDummyUpgradeStepVersion() throws Exception {
		test("DummyUpgradeStepVersion.testjava");
	}

	@Test
	public void testMajorUpgradeByAlterColumnName() throws Exception {
		test("MajorUpgradeByAlterColumnName.testjava", "2.0.0", 30);
	}

	@Test
	public void testMajorUpgradeByAlterTableDropColumn() throws Exception {
		test("MajorUpgradeByAlterTableDropColumn.testjava", "2.0.0", 31);
	}

	@Test
	public void testMajorUpgradeByAlterTableDropColumnClause()
		throws Exception {

		test("MajorUpgradeByAlterTableDropColumnClause.testjava", "2.0.0", 31);
	}

	@Test
	public void testMajorUpgradeByDropTable() throws Exception {
		test("MajorUpgradeByDropTable.testjava", "2.0.0", 30);
	}

	@Test
	public void testMinorUpgradeByAlterTableAddColumn() throws Exception {
		test("MinorUpgradeByAlterTableAddColumn.testjava", "1.1.0", 31);
	}

	@Test
	public void testMinorUpgradeByAlterTableAddColumnClause() throws Exception {
		test("MinorUpgradeByAlterTableAddColumnClause.testjava", "1.1.0", 31);
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