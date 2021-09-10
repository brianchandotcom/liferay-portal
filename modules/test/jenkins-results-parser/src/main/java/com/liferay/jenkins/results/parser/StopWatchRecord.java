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

		_jsonObject.put("name", name);
		_jsonObject.put("startTimestamp", startTimestamp);
	}

	public StopWatchRecord(String name, long startTimestamp, long duration) {
		_jsonObject = new JSONObject();

		_jsonObject.put("duration", duration);
		_jsonObject.put("name", name);
		_jsonObject.put("startTimestamp", startTimestamp);
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

		jsonObject.put("duration", getDuration());
		jsonObject.put("name", getName());
		jsonObject.put("startTimestamp", getStartTimestamp());

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