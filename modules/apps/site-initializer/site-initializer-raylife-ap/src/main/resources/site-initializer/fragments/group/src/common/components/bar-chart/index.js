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

import ClayChart from '@clayui/charts';
import React from 'react';

import './index.css';

const BarChart = ({
	colors,
	dataColumns,
	height = 200,
	labelColumns,
	showLegend = false,
	showTooltip = false,
	titleTotal = true,
	totalSum,
	width = 300,
}) => {
	return (
		<div className="align-items-center bar-chart d-flex justify-content-between mb-3 mt-2">
			{titleTotal && (
				<div className="bar-chart-title px-4">
					<h6 className="mb-0 text-neutral-6">Total</h6>

					<h1 className="font-weight-bold">{totalSum}</h1>
				</div>
			)}

			<ClayChart
				axis={{
					x: {
						type: 'category',
					},
					y: {
						show: false,
					},
				}}
				bar={{
					radius: {
						ratio: 0.2,
					},
					width: {
						data: 20,
					},
				}}
				data={{
					color(color, d) {
						return colors[d.index];
					},
					columns: [labelColumns, dataColumns],
					labels: {
						colors: '#272833',
						position: {
							y: -10,
						},
					},
					type: 'bar',
					x: 'x',
				}}
				legend={{
					show: showLegend,
				}}
				size={{
					height,
					width,
				}}
				tooltip={{
					show: showTooltip,
				}}
			/>
		</div>
	);
};

export default BarChart;
