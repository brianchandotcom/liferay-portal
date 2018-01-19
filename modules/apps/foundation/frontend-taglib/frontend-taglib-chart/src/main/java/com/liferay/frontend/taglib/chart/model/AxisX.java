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

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class AxisX extends ChartObject {

	public void addCategories(String...categories) {
		for (String category : categories) {
			addCategory(category);
		}
	}

	public void addCategories(Collection<String> categories) {
		for (String category : categories) {
			addCategory(category);
		}
	}

	public void addCategory(String category) {
		get("categories", ArrayList.class).add(category);
	}

	@JSON(include = false)
	public AxisXTick getTick() {
		return get("tick", AxisXTick.class);
	}

	public void setExtent(Extent extent) {
		set("extent", extent);
	}

	public void setHeight(int height) {
		set("height", height);
	}

	public void setLabel(PositionLabel positionLabel) {
		set("label", positionLabel);
	}

	public void setLabel(String label) {
		set("label", label);
	}

	public void setLocalTime(boolean localTime) {
		set("localtime", localTime);
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

	public void setShow(boolean show) {
		set("show", show);
	}

	public void setType(Type type) {
		set("type", type._value);
	}

	public enum Type {

		TIME_SERIES("timeseries"), CATEGORY("category"), INDEXED("indexed");

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}