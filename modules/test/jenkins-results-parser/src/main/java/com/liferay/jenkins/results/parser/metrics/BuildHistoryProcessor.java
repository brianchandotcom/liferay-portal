/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.metrics;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;

/**
 * @author Kenji Heigel
 */
public class BuildHistoryProcessor {

	public static BuildHistory mergeBuildHistories(
		String name, BuildHistory... buildHistories) {

		return _mergeBuildHistories(name, Arrays.asList(buildHistories));
	}

	public static BuildHistory mergeBuildHistories(
		String name, Collection<BuildHistory> buildHistories) {

		return _mergeBuildHistories(name, new ArrayList<>(buildHistories));
	}

	public static Collection<BuildHistory> newAggregateJobHistories(
		long startTime, long duration) {

		Set<BuildJSONObject> buildJSONObjects =
			_getFilteredBuildDataJSONObjects(startTime, duration);

		return _getGroupedBuildHistories(
			buildJSONObjects, new GroupByCategory(), startTime, duration);
	}

	public static Collection<BuildHistory> newDefaultJobHistories(
		long startTime, long duration) {

		Set<BuildJSONObject> buildJSONObjects =
			_getFilteredBuildDataJSONObjects(startTime, duration);

		return _getGroupedBuildHistories(
			buildJSONObjects, new GroupByJobName(), startTime, duration);
	}

	public static Collection<BuildHistory> newTestSuiteJobHistories(
		long startTime, long duration, Pattern jobNamePattern) {

		Set<BuildJSONObject> buildJSONObjects =
			_getFilteredBuildDataJSONObjects(
				startTime, duration, jobNamePattern);

		Set<BuildJSONObject> downstreamBuildJSONObjects = new HashSet<>();
		Set<BuildJSONObject> topLevelBuildJSONObjects = new HashSet<>();

		for (BuildJSONObject buildJSONObject : buildJSONObjects) {
			if (buildJSONObject.isTopLevelBuild()) {
				topLevelBuildJSONObjects.add(buildJSONObject);
			}
			else {
				downstreamBuildJSONObjects.add(buildJSONObject);
			}
		}

		GroupByTopLevelTestSuite groupByTopLevelTestSuite =
			new GroupByTopLevelTestSuite();

		Map<String, BuildHistory> groupedBuildHistoriesMap =
			_getGroupedBuildHistoriesMap(
				topLevelBuildJSONObjects, groupByTopLevelTestSuite, startTime,
				duration);

		Map<String, Set<BuildJSONObject>> groupedBuildDataJSONObjectsMap =
			_getGroupedBuildDataJSONObjectsMap(
				downstreamBuildJSONObjects, groupByTopLevelTestSuite);

		for (Map.Entry<String, Set<BuildJSONObject>> entry :
				groupedBuildDataJSONObjectsMap.entrySet()) {

			String key = entry.getKey();

			if (!groupedBuildHistoriesMap.containsKey(key)) {
				BuildHistory buildHistory = new BuildHistory(
					key, startTime, duration);

				groupedBuildHistoriesMap.put(key, buildHistory);
			}

			BuildHistory buildHistory = groupedBuildHistoriesMap.get(key);

			buildHistory.addBuildDataJSONObjects(
				groupedBuildDataJSONObjectsMap.get(key));
		}

		return _getSortedBuildHistories(groupedBuildHistoriesMap.values());
	}

	private static Set<BuildJSONObject> _getFilteredBuildDataJSONObjects(
		long startTime, long duration) {

		return _getFilteredBuildDataJSONObjects(startTime, duration, null);
	}

	private static Set<BuildJSONObject> _getFilteredBuildDataJSONObjects(
		long startTime, long duration, Pattern jobNamePattern) {

		Set<BuildJSONObject> buildJSONObjects = new HashSet<>();

		for (String dateString :
				JenkinsResultsParserUtil.getDateStrings(startTime, duration)) {

			if (!_buildDataJSONObjectsMap.containsKey(dateString)) {
				System.out.println("Loading build JSONs for " + dateString);

				_loadBuildJSONObjects(dateString);
			}

			for (BuildJSONObject buildJSONObject :
					_buildDataJSONObjectsMap.get(dateString)) {

				if (jobNamePattern == null) {
					buildJSONObjects.add(buildJSONObject);

					continue;
				}

				Matcher jobNameMatcher = jobNamePattern.matcher(
					buildJSONObject.getJobName());

				if (jobNameMatcher.matches()) {
					buildJSONObjects.add(buildJSONObject);
				}
			}
		}

		return buildJSONObjects;
	}

