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

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jonathan McCann
 * @author Sergio González
 */
public class IntervalAction {

	public static final int DEFAULT_INTERVAL = 100;

	public IntervalAction(int total) {
		if (total < 0) {
			throw new IllegalArgumentException();
		}

		_total = total;

		_interval = DEFAULT_INTERVAL;
	}

	public IntervalAction(int total, int interval) {
		if ((total < 0) || (interval <= 0)) {
			throw new IllegalArgumentException();
		}

		_total = total;
		_interval = interval;
	}

	public void incrementStart() {
		_start++;
	}

	public void incrementStart(int increment) {
		if (increment < 0) {
			throw new IllegalArgumentException();
		}

		_start += increment;
	}

	public void performIntervalActions() throws PortalException {
		int pages = _total / _interval;

		for (int i = 0; i <= pages; i++) {
			int end = _start + _interval;

			if (end > _total) {
				end = _total;
			}

			performIntervalActions(_start, end);
		}
	}

	public void setPerformIntervalActionMethod(
		PerformIntervalActionMethod performIntervalActionMethod) {

		_performIntervalActionMethod = performIntervalActionMethod;
	}

	public interface PerformIntervalActionMethod {

		public void performIntervalAction(int start, int end)
			throws PortalException;

	}

	protected void performIntervalActions(int start, int end)
		throws PortalException {

		if (_performIntervalActionMethod != null) {
			_performIntervalActionMethod.performIntervalAction(start, end);
		}
	}

	private final int _interval;
	private PerformIntervalActionMethod _performIntervalActionMethod;
	private int _start;
	private final int _total;

}