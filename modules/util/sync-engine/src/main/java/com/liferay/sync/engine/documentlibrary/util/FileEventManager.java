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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.model.SyncFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Shinn Lok
 */
public class FileEventManager {

	public static void addEvent(Event event) {
		synchronized (_eventMap) {
			SyncFile syncFile = (SyncFile)event.getParameterValue("syncFile");

			if (syncFile == null) {
				return;
			}

			Set<Event> events = _eventMap.get(syncFile.getSyncFileId());

			if (events == null) {
				events = new HashSet<>();

				_eventMap.put(syncFile.getSyncFileId(), events);
			}

			events.add(event);
		}
	}

	public static Set<Event> getEvents(long syncFileId) {
		Set<Event> events = _eventMap.get(syncFileId);

		if (events == null) {
			return new HashSet<>();
		}

		return events;
	}

	public static void removeEvent(Event event) {
		synchronized (_eventMap) {
			SyncFile syncFile = (SyncFile)event.getParameterValue("syncFile");

			if (syncFile == null) {
				return;
			}

			Set<Event> events = _eventMap.get(syncFile.getSyncFileId());

			if (events == null) {
				return;
			}

			events.remove(event);

			if (events.isEmpty()) {
				_eventMap.remove(syncFile.getSyncFileId());
			}
		}
	}

	private static final Map<Long, Set<Event>> _eventMap = new HashMap<>();

}