	private static Map<String, Set<BuildJSONObject>>
		_getGroupedBuildDataJSONObjectsMap(
			Collection<BuildJSONObject> buildJSONObjects,
			Function<BuildJSONObject, String> groupingFunction) {

		Map<String, Set<BuildJSONObject>> groupedBuildDataJSONObjectsMap =
			new HashMap<>();

		for (BuildJSONObject buildJSONObject : buildJSONObjects) {
			String groupName = groupingFunction.apply(buildJSONObject);

			if (!groupedBuildDataJSONObjectsMap.containsKey(groupName)) {
				groupedBuildDataJSONObjectsMap.put(
					groupName, new HashSet<BuildJSONObject>());
			}

			Set<BuildJSONObject> groupedBuildJSONObjects =
				groupedBuildDataJSONObjectsMap.get(groupName);

			groupedBuildJSONObjects.add(buildJSONObject);
		}

		return groupedBuildDataJSONObjectsMap;
	}

	private static Collection<BuildHistory> _getGroupedBuildHistories(
		Collection<BuildJSONObject> buildJSONObjects,
		Function<BuildJSONObject, String> groupingFunction, long startTime,
		long duration) {

		Map<String, BuildHistory> groupedBuildHistories =
			_getGroupedBuildHistoriesMap(
				buildJSONObjects, groupingFunction, startTime, duration);

		return _getSortedBuildHistories(groupedBuildHistories.values());
	}

	private static Map<String, BuildHistory> _getGroupedBuildHistoriesMap(
		Collection<BuildJSONObject> buildJSONObjects,
		Function<BuildJSONObject, String> groupingFunction, long startTime,
		long duration) {

		Map<String, BuildHistory> groupedBuildHistories = new HashMap<>();

		Map<String, Set<BuildJSONObject>> groupedBuildDataJSONObjectsMap =
			_getGroupedBuildDataJSONObjectsMap(
				buildJSONObjects, groupingFunction);

		for (Map.Entry<String, Set<BuildJSONObject>> entry :
				groupedBuildDataJSONObjectsMap.entrySet()) {

			BuildHistory buildHistory = new BuildHistory(
				entry.getKey(), startTime, duration);

			buildHistory.addBuildDataJSONObjects(entry.getValue());

			groupedBuildHistories.put(entry.getKey(), buildHistory);
		}

		return groupedBuildHistories;
	}

	private static List<BuildHistory> _getSortedBuildHistories(
		Collection<BuildHistory> buildHistories) {

		List<BuildHistory> buildHistoryList = new ArrayList<>(buildHistories);

		Collections.sort(
			buildHistoryList,
			new Comparator<BuildHistory>() {

				@Override
				public int compare(
					BuildHistory buildHistory1, BuildHistory buildHistory2) {

					Set<BuildJSONObject> buildJSONObjects1 =
						buildHistory1.getBuildDataJSONObjects();
					Set<BuildJSONObject> buildJSONObjects2 =
						buildHistory2.getBuildDataJSONObjects();

					Integer size1 = buildJSONObjects1.size();
					Integer size2 = buildJSONObjects2.size();

					return size2.compareTo(size1);
				}

			});

		return buildHistoryList;
	}

	private static void _loadBuildJSONObjects(String dateString) {
		File dateDir = new File(_BASE_DIR, dateString);

		if (!dateDir.exists()) {
			_buildDataJSONObjectsMap.put(
				dateString, new ArrayList<BuildJSONObject>());
		}

		if (dateDir.listFiles() == null) {
			return;
		}

		List<BuildJSONObject> buildJSONObjects = new ArrayList<>();

		for (File jsonFile : dateDir.listFiles()) {
			try {
				String jsonFileName = jsonFile.getCanonicalPath();

				if (jsonFileName.contains("test-1-0") ||
					jsonFileName.contains("test-1-41")) {

					continue;
				}

				String content = JenkinsResultsParserUtil.read(jsonFile);

				JSONArray jsonArray = new JSONArray(content.trim());

				for (int i = 0; i < jsonArray.length(); i++) {
					buildJSONObjects.add(
						new BuildJSONObject(jsonArray.getJSONObject(i)));
				}
			}
			catch (IOException ioException) {
				System.out.println("Unable to read " + jsonFile);
			}
		}

		_buildDataJSONObjectsMap.put(dateString, buildJSONObjects);
	}

