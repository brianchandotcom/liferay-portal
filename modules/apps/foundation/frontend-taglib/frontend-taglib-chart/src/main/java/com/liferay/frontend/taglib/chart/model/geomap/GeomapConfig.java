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

package com.liferay.frontend.taglib.chart.model.geomap;

import com.liferay.frontend.taglib.chart.model.ChartObject;
import com.liferay.petra.string.StringPool;

/**
 * @author Julien Castelain
 */
public class GeomapConfig extends ChartObject {

	public GeomapColor getColor() {
		return get("color", GeomapColor.class);
	}

	public Object getData() {
		Object data = get("data", Object.class, false);

		if (data == null) {
			return StringPool.BLANK;
		}

		return data;
	}

	public void setColor(GeomapColor geomapColor) {
		set("color", geomapColor);
	}

	public void setDataHREF(String dataHREF) {
		Object data = get("data", Object.class, false);

		if ((data != null) && !(data instanceof String)) {
			throw new IllegalStateException(
				"Unable to set data HREF because is has been set as Object");
		}

		set("data", dataHREF);
	}

	public void setDataObject(Object dataObject) {
		Object data = get("data", Object.class, false);

		if ((data != null) && !(data instanceof Object)) {
			throw new IllegalStateException(
				"Unable to set Object data because it has been set as URL");
		}

		set("data", dataObject);
	}

}