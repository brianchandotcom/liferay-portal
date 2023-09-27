/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import React from 'react';

import './index.css';

const BarChart = ({
	barRatio = 0.2,
	barWidth = 20,
	colors,
	dataColumns,
	format = false,
	height = 100,
	labelColumns,
	labelRef,
	showLegend = false,
	showTooltip = false,
	titleTotal = true,
	totalSum,
	width = 300,
}) => {
	return (
		<div className="align-items-center bar-chart d-flex justify-content-between mt-2">
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
						ratio: barRatio,
					},
					width: {
						data: barWidth,
					},
				}}
				data={{
					color(color, d) {
						return colors[d.index];
					},
					columns: [labelColumns, dataColumns],
					labels: {
						colors: '#272833',
						format: {
							data: (value) => {
								if (format) {
									return new Intl.NumberFormat('en-us', {
										currency: 'USD',
										maximumFractionDigits: 0,
										minimumFractionDigits: 0,
										style: 'currency',
									}).format(value);
								}

								return value;
							},
						},
						position: {
							y: -1,
						},
					},
					type: 'bar',
					x: 'x',
				}}
				grid={{
					x: {
						show: false,
					},
					y: {
						show: false,
					},
				}}
				legend={{
					show: showLegend,
				}}
				ref={labelRef}
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
