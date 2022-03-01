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

package com.liferay.jenkins.results.parser.job.property;

import com.liferay.jenkins.results.parser.Job;

import java.io.File;

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesTestDirJobProperty extends BaseTestDirJobProperty {

	protected QAWebsitesTestDirJobProperty(
		Job job, Type type, File testBaseDir, String basePropertyName,
		boolean useBasePropertyName, String testSuiteName,
		String testBatchName) {

		super(
			job, type, testBaseDir, basePropertyName, useBasePropertyName,
			testSuiteName, testBatchName);
	}

	@Override
	protected List<File> getJobPropertiesFiles() {
		return Arrays.asList(
			new File(getTestBaseDir(), "poshi-ext.properties"),
			new File(getTestBaseDir(), "poshi.properties"),
			new File(getTestBaseDir(), "test.properties"));
	}

}