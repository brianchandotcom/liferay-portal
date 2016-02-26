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

package com.liferay.portal.kernel.interval;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jonathan McCann
 * @author Sergio González
 * @author Preston Crary
 */
public class IntervalActionProcessor<T> {

	public static final int INTERVAL_DEFAULT = 100;

	public IntervalActionProcessor(int total) {
		if (total < 0) {
			throw new IllegalArgumentException(
				"Total " + total + " is less than zero");
		}

		_total = total;

		_interval = INTERVAL_DEFAULT;
	}

	public IntervalActionProcessor(int total, int interval) {
		if (total < 0) {
			throw new IllegalArgumentException(
				"Total " + total + " is less than zero");
		}

		if (interval <= 0) {
			throw new IllegalArgumentException(
				"Interval " + interval + " is less than or equal to zero");
		}

		_total = total;
		_interval = interval;
	}

	public void incrementStart() {
		_start++;
	}

	public void incrementStart(int increment) {
		if (increment < 0) {
			throw new IllegalArgumentException(
				"Increment " + increment + " is less than zero");
		}

		_start += increment;
	}

	public T performIntervalActions() throws PortalException {
		if (_total == 0) {
			return null;
		}

		int pages = _total / _interval;

		for (int i = 0; i <= pages; i++) {
			int end = _start + _interval;

			if (end > _total) {
				end = _total;
			}

			T result = performIntervalActions(_start, end);

			if (result != null) {
				return result;
			}
		}

		return null;
	}

	public void setPerformIntervalActionMethod(
		PerformIntervalActionMethod<T> performIntervalActionMethod) {

		_performIntervalActionMethod = performIntervalActionMethod;
	}

	public interface PerformIntervalActionMethod<T> {

		public T performIntervalAction(int start, int end)
			throws PortalException;

	}

	protected T performIntervalActions(int start, int end)
		throws PortalException {

		if (_performIntervalActionMethod != null) {
			return _performIntervalActionMethod.performIntervalAction(
				start, end);
		}

		return null;
	}

	private final int _interval;
	private PerformIntervalActionMethod<T> _performIntervalActionMethod;
	private int _start;
	private final int _total;

}