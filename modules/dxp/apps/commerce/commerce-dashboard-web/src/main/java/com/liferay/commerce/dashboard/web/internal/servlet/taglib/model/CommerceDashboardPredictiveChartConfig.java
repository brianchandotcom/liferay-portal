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

package com.liferay.commerce.dashboard.web.internal.servlet.taglib.model;

import com.liferay.frontend.taglib.chart.model.predictive.PredictiveChartConfig;

import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDashboardPredictiveChartConfig
	extends PredictiveChartConfig {

	public Map<String, String> getColors() {
		return get("colors", Map.class);
	}

	public void setColors(Map<String, String> colors) {
		set("colors", colors);
	}

}