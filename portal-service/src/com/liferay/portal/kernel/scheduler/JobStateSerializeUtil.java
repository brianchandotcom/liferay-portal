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

		switch (version) {
			case 1:
				return _doDeserialize1(jobStateMap);
			default:
				throw new IllegalStateException(
					"Unable to find deserialize method for JobState with " +
						"version " + version);
		}
	}

	public static Map<String, Object> serialize(JobState jobState) {
		switch (JobState.VERSION) {
			case 1:
				return _doSerialize1(jobState);
			default:
				throw new IllegalStateException(
					"Unable to find serialize method for JobState with " +
						"current version " + JobState.VERSION);
		}
	}

	private static JobState _doDeserialize1(Map<String, Object> jobStateMap) {
		ArrayList<Object[]> exceptionsList =
			(ArrayList<Object[]>)jobStateMap.get(_EXCEPTIONS_FIELD);
		int exceptionsMaxSize = (Integer)jobStateMap.get(
			_EXCEPTIONS_MAX_SIZE_FIELD);
		String triggerStateString =
			(String)jobStateMap.get(_TRIGGER_STATE_FIELD);
		Map<String, Date> triggerTimeInfomation =
			(Map<String, Date>)jobStateMap.get(_TRIGGER_TIME_INFOMATION_FIELD);

		TriggerState triggerState = null;

		try {
			triggerState = TriggerState.valueOf(triggerStateString);
		}
		catch (IllegalArgumentException iae) {
			throw new IllegalStateException(
				"Unable to cast string " + triggerStateString +
					" to TriggerState enum type", iae);
		}

		JobState jobState = new JobState(triggerState, exceptionsMaxSize,
			triggerTimeInfomation);

		if (exceptionsList != null) {
			for (Object[] exceptions : exceptionsList) {
				jobState.addException((Exception)exceptions[0],
					(Date)exceptions[1]);
			}
		}

		return jobState;
	}

	private static Map<String, Object> _doSerialize1(JobState jobState) {
		Map<String, Object> jobStateMap = new HashMap<String, Object>();

		ArrayList<Object[]> exceptionsList = new ArrayList<Object[]>();

		ObjectValuePair<Exception, Date>[] exceptions =
			jobState.getExceptions();

		for (ObjectValuePair<Exception, Date> exception : exceptions) {
			exceptionsList.add(
				new Object[]{exception.getKey(), exception.getValue()});
		}

		exceptionsList.trimToSize();

		jobStateMap.put(_EXCEPTIONS_FIELD, exceptionsList);
		jobStateMap.put(
			_EXCEPTIONS_MAX_SIZE_FIELD, jobState.getExceptionsMaxSize());
		jobStateMap.put(
			_TRIGGER_STATE_FIELD, jobState.getTriggerState().toString());
		jobStateMap.put(
			_TRIGGER_TIME_INFOMATION_FIELD,
			jobState.getTriggerTimeInfomations());
		jobStateMap.put(_VERSION_FIELD, JobState.VERSION);

		return jobStateMap;
	}

	private static final String _EXCEPTIONS_FIELD = "exceptions";
	private static final String _EXCEPTIONS_MAX_SIZE_FIELD =
		"exceptionsMaxSize";
	private static final String _TRIGGER_STATE_FIELD = "triggerState";
	private static final String _TRIGGER_TIME_INFOMATION_FIELD =
		"triggerTimeInfomation";
	private static final String _VERSION_FIELD = "version";

}