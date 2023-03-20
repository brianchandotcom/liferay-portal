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

package com.liferay.portal.search.pit;

/**
 * @author Bryan Engler
 */
public class PointInTime {

	public PointInTime(String pointInTimeId) {
		_pointInTimeId = pointInTimeId;
	}

	public String getKeepAlive() {
		return _keepAlive;
	}

	public String getPointInTimeId() {
		return _pointInTimeId;
	}

	public void setKeepAlive(String keepAlive) {
		_keepAlive = keepAlive;
	}

	private String _keepAlive;
	private final String _pointInTimeId;

}