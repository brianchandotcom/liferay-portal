/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.lang;

/**
 * @author Julius Lee
 */
public class StopWatch {

	public long getTime() {
		if (_startTime == -1) {
			return 0;
		}
		else if (_stopTime == -1) {
			return System.currentTimeMillis() - _startTime;
		}

		return _stopTime - _startTime;
	}

	public void start() {
		_startTime = System.currentTimeMillis();
	}

	public void stop() {
		_stopTime = System.currentTimeMillis();
	}

	private long _startTime = -1;
	private long _stopTime = -1;

}