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

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Tina Tian
 */
public class JobState implements Cloneable, Serializable {

	public static final int VERSION = 1;

	public JobState(TriggerState triggerState) {
		this(triggerState, _EXCEPTIONS_MAX_SIZE);
	}

	public JobState(TriggerState triggerState, int exceptionsMaxSize) {
		if (exceptionsMaxSize <= 0) {
			exceptionsMaxSize = _EXCEPTIONS_MAX_SIZE;
		}

		_triggerState = triggerState;
		_exceptionsMaxSize = exceptionsMaxSize;
	}

	public JobState(
		TriggerState triggerState, int exceptionsMaxSize,
		Map<String, Date> triggerDates) {

		this(triggerState, exceptionsMaxSize);

		_triggerDates = new HashMap<String, Date>(triggerDates);
	}

	public void addException(Exception exception, Date date) {
		if (_exceptions == null) {
			_exceptions = new LinkedList<ObjectValuePair<Exception, Date>>();
		}

		_exceptions.add(new ObjectValuePair<Exception, Date>(exception, date));

		while (_exceptions.size() > _exceptionsMaxSize) {
			_exceptions.poll();
		}
	}

	public void clearExceptions() {
		if ((_exceptions != null) && !_exceptions.isEmpty()) {
			_exceptions.clear();
		}
	}

	@Override
	public Object clone() {
		JobState jobState = new JobState(_triggerState, _exceptionsMaxSize);

		if (_exceptions != null) {
			Queue<ObjectValuePair<Exception, Date>> exceptions =
				new LinkedList<ObjectValuePair<Exception, Date>>();

			exceptions.addAll(_exceptions);

			jobState._exceptions = exceptions;
		}

		if (_triggerDates != null) {
			Map<String, Date> triggerTimeInfomation =
				new HashMap<String, Date>();

			triggerTimeInfomation.putAll(_triggerDates);

			jobState._triggerDates = triggerTimeInfomation;
		}

		return jobState;
	}

	public ObjectValuePair<Exception, Date>[] getExceptions() {
		if (_exceptions == null) {
			return null;
		}

		return _exceptions.toArray(new ObjectValuePair[_exceptions.size()]);
	}

	public int getExceptionsMaxSize() {
		return _exceptionsMaxSize;
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
			_triggerDates = new HashMap<String, Date>();
		}

		_triggerDates.put(key, date);
	}

	public void setTriggerState(TriggerState triggerState) {
		_triggerState = triggerState;
	}

	private static final int _EXCEPTIONS_MAX_SIZE = 10;

	private static final long serialVersionUID = 5747422831990881126L;

	private Queue<ObjectValuePair<Exception, Date>> _exceptions;
	private final int _exceptionsMaxSize;
	private Map<String, Date> _triggerDates;
	private TriggerState _triggerState;

}