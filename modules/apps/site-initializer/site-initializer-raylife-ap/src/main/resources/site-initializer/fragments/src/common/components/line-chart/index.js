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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import ClayIconProvider from '../../../common/context/ClayIconProvider';

import './index.css';

const LineChart = ({
	axispaddingleft = 0.1,
	axispaddingright = 0.5,
	chartData,
	percentual,
	value,
	getDataDate,
	getPeriod,
	height = 150,
	patternColor = ['#4BC286'],
	pointRadius = 1.5,
	showaxisx = false,
	showaxisy = false,
	width = 200,
}) => {
	return (
		<ClayIconProvider>
			<div className="align-items-center d-flex flex-wrap justify-content-center line-chart-container mt-3 py-1">
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

								const value = data[0].value.toFixed(1);

								return `<div class="line-chart-tooltip w-100 bg-neutral-0 d-flex font-weight-bold rounded-sm p-2"><span class="d-flex font-weight-normal mr-2 w-100">${title}</span> $${value}</div>`;
							},
						}}
					/>
				)}

				<div className="d-flex flex-column h-100 justify-content-end line-chart-legend mt-5">
					<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
						{getDataDate}
					</div>

					<div className="font-weight-bolder h5 line-chart-screen mb-3">
						{new Intl.NumberFormat('en-US', {
							currency: 'USD',
							style: 'currency',
						}).format(value)}
					</div>

					<div
						className={classNames(
							'text-paragraph-sm font-weight-bolder',
							{
								'line-chart-icon-success-color':
									percentual >= 0,
								'text-danger': percentual < 0,
							}
						)}
					>
						{percentual > 0 && <ClayIcon symbol="caret-top" />}

						{percentual === 0 && <ClayIcon symbol="hr" />}

						{percentual < 0 && <ClayIcon symbol="caret-bottom" />}

						{getPeriod === 1
							? `${percentual}% MoM`
							: `${percentual}% YoY`}
					</div>
				</div>
			</div>
		</ClayIconProvider>
	);
};

export default LineChart;
