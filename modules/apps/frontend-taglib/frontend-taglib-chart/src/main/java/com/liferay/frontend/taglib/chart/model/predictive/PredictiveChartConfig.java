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

package com.liferay.frontend.taglib.chart.model.predictive;

import com.liferay.frontend.taglib.chart.model.ChartConfig;
import com.liferay.frontend.taglib.chart.model.MixedDataColumn;

import java.util.List;

/**
 * @author Julien Castelain
 */
public class PredictiveChartConfig extends ChartConfig<MixedDataColumn> {

	public void addDataColumn(MixedDataColumn column) {
		addColumn(column);
	}

	public String getAxisXTickFormat() {
		return get("axisXTickFormat", String.class);
	}

	public String getPredictionDate() {
		return get("predictionDate", String.class);
	}

	public List<String> getTimeseries() {
		return get("timeseries", List.class);
	}

	public void setAxisXTickFormat(String axisXTickFormat) {
		set("axisXTickFormat", axisXTickFormat);
	}

	public void setPredictionDate(String predictionDate) {
		set("predictionDate", predictionDate);
	}

	public void setTimeseries(List<String> timeseries) {
		set("timeseries", timeseries);
	}

}