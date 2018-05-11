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
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public abstract class Column extends ChartObject {

	public Column(String id) {
		setId(id);
	}

	public void setId(String id) {
		set("id", id);
	}

	public void setName(String name) {
		set("name", name);
	}

	@JSON(include = false)
	protected <T> List<T> getData() {
		return get("data", ArrayList.class);
	}

}