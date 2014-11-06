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

package com.liferay.counter.model;

import com.liferay.portal.kernel.concurrent.CompeteLatch;

/**
 * @author Harry Mark
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class CounterRegister {

	public CounterRegister(
		String name, CounterHolder counterHolder, int rangeSize) {

		_name = name;
		_counterHolder = counterHolder;
		_rangeSize = rangeSize;

		_competeLatch = new CompeteLatch();
	}

	public CounterRegister(
		String name, long rangeMin, long rangeMax, int rangeSize) {

		this(name, new CounterHolder(rangeMin, rangeMax), rangeSize);
	}

	public CompeteLatch getCompeteLatch() {
		return _competeLatch;
	}

	public CounterHolder getCounterHolder() {
		return _counterHolder;
	}

	public String getName() {
		return _name;
	}

	public int getRangeSize() {
		return _rangeSize;
	}

	public void setCounterHolder(CounterHolder holder) {
		_counterHolder = holder;
	}

	public void setName(String name) {
		_name = name;
	}

	private final CompeteLatch _competeLatch;
	private volatile CounterHolder _counterHolder;
	private String _name;
	private final int _rangeSize;

}