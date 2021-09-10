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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class StopWatchRecordsGroup implements Iterable<StopWatchRecord> {

	public StopWatchRecordsGroup() {
	}

	public StopWatchRecordsGroup(JSONObject buildResultJSONObject) {
		if ((buildResultJSONObject == null) ||
			!buildResultJSONObject.has("duration")) {

			return;
		}

		StopWatchRecord stopWatchRecord = new StopWatchRecord(
			"total.duration", _startTimestamp,
			buildResultJSONObject.getLong("duration"));

		_startTimestamp += 1000;

		add(stopWatchRecord);

		if (!buildResultJSONObject.has("stopWatchRecords")) {
			return;
		}

		JSONArray stopWatchRecordsJSONArray =
			buildResultJSONObject.getJSONArray("stopWatchRecords");

		for (int i = 0; i < stopWatchRecordsJSONArray.length(); i++) {
			JSONObject childStopWatchRecordJSONObject =
				stopWatchRecordsJSONArray.getJSONObject(i);

			childStopWatchRecordJSONObject.put(
				"startTimestamp", _startTimestamp);

			if (!childStopWatchRecordJSONObject.has("duration") ||
				!childStopWatchRecordJSONObject.has("name")) {

				continue;
			}

			_startTimestamp += 1000;

			StopWatchRecord childStopWatchRecord = new StopWatchRecord(
				childStopWatchRecordJSONObject);

			stopWatchRecord.addChildStopWatchRecord(childStopWatchRecord);

			add(childStopWatchRecord);

			_addChildStopWatchRecords(
				childStopWatchRecord, childStopWatchRecordJSONObject);
		}
	}

	public void add(StopWatchRecord newStopWatchRecord) {
		_stopWatchRecordsMap.put(
			newStopWatchRecord.getName(), newStopWatchRecord);
	}

	public StopWatchRecord get(String name) {
		return _stopWatchRecordsMap.get(name);
	}

	public JSONArray getJSONArray() {
		JSONArray jsonArray = new JSONArray();

		for (StopWatchRecord stopWatchRecord : getStopWatchRecords()) {
			jsonArray.put(stopWatchRecord.getJSONObject());
		}

		return jsonArray;
	}

	public List<StopWatchRecord> getStopWatchRecords() {
		List<StopWatchRecord> allStopWatchRecords = new ArrayList<>(
			_stopWatchRecordsMap.values());

		Collections.sort(allStopWatchRecords);

		List<StopWatchRecord> parentStopWatchRecords = new ArrayList<>();

		for (StopWatchRecord stopWatchRecord : allStopWatchRecords) {
			for (StopWatchRecord parentStopWatchRecord :
					parentStopWatchRecords) {

				if (parentStopWatchRecord.isParentOf(stopWatchRecord)) {
					parentStopWatchRecord.addChildStopWatchRecord(
						stopWatchRecord);

					break;
				}
			}

			if (stopWatchRecord.getParentStopWatchRecord() == null) {
				parentStopWatchRecords.add(stopWatchRecord);
			}
		}

		return parentStopWatchRecords;
	}

	public boolean isEmpty() {
		return _stopWatchRecordsMap.isEmpty();
	}

	@Override
	public Iterator<StopWatchRecord> iterator() {
		List<StopWatchRecord> list = getStopWatchRecords();

		return list.iterator();
	}

	public int size() {
		List<StopWatchRecord> stopWatchRecords = getStopWatchRecords();

		return stopWatchRecords.size();
	}

	private void _addChildStopWatchRecords(
		StopWatchRecord stopWatchRecord, JSONObject stopWatchRecordJSONObject) {

		if (!stopWatchRecordJSONObject.has("childStopWatchRecords")) {
			return;
		}

		JSONArray childStopWatchRecordsJSONArray =
			stopWatchRecordJSONObject.getJSONArray("childStopWatchRecords");

		for (int i = 0; i < childStopWatchRecordsJSONArray.length(); i++) {
			JSONObject childStopWatchRecordJSONObject =
				childStopWatchRecordsJSONArray.getJSONObject(i);

			childStopWatchRecordJSONObject.put(
				"startTimestamp", _startTimestamp);

			_startTimestamp += 1000;

			StopWatchRecord childStopWatchRecord = new StopWatchRecord(
				childStopWatchRecordJSONObject);

			add(childStopWatchRecord);

			stopWatchRecord.addChildStopWatchRecord(childStopWatchRecord);

			_addChildStopWatchRecords(
				childStopWatchRecord, childStopWatchRecordJSONObject);
		}
	}

	private long _startTimestamp;
	private final Map<String, StopWatchRecord> _stopWatchRecordsMap =
		new HashMap<>();

}