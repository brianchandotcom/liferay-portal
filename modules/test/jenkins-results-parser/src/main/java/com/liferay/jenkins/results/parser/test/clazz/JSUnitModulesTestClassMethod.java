/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassReport;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSUnitModulesTestClassMethod extends TestClassMethod {

	public DownstreamBuildReport getCachedDownstreamBuildReport() {
		if (!_cachedTestClassReportsSearched) {
			getCachedTestClassReports();
		}

		return _cachedDownstreamBuildReport;
	}

	public List<TestClassReport> getCachedTestClassReports() {
		if (!JenkinsResultsParserUtil.isBuildCachingEnabled() ||
			_cachedTestClassReportsSearched) {

			return _cachedTestClassReports;
		}

		_cachedTestClassReports = new ArrayList<>();

		BatchTestClassGroup batchTestClassGroup =
			_jsUnitModulesTestClass.getBatchTestClassGroup();

		for (DownstreamBuildReport cachedDownstreamBuildReport :
				batchTestClassGroup.getCachedDownstreamBuildReports()) {

			for (TestClassReport testClassResult :
					cachedDownstreamBuildReport.getTestClassReports()) {

				if (!Objects.equals(
						getName(), testClassResult.getTestTaskName())) {

					continue;
				}

				_cachedTestClassReports.add(testClassResult);
			}

			if (_cachedTestClassReports.isEmpty()) {
				continue;
			}

			_cachedDownstreamBuildReport = cachedDownstreamBuildReport;

			return _cachedTestClassReports;
		}

		_cachedTestClassReportsSearched = true;

		return _cachedTestClassReports;
	}

	protected JSUnitModulesTestClassMethod(
		boolean ignored, String name, TestClass testClass) {

		super(ignored, name, testClass);

		_jsUnitModulesTestClass = (JSUnitModulesTestClass)testClass;
	}

	protected JSUnitModulesTestClassMethod(
		JSONObject jsonObject, TestClass testClass) {

		super(jsonObject, testClass);

		_jsUnitModulesTestClass = (JSUnitModulesTestClass)testClass;
	}

	private DownstreamBuildReport _cachedDownstreamBuildReport;
	private List<TestClassReport> _cachedTestClassReports = new ArrayList<>();
	private boolean _cachedTestClassReportsSearched;
	private final JSUnitModulesTestClass _jsUnitModulesTestClass;

}