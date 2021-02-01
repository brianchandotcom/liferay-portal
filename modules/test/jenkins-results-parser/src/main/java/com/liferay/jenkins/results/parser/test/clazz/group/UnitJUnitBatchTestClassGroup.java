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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public class UnitJUnitBatchTestClassGroup extends JUnitBatchTestClassGroup {

	protected UnitJUnitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected boolean isValidTestClass(TestClass testClass) {
		File testClassFile = testClass.getTestClassFile();

		try {
			String testClassFilePath = testClassFile.getCanonicalPath();

			if (testClassFilePath.contains("/test/unit/")) {
				return true;
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return false;
	}

}