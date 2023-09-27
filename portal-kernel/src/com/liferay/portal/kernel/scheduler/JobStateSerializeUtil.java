/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Date;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class JobStateSerializeUtil {

	public static JobState deserialize(Map<String, Object> jobStateMap) {
		Object object = jobStateMap.get(_VERSION_FIELD);

		if (!(object instanceof Integer)) {
			throw new IllegalStateException(
				"Unable to find JobState version number");
		}

		int version = (Integer)object;

		if (version == 1) {
			return _deserialize_1(jobStateMap);
		}

		throw new IllegalStateException(
			"Unable to deserialize field for job state with version " +
				version);
	}

	public static Map<String, Object> serialize(JobState jobState) {
		if (JobState.VERSION == 1) {
			return _serialize_1(jobState);
		}

		throw new IllegalStateException(
			"Unable to serialize field for job state with version " +
				JobState.VERSION);
	}

	private static JobState _deserialize_1(Map<String, Object> jobStateMap) {
		TriggerState triggerState = null;

		String triggerStateString = (String)jobStateMap.get(
			_TRIGGER_STATE_FIELD);

		try {
			triggerState = TriggerState.valueOf(triggerStateString);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			throw new IllegalStateException(
				"Invalid value " + triggerStateString,
				illegalArgumentException);
		}

		Map<String, Date> triggerDates = (Map<String, Date>)jobStateMap.get(
			_TRIGGER_DATES_FIELD);

		JobState jobState = null;

		if (triggerDates != null) {
			jobState = new JobState(triggerState, triggerDates);
		}
		else {
			jobState = new JobState(triggerState);
		}

		return jobState;
	}

	private static Map<String, Object> _serialize_1(JobState jobState) {
		return HashMapBuilder.<String, Object>put(
			_TRIGGER_DATES_FIELD, jobState.getTriggerDates()
		).put(
			_TRIGGER_STATE_FIELD, String.valueOf(jobState.getTriggerState())
		).put(
			_VERSION_FIELD, JobState.VERSION
		).build();
	}

	private static final String _TRIGGER_DATES_FIELD = "triggerDates";

	private static final String _TRIGGER_STATE_FIELD = "triggerState";

	private static final String _VERSION_FIELD = "version";

}