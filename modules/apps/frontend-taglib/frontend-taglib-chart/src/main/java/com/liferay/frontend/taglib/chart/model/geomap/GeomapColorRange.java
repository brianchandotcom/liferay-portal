/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model.geomap;

import com.liferay.frontend.taglib.chart.model.ChartObject;

/**
 * @author Julien Castelain
 */
public class GeomapColorRange extends ChartObject {

	public String getMax() {
		return get("max", String.class);
	}

	public String getMin() {
		return get("min", String.class);
	}

	public void setMax(String max) {
		set("max", max);
	}

	public void setMin(String min) {
		set("min", min);
	}

}