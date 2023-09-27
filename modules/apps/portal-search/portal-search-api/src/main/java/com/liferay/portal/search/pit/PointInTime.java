/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.pit;

/**
 * @author Bryan Engler
 */
public class PointInTime {

	public PointInTime(String pointInTimeId) {
		_pointInTimeId = pointInTimeId;
	}

	public long getKeepAlive() {
		return _keepAlive;
	}

	public String getPointInTimeId() {
		return _pointInTimeId;
	}

	public void setKeepAlive(long keepAlive) {
		_keepAlive = keepAlive;
	}

	private long _keepAlive;
	private final String _pointInTimeId;

}