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

	public BuildHistory(long duration, String name, long startTime) {
		_duration = duration;
		_name = name;
		_startTime = startTime;
	}

	public void addBuildJSONObject(BuildJSONObject buildJSONObject) {
		_addData(buildJSONObject);

		Timeline timeline = _getTimeline();

		timeline.addData(buildJSONObject);
	}

	public void addBuildJSONObjects(
		Collection<BuildJSONObject> buildJSONObjects) {

		for (BuildJSONObject buildJSONObject : buildJSONObjects) {
			addBuildJSONObject(buildJSONObject);
		}
	}

	public boolean containsTopLevelBuildURL(String url) {
		return _topLevelBuildURLs.contains(url);
	}

	public Map<String, Long> getDailyInvokedBuilds() {
		return _dailyInvokedBuilds;
	}

	public Map<String, Long> getDailyInvokedTopLevelBuilds() {
		return _dailyInvokedTopLevelBuilds;
	}

	public Map<String, Long> getDailyTotalBuildDurations() {
		return _dailyTotalBuildDurations;
	}

	public Map<String, Long> getDailyTotalQueueTime() {
		return _dailyTotalTopLevelQueueTime;
	}

	public Map<String, Long> getDailyTotalTopLevelBuildDurations() {
		return _dailyTotalTopLevelBuildDurations;
	}

	public long getDuration() {
		return _duration;
	}

	public long getInvokedBuildCount() {
		long totalInvokedBuildCount = 0;

		for (Long invokedBuildCount : _dailyInvokedBuilds.values()) {
			totalInvokedBuildCount += invokedBuildCount;
		}

		return totalInvokedBuildCount;
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

	public void merge(BuildHistory buildHistory) {
		_mergeMap(_dailyInvokedBuilds, buildHistory.getDailyInvokedBuilds());
		_mergeMap(
			_dailyTotalTopLevelQueueTime,
			buildHistory.getDailyTotalQueueTime());
		_mergeMap(
			_dailyInvokedTopLevelBuilds,
			buildHistory.getDailyInvokedTopLevelBuilds());
		_mergeMap(
			_dailyTotalTopLevelBuildDurations,
			buildHistory.getDailyTotalTopLevelBuildDurations());
		_mergeMap(
			_dailyTotalBuildDurations,
			buildHistory.getDailyTotalBuildDurations());

		if (buildHistory.getDuration() > _duration) {
			setDuration(buildHistory.getDuration());
		}

		if (buildHistory.getStartTime() < _startTime) {
			setStartTime(buildHistory.getStartTime());
		}

		_topLevelBuildURLs.addAll(buildHistory.getTopLevelBuildURLs());
	}

	public void setDuration(long duration) {
		_duration = duration;
	}

	public void setStartTime(long startTime) {
		_startTime = startTime;
	}

	protected static JSONArray getTimeJSONArray(long duration, long startTime) {
		int size = _getTimelineSize(duration);

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

	protected class Table {

		public JSONArray getJSONArray() {
			JSONArray jsonArray = new JSONArray();

			for (List<Object> row : _rows) {
				jsonArray.put(new JSONArray(row));
			}

			return jsonArray;
		}

		protected Table(final String firstColumnHeader) {
			final String[] dateStrings =
				JenkinsResultsParserUtil.getDateStrings(
					getStartTime(), getDuration());

			_averageTopLevelBuildDurations = new Long[dateStrings.length];
			_averageTopLevelQueueDurations = new Long[dateStrings.length];
			_invokedBuilds = new Long[dateStrings.length];
			_invokedTopLevelBuilds = new Long[dateStrings.length];
			_totalServerDurations = new Long[dateStrings.length];

			for (int i = 0; i < dateStrings.length; i++) {
				String dateString = dateStrings[i];

				_invokedBuilds[i] = _getValue(_dailyInvokedBuilds, dateString);

				_invokedTopLevelBuilds[i] = _getValue(
					_dailyInvokedTopLevelBuilds, dateString);

				_totalServerDurations[i] = _getValue(
					_dailyTotalBuildDurations, dateString);

				_averageTopLevelBuildDurations[i] = _getQuotient(
					_getValue(_dailyTotalTopLevelBuildDurations, dateString),
					_getValue(_dailyInvokedTopLevelBuilds, dateString));

				_averageTopLevelQueueDurations[i] = _getQuotient(
					_getValue(_dailyTotalTopLevelQueueTime, dateString),
					_getValue(_dailyInvokedTopLevelBuilds, dateString));
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

	protected class Timeline {

		public void addData(BuildJSONObject buildJSONObject) {
			long buildStartTime = buildJSONObject.getStartTime();

			int startIndex = _getIndex(buildStartTime);

			long buildDuration = buildJSONObject.getDuration();

			long relativeStartTime = buildStartTime - _startTime;

			long relativeEndTime = relativeStartTime + buildDuration;

			long timelineSamplePeriodMillis = TimeUnit.MINUTES.toMillis(
				_TIMELINE_SAMPLE_PERIOD_MINUTES);

			if ((relativeStartTime >
					((_size - 1) * timelineSamplePeriodMillis)) ||
				((relativeStartTime >
					(startIndex * timelineSamplePeriodMillis)) &&
				 (relativeEndTime <
					 ((startIndex + 1) * timelineSamplePeriodMillis)))) {

				return;
			}

			if (relativeEndTime > (startIndex * timelineSamplePeriodMillis)) {
				int endIndex = _getIndex(buildStartTime + buildDuration);

				if (startIndex < (_size - 1)) {
					startIndex++;
				}

				for (int i = startIndex; i <= endIndex; i++) {
					_buildCounts[i]++;

					if (containsTopLevelBuildURL(buildJSONObject.getURL())) {
						_topLevelBuildCounts[i]++;
					}
				}

				_totalBuildTime[startIndex] += buildDuration;

				long queueDuration = buildJSONObject.getQueueDuration();

				_totalQueueTime[startIndex] += queueDuration;

				_buildCountsForAverage[startIndex]++;
			}
		}

		public JSONObject getJSONObject() {
			_calculateAverages();

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

		protected Timeline() {
			_size = _getTimelineSize(_duration);

			_buildCounts = new long[_size];
			_topLevelBuildCounts = new long[_size];
			_buildCountsForAverage = new long[_size];
			_totalBuildTime = new long[_size];
			_totalQueueTime = new long[_size];
			_averageBuildTime = new long[_size];
			_averageQueueTime = new long[_size];
		}

		private void _calculateAverages() {
			for (int i = 0; i < _size; i++) {
				if (_buildCountsForAverage[i] == 0) {
					_averageBuildTime[i] = 0;
					_averageQueueTime[i] = 0;

					continue;
				}

				_averageBuildTime[i] =
					_totalBuildTime[i] / _buildCountsForAverage[i];
				_averageQueueTime[i] =
					_totalQueueTime[i] / _buildCountsForAverage[i];
			}
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
		private final long[] _buildCountsForAverage;
		private final int _size;
		private final long[] _topLevelBuildCounts;
		private final long[] _totalBuildTime;
		private final long[] _totalQueueTime;

	}

	private static int _getTimelineSize(long duration) {
		return (int)
			(duration /
				TimeUnit.MINUTES.toMillis(_TIMELINE_SAMPLE_PERIOD_MINUTES));
	}

	private void _addData(BuildJSONObject buildJSONObject) {
		String dateString = buildJSONObject.getStartDateString();

		_addData(_dailyInvokedBuilds, dateString, 1L);
		_addData(
			_dailyTotalBuildDurations, dateString,
			buildJSONObject.getDuration());

		if (buildJSONObject.isTopLevelBuild()) {
			_topLevelBuildURLs.add(buildJSONObject.getURL());

			_addData(
				_dailyTotalTopLevelQueueTime, dateString,
				buildJSONObject.getQueueDuration());
			_addData(_dailyInvokedTopLevelBuilds, dateString, 1L);
			_addData(
				_dailyTotalTopLevelBuildDurations, dateString,
				buildJSONObject.getDuration());
		}
	}

	private void _addData(Map<String, Long> dataMap, String key, Long value) {
		if (!dataMap.containsKey(key)) {
			dataMap.put(key, value);

			return;
		}

		dataMap.put(key, dataMap.get(key) + value);
	}

	private Long _getQuotient(Long value1, Long value2) {
		if (value1 == 0L) {
			return value1;
		}

		return value1 / value2;
	}

	private Table _getTable(String firstColumnHeader) {
		if (_table == null) {
			_table = new Table(firstColumnHeader);
		}

		return _table;
	}

	private Timeline _getTimeline() {
		if (_timeline == null) {
			_timeline = new Timeline();
		}

		return _timeline;
	}

	private Long _getValue(Map<String, Long> dailyValueMap, String dateString) {
		if (dailyValueMap.containsKey(dateString)) {
			return dailyValueMap.get(dateString);
		}

		return 0L;
	}

	private void _mergeMap(
		Map<String, Long> dataMap1, Map<String, Long> dataMap2) {

		for (Map.Entry<String, Long> entry : dataMap2.entrySet()) {
			String key = entry.getKey();

			Long currentValue = dataMap1.get(key);

			dataMap1.put(
				key,
				(currentValue == null) ? entry.getValue() :
					entry.getValue() + currentValue);
		}
	}

	private static final long _TIMELINE_SAMPLE_PERIOD_MINUTES = 15;

	private final Map<String, Long> _dailyInvokedBuilds = new TreeMap<>();
	private final Map<String, Long> _dailyInvokedTopLevelBuilds =
		new TreeMap<>();
	private final Map<String, Long> _dailyTotalBuildDurations = new TreeMap<>();
	private final Map<String, Long> _dailyTotalTopLevelBuildDurations =
		new TreeMap<>();
	private final Map<String, Long> _dailyTotalTopLevelQueueTime =
		new TreeMap<>();
	private long _duration;
	private final String _name;
	private long _startTime;
	private Table _table;
	private Timeline _timeline;
	private final Set<String> _topLevelBuildURLs = new HashSet<>();

}