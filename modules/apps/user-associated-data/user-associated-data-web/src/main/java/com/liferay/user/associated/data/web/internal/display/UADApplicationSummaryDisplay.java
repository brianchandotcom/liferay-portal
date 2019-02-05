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

package com.liferay.user.associated.data.web.internal.display;

/**
 * @author Drew Brokke
 */
public class UADApplicationSummaryDisplay {

	public String getApplicationKey() {
		return _applicationKey;
	}

	public int getCount() {
		return _count;
	}

	public boolean hasItems() {
		if (getCount() > 0) {
			return true;
		}

		return false;
	}

	public void setApplicationKey(String applicationKey) {
		_applicationKey = applicationKey;
	}

	public void setCount(int count) {
		_count = count;
	}

	private String _applicationKey;
	private int _count;

}