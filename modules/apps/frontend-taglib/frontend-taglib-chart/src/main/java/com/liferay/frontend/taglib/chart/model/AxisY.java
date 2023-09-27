/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Iván Zaera Avellón
 */
public class AxisY extends ChartObject {

	@JSON(include = false)
	public AxisYTick getAxisYTick() {
		return get("tick", AxisYTick.class);
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

	public void setType(Type type) {
		set("type", type._value);
	}

	public enum Type {

		CATEGORY("category"), INDEXED("indexed"), TIME_SERIES("timeseries");

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}