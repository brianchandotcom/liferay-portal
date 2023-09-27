/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class AxisXTick extends ChartObject {

	public void addValue(Number value) {
		ArrayList<Number> values = get("values", ArrayList.class);

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

	public void setCentered(boolean centered) {
		set("centered", centered);
	}

	public final void setCount(int count) {
		set("count", count);
	}

	public void setCulling(boolean culling) {
		set("culling", culling);
	}

	public void setCulling(Culling culling) {
		set("culling", culling);
	}

	public void setFit(boolean fit) {
		set("fit", fit);
	}

	public void setMultiline(boolean multiline) {
		set("multiline", multiline);
	}

	public final void setOuter(boolean outer) {
		set("outer", outer);
	}

	public void setRotate(boolean rotate) {
		set("rotate", rotate);
	}

	public void setWidth(int width) {
		set("width", width);
	}

}