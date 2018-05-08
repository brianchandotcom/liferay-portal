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