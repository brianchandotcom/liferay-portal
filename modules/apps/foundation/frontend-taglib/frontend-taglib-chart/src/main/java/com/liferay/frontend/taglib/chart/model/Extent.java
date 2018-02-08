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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class Extent implements Iterable {

	public Extent() {
		this(0, 0, 0, 0);
	}

	public Extent(int left, int top, int right, int bottom) {
		_values.add(new ArrayList<Integer>(Arrays.asList(left, top)));
		_values.add(new ArrayList<Integer>(Arrays.asList(right, bottom)));
	}

	@Override
	public Iterator iterator() {
		return _values.iterator();
	}

	public void setBottom(int bottom) {
		List<Integer> values = _values.get(1);

		values.set(1, bottom);
	}

	public void setLeft(int left) {
		List<Integer> values = _values.get(0);

		values.set(0, left);
	}

	public void setRight(int right) {
		List<Integer> values = _values.get(1);

		values.set(0, right);
	}

	public void setTop(int top) {
		List<Integer> values = _values.get(0);

		values.set(1, top);
	}

	private List<List<Integer>> _values = new ArrayList<>();

}