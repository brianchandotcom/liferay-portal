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

import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class SingleValueColumn extends Column {

	public SingleValueColumn(String id) {
		super(id);
	}

	public SingleValueColumn(String id, Number value) {
		super(id);

		setValue(value);
	}

	public void setValue(Number value) {
		List<Number> data = getData();

		if (data.isEmpty()) {
			data.add(value);
		}
		else {
			data.set(0, value);
		}
	}

}