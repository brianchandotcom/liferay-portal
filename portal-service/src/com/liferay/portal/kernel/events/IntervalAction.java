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
 */
public class IntervalAction {

	public static final int DEFAULT_INTERVAL = 100;

	public void incrementStart() {
		_start++;
	}

	public void performActions() throws PortalException {
		int pages = _total / DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int end = _start + DEFAULT_INTERVAL;

			performActions(_start, end);
		}
	}

	public void setPerformActionMethod(
		PerformIntervalActionMethod performActionMethod) {

		_performIntervalActionMethod = performActionMethod;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public interface PerformIntervalActionMethod {

		public void performAction(int start, int end) throws PortalException;

	}

	protected void performActions(int start, int end) throws PortalException {
		if (_performIntervalActionMethod != null) {
			_performIntervalActionMethod.performAction(start, end);
		}
	}

	private PerformIntervalActionMethod _performIntervalActionMethod;
	private int _start;
	private int _total;

}