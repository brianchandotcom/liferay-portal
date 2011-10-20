/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
		else {
			throw new IllegalStateException(
				"Unable to deserialize field for job state with version " +
					version);
		}
	}

	public static Map<String, Object> serialize(JobState jobState) {
		switch (JobState.VERSION) {
			case 1:
				return _serialize_1(jobState);

			default:
				throw new IllegalStateException(
					"Unable to serialize field for job state with version " +
						JobState.VERSION);
		}
	}

	private static JobState _deserialize_1(Map<String, Object> jobStateMap) {
		TriggerState triggerState = null;

		String triggerStateString = (String)jobStateMap.get(
			_TRIGGER_STATE_FIELD);

		try {
			triggerState = TriggerState.valueOf(triggerStateString);
		}
		catch (IllegalArgumentException iae) {
			throw new IllegalStateException(
				"Invalid value " + triggerStateString, iae);
		}

		int exceptionsMaxSize = (Integer)jobStateMap.get(
			_EXCEPTIONS_MAX_SIZE_FIELD);
		Map<String, Date> triggerDates = (Map<String, Date>)jobStateMap.get(
			_TRIGGER_DATES_FIELD);

		JobState jobState = null;

		if (triggerDates != null) {
			jobState = new JobState(
				triggerState, exceptionsMaxSize, triggerDates);
		}
		else {
			jobState = new JobState(triggerState, exceptionsMaxSize);
		}

		ArrayList<Object[]> exceptionsList =
			(ArrayList<Object[]>)jobStateMap.get(_EXCEPTIONS_FIELD);

		if (exceptionsList != null) {
			for (Object[] exceptions : exceptionsList) {
				jobState.addException(
					(Exception)exceptions[0], (Date)exceptions[1]);
			}
		}

		return jobState;
	}

	private static Map<String, Object> _serialize_1(JobState jobState) {
		Map<String, Object> jobStateMap = new HashMap<String, Object>();

		ObjectValuePair<Exception, Date>[] exceptions =
			jobState.getExceptions();

		if (exceptions != null) {
			ArrayList<Object[]> exceptionsList = new ArrayList<Object[]>();

			for (ObjectValuePair<Exception, Date> exception : exceptions) {
				exceptionsList.add(
					new Object[] {exception.getKey(), exception.getValue()});
			}

			exceptionsList.trimToSize();

			jobStateMap.put(_EXCEPTIONS_FIELD, exceptionsList);
		}

		jobStateMap.put(
			_EXCEPTIONS_MAX_SIZE_FIELD, jobState.getExceptionsMaxSize());
		jobStateMap.put(
			_TRIGGER_DATES_FIELD, jobState.getTriggerDates());
		jobStateMap.put(
			_TRIGGER_STATE_FIELD, jobState.getTriggerState().toString());
		jobStateMap.put(_VERSION_FIELD, JobState.VERSION);

		return jobStateMap;
	}

	private static final String _EXCEPTIONS_FIELD = "exceptions";

	private static final String _EXCEPTIONS_MAX_SIZE_FIELD =
		"exceptionsMaxSize";

	private static final String _TRIGGER_DATES_FIELD = "triggerDates";

	private static final String _TRIGGER_STATE_FIELD = "triggerState";

	private static final String _VERSION_FIELD = "version";

}