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

package com.liferay.lcs.messaging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ScheduleTasksCommandMessage extends CommandMessage {

	public Map<String, List<Map<String, String>>>
		getPrioritySchedulerContexts() {

		return _prioritySchedulerContexts;
	}

	public void setPrioritySchedulerContexts(
		Map<String, List<Map<String, String>>> prioritySchedulerContexts) {

		_prioritySchedulerContexts = prioritySchedulerContexts;
	}

	private Map<String, List<Map<String, String>>> _prioritySchedulerContexts =
		new HashMap<String, List<Map<String, String>>>();

}