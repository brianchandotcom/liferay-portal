/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestReport;
import com.liferay.jenkins.results.parser.test.clazz.SemVerModulesTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class SemVerModulesAxisTestClassGroup extends AxisTestClassGroup {

	@Override
	public List<DownstreamBuildReport> getCachedDownstreamBuildReports() {
		if (!JenkinsResultsParserUtil.isBuildCachingEnabled() ||
			!isResultsCached()) {

			return null;
		}

		List<DownstreamBuildReport> cachedDownstreamBuildReports =
			new ArrayList<>();

		for (SemVerModulesTestClass semVerModulesTestClass :
				getSemVerModulesTestClasses()) {

			DownstreamBuildReport downstreamBuildReport =
				semVerModulesTestClass.getCachedDownstreamBuildReport();

			if (cachedDownstreamBuildReports.contains(downstreamBuildReport)) {
				continue;
			}

			cachedDownstreamBuildReports.add(downstreamBuildReport);
		}

		return cachedDownstreamBuildReports;
	}

	public List<SemVerModulesTestClass> getSemVerModulesTestClasses() {
		List<SemVerModulesTestClass> semVerModulesTestClasses =
			new ArrayList<>();

		for (TestClass testClass : getTestClasses()) {
			if (!(testClass instanceof SemVerModulesTestClass)) {
				continue;
			}

			semVerModulesTestClasses.add((SemVerModulesTestClass)testClass);
		}

		return semVerModulesTestClasses;
	}

	@Override
	public boolean isResultsCached() {
		if (!JenkinsResultsParserUtil.isBuildCachingEnabled()) {
			return false;
		}

		for (SemVerModulesTestClass semVerModulesTestClass :
				getSemVerModulesTestClasses()) {

			List<TestReport> cachedTestReports =
				semVerModulesTestClass.getCachedTestReports();

			if ((cachedTestReports == null) || cachedTestReports.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	protected SemVerModulesAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);
	}

	protected SemVerModulesAxisTestClassGroup(
		SemVerModulesBatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

}