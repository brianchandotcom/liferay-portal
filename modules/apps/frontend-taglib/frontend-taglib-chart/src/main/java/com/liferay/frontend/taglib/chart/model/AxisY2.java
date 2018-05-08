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

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Iván Zaera Avellón
 */
public class AxisY2 extends ChartObject {

	@JSON(include = false)
	public AxisY2Tick getAxis2Tick() {
		return get("tick", AxisY2Tick.class);
	}

	public void setCenter(Number center) {
		set("center", center);
	}

	public void setDefaultRange(Range defaultRange) {
		set("default", defaultRange);
	}

	public void setInner(boolean inner) {
		set("inner", inner);
	}

	public void setInverted(boolean inverted) {
		set("inverted", inverted);
	}

	public void setLabel(String label) {
		set("label", label);
	}

	public void setMax(Number max) {
		set("max", max);
	}

	public void setMin(Number min) {
		set("min", min);
	}

	public void setPadding(Padding padding) {
		set("padding", padding);
	}

	public void setPositionLabel(PositionLabel positionLabel) {
		set("label", positionLabel);
	}

	public void setShow(boolean show) {
		set("show", show);
	}

}