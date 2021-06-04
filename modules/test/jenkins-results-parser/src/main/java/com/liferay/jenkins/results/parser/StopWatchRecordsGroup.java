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

/**
 * @author Michael Hashimoto
 */
public class StopWatchRecordsGroup implements Iterable<StopWatchRecord> {

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

	private final Map<String, StopWatchRecord> _stopWatchRecordsMap =
		new HashMap<>();

}