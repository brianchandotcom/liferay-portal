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

package com.liferay.frontend.taglib.chart.sample.web.internal.display.context;

import com.liferay.frontend.taglib.chart.model.MixedDataColumn;
import com.liferay.frontend.taglib.chart.model.MultiValueColumn;
import com.liferay.frontend.taglib.chart.model.SingleValueColumn;
import com.liferay.frontend.taglib.chart.model.TypedMultiValueColumn;
import com.liferay.frontend.taglib.chart.model.area.spline.AreaSplineChartConfig;
import com.liferay.frontend.taglib.chart.model.area.step.AreaStepChartConfig;
import com.liferay.frontend.taglib.chart.model.combination.CombinationChartConfig;
import com.liferay.frontend.taglib.chart.model.gauge.GaugeChartConfig;
import com.liferay.frontend.taglib.chart.model.geomap.GeomapColor;
import com.liferay.frontend.taglib.chart.model.geomap.GeomapColorRange;
import com.liferay.frontend.taglib.chart.model.geomap.GeomapConfig;
import com.liferay.frontend.taglib.chart.model.percentage.donut.DonutChartConfig;
import com.liferay.frontend.taglib.chart.model.percentage.pie.PieChartConfig;
import com.liferay.frontend.taglib.chart.model.point.bar.BarChartConfig;
import com.liferay.frontend.taglib.chart.model.point.line.LineChartConfig;
import com.liferay.frontend.taglib.chart.model.point.scatter.ScatterChartConfig;
import com.liferay.frontend.taglib.chart.model.point.spline.SplineChartConfig;
import com.liferay.frontend.taglib.chart.model.point.step.StepChartConfig;
import com.liferay.frontend.taglib.chart.model.predictive.PredictiveChartConfig;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class ChartSampleDisplayContext {

	public ChartSampleDisplayContext(PortletRequest portletRequest) {
		_portletRequest = portletRequest;

		_initAreaSplineChartConfig();
		_initAreaStepChartConfig();
		_initBarChartConfig();
		_initCombinationChartConfig();
		_initDonutChartConfig();
		_initGaugeChartConfig();
		_initGeomapConfig();
		_initLineChartConfig();
		_initPieChartConfig();
		_initPollingIntervalLineChartConfig();
		_initPredictiveChartConfig();
		_initScatterChartConfig();
		_initSplineChartConfig();
		_initStepChartConfig();
	}

	public AreaSplineChartConfig getAreaSplineChartConfig() {
		return _areaSplineChartConfig;
	}

	public AreaStepChartConfig getAreaStepChartConfig() {
		return _areaStepChartConfig;
	}

	public BarChartConfig getBarChartConfig() {
		return _barChartConfig;
	}

	public CombinationChartConfig getCombinationChartConfig() {
		return _combinationChartConfig;
	}

	public DonutChartConfig getDonutChartConfig() {
		return _donutChartConfig;
	}

	public GaugeChartConfig getGaugeChartConfig() {
		return _gaugeChartConfig;
	}

	public GeomapConfig getGeomapConfig1() {
		return _geomapConfig1;
	}

	public GeomapConfig getGeomapConfig2() {
		return _geomapConfig2;
	}

	public LineChartConfig getLineChartConfig() {
		return _lineChartConfig;
	}

	public PieChartConfig getPieChartConfig() {
		return _pieChartConfig;
	}

	public LineChartConfig getPollingIntervalLineChartConfig() {
		return _pollingIntervalLineChartConfig;
	}

	public PredictiveChartConfig getPredictiveChartConfig() {
		return _predictiveChartConfig;
	}

	public ScatterChartConfig getScatterChartConfig() {
		return _scatterChartConfig;
	}

	public SplineChartConfig getSplineChartConfig() {
		return _splineChartConfig;
	}

	public StepChartConfig getStepChartConfig() {
		return _stepChartConfig;
	}

	private void _initAreaSplineChartConfig() {
		_areaSplineChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private void _initAreaStepChartConfig() {
		_areaStepChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private void _initBarChartConfig() {
		_barChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private void _initCombinationChartConfig() {
		_combinationChartConfig.addColumns(
			new TypedMultiValueColumn(
				"data1", TypedMultiValueColumn.Type.BAR, 30, 20, 50, 40, 60,
				50),
			new TypedMultiValueColumn(
				"data2", TypedMultiValueColumn.Type.BAR, 200, 130, 90, 240, 130,
				220),
			new TypedMultiValueColumn(
				"data3", TypedMultiValueColumn.Type.SPLINE, 300, 200, 160, 400,
				250, 250),
			new TypedMultiValueColumn(
				"data4", TypedMultiValueColumn.Type.LINE, 200, 130, 90, 240,
				130, 220),
			new TypedMultiValueColumn(
				"data5", TypedMultiValueColumn.Type.BAR, 130, 120, 150, 140,
				160, 150),
			new TypedMultiValueColumn(
				"data6", TypedMultiValueColumn.Type.AREA, 90, 70, 20, 50, 60,
				120));

		_combinationChartConfig.addGroup("data1", "data2");
	}

	private void _initDonutChartConfig() {
		_donutChartConfig.addColumns(
			new SingleValueColumn("data1", 30),
			new SingleValueColumn("data2", 70));
	}

	private void _initGaugeChartConfig() {
		_gaugeChartConfig.addColumn(new SingleValueColumn("data1", 85.4));
	}

	private void _initGeomapConfig() {
		GeomapColor geomapColor = new GeomapColor();

		GeomapColorRange geomapColorRange = new GeomapColorRange();

		geomapColorRange.setMax("#b2150a");
		geomapColorRange.setMin("#ee3e32");

		geomapColor.setGeomapColorRange(geomapColorRange);

		geomapColor.setSelected("#a9615c");
		geomapColor.setValue("name_len");

		_geomapConfig2.setColor(geomapColor);

		StringBuilder sb = new StringBuilder();

		sb.append(_portletRequest.getContextPath());
		sb.append(StringPool.SLASH);
		sb.append("geomap.geo.json");

		_geomapConfig1.setDataHREF(sb.toString());
		_geomapConfig2.setDataHREF(sb.toString());
	}

	private void _initLineChartConfig() {
		_lineChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private void _initPieChartConfig() {
		_pieChartConfig.addColumns(
			new SingleValueColumn("data1", 30),
			new SingleValueColumn("data2", 70));
	}

	private void _initPollingIntervalLineChartConfig() {
		_pollingIntervalLineChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));

		_pollingIntervalLineChartConfig.setPollingInterval(2000);
	}

	private void _initPredictiveChartConfig() {
		MixedDataColumn mixedDataColumn1 = new MixedDataColumn(
			"data1", 130, 340, 200, 500, 80, 240, 40,
			new Number[] {370, 400, 450}, new Number[] {210, 240, 270},
			new Number[] {150, 180, 210}, new Number[] {60, 90, 120},
			new Number[] {310, 340, 370});

		_predictiveChartConfig.addDataColumn(mixedDataColumn1);

		MixedDataColumn mixedDataColumn2 = new MixedDataColumn(
			"data2", 210, 160, 50, 125, 230, 110, 90,
			Arrays.asList(170, 200, 230), Arrays.asList(10, 40, 70),
			Arrays.asList(350, 380, 410), Arrays.asList(260, 290, 320),
			Arrays.asList(30, 70, 150));

		_predictiveChartConfig.addDataColumn(mixedDataColumn2);

		_predictiveChartConfig.setAxisXTickFormat("%b");
		_predictiveChartConfig.setPredictionDate("2018-07-01");

		List<String> timeseries = new ArrayList<>();

		timeseries.add("2018-01-01");
		timeseries.add("2018-02-01");
		timeseries.add("2018-03-01");
		timeseries.add("2018-04-01");
		timeseries.add("2018-05-01");
		timeseries.add("2018-06-01");
		timeseries.add("2018-07-01");
		timeseries.add("2018-08-01");
		timeseries.add("2018-09-01");
		timeseries.add("2018-10-01");
		timeseries.add("2018-11-01");
		timeseries.add("2018-12-01");

		_predictiveChartConfig.setTimeseries(timeseries);
	}

	private void _initScatterChartConfig() {
		_scatterChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private void _initSplineChartConfig() {
		_splineChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private void _initStepChartConfig() {
		_stepChartConfig.addColumns(
			new MultiValueColumn("data1", 100, 20, 30),
			new MultiValueColumn("data2", 20, 70, 100));
	}

	private AreaSplineChartConfig _areaSplineChartConfig =
		new AreaSplineChartConfig();
	private AreaStepChartConfig _areaStepChartConfig =
		new AreaStepChartConfig();
	private BarChartConfig _barChartConfig = new BarChartConfig();
	private CombinationChartConfig _combinationChartConfig =
		new CombinationChartConfig();
	private DonutChartConfig _donutChartConfig = new DonutChartConfig();
	private GaugeChartConfig _gaugeChartConfig = new GaugeChartConfig();
	private GeomapConfig _geomapConfig1 = new GeomapConfig();
	private GeomapConfig _geomapConfig2 = new GeomapConfig();
	private LineChartConfig _lineChartConfig = new LineChartConfig();
	private PieChartConfig _pieChartConfig = new PieChartConfig();
	private LineChartConfig _pollingIntervalLineChartConfig =
		new LineChartConfig();
	private final PortletRequest _portletRequest;
	private PredictiveChartConfig _predictiveChartConfig =
		new PredictiveChartConfig();
	private ScatterChartConfig _scatterChartConfig = new ScatterChartConfig();
	private SplineChartConfig _splineChartConfig = new SplineChartConfig();
	private StepChartConfig _stepChartConfig = new StepChartConfig();

}