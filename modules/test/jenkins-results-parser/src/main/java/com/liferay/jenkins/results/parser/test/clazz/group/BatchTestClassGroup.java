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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.BatchHistory;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.JobHistory;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.TestHistory;
import com.liferay.jenkins.results.parser.TestSuiteJob;
import com.liferay.jenkins.results.parser.job.property.GlobJobProperty;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.File;
import java.io.IOException;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchTestClassGroup extends BaseTestClassGroup {

	public void addAxisTestClassGroup(AxisTestClassGroup axisTestClassGroup) {
		axisTestClassGroups.add(axisTestClassGroup);
	}

	public long getAverageTestDuration(String testName) {
		if (_averageTestDurations.containsKey(testName)) {
			return _averageTestDurations.get(testName);
		}

		long averageTestDuration = _getDefaultTestDuration();

		BatchHistory batchHistory = getBatchHistory();

		if (batchHistory != null) {
			TestHistory testHistory = batchHistory.getTestHistory(testName);

			if (testHistory != null) {
				averageTestDuration = testHistory.getAverageDuration();
			}
		}

		_averageTestDurations.put(testName, averageTestDuration);

		return averageTestDuration;
	}

	public long getAverageTestOverheadDuration(String testName) {
		if (_averageTestOverheadDurations.containsKey(testName)) {
			return _averageTestOverheadDurations.get(testName);
		}

		long averageTestOverheadDuration = _getDefaultTestOverheadDuration();

		BatchHistory batchHistory = getBatchHistory();

		if (batchHistory != null) {
			TestHistory testHistory = batchHistory.getTestHistory(testName);

			if (testHistory != null) {
				averageTestOverheadDuration =
					testHistory.getAverageOverheadDuration();
			}
		}

		_averageTestOverheadDurations.put(
			testName, averageTestOverheadDuration);

		return averageTestOverheadDuration;
	}

	public int getAxisCount() {
		JobProperty jobProperty = getJobProperty("test.batch.axis.count");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isInteger(jobPropertyValue)) {
			recordJobProperty(jobProperty);

			return Integer.parseInt(jobPropertyValue);
		}

		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			return 0;
		}

		int axisMaxSize = getAxisMaxSize();

		if (axisMaxSize <= 0) {
			throw new RuntimeException(
				"'test.batch.axis.max.size' cannot be 0 or less");
		}

		return (int)Math.ceil((double)testClassCount / axisMaxSize);
	}

	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		return axisTestClassGroups.get(axisId);
	}

	public List<AxisTestClassGroup> getAxisTestClassGroups() {
		return axisTestClassGroups;
	}

	public BatchHistory getBatchHistory() {
		if (_batchHistory != null) {
			return _batchHistory;
		}

		Job job = getJob();

		JobHistory jobHistory = job.getJobHistory();

		_batchHistory = jobHistory.getBatchHistory(getBatchName());

		return _batchHistory;
	}

	public String getBatchJobName() {
		String topLevelJobName = portalTestClassJob.getJobName();

		Matcher jobNameMatcher = _jobNamePattern.matcher(topLevelJobName);

		String batchJobSuffix = "-batch";

		String slaveLabel = getSlaveLabel();

		if (slaveLabel.contains("win")) {
			batchJobSuffix = "-windows-batch";
		}

		if (jobNameMatcher.find()) {
			return JenkinsResultsParserUtil.combine(
				jobNameMatcher.group("jobBaseName"), batchJobSuffix,
				jobNameMatcher.group("jobVariant"));
		}

		return topLevelJobName + batchJobSuffix;
	}

	public String getBatchName() {
		return batchName;
	}

	public String getCohortName() {
		JobProperty jobProperty = getJobProperty("test.batch.cohort.name");

		String jobPropertyValue = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			recordJobProperty(jobProperty);

			return jobPropertyValue;
		}

		jobPropertyValue = JenkinsResultsParserUtil.getCohortName();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return jobPropertyValue;
		}

		return "test-1";
	}

	public String getDownstreamJobName() {
		String topLevelJobName = portalTestClassJob.getJobName();

		Matcher jobNameMatcher = _jobNamePattern.matcher(topLevelJobName);

		String batchJobSuffix = "-downstream";

		String slaveLabel = getSlaveLabel();

		if (slaveLabel.contains("win")) {
			batchJobSuffix = "-windows-downstream";
		}

		if (jobNameMatcher.find()) {
			return JenkinsResultsParserUtil.combine(
				jobNameMatcher.group("jobBaseName"), batchJobSuffix,
				jobNameMatcher.group("jobVariant"));
		}

		return topLevelJobName + batchJobSuffix;
	}

	@Override
	public Job getJob() {
		return portalTestClassJob;
	}

	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = new JSONObject();

		jsonObject.put("batch_name", getBatchName());
		jsonObject.put("include_stable_test_suite", includeStableTestSuite);
		jsonObject.put("job_properties", _getJobPropertiesMap());

		JSONArray segmentJSONArray = new JSONArray();

		for (SegmentTestClassGroup segmentTestClassGroup :
				getSegmentTestClassGroups()) {

			segmentJSONArray.put(segmentTestClassGroup.getJSONObject());
		}

		jsonObject.put("segments", segmentJSONArray);

		jsonObject.put("test_release_bundle", testReleaseBundle);
		jsonObject.put("test_relevant_changes", testRelevantChanges);
		jsonObject.put(
			"test_relevant_integration_unit_only",
			testRelevantIntegrationUnitOnly);

		return jsonObject;
	}

	public Integer getMaximumSlavesPerHost() {
		JobProperty jobProperty = getJobProperty(
			"test.batch.maximum.slaves.per.host");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isInteger(jobPropertyValue)) {
			recordJobProperty(jobProperty);

			return Integer.valueOf(jobPropertyValue);
		}

		return JenkinsMaster.getSlavesPerHostDefault();
	}

	public Integer getMinimumSlaveRAM() {
		JobProperty jobProperty = getJobProperty(
			"test.batch.minimum.slave.ram");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isInteger(jobPropertyValue)) {
			recordJobProperty(jobProperty);

			return Integer.valueOf(jobPropertyValue);
		}

		return JenkinsMaster.getSlaveRAMMinimumDefault();
	}

	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return portalGitWorkingDirectory;
	}

	public int getSegmentCount() {
		return _segmentTestClassGroups.size();
	}

	public SegmentTestClassGroup getSegmentTestClassGroup(int segmentId) {
		if ((_segmentTestClassGroups.size() - 1) < segmentId) {
			return null;
		}

		return _segmentTestClassGroups.get(segmentId);
	}

	public List<SegmentTestClassGroup> getSegmentTestClassGroups() {
		return _segmentTestClassGroups;
	}

	public String getSlaveLabel() {
		JobProperty jobProperty = getJobProperty("test.batch.slave.label");

		String jobPropertyValue = jobProperty.getValue();

		if (jobPropertyValue != null) {
			recordJobProperty(jobProperty);

			return jobPropertyValue;
		}

		return SLAVE_LABEL_DEFAULT;
	}

	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		for (SegmentTestClassGroup segmentTestClassGroup :
				getSegmentTestClassGroups()) {

			sb.append(segmentTestClassGroup.getTestCasePropertiesContent());
			sb.append("\n");
		}

		return sb.toString();
	}

	protected BatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		this.jsonObject = jsonObject;
		this.portalTestClassJob = portalTestClassJob;

		batchName = jsonObject.getString("batch_name");

		includeStableTestSuite = jsonObject.getBoolean(
			"include_stable_test_suite");

		portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		JSONArray segmentsJSONArray = jsonObject.optJSONArray("segments");

		if ((segmentsJSONArray != null) && !segmentsJSONArray.isEmpty()) {
			for (int i = 0; i < segmentsJSONArray.length(); i++) {
				JSONObject segmentJSONObject = segmentsJSONArray.getJSONObject(
					i);

				_segmentTestClassGroups.add(
					TestClassGroupFactory.newSegmentTestClassGroup(
						this, segmentJSONObject));
			}
		}

		testRelevantChanges = jsonObject.getBoolean("test_relevant_changes");
		testReleaseBundle = jsonObject.getBoolean("test_release_bundle");
		testRelevantIntegrationUnitOnly = jsonObject.getBoolean(
			"test_relevant_integration_unit_only");

		if (portalTestClassJob instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)portalTestClassJob;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}
		else {
			testSuiteName = null;
		}
	}

	protected BatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		this.batchName = batchName;
		this.portalTestClassJob = portalTestClassJob;

		portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		if (portalTestClassJob instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)portalTestClassJob;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}
		else {
			testSuiteName = null;
		}

		_setTestReleaseBundle();
		_setTestRelevantChanges();

		_setTestRelevantIntegrationUnitOnly();

		_setIncludeStableTestSuite();
	}

	protected int getAxisMaxSize() {
		JobProperty jobProperty = getJobProperty("test.batch.axis.max.size");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isInteger(jobPropertyValue)) {
			recordJobProperty(jobProperty);

			return Integer.parseInt(jobPropertyValue);
		}

		return AXES_SIZE_MAX_DEFAULT;
	}

	protected List<String> getGlobs(List<JobProperty> jobProperties) {
		List<String> globs = new ArrayList<>();

		for (JobProperty jobProperty : jobProperties) {
			if (!(jobProperty instanceof GlobJobProperty)) {
				continue;
			}

			GlobJobProperty globJobProperty = (GlobJobProperty)jobProperty;

			for (String relativeGlob : globJobProperty.getRelativeGlobs()) {
				if ((relativeGlob == null) || globs.contains(relativeGlob)) {
					continue;
				}

				globs.add(relativeGlob);
			}
		}

		Collections.sort(globs);

		return globs;
	}

	protected JobProperty getJobProperty(String basePropertyName) {
		return _getJobProperty(basePropertyName, null, null, null, null, true);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, File testBaseDir, JobProperty.Type type) {

		return _getJobProperty(
			basePropertyName, null, null, testBaseDir, type, true);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, File testBaseDir, JobProperty.Type type,
		boolean useBasePropertyName) {

		return _getJobProperty(
			basePropertyName, null, null, testBaseDir, type,
			useBasePropertyName);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, JobProperty.Type type) {

		return _getJobProperty(basePropertyName, null, null, null, type, true);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, String testSuiteName, File testBaseDir,
		JobProperty.Type type) {

		return _getJobProperty(
			basePropertyName, testSuiteName, null, testBaseDir, type, true);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, String testSuiteName, String testBatchName) {

		return _getJobProperty(
			basePropertyName, testSuiteName, testBatchName, null, null, true);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, String testSuiteName, String testBatchName,
		File testBaseDir, JobProperty.Type type) {

		return _getJobProperty(
			basePropertyName, testSuiteName, testBatchName, testBaseDir, type,
			true);
	}

	protected JobProperty getJobProperty(
		String basePropertyName, String testSuiteName, String testBatchName,
		JobProperty.Type type) {

		return _getJobProperty(
			basePropertyName, testSuiteName, testBatchName, null, type, true);
	}

	protected List<PathMatcher> getPathMatchers(
		List<JobProperty> jobProperties) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		for (JobProperty jobProperty : jobProperties) {
			if (!(jobProperty instanceof GlobJobProperty)) {
				continue;
			}

			GlobJobProperty globJobProperty = (GlobJobProperty)jobProperty;

			List<PathMatcher> globPathMatchers =
				globJobProperty.getPathMatchers();

			if (globPathMatchers == null) {
				continue;
			}

			for (PathMatcher globPathMatcher : globPathMatchers) {
				if ((globPathMatcher == null) ||
					pathMatchers.contains(globPathMatcher)) {

					continue;
				}

				pathMatchers.add(globPathMatcher);
			}
		}

		return pathMatchers;
	}

	protected List<PathMatcher> getPathMatchers(
		String relativeGlobs, File workingDirectory) {

		if ((relativeGlobs == null) || relativeGlobs.isEmpty()) {
			return Collections.emptyList();
		}

		return JenkinsResultsParserUtil.toPathMatchers(
			JenkinsResultsParserUtil.getCanonicalPath(workingDirectory) +
				File.separator,
			JenkinsResultsParserUtil.getGlobsFromProperty(relativeGlobs));
	}

	protected List<String> getRelevantIntegrationUnitBatchNames() {
		List<String> relevantIntegrationUnitBatchNames = new ArrayList<>();

		if (!testSuiteName.equals("relevant")) {
			return relevantIntegrationUnitBatchNames;
		}

		JobProperty jobProperty = getJobProperty("test.batch.names");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return relevantIntegrationUnitBatchNames;
		}

		for (String relevantTestBatchName : jobPropertyValue.split(",")) {
			if (relevantTestBatchName.startsWith("integration-") ||
				relevantTestBatchName.startsWith("modules-integration") ||
				relevantTestBatchName.startsWith("modules-unit") ||
				relevantTestBatchName.startsWith("unit-")) {

				relevantIntegrationUnitBatchNames.add(relevantTestBatchName);
			}
		}

		return relevantIntegrationUnitBatchNames;
	}

	protected List<PathMatcher>
		getRelevantIntegrationUnitIncludePathMatchers() {

		List<PathMatcher> relevantIntegrationUnitIncludePathMatchers =
			new ArrayList<>();

		for (String relevantIntegrationUnitBatchName :
				getRelevantIntegrationUnitBatchNames()) {

			JobProperty jobProperty = getJobProperty(
				"test.batch.class.names.includes", getTestSuiteName(),
				relevantIntegrationUnitBatchName,
				JobProperty.Type.INCLUDE_GLOB);

			if (!(jobProperty instanceof GlobJobProperty)) {
				continue;
			}

			String jobPropertyValue = jobProperty.getValue();

			if (jobPropertyValue == null) {
				continue;
			}

			GlobJobProperty globJobProperty = (GlobJobProperty)jobProperty;

			relevantIntegrationUnitIncludePathMatchers.addAll(
				globJobProperty.getPathMatchers());
		}

		return relevantIntegrationUnitIncludePathMatchers;
	}

	protected List<File> getRequiredModuleDirs(List<File> moduleDirs) {
		return _getRequiredModuleDirs(moduleDirs, new ArrayList<>(moduleDirs));
	}

	protected int getSegmentMaxChildren() {
		JobProperty jobProperty = getJobProperty(
			"test.batch.segment.max.children");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isInteger(jobPropertyValue)) {
			recordJobProperty(jobProperty);

			return Integer.valueOf(jobPropertyValue);
		}

		return _SEGMENT_MAX_CHILDREN_DEFAULT;
	}

	protected long getTargetAxisDuration() {
		JobProperty jobProperty = getJobProperty(
			"test.batch.target.axis.duration");

		String jobPropertyValue = jobProperty.getValue();

		if ((jobPropertyValue == null) || !jobPropertyValue.matches("\\d+")) {
			return 0L;
		}

		recordJobProperty(jobProperty);

		return Long.parseLong(jobPropertyValue);
	}

	protected String getTestSuiteName() {
		return testSuiteName;
	}

	protected boolean isIntegrationUnitTestFileModifiedOnly() {
		List<PathMatcher> relevantIntegrationUnitIncludePathMatchers =
			getRelevantIntegrationUnitIncludePathMatchers();

		List<File> modifiedFilesList =
			portalGitWorkingDirectory.getModifiedFilesList();

		if (relevantIntegrationUnitIncludePathMatchers.isEmpty() ||
			modifiedFilesList.isEmpty()) {

			return false;
		}

		for (File modifiedFile : modifiedFilesList) {
			if (!JenkinsResultsParserUtil.isFileIncluded(
					null, relevantIntegrationUnitIncludePathMatchers,
					modifiedFile)) {

				return false;
			}
		}

		return true;
	}

	protected boolean isStableTestSuiteBatch() {
		return isStableTestSuiteBatch(batchName);
	}

	protected boolean isStableTestSuiteBatch(String batchName) {
		List<String> testBatchNames = new ArrayList<>();

		JobProperty jobProperty = getJobProperty("test.batch.names[stable]");

		String jobPropertyValue = jobProperty.getValue();

		if (jobPropertyValue != null) {
			Collections.addAll(testBatchNames, jobPropertyValue.split(","));
		}

		if (testBatchNames.contains(batchName)) {
			return true;
		}

		return false;
	}

	protected void recordJobProperties(List<JobProperty> jobProperties) {
		for (JobProperty jobProperty : jobProperties) {
			recordJobProperty(jobProperty);
		}
	}

	protected void recordJobProperty(JobProperty jobProperty) {
		if ((jobProperty == null) || (jobProperty.getValue() == null) ||
			_jobProperties.contains(jobProperty)) {

			return;
		}

		_jobProperties.add(jobProperty);
	}

	protected void setAxisTestClassGroups() {
		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			return;
		}

		int axisCount = getAxisCount();

		int axisSize = (int)Math.ceil((double)testClassCount / axisCount);

		for (List<TestClass> axisTestClasses :
				Lists.partition(testClasses, axisSize)) {

			AxisTestClassGroup axisTestClassGroup =
				TestClassGroupFactory.newAxisTestClassGroup(this);

			for (TestClass axisTestClass : axisTestClasses) {
				axisTestClassGroup.addTestClass(axisTestClass);
			}

			axisTestClassGroups.add(axisTestClassGroup);
		}
	}

	protected void setSegmentTestClassGroups() {
		if (!_segmentTestClassGroups.isEmpty() ||
			axisTestClassGroups.isEmpty()) {

			return;
		}

		List<List<AxisTestClassGroup>> axisTestClassGroupsList =
			new ArrayList<>();

		axisTestClassGroupsList.add(axisTestClassGroups);

		axisTestClassGroupsList = _partitionByMinimumSlaveRAM(
			axisTestClassGroupsList);
		axisTestClassGroupsList = _partitionBySlaveLabel(
			axisTestClassGroupsList);
		axisTestClassGroupsList = _partitionByTestBaseDir(
			axisTestClassGroupsList);

		axisTestClassGroupsList = _partitionByMaxChildren(
			axisTestClassGroupsList);

		for (List<AxisTestClassGroup> axisTestClassGroups :
				axisTestClassGroupsList) {

			SegmentTestClassGroup segmentTestClassGroup =
				TestClassGroupFactory.newSegmentTestClassGroup(this);

			for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
				segmentTestClassGroup.addAxisTestClassGroup(axisTestClassGroup);
			}

			_segmentTestClassGroups.add(segmentTestClassGroup);
		}
	}

	protected static final int AXES_SIZE_MAX_DEFAULT = 5000;

	protected static final String NAME_STABLE_TEST_SUITE = "stable";

	protected static final String SLAVE_LABEL_DEFAULT = "!master";

	protected final List<AxisTestClassGroup> axisTestClassGroups =
		new ArrayList<>();
	protected final String batchName;
	protected boolean includeStableTestSuite;
	protected JSONObject jsonObject;
	protected final PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected final PortalTestClassJob portalTestClassJob;
	protected boolean testReleaseBundle;
	protected boolean testRelevantChanges;
	protected boolean testRelevantIntegrationUnitOnly;
	protected final String testSuiteName;

	protected static final class CSVReport {

		public CSVReport(Row headerRow) {
			if (headerRow == null) {
				throw new IllegalArgumentException("headerRow is null");
			}

			_csvReportRows.add(headerRow);
		}

		public void addRow(Row csvReportRow) {
			Row headerRow = _csvReportRows.get(0);

			if (csvReportRow.size() != headerRow.size()) {
				throw new IllegalArgumentException(
					"Row length does not match headers length");
			}

			_csvReportRows.add(csvReportRow);
		}

		@Override
		public String toString() {
			StringBuilder sb = null;

			for (Row csvReportRow : _csvReportRows) {
				if (sb == null) {
					sb = new StringBuilder();
				}
				else {
					sb.append("\n");
				}

				sb.append(csvReportRow.toString());
			}

			return sb.toString();
		}

		protected static final class Row extends ArrayList<String> {

			public Row() {
			}

			public Row(String... strings) {
				for (String string : strings) {
					add(string);
				}
			}

			@Override
			public String toString() {
				return StringUtils.join(iterator(), ",");
			}

		}

		private List<Row> _csvReportRows = new ArrayList<>();

	}

	protected static class TestClassDurationComparator
		implements Comparator<TestClass> {

		@Override
		public int compare(TestClass testClass1, TestClass testClass2) {
			Long duration1 =
				testClass1.getAverageDuration() +
					testClass1.getAverageOverheadDuration();
			Long duration2 =
				testClass2.getAverageDuration() +
					testClass2.getAverageOverheadDuration();

			return duration2.compareTo(duration1);
		}

	}

	private long _getDefaultTestDuration() {
		JobProperty jobProperty = getJobProperty(
			"test.batch.default.test.duration");

		if (jobProperty == null) {
			return 0L;
		}

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return 0L;
		}

		recordJobProperty(jobProperty);

		return Long.valueOf(jobPropertyValue);
	}

	private long _getDefaultTestOverheadDuration() {
		JobProperty jobProperty = getJobProperty(
			"test.batch.default.test.overhead.duration");

		if (jobProperty == null) {
			return 0L;
		}

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return 0L;
		}

		recordJobProperty(jobProperty);

		return Long.valueOf(jobPropertyValue);
	}

	private Map<String, Properties> _getJobPropertiesMap() {
		Map<String, Properties> batchProperties = new TreeMap<>();

		for (JobProperty jobProperty : _jobProperties) {
			String jobPropertyValue = jobProperty.getValue();

			if (jobPropertyValue == null) {
				continue;
			}

			String propertiesFilePath = jobProperty.getPropertiesFilePath();

			Properties properties = batchProperties.get(propertiesFilePath);

			if (properties == null) {
				properties = new Properties();
			}

			properties.setProperty(jobProperty.getName(), jobPropertyValue);

			batchProperties.put(propertiesFilePath, properties);
		}

		return batchProperties;
	}

	private JobProperty _getJobProperty(
		String basePropertyName, String testSuiteName, String testBatchName,
		File testBaseDir, JobProperty.Type type, boolean useBasePropertyName) {

		if (testBatchName == null) {
			testBatchName = getBatchName();
		}

		return JobPropertyFactory.newJobProperty(
			basePropertyName, testSuiteName, testBatchName, getJob(),
			testBaseDir, type, useBasePropertyName);
	}

	private List<File> _getRequiredModuleDirs(
		List<File> moduleDirs, List<File> requiredModuleDirs) {

		List<File> modifiedPoshiModulesList = new ArrayList<>();
		List<File> modifiedNonposhiModulesList = new ArrayList<>();

		try {
			modifiedPoshiModulesList =
				portalGitWorkingDirectory.getModifiedPoshiModules();
			modifiedNonposhiModulesList =
				portalGitWorkingDirectory.getModifiedNonposhiModules();
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get modified modules with non poshi and poshi " +
						"changes directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		for (File moduleDir : moduleDirs) {
			if (testRelevantChanges &&
				modifiedPoshiModulesList.contains(moduleDir) &&
				!modifiedNonposhiModulesList.contains(moduleDir)) {

				continue;
			}

			JobProperty jobProperty = getJobProperty(
				"modules.includes.required[" + getTestSuiteName() + "]",
				moduleDir, JobProperty.Type.MODULE_TEST_DIR);

			String jobPropertyValue = jobProperty.getValue();

			if (jobPropertyValue == null) {
				continue;
			}

			recordJobProperty(jobProperty);

			for (String requiredModuleDirPath : jobPropertyValue.split(",")) {
				File requiredModuleDir = new File(
					modulesBaseDir, requiredModuleDirPath);

				if (!requiredModuleDir.exists() ||
					requiredModuleDirs.contains(requiredModuleDir)) {

					continue;
				}

				requiredModuleDirs.add(requiredModuleDir);
			}
		}

		return new ArrayList<>(requiredModuleDirs);
	}

	private List<List<AxisTestClassGroup>> _partitionByMaxChildren(
		List<List<AxisTestClassGroup>> axisTestClassGroupsList) {

		List<List<AxisTestClassGroup>> partitionedAxisTestClassGroupsList =
			new ArrayList<>();

		for (List<AxisTestClassGroup> axisTestClassGroups :
				axisTestClassGroupsList) {

			partitionedAxisTestClassGroupsList.addAll(
				Lists.partition(axisTestClassGroups, getSegmentMaxChildren()));
		}

		return partitionedAxisTestClassGroupsList;
	}

	private List<List<AxisTestClassGroup>> _partitionByMinimumSlaveRAM(
		List<List<AxisTestClassGroup>> axisTestClassGroupsList) {

		List<List<AxisTestClassGroup>> partitionedAxisTestClassGroupsList =
			new ArrayList<>();

		for (List<AxisTestClassGroup> axisTestClassGroups :
				axisTestClassGroupsList) {

			Map<Integer, List<AxisTestClassGroup>> axisTestClassGroupsMap =
				new HashMap<>();

			for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
				Integer minimumSlaveRAM =
					axisTestClassGroup.getMinimumSlaveRAM();

				List<AxisTestClassGroup> minimumSlaveRAMAxisTestClassGroups =
					axisTestClassGroupsMap.get(minimumSlaveRAM);

				if (minimumSlaveRAMAxisTestClassGroups == null) {
					minimumSlaveRAMAxisTestClassGroups = new ArrayList<>();
				}

				minimumSlaveRAMAxisTestClassGroups.add(axisTestClassGroup);

				axisTestClassGroupsMap.put(
					minimumSlaveRAM, minimumSlaveRAMAxisTestClassGroups);
			}

			partitionedAxisTestClassGroupsList.addAll(
				axisTestClassGroupsMap.values());
		}

		return partitionedAxisTestClassGroupsList;
	}

	private List<List<AxisTestClassGroup>> _partitionBySlaveLabel(
		List<List<AxisTestClassGroup>> axisTestClassGroupsList) {

		List<List<AxisTestClassGroup>> partitionedAxisTestClassGroupsList =
			new ArrayList<>();

		for (List<AxisTestClassGroup> axisTestClassGroups :
				axisTestClassGroupsList) {

			Map<String, List<AxisTestClassGroup>> axisTestClassGroupsMap =
				new HashMap<>();

			for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
				String slaveLabel = axisTestClassGroup.getSlaveLabel();

				List<AxisTestClassGroup> slaveLabelAxisTestClassGroups =
					axisTestClassGroupsMap.get(slaveLabel);

				if (slaveLabelAxisTestClassGroups == null) {
					slaveLabelAxisTestClassGroups = new ArrayList<>();
				}

				slaveLabelAxisTestClassGroups.add(axisTestClassGroup);

				axisTestClassGroupsMap.put(
					slaveLabel, slaveLabelAxisTestClassGroups);
			}

			partitionedAxisTestClassGroupsList.addAll(
				axisTestClassGroupsMap.values());
		}

		return partitionedAxisTestClassGroupsList;
	}

	private List<List<AxisTestClassGroup>> _partitionByTestBaseDir(
		List<List<AxisTestClassGroup>> axisTestClassGroupsList) {

		List<List<AxisTestClassGroup>> partitionedAxisTestClassGroupsList =
			new ArrayList<>();

		for (List<AxisTestClassGroup> axisTestClassGroups :
				axisTestClassGroupsList) {

			Map<File, List<AxisTestClassGroup>> axisTestClassGroupsMap =
				new HashMap<>();

			for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
				File testBaseDir = axisTestClassGroup.getTestBaseDir();

				List<AxisTestClassGroup> testBaseDirAxisTestClassGroups =
					axisTestClassGroupsMap.get(testBaseDir);

				if (testBaseDirAxisTestClassGroups == null) {
					testBaseDirAxisTestClassGroups = new ArrayList<>();
				}

				testBaseDirAxisTestClassGroups.add(axisTestClassGroup);

				axisTestClassGroupsMap.put(
					testBaseDir, testBaseDirAxisTestClassGroups);
			}

			partitionedAxisTestClassGroupsList.addAll(
				axisTestClassGroupsMap.values());
		}

		return partitionedAxisTestClassGroupsList;
	}

	private void _setIncludeStableTestSuite() {
		includeStableTestSuite = testRelevantChanges;
	}

	private void _setTestReleaseBundle() {
		Job job = getJob();

		testReleaseBundle = job.testReleaseBundle();
	}

	private void _setTestRelevantChanges() {
		Job job = getJob();

		testRelevantChanges = job.testRelevantChanges();
	}

	private void _setTestRelevantIntegrationUnitOnly() {
		if (testRelevantChanges && isIntegrationUnitTestFileModifiedOnly()) {
			testRelevantIntegrationUnitOnly = true;

			return;
		}

		testRelevantIntegrationUnitOnly = false;
	}

	private static final int _SEGMENT_MAX_CHILDREN_DEFAULT = 25;

	private static final Pattern _jobNamePattern = Pattern.compile(
		"(?<jobBaseName>.*)(?<jobVariant>\\([^\\)]+\\))");

	private final Map<String, Long> _averageTestDurations = new HashMap<>();
	private final Map<String, Long> _averageTestOverheadDurations =
		new HashMap<>();
	private BatchHistory _batchHistory;
	private final List<JobProperty> _jobProperties = new ArrayList<>();
	private final List<SegmentTestClassGroup> _segmentTestClassGroups =
		new ArrayList<>();

}