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
 * @author Qi Zhang
 */
public class JavaUpgradeProcessTest extends BaseSourceProcessorTestCase {

	@Test
	public void testMoveUpgradeSteps() throws Exception {
		test(
			"MoveUpgradeSteps.testjava",
			new String[] {
				"Move 'alterTableAddColumn' call inside 'getPreUpgradeSteps' " +
					"method",
				"Move 'alterTableAddColumn' call inside " +
					"'getPostUpgradeSteps' method"
			},
			new Integer[] {26, 30});
	}

	@Test
	public void testUnnecessaryUpgradeProcessClass() throws Exception {
		test(
			"UnnecessaryUpgradeProcessClass.testjava",
			"No need to create 'UnnecessaryUpgradeProcessClass' class. " +
				"Replace it by inline calls to the 'UpgradeProcessFactory' " +
					"class in the registry class",
			22);
	}

	@Test
	public void testUpgradeProcessUnnecessaryIfStatement() throws Exception {
		test(
			"UpgradeProcessUnnecessaryIfStatement.testjava",
			"No need to use if-statement to wrap 'alterColumn*' and " +
				"'alterTable*' calls",
			26);
	}

}