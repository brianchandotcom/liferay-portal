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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RootCauseAnalysisToolJob;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class FunctionalRCABatchTestClassGroup extends RCABatchTestClassGroup {

	protected FunctionalRCABatchTestClassGroup(
		String batchName, RootCauseAnalysisToolJob rootCauseAnalysisToolJob) {

		super(batchName, rootCauseAnalysisToolJob);

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	@Override
	protected void setAxisTestClassGroups() {
		if (!axisTestClassGroups.isEmpty()) {
			return;
		}

		String portalBatchTestSelector = System.getenv(
			"PORTAL_BATCH_TEST_SELECTOR");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBatchTestSelector)) {
			portalBatchTestSelector = getBuildStartProperty(
				"PORTAL_BATCH_TEST_SELECTOR");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBatchTestSelector)) {
			return;
		}

		Matcher matcher = _pattern.matcher(portalBatchTestSelector);

		if (!matcher.find()) {
			return;
		}

		String namespace = matcher.group("namespace");

		if (JenkinsResultsParserUtil.isNullOrEmpty(namespace)) {
			namespace = "LocalFile";
		}

		String classMethodName = matcher.group("classMethodName");

		AxisTestClassGroup axisTestClassGroup =
			TestClassGroupFactory.newAxisTestClassGroup(
				this, _getTestBaseDir());

		axisTestClassGroup.addTestClass(
			FunctionalBatchTestClassGroup.FunctionalTestClass.getInstance(
				JenkinsResultsParserUtil.combine(
					namespace, ".", classMethodName)));

		axisTestClassGroups.add(axisTestClassGroup);
	}

	private File _getTestBaseDir() {
		return new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			"portal-web/test/functional/portalweb");
	}

	private static final Pattern _pattern = Pattern.compile(
		"((<?namespace>[^\\.]+)\\.)?(?<classMethodName>[^#]+#.+)");

}