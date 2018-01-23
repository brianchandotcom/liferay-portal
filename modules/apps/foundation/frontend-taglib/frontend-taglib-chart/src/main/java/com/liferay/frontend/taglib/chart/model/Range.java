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

package com.liferay.frontend.taglib.chart.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class Range implements Iterable {

	public Range() {
		this(0, 0);
	}

	public Range(Number start, Number end) {
		_values.add(start);
		_values.add(end);
	}

	@Override
	public Iterator iterator() {
		return _values.iterator();
	}

	public void setEnd(Number end) {
		_values.set(1, end);
	}

	public void setStart(Number start) {
		_values.set(0, start);
	}

	private List<Number> _values = new ArrayList<>();

}