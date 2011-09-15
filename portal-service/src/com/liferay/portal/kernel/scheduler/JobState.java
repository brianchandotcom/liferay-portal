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

	JobState(TriggerState triggerState, int exceptionsMaxSize,
		Map<String, Date> triggerTimeInfomation) {

		if (exceptionsMaxSize <= 0) {
			exceptionsMaxSize = _EXCEPTIONS_MAX_SIZE;
		}

		_triggerState = triggerState;
		_exceptionsMaxSize = exceptionsMaxSize;
		_triggerTimeInfomation = new HashMap<String, Date>(
			triggerTimeInfomation);
	}

	public void addException(Exception exception, Date date) {
		if (_exceptions == null) {
			_exceptions = new LinkedList<ObjectValuePair<Exception, Date>>();
		}

		_exceptions.add(
			new ObjectValuePair<Exception, Date>(exception, date));

		while (_exceptions.size() > _exceptionsMaxSize) {
			_exceptions.poll();
		}
	}

	public void clearExceptions() {
		if (_exceptions != null && !_exceptions.isEmpty()) {
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

		if (_triggerTimeInfomation != null) {
			Map<String, Date> triggerTimeInfomation =
				new HashMap<String, Date>();

			triggerTimeInfomation.putAll(_triggerTimeInfomation);

			jobState._triggerTimeInfomation = triggerTimeInfomation;
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

	public TriggerState getTriggerState() {
		return _triggerState;
	}

	public Date getTriggerTimeInfomation(String key) {
		if (_triggerTimeInfomation == null) {
			return null;
		}

		return _triggerTimeInfomation.get(key);
	}

	public Map<String, Date> getTriggerTimeInfomations() {
		if (_triggerTimeInfomation == null) {
			return Collections.EMPTY_MAP;
		}

		return _triggerTimeInfomation;
	}

	public void setTriggerState(TriggerState triggerState) {
		_triggerState = triggerState;
	}

	public void setTriggerTimeInfomation(String key, Date date) {
		if (_triggerTimeInfomation == null) {
			_triggerTimeInfomation = new HashMap<String, Date>();
		}

		_triggerTimeInfomation.put(key, date);
	}

	public static final int VERSION = 1;

	private static final int _EXCEPTIONS_MAX_SIZE = 10;
	private static final long serialVersionUID = 5747422831990881126L;

	private Queue<ObjectValuePair<Exception, Date>> _exceptions;
	private int _exceptionsMaxSize;
	private TriggerState _triggerState;
	private Map<String, Date> _triggerTimeInfomation;

}