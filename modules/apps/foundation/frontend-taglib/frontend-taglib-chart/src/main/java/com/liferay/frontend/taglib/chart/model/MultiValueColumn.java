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

import java.util.Collection;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class MultiValueColumn extends Column {

	public MultiValueColumn(String id) {
		super(id);
	}

	public MultiValueColumn(String id, Collection<? extends Number> values) {
		super(id);

		addValues(values);
	}

	public MultiValueColumn(String id, Number... values) {
		super(id);

		addValues(values);
	}

	public void addValue(Number value) {
		List<Number> data = getData();

		data.add(value);
	}

	public void addValues(Collection<? extends Number> values) {
		for (Number value : values) {
			addValue(value);
		}
	}

	public void addValues(Number... values) {
		for (Number value : values) {
			addValue(value);
		}
	}

}