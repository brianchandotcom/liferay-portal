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
	public void testCheckExtraJudgement() throws Exception {
		test(
			"CheckUnnecessaryMethodUpgradeProcess.testjava",
			"Remove unnecessary usages of hasColumn or hasColumnType.", 26);
	}

	@Test
	public void testCheckIfDelete() throws Exception {
		test(
			"CheckIfDeleteUpgradeProcess.testjava",
			"Delete this class and replace it by inline calls to the " +
				"UpgradeProcessFactory class in the registry class.",
			22);
	}

	@Test
	public void testMoveLogicToPreOrPostMethod() throws Exception {
		test(
			"MoveLogicToPreOrPostMethodUpgradeProcess.testjava",
			new String[] {
				"Drop 26 to 26 line and make that operation from the " +
					"'getPreUpgradeSteps' method by using the " +
						"UpgradeProcessFactory class.",
				"Drop 30 to 30 line and make that operation from the " +
					"'getPostUpgradeSteps' method by using the " +
						"UpgradeProcessFactory class."
			},
			new Integer[] {26, 30});
	}

}