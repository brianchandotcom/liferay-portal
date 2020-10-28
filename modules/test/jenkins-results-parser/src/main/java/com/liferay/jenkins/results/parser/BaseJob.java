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

import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroupFactory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJob implements Job {

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
		return BuildProfile.PORTAL;
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
	public void readJobProperties() {
		_jobProperties.clear();

		for (File jobPropertiesFile : jobPropertiesFiles) {
			_jobProperties.putAll(
				JenkinsResultsParserUtil.getProperties(jobPropertiesFile));
		}
	}

	protected BaseJob(String jobName) {
		_jobName = jobName;
	}

	protected List<BatchTestClassGroup> getBatchTestClassGroups(
		Set<String> batchNames) {

		if ((batchNames == null) || batchNames.isEmpty()) {
			return new ArrayList<>();
		}

		BuildProfile jobBuildProfile = getBuildProfile();

		if (jobBuildProfile == null) {
			jobBuildProfile = BuildProfile.PORTAL;
		}

		String buildProfile = jobBuildProfile.toString();

		BatchTestClassGroup.BuildProfile batchBuildProfile =
			BatchTestClassGroup.BuildProfile.valueOf(
				buildProfile.toUpperCase());

		List<BatchTestClassGroup> batchTestClassGroups = new ArrayList<>();

		for (String batchName : batchNames) {
			BatchTestClassGroup batchTestClassGroup =
				TestClassGroupFactory.newBatchTestClassGroup(
					batchName, batchBuildProfile, this);

			if (batchTestClassGroup.getAxisCount() <= 0) {
				continue;
			}

			batchTestClassGroups.add(batchTestClassGroup);
		}

		return batchTestClassGroups;
	}

	protected Set<String> getFilteredBatchNames(Set<String> batchNames) {
		Set<String> filteredBatchNames = new TreeSet<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups(batchNames)) {

			filteredBatchNames.add(batchTestClassGroup.getBatchName());
		}

		return filteredBatchNames;
	}

	protected Set<String> getFilteredSegmentNames(Set<String> batchNames) {
		Set<String> segmentNames = new TreeSet<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups(batchNames)) {

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

	protected List<SegmentTestClassGroup> getSegmentTestClassGroups(
		Set<String> batchNames) {

		if ((batchNames == null) || batchNames.isEmpty()) {
			return new ArrayList<>();
		}

		List<SegmentTestClassGroup> segmentTestClassGroups = new ArrayList<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getBatchTestClassGroups(batchNames)) {

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

	protected final List<File> jobPropertiesFiles = new ArrayList<>();

	private final String _jobName;
	private final Properties _jobProperties = new Properties();

}