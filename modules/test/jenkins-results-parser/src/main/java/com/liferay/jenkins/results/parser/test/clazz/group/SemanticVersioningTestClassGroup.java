/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Charlotte Wong
 */
public class SemanticVersioningTestClassGroup extends BatchTestClassGroup {

	protected SemanticVersioningTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected SemanticVersioningTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		if (ignore()) {
			return;
		}

		File buildTestBatchFile = new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			"build-test-batch.xml");

		addTestClass(TestClassFactory.newTestClass(this, buildTestBatchFile));

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	@Override
	protected boolean ignore() {
		if (!isStableTestSuiteBatch() && testRelevantJUnitTestsOnly) {
			return true;
		}

		if ((isStableTestSuiteBatch() && testRelevantJUnitTestsOnlyInStable) ||
			isQuarterlyReleaseBranch()) {

			return true;
		}

		return false;
	}

	protected boolean isQuarterlyReleaseBranch() {
		Matcher quarterlyReleaseNameMatcher =
			_quarterlyReleaseNamePattern.matcher(
				portalGitWorkingDirectory.getUpstreamBranchName());

		if (quarterlyReleaseNameMatcher.find()) {
			return true;
		}

		return false;
	}

	private static final Pattern _quarterlyReleaseNamePattern = Pattern.compile(
		"(release-\\d{4}.[qQ](.\\d)?)");

}