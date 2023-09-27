/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import React from 'react';

import ClayIconProvider from '../../../common/context/ClayIconProvider';

import './index.css';

const LineChart = ({
	axispaddingleft = 0.1,
	axispaddingright = 0.5,
	chartData,
	hasPersonalizedTooltip = false,
	height = 150,
	patternColor = ['#4BC286'],
	pointRadius = 1.5,
	showaxisx = false,
	showaxisy = false,
	width = 200,
	LegendElement = () => null,
}) => {
	return (
		<ClayIconProvider>
			<div className="align-items-center d-flex flex-grow-1 flex-wrap justify-content-center line-chart-container mt-3 py-1">
				{chartData && (
					<ClayChart
						axis={{
							x: {
								padding: {
									left: axispaddingleft,
									right: axispaddingright,
								},
								show: showaxisx,
							},
							y: {
								show: showaxisy,
							},
						}}
						color={{
							pattern: patternColor,
						}}
						data={chartData}
						grid={{
							x: {
								show: false,
							},
							y: {
								show: false,
							},
						}}
						legend={{
							show: false,
						}}
						line={{
							classes: ['line-class-data'],
						}}
						point={{
							r: pointRadius,
						}}
						size={{
							height,
							width,
						}}
						tooltip={{
							contents: (data) => {
								const title = Liferay.Language.get(data[0].id);

								const value = data[0].value;

								const formatedValue = hasPersonalizedTooltip
									? value
									: `$${value.toFixed(2)}`;

								return `<div class="line-chart-tooltip w-100 bg-neutral-0 d-flex font-weight-bold rounded-sm p-1"><span class="d-flex text-nowrap font-weight-normal mr-1 w-100">${title}</span> ${formatedValue}</div>`;
							},
						}}
					/>
				)}

				<LegendElement />
			</div>
		</ClayIconProvider>
	);
};

export default LineChart;