	private static BuildHistory _mergeBuildHistories(
		String name, List<BuildHistory> buildHistories) {

		Set<BuildJSONObject> buildJSONObjects = new HashSet<>();
		long duration = 0;
		long startTime = System.currentTimeMillis();
		Set<String> topLevelBuildURLs = new HashSet<>();

		for (BuildHistory buildHistory : buildHistories) {
			if (buildHistory.getDuration() > duration) {
				duration = buildHistory.getDuration();
			}

			if (buildHistory.getStartTime() < startTime) {
				startTime = buildHistory.getStartTime();
			}

			buildJSONObjects.addAll(buildHistory.getBuildDataJSONObjects());
			topLevelBuildURLs.addAll(buildHistory.getTopLevelBuildURLs());
		}

		BuildHistory buildHistory = new BuildHistory(name, startTime, duration);

		buildHistory.addBuildDataJSONObjects(buildJSONObjects);

		return buildHistory;
	}

	private static final File _BASE_DIR = new File(
		"/opt/dev/projects/github/liferay-jenkins-ee/tmp/jenkins");

	private static final Map<String, List<BuildJSONObject>>
		_buildDataJSONObjectsMap = new HashMap<>();

	private static class GroupByCategory
		implements Function<BuildJSONObject, String> {

		public String apply(BuildJSONObject buildJSONObject) {
			String jobName = buildJSONObject.getJobName();

			jobName = jobName.replace("-batch", "");
			jobName = jobName.replace("-downstream", "");
			jobName = jobName.replace("-validation", "");

			if (jobName.contains("maintenance-") ||
				jobName.contains("mirrors-") ||
				jobName.contains("verification-")) {

				return Category.MAINTENANCE.toString();
			}

			if (jobName.equals("test-portal-acceptance-pullrequest(master)")) {
				return Category.PORTAL_MASTER_PULLREQUEST.toString();
			}

			if (jobName.equals("test-portal-acceptance-upstream(master)") ||
				jobName.equals("test-portal-acceptance-upstream-dxp(master)") ||
				jobName.equals("test-portal-testsuite-upstream(master)")) {

				return Category.PORTAL_MASTER_UPSTREAM.toString();
			}

			if (jobName.equals("test-portal-release")) {
				return Category.PORTAL_RELEASE.toString();
			}

			if (jobName.equals("test-portal-fixpack-release") ||
				jobName.equals("test-portal-hotfix-release")) {

				return Category.PORTAL_OTHER_RELEASE.toString();
			}

			if (jobName.contains("test-portal-")) {
				return Category.PORTAL_OTHER.toString();
			}

			return Category.OTHER.toString();
		}

		private enum Category {

			MAINTENANCE("CI Maintenance"), OTHER("Other"),
			PORTAL_MASTER_PULLREQUEST("liferay-portal/master PR's"),
			PORTAL_MASTER_UPSTREAM("liferay-portal/master Upstream"),
			PORTAL_OTHER("liferay-portal-ee PR's & Upstream"),
			PORTAL_OTHER_RELEASE("Portal Fixpack & Hotfix Release"),
			PORTAL_RELEASE("Portal Release");

			@Override
			public String toString() {
				return _string;
			}

			private Category(String string) {
				_string = string;
			}

			private final String _string;

		}

	}

	private static class GroupByJobName
		implements Function<BuildJSONObject, String> {

		public String apply(BuildJSONObject buildJSONObject) {
			String jobName = buildJSONObject.getJobName();

			String name = jobName.replace("-batch", "");

			name = name.replace("-downstream", "");
			name = name.replace("-validation", "");

			return name;
		}

	}

	private static class GroupByTopLevelTestSuite
		implements Function<BuildJSONObject, String> {

		public String apply(BuildJSONObject buildJSONObject) {
			if (buildJSONObject.isTopLevelBuild()) {
				Map<String, String> parameters =
					buildJSONObject.getParameters();

				if (parameters.containsKey("CI_TEST_SUITE")) {
					_topLevelBuildTestSuiteMap.put(
						buildJSONObject.getURL(),
						parameters.get("CI_TEST_SUITE"));

					return parameters.get("CI_TEST_SUITE");
				}

				return "[Unknown]";
			}

			String topLevelBuildURL = buildJSONObject.getTopLevelBuildURL();

			if (_topLevelBuildTestSuiteMap.containsKey(topLevelBuildURL)) {
				return _topLevelBuildTestSuiteMap.get(topLevelBuildURL);
			}

			return "[Unknown]";
		}

		private final Map<String, String> _topLevelBuildTestSuiteMap =
			new HashMap<>();

	}

}