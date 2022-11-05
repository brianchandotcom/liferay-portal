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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class JobState implements Cloneable, Serializable {

	public static final int VERSION = 1;

	public JobState(TriggerState triggerState) {
		_triggerState = triggerState;
	}

	public JobState(TriggerState triggerState, Map<String, Date> triggerDates) {
		this(triggerState);

		_triggerDates = new HashMap<>(triggerDates);
	}

	@Override
	public Object clone() {
		JobState jobState = new JobState(_triggerState);

		if (_triggerDates != null) {
			jobState._triggerDates = HashMapBuilder.<String, Date>putAll(
				_triggerDates
			).build();
		}

		return jobState;
	}

	public Date getTriggerDate(String key) {
		if (_triggerDates == null) {
			return null;
		}

		return _triggerDates.get(key);
	}

	public Map<String, Date> getTriggerDates() {
		if (_triggerDates == null) {
			return Collections.emptyMap();
		}

		return _triggerDates;
	}

	public TriggerState getTriggerState() {
		return _triggerState;
	}

	public void setTriggerDate(String key, Date date) {
		if (_triggerDates == null) {
			_triggerDates = new HashMap<>();
		}

		_triggerDates.put(key, date);
	}

	public void setTriggerState(TriggerState triggerState) {
		_triggerState = triggerState;
	}

	private static final long serialVersionUID = 5747422831990881126L;

	private Map<String, Date> _triggerDates;
	private TriggerState _triggerState;

}