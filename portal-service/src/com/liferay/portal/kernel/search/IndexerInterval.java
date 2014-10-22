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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jonathan McCann
 */
public class IndexerInterval {

	public static final int DEFAULT_INTERVAL = 100;

	public void incrementStart() {
		_start++;
	}

	public void performInterval() throws PortalException {
		int pages = _count / DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int end = _start + DEFAULT_INTERVAL;

			performIntervalAction(_start, end);
		}
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setPerformActionMethod(
		PerformIntervalActionMethod performActionMethod) {

		_performIntervalActionMethod = performActionMethod;
	}

	public interface PerformIntervalActionMethod {

		public void performIntervalAction(int start, int end)
			throws PortalException;

	}

	protected void performIntervalAction(int start, int end)
		throws PortalException {

		if (_performIntervalActionMethod != null) {
			_performIntervalActionMethod.performIntervalAction(start, end);
		}
	}

	private int _count;
	private PerformIntervalActionMethod _performIntervalActionMethod;
	private int _start;

}