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
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class AxisY2Tick extends ChartObject {

	public void addValue(Number value) {
		ArrayList values = get("values", ArrayList.class);

		values.add(value);
	}

	public final void addValues(Collection<? extends Number> values) {
		for (Number value : values) {
			addValue(value);
		}
	}

	public final void addValues(Number... values) {
		for (Number value : values) {
			addValue(value);
		}
	}

	public final void setCount(int count) {
		set("count", count);
	}

	public final void setOuter(boolean outer) {
		set("outer", outer);
	}

}