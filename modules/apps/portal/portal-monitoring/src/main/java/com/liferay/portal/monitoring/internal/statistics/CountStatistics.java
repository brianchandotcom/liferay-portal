/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics;

/**
 * @author Rajesh Thiagarajan
 * @author Brian Wing Shun Chan
 */
public class CountStatistics extends BaseStatistics {

	public CountStatistics(String name) {
		super(name);
	}

	public void decrementCount() {
		_count--;

		setLastSampleTime(System.currentTimeMillis());
	}

	public long getCount() {
		return _count;
	}

	public void incrementCount() {
		_count++;

		setLastSampleTime(System.currentTimeMillis());
	}

	@Override
	public void reset() {
		super.reset();

		_count = 0;
	}

	public void setCount(long count) {
		_count = count;

		setLastSampleTime(System.currentTimeMillis());
	}

	private long _count;

}