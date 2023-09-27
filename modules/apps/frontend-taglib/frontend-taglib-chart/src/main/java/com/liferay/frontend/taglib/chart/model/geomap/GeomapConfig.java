/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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