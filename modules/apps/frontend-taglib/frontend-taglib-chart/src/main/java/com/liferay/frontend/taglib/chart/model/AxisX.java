/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model;

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class AxisX extends ChartObject {

	public void addCategories(Collection<String> categories) {
		for (String category : categories) {
			addCategory(category);
		}
	}

	public void addCategories(String... categories) {
		for (String category : categories) {
			addCategory(category);
		}
	}

	public void addCategory(String category) {
		List<String> categories = get("categories", ArrayList.class);

		categories.add(category);
	}

	@JSON(include = false)
	public AxisXTick getAxisXTick() {
		return get("tick", AxisXTick.class);
	}

	public void setExtent(Extent extent) {
		set("extent", extent);
	}

	public void setHeight(int height) {
		set("height", height);
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