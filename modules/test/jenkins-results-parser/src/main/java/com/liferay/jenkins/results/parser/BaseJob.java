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

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalSegmentTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroupFactory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJob implements Job {

	@Override
	public List<AxisTestClassGroup> getAxisTestClassGroups() {
		List<AxisTestClassGroup> axisTestClassGroups = new ArrayList<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups()) {

			axisTestClassGroups.addAll(
				batchTestClassGroup.getAxisTestClassGroups());
		}

		return axisTestClassGroups;
	}

	@Override
	public Set<String> getBatchNames() {
		return getFilteredBatchNames(getRawBatchNames());
	}

	@Override
	public List<BatchTestClassGroup> getBatchTestClassGroups() {
		return getBatchTestClassGroups(getRawBatchNames());
	}

	@Override
	public List<Build> getBuildHistory(JenkinsMaster jenkinsMaster) {
		JSONObject jobJSONObject = getJobJSONObject(
			jenkinsMaster, "builds[number]");

		JSONArray buildsJSONArray = jobJSONObject.getJSONArray("builds");

		List<Build> builds = new ArrayList<>(buildsJSONArray.length());

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			builds.add(
				BuildFactory.newBuild(
					JenkinsResultsParserUtil.combine(
						jenkinsMaster.getURL(), "/job/", getJobName(), "/",
						String.valueOf(buildJSONObject.getInt("number"))),
					null));
		}

		return builds;
	}

	@Override
	public BuildProfile getBuildProfile() {
		return _buildProfile;
	}

	@Override
	public DistType getDistType() {
		return DistType.CI;
	}

	@Override
	public Set<String> getDistTypesExcludingTomcat() {
		Set<String> distTypesExcludingTomcat = new TreeSet<>(getDistTypes());

		distTypesExcludingTomcat.remove("tomcat");

		return distTypesExcludingTomcat;
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public Properties getJobProperties() {
		return _jobProperties;
	}

	@Override
	public String getJobProperty(String key) {
		return _jobProperties.getProperty(key);
	}

	@Override
	public String getJobURL(JenkinsMaster jenkinsMaster) {
		return JenkinsResultsParserUtil.combine(
			jenkinsMaster.getURL(), "/job/", _jobName);
	}

	@Override
	public Set<String> getSegmentNames() {
		return getFilteredSegmentNames(getRawBatchNames());
	}

	@Override
	public List<SegmentTestClassGroup> getSegmentTestClassGroups() {
		return getSegmentTestClassGroups(getRawBatchNames());
	}

	@Override
	public String getTestPropertiesContent() {
		Map<String, Properties> propertiesMap = new HashMap<>();

		List<BatchTestClassGroup> batchTestClassGroups =
			getBatchTestClassGroups();

		if (this instanceof BatchDependentJob) {
			BatchDependentJob batchDependentJob = (BatchDependentJob)this;

			batchTestClassGroups.addAll(
				batchDependentJob.getDependentBatchTestClassGroups());
		}

		for (BatchTestClassGroup batchTestClassGroup : batchTestClassGroups) {
			Properties batchProperties = new Properties();

			batchProperties.setProperty(
				"test.batch.job.name", batchTestClassGroup.getBatchJobName());
			batchProperties.setProperty(
				"test.batch.maximum.slaves.per.host",
				String.valueOf(batchTestClassGroup.getMaximumSlavesPerHost()));
			batchProperties.setProperty(
				"test.batch.minimum.slave.ram",
				String.valueOf(batchTestClassGroup.getMinimumSlaveRAM()));

			if (batchTestClassGroup instanceof FunctionalBatchTestClassGroup) {
				FunctionalBatchTestClassGroup functionalBatchTestClassGroup =
					(FunctionalBatchTestClassGroup)batchTestClassGroup;

				String testBatchRunPropertyQuery =
					functionalBatchTestClassGroup.
						getTestBatchRunPropertyQuery();

				if (testBatchRunPropertyQuery != null) {
					batchProperties.setProperty(
						"test.batch.run.property.query",
						testBatchRunPropertyQuery);
				}
			}
			else {
				batchProperties.setProperty(
					"test.batch.size",
					String.valueOf(batchTestClassGroup.getAxisCount()));
			}

			propertiesMap.put(
				batchTestClassGroup.getBatchName(), batchProperties);

			for (int i = 0; i < batchTestClassGroup.getSegmentCount(); i++) {
				Properties segmentProperties = new Properties();

				SegmentTestClassGroup segmentTestClassGroup =
					batchTestClassGroup.getSegmentTestClassGroup(i);

				segmentProperties.setProperty(
					"test.batch.job.name",
					segmentTestClassGroup.getBatchJobName());
				segmentProperties.setProperty(
					"test.batch.maximum.slaves.per.host",
					String.valueOf(
						segmentTestClassGroup.getMaximumSlavesPerHost()));
				segmentProperties.setProperty(
					"test.batch.minimum.slave.ram",
					String.valueOf(segmentTestClassGroup.getMinimumSlaveRAM()));
				segmentProperties.setProperty(
					"test.batch.name", segmentTestClassGroup.getBatchName());
				segmentProperties.setProperty(
					"test.batch.size",
					String.valueOf(segmentTestClassGroup.getAxisCount()));

				String testCasePropertiesContent =
					segmentTestClassGroup.getTestCasePropertiesContent();

				if (testCasePropertiesContent != null) {
					testCasePropertiesContent =
						testCasePropertiesContent.replaceAll(
							"\n", "\\${line.separator}");

					segmentProperties.setProperty(
						"test.case.properties", testCasePropertiesContent);
				}

				if (segmentTestClassGroup instanceof
						FunctionalSegmentTestClassGroup) {

					segmentProperties.setProperty(
						"run.test.case.method.group", String.valueOf(i));
				}

				propertiesMap.put(
					segmentTestClassGroup.getSegmentName(), segmentProperties);
			}
		}

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Properties> propertiesEntry :
				propertiesMap.entrySet()) {

			Properties properties = propertiesEntry.getValue();

			for (String propertyName : properties.stringPropertyNames()) {
				sb.append(propertyName);
				sb.append("[");
				sb.append(propertiesEntry.getKey());
				sb.append("]=");
				sb.append(properties.getProperty(propertyName));
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	@Override
	public boolean isSegmentEnabled() {
		String testSuiteName = "default";

		if (this instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)this;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}

		String segmentEnabled = JenkinsResultsParserUtil.getProperty(
			_jobProperties, "test.batch.segment.enabled", getJobName(),
			testSuiteName);

		if ((segmentEnabled != null) && segmentEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isValidationRequired() {
		return false;
	}

	@Override
	public void readJobProperties() {
		_jobProperties.clear();

		for (File jobPropertiesFile : jobPropertiesFiles) {
			_jobProperties.putAll(
				JenkinsResultsParserUtil.getProperties(jobPropertiesFile));
		}
	}

	protected BaseJob(String jobName, BuildProfile buildProfile) {
		_jobName = jobName;
		_buildProfile = buildProfile;
	}

	protected List<BatchTestClassGroup> getBatchTestClassGroups(
		Set<String> rawBatchNames) {

		if ((rawBatchNames == null) || rawBatchNames.isEmpty()) {
			return new ArrayList<>();
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Started creating ", String.valueOf(rawBatchNames.size()),
				" batch test class groups at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		List<Callable<BatchTestClassGroup>> callables = new ArrayList<>();

		final Job job = this;

		for (final String batchName : rawBatchNames) {
			callables.add(
				new Callable<BatchTestClassGroup>() {

					@Override
					public BatchTestClassGroup call() throws Exception {
						for (int i = 0; i < _pauseRetryCount; i++) {
							try {
								return _call();
							}
							catch (Exception exception) {
								System.out.println(
									JenkinsResultsParserUtil.combine(
										"Retry creating a test class group in ",
										String.valueOf(
											_pauseRetryDuration / 1000),
										" seconds."));

								JenkinsResultsParserUtil.sleep(
									_pauseRetryDuration);
							}
						}

						return _call();
					}

					private BatchTestClassGroup _call() throws Exception {
						long start =
							JenkinsResultsParserUtil.getCurrentTimeMillis();

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"[", batchName, "] Started batch test class ",
								"group at ",
								JenkinsResultsParserUtil.toDateString(
									new Date(start))));

						BatchTestClassGroup batchTestClassGroup =
							TestClassGroupFactory.newBatchTestClassGroup(
								batchName, job);

						if (batchTestClassGroup.getAxisCount() <= 0) {
							return null;
						}

						long duration =
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start;

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"[", batchName, "] Completed batch test class ",
								"group in ",
								JenkinsResultsParserUtil.toDurationString(
									duration),
								" at ",
								JenkinsResultsParserUtil.toDateString(
									new Date())));

						return batchTestClassGroup;
					}

					private final Integer _pauseRetryCount = 2;
					private final Integer _pauseRetryDuration = 5000;

				});
		}

		ParallelExecutor<BatchTestClassGroup> parallelExecutor =
			new ParallelExecutor<>(callables, _executorService);

		List<BatchTestClassGroup> batchTestClassGroups =
			parallelExecutor.execute();

		batchTestClassGroups.removeAll(Collections.singleton(null));

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Completed creating ",
				String.valueOf(batchTestClassGroups.size()),
				" batch test class groups in ",
				JenkinsResultsParserUtil.toDurationString(
					JenkinsResultsParserUtil.getCurrentTimeMillis() - start),
				" at ", JenkinsResultsParserUtil.toDateString(new Date())));

		return batchTestClassGroups;
	}

	protected Set<String> getFilteredBatchNames(Set<String> rawBatchNames) {
		Set<String> batchNames = new TreeSet<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups(rawBatchNames)) {

			batchNames.add(batchTestClassGroup.getBatchName());
		}

		return batchNames;
	}

	protected Set<String> getFilteredSegmentNames(Set<String> rawBatchNames) {
		Set<String> segmentNames = new TreeSet<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups(rawBatchNames)) {

			for (int i = 0; i < batchTestClassGroup.getSegmentCount(); i++) {
				SegmentTestClassGroup segmentTestClassGroup =
					batchTestClassGroup.getSegmentTestClassGroup(i);

				if (segmentTestClassGroup.getAxisCount() <= 0) {
					continue;
				}

				segmentNames.add(batchTestClassGroup.getBatchName() + "/" + i);
			}
		}

		return segmentNames;
	}

	protected JSONObject getJobJSONObject(
		JenkinsMaster jenkinsMaster, String tree) {

		if (getJobURL(jenkinsMaster) == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(
			JenkinsResultsParserUtil.getLocalURL(getJobURL(jenkinsMaster)));
		sb.append("/api/json?pretty");

		if (tree != null) {
			sb.append("&tree=");
			sb.append(tree);
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(sb.toString(), false);
		}
		catch (IOException ioException) {
			throw new RuntimeException("Unable to get job JSON", ioException);
		}
	}

	protected abstract Set<String> getRawBatchNames();

	protected List<SegmentTestClassGroup> getSegmentTestClassGroups(
		Set<String> rawBatchNames) {

		List<SegmentTestClassGroup> segmentTestClassGroups = new ArrayList<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups(rawBatchNames)) {

			for (int i = 0; i < batchTestClassGroup.getSegmentCount(); i++) {
				SegmentTestClassGroup segmentTestClassGroup =
					batchTestClassGroup.getSegmentTestClassGroup(i);

				if (segmentTestClassGroup.getAxisCount() <= 0) {
					continue;
				}

				segmentTestClassGroups.add(segmentTestClassGroup);
			}
		}

		return segmentTestClassGroups;
	}

	protected Set<String> getSetFromString(String string) {
		Set<String> set = new TreeSet<>();

		if (string == null) {
			return set;
		}

		for (String item : StringUtils.split(string, ",")) {
			if (item.startsWith("#")) {
				continue;
			}

			set.add(item.trim());
		}

		return set;
	}

	protected void setJobProperties(Properties properties) {
		_jobProperties.clear();

		_jobProperties.putAll(properties);
	}

	protected final List<File> jobPropertiesFiles = new ArrayList<>();

	private static final Integer _THREAD_COUNT = 20;

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(_THREAD_COUNT, true);

	private final BuildProfile _buildProfile;
	private final String _jobName;
	private final Properties _jobProperties = new Properties();

}