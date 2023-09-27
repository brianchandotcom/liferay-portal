/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class Range implements Iterable<Number> {

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

	private final List<Number> _values = new ArrayList<>();

}