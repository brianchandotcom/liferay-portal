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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ScheduleMessageListenersCommandMessage extends CommandMessage {

	public List<Map<String, String>> getSchedulerContexts() {
		return _schedulerContexts;
	}

	public void setSchedulerContexts(
		List<Map<String, String>> schedulerContexts) {

		_schedulerContexts = schedulerContexts;
	}

	private List<Map<String, String>> _schedulerContexts =
		new ArrayList<Map<String, String>>();

}