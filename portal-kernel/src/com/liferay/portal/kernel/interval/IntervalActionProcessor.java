/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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