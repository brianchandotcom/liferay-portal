/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.metrics;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class BuildHistory {

	public BuildHistory(String name, long startTime, long duration) {
		_name = name;
		_startTime = startTime;
		_duration = duration;
	}

	public void addBuildDataJSONObject(BuildJSONObject buildJSONObject) {
		_buildJSONObjects.add(buildJSONObject);

		if (buildJSONObject.isTopLevelBuild()) {
			_topLevelBuildURLs.add(buildJSONObject.getURL());
		}
	}

	public void addBuildDataJSONObjects(
		Collection<BuildJSONObject> buildJSONObjects) {

		for (BuildJSONObject buildJSONObject : buildJSONObjects) {
			addBuildDataJSONObject(buildJSONObject);
		}
	}

	public boolean containsTopLevelBuildURL(String url) {
		return _topLevelBuildURLs.contains(url);
	}

	public Set<BuildJSONObject> getBuildDataJSONObjects() {
		return _buildJSONObjects;
	}

	public long getDuration() {
		return _duration;
	}

	public String getName() {
		return _name;
	}

	public long getStartTime() {
		return _startTime;
	}

	public JSONArray getTableJSONArray(String firstColumnHeader) {
		Table table = _getTable(firstColumnHeader);

		return table.getJSONArray();
	}

	public JSONObject getTimelineJSONObject() {
		Timeline timeline = _getTimeline();

		return timeline.getJSONObject();
	}

	public Set<String> getTopLevelBuildURLs() {
		return _topLevelBuildURLs;
	}

	protected static class Timeline {

		public static final long TIMELINE_SAMPLE_PERIOD_MINUTES = 10;

		public static JSONArray getTimeJSONArray(
			long startTime, long duration) {

			int size = getTimelineSize(duration);

			long[] timeMillis = new long[size];

			for (int i = 0; i < timeMillis.length; i++) {
				if (i == 0) {
					timeMillis[i] = startTime;

					continue;
				}

				timeMillis[i] = timeMillis[i - 1] + (duration / size);
			}

			return new JSONArray(timeMillis);
		}

		public static int getTimelineSize(long duration) {
			return (int)
				(duration /
					TimeUnit.MINUTES.toMillis(TIMELINE_SAMPLE_PERIOD_MINUTES));
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"averageBuildTime", new JSONArray(_averageBuildTime)
			).put(
				"averageQueueTime", new JSONArray(_averageQueueTime)
			).put(
				"buildCounts", new JSONArray(_buildCounts)
			).put(
				"name", _name
			).put(
				"topLevelBuildCounts", new JSONArray(_topLevelBuildCounts)
			);

			return jsonObject;
		}

		protected Timeline(
			BuildHistory buildHistory, long startTime, long duration) {

			_startTime = startTime;
			_duration = duration;

			_size = getTimelineSize(duration);

			_buildCounts = new long[_size];

			_name = buildHistory.getName();
			_topLevelBuildCounts = new long[_size];

			Set<BuildJSONObject> buildJSONObjects =
				buildHistory.getBuildDataJSONObjects();

			long[] buildCountsForAverage = new long[_size];
			long[] totalBuildTime = new long[_size];
			long[] totalQueueTime = new long[_size];

			for (BuildJSONObject buildJSONObject : buildJSONObjects) {
				long buildDuration = buildJSONObject.getDuration();

				if (buildDuration < (5 * 60 * 1000)) {
					continue;
				}

				long buildStartTime = buildJSONObject.getStartTime();
				long queueDuration = buildJSONObject.getQueueDuration();

				int startIndex = _getIndex(buildStartTime);

				totalBuildTime[startIndex] += buildDuration;
				totalQueueTime[startIndex] += queueDuration;

				buildCountsForAverage[startIndex]++;

				int endIndex =
					startIndex + _getDurationIndexSize(buildDuration);

				if (endIndex > (_size - 1)) {
					endIndex = _size - 1;
				}

				for (int i = startIndex; i < endIndex; i++) {
					_buildCounts[i]++;

					if (buildHistory.containsTopLevelBuildURL(
							buildJSONObject.getURL())) {

						_topLevelBuildCounts[i]++;
					}
				}
			}

			_averageBuildTime = new long[_size];
			_averageQueueTime = new long[_size];

			for (int i = 0; i < _size; i++) {
				if (buildCountsForAverage[i] == 0) {
					_averageBuildTime[i] = 0;
					_averageQueueTime[i] = 0;

					continue;
				}

				_averageBuildTime[i] =
					totalBuildTime[i] / buildCountsForAverage[i];
				_averageQueueTime[i] =
					totalQueueTime[i] / buildCountsForAverage[i];
			}
		}

		private int _getDurationIndexSize(long duration) {
			long durationIndexSize = getTimelineSize(duration);

			long timelineSamplePeriodMillis = TimeUnit.MINUTES.toMillis(
				TIMELINE_SAMPLE_PERIOD_MINUTES);

			long roundingSize = timelineSamplePeriodMillis / 2;

			long durationRemainder = duration % timelineSamplePeriodMillis;

			if (durationRemainder >= roundingSize) {
				durationIndexSize++;
			}

			return (int)durationIndexSize;
		}

		private int _getIndex(long timeMillis) {
			int index = (int)((timeMillis - _startTime) * _size / _duration);

			if (index >= _size) {
				return _size - 1;
			}

			if (index < 0) {
				return 0;
			}

			return index;
		}

		private final long[] _averageBuildTime;
		private final long[] _averageQueueTime;
		private final long[] _buildCounts;
		private final long _duration;
		private final String _name;
		private final int _size;
		private final long _startTime;
		private final long[] _topLevelBuildCounts;

	}

	protected class Table {

		public JSONArray getJSONArray() {
			JSONArray jsonArray = new JSONArray();

			for (List<Object> row : _rows) {
				jsonArray.put(new JSONArray(row));
			}

			return jsonArray;
		}

		protected Table(final String firstColumnHeader) {
			Set<BuildJSONObject> buildJSONObjects = getBuildDataJSONObjects();

			Map<String, List<BuildJSONObject>> groupedBuildDataJSONObjectsMap =
				new TreeMap<>();

			final String[] dateStrings =
				JenkinsResultsParserUtil.getDateStrings(
					getStartTime(), getDuration());

			for (String dateString : dateStrings) {
				groupedBuildDataJSONObjectsMap.put(
					dateString, new ArrayList<BuildJSONObject>());
			}

			for (BuildJSONObject buildJSONObject : buildJSONObjects) {
				String startDateString = buildJSONObject.getStartDateString();

				if (!groupedBuildDataJSONObjectsMap.containsKey(
						startDateString)) {

					continue;
				}

				List<BuildJSONObject> groupedBuildJSONObjects =
					groupedBuildDataJSONObjectsMap.get(startDateString);

				groupedBuildJSONObjects.add(buildJSONObject);
			}

			int size = groupedBuildDataJSONObjectsMap.size();

			_averageTopLevelBuildDurations = new Long[size];
			_averageTopLevelQueueDurations = new Long[size];
			_invokedBuilds = new Long[size];
			_invokedTopLevelBuilds = new Long[size];
			_totalServerDurations = new Long[size];

			int index = 0;

			for (List<BuildJSONObject> groupedBuildJSONObjects :
					groupedBuildDataJSONObjectsMap.values()) {

				long buildsInvoked = 0;
				long topLevelBuildsInvoked = 0;
				long totalTopLevelBuildDuration = 0;
				long totalDownstreamBuildDuration = 0;
				long totalTopLevelQueueDuration = 0;

				for (BuildJSONObject buildJSONObject :
						groupedBuildJSONObjects) {

					if (containsTopLevelBuildURL(buildJSONObject.getURL())) {
						topLevelBuildsInvoked++;

						totalTopLevelBuildDuration +=
							buildJSONObject.getDuration();

						totalTopLevelQueueDuration +=
							buildJSONObject.getQueueDuration();
					}
					else {
						buildsInvoked++;

						totalDownstreamBuildDuration +=
							buildJSONObject.getDuration();
					}
				}

				_invokedBuilds[index] = buildsInvoked;
				_invokedTopLevelBuilds[index] = topLevelBuildsInvoked;

				if (topLevelBuildsInvoked != 0) {
					_averageTopLevelBuildDurations[index] =
						totalTopLevelBuildDuration / topLevelBuildsInvoked;

					_averageTopLevelQueueDurations[index] =
						totalTopLevelQueueDuration / topLevelBuildsInvoked;
				}
				else {
					_averageTopLevelBuildDurations[index] = 0L;
					_averageTopLevelQueueDurations[index] = 0L;
				}

				_totalServerDurations[index] =
					totalTopLevelBuildDuration + totalDownstreamBuildDuration;

				index++;
			}

			_rows.add(
				new ArrayList<Object>() {
					{
						add(firstColumnHeader);
						add("Metric");
						addAll(Arrays.asList(dateStrings));
					}
				});

			_rows.add(
				new ArrayList<Object>() {
					{
						add(getName());
						add("Invoked Builds");
						addAll(Arrays.asList(_invokedBuilds));
					}
				});

			_rows.add(
				new ArrayList<Object>() {
					{
						add(getName());
						add("Invoked Top Level Builds");
						addAll(Arrays.asList(_invokedTopLevelBuilds));
					}
				});

			_rows.add(
				new ArrayList<Object>() {
					{
						add(getName());
						add("Average Top Level Build Duration");
						addAll(Arrays.asList(_averageTopLevelBuildDurations));
					}
				});

			_rows.add(
				new ArrayList<Object>() {
					{
						add(getName());
						add("Average Top Level Duration in Queue");
						addAll(Arrays.asList(_averageTopLevelQueueDurations));
					}
				});

			_rows.add(
				new ArrayList<Object>() {
					{
						add(getName());
						add("Total Server Duration");
						addAll(Arrays.asList(_totalServerDurations));
					}
				});
		}

		private final Long[] _averageTopLevelBuildDurations;
		private final Long[] _averageTopLevelQueueDurations;
		private final Long[] _invokedBuilds;
		private final Long[] _invokedTopLevelBuilds;
		private final List<List<Object>> _rows = new ArrayList<>();
		private final Long[] _totalServerDurations;

	}

	private Table _getTable(String firstColumnHeader) {
		if (_table == null) {
			_table = new Table(firstColumnHeader);
		}

		return _table;
	}

	private Timeline _getTimeline() {
		if (_timeline == null) {
			_timeline = new Timeline(this, getStartTime(), getDuration());
		}

		return _timeline;
	}

	private final Set<BuildJSONObject> _buildJSONObjects = new HashSet<>();
	private final long _duration;
	private final String _name;
	private final long _startTime;
	private Table _table;
	private Timeline _timeline;
	private final Set<String> _topLevelBuildURLs = new HashSet<>();

}