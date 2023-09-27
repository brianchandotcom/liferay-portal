/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class Extent implements Iterable<List<Integer>> {

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

	private final List<List<Integer>> _values = new ArrayList<>();

}