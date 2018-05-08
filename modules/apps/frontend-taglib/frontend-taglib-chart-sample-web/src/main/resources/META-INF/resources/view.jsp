<%--
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
--%>

<%@ include file="/init.jsp" %>

<style type="text/css">
	.geomap {
		margin: 10px 0 10px 0;
	}
	.geomap svg {
		width: 100%;
		height: 500px !important;
	}
</style>

<div class="container-fluid">
	<div class="row">
		<div class="col">
			<chart:area-spline
				config="<%= chartSampleDisplayContext.getAreaSplineChartConfig() %>"
				id="area-spline"
			/>
		</div>

		<div class="col">
			<chart:area-step
				config="<%= chartSampleDisplayContext.getAreaStepChartConfig() %>"
				id="area-step"
			/>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col">
			<chart:line
				config="<%= chartSampleDisplayContext.getLineChartConfig() %>"
				id="line"
			/>
		</div>

		<div class="col">
			<chart:scatter
				config="<%= chartSampleDisplayContext.getScatterChartConfig() %>"
				id="scatter"
			/>
		</div>

		<div class="col">
			<chart:spline
				config="<%= chartSampleDisplayContext.getSplineChartConfig() %>"
				id="spline"
			/>
		</div>

		<div class="col">
			<chart:step
				config="<%= chartSampleDisplayContext.getStepChartConfig() %>"
				id="step"
			/>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col">
			<chart:bar
				config="<%= chartSampleDisplayContext.getBarChartConfig() %>"
				id="bar"
			/>
		</div>

		<div class="col">
			<chart:combination
				config="<%= chartSampleDisplayContext.getCombinationChartConfig() %>"
				id="combination"
			/>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col">
			<chart:donut
				config="<%= chartSampleDisplayContext.getDonutChartConfig() %>"
				id="donut"
			/>
		</div>

		<div class="col">
			<chart:pie
				config="<%= chartSampleDisplayContext.getPieChartConfig() %>"
				id="pie"
			/>
		</div>
	</div>
</div>

<div class="container-fluid">
		<div class="row">
			<div class="col">
				<chart:gauge
					config="<%= chartSampleDisplayContext.getGaugeChartConfig() %>"
					id="gauge"
				/>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col geomap">
			<chart:geomap
				config="<%= chartSampleDisplayContext.getGeomapConfig1() %>"
				id="geomap-default-colors"
			/>
		</div>

		<div class="col geomap">
			<chart:geomap
				config="<%= chartSampleDisplayContext.getGeomapConfig2() %>"
				id="gemomap-custom-colors"
			/>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col polling-interval">
			<chart:line
				componentId="polling-interval-line-chart"
				config="<%= chartSampleDisplayContext.getPollingIntervalLineChartConfig() %>"
				id="polling-interval-line-chart"
			/>
		</div>
	</div>
</div>

<aui:script>
	Liferay.componentReady('polling-interval-line-chart').then(
		function(chart) {
			chart.data = function() {
				return Promise.resolve(
					[
						{
							data: [Math.random() * 100, Math.random() * 100, Math.random() * 100],
							id: 'data1'
						},
						{
							data: [Math.random() * 100, Math.random() * 100, Math.random() * 100],
							id: 'data2'
						}
					]
				);
			};
		}
	);
</aui:script>