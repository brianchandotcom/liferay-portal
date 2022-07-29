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
import React, {useEffect, useRef} from 'react';

import ClayIconProvider from '../../context/ClayIconProvider';

import './index.css';

const DonutChart = ({
	chartData,
	dateUntilGoal,
	goalValue,
	hasLegend = false,
	height = 190,
	salesValue,
	showLabel = false,
	showLegend = false,
	title = '',
	width = 190,
}) => {
	const chartRef = useRef();

	useEffect(() => {
		if (title) {
			const titleElement = chartRef.current.element.querySelector(
				'.bb-chart-arcs-title'
			);

			titleElement.textContent = title;
		}
	}, [title]);

	return (
		<ClayIconProvider>
			<div className="align-items-center d-flex donut-chart-container flex-wrap justify-content-center">
				<ClayChart
					data={chartData}
					donut={{
						label: {
							show: showLabel,
						},
						title: '0',
						width: 15,
					}}
					legend={{
						show: showLegend,
					}}
					ref={chartRef}
					size={{
						height,
						width,
					}}
					tooltip={{
						contents: (data) => {
							const title = Liferay.Language.get(data[0].id);
							const percent = (data[0].ratio * 100).toFixed(1);

							return `<div class="donut-chart-tooltip bg-neutral-0 d-flex font-weight-bold p-2 rounded-sm text-capitalize"><span class="d-flex mr-2 w-100 text-capitalize">${title}</span> ${percent}%</div>`;
						},
					}}
				/>

				{!hasLegend ? (
					<div className="d-flex legend-container">
						{chartData?.columns?.map((column, index) => (
							<div
								className="d-flex flex-row justify-content-between legend-content pr-1"
								key={index}
							>
								<div className="align-items-center d-flex flex-row justify-content-between mr-2">
									<div
										className="flex-shrink-0 legend-color mr-2 rounded-circle"
										style={{
											backgroundColor:
												chartData?.colors[column[0]],
										}}
									></div>

									<span className="legend-title">
										{column[2]}
									</span>
								</div>

								<span className="font-weight-bolder">
									{column[1]}
								</span>
							</div>
						))}
					</div>
				) : (
					<div className="d-flex donut-chart-legend flex-column h-100 justify-content-end ml-5 mt-5">
						<div className="donut-chart-screen font-weight-bolder h5">
							{salesValue}
						</div>

						<div className="font-weight-normal mb-2 text-neutral-8 text-paragraph-sm">
							{`Goal ${goalValue}`}
						</div>

						<div className="font-weight-bolder text-danger text-paragraph-sm">
							<ClayIcon className="mr-1" symbol="time" />

							{dateUntilGoal}
						</div>
					</div>
				)}
			</div>
		</ClayIconProvider>
	);
};

export default DonutChart;
