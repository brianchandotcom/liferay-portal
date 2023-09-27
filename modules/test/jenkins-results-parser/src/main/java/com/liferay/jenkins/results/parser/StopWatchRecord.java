/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class StopWatchRecord implements Comparable<StopWatchRecord> {

	public StopWatchRecord(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	public StopWatchRecord(String name, long startTimestamp) {
		_jsonObject = new JSONObject();

		_jsonObject.put(
			"name", name
		).put(
			"startTimestamp", startTimestamp
		);
	}

	public StopWatchRecord(String name, long startTimestamp, long duration) {
		_jsonObject = new JSONObject();

		_jsonObject.put(
			"duration", duration
		).put(
			"name", name
		).put(
			"startTimestamp", startTimestamp
		);
	}

	public void addChildStopWatchRecord(
		StopWatchRecord newChildStopWatchRecord) {

		if (_childStopWatchRecords == null) {
			_childStopWatchRecords = new TreeSet<>();
		}

		for (StopWatchRecord childStopWatchRecord : _childStopWatchRecords) {
			if (childStopWatchRecord.isParentOf(newChildStopWatchRecord)) {
				childStopWatchRecord.addChildStopWatchRecord(
					newChildStopWatchRecord);

				return;
			}
		}

		newChildStopWatchRecord.setParentStopWatchRecord(this);

		_childStopWatchRecords.add(newChildStopWatchRecord);
	}

	@Override
	public int compareTo(StopWatchRecord stopWatchRecord) {
		Long startTimestamp = getStartTimestamp();

		int compareToValue = startTimestamp.compareTo(
			stopWatchRecord.getStartTimestamp());

		if (compareToValue != 0) {
			return compareToValue;
		}

		Long duration = getDuration();
		Long stopWatchRecordDuration = stopWatchRecord.getDuration();

		if ((duration == null) && (stopWatchRecordDuration != null)) {
			return -1;
		}

		if ((duration != null) && (stopWatchRecordDuration == null)) {
			return 1;
		}

		if ((duration != null) && (stopWatchRecordDuration != null)) {
			compareToValue = -1 * duration.compareTo(stopWatchRecordDuration);
		}

		if (compareToValue != 0) {
			return compareToValue;
		}

		String name = getName();

		return name.compareTo(stopWatchRecord.getName());
	}

	public Set<StopWatchRecord> getChildStopWatchRecords() {
		return _childStopWatchRecords;
	}

	public int getDepth() {
		if (_parentStopWatchRecord == null) {
			return 0;
		}

		return _parentStopWatchRecord.getDepth() + 1;
	}

	public Long getDuration() {
		if (!_jsonObject.has("duration")) {
			return null;
		}

		return _jsonObject.getLong("duration");
	}

	public JSONObject getJSONObject() {
		JSONArray childStopWatchRecordJSONArray = new JSONArray();

		if (_childStopWatchRecords != null) {
			for (StopWatchRecord childStopWatchRecord :
					_childStopWatchRecords) {

				childStopWatchRecordJSONArray.put(
					childStopWatchRecord.getJSONObject());
			}
		}

		JSONObject jsonObject = new JSONObject();

		if (childStopWatchRecordJSONArray.length() > 0) {
			jsonObject.put(
				"childStopWatchRecords", childStopWatchRecordJSONArray);
		}

		jsonObject.put(
			"duration", getDuration()
		).put(
			"name", getName()
		).put(
			"startTimestamp", getStartTimestamp()
		);

		return jsonObject;
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public StopWatchRecord getParentStopWatchRecord() {
		return _parentStopWatchRecord;
	}

	public String getShortName() {
		String shortName = getName();

		StopWatchRecord parentStopWatchRecord = getParentStopWatchRecord();

		if (parentStopWatchRecord == null) {
			return shortName;
		}

		return shortName.replace(parentStopWatchRecord.getName(), "");
	}

	public Long getStartTimestamp() {
		return _jsonObject.getLong("startTimestamp");
	}

	public boolean isParentOf(StopWatchRecord stopWatchRecord) {
		if (this == stopWatchRecord) {
			return false;
		}

		Long duration = getDuration();
		Long stopWatchRecordDuration = stopWatchRecord.getDuration();

		if ((duration != null) && (stopWatchRecordDuration == null)) {
			return false;
		}

		Long startTimestamp = getStartTimestamp();
		Long stopWatchRecordStartTimestamp =
			stopWatchRecord.getStartTimestamp();

		if (startTimestamp <= stopWatchRecordStartTimestamp) {
			if (duration == null) {
				return true;
			}

			Long endTimestamp = startTimestamp + duration;
			Long stopWatchRecordEndTimestamp =
				stopWatchRecordStartTimestamp + stopWatchRecordDuration;

			if (endTimestamp >= stopWatchRecordEndTimestamp) {
				return true;
			}
		}

		return false;
	}

	public void setDuration(long duration) {
		_jsonObject.put("duration", duration);
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			getName(), " started at ",
			JenkinsResultsParserUtil.toDateString(
				new Date(getStartTimestamp())),
			" and ran for ",
			JenkinsResultsParserUtil.toDurationString(getDuration()), ".");
	}

	protected void setParentStopWatchRecord(StopWatchRecord stopWatchRecord) {
		_parentStopWatchRecord = stopWatchRecord;
	}

	private Set<StopWatchRecord> _childStopWatchRecords;
	private final JSONObject _jsonObject;
	private StopWatchRecord _parentStopWatchRecord;

}