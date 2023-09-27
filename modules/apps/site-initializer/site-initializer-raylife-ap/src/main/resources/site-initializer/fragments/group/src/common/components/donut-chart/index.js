/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import React, {useEffect, useRef} from 'react';

import ClayIconProvider from '../../context/ClayIconProvider';

import './index.css';

const DonutChart = ({
	chartData,
	hasLegend = false,
	height = 190,
	LegendElement = () => null,
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
			<div className="align-items-center d-flex donut-chart-container flex-grow-1 flex-wrap justify-content-center">
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

				<LegendElement />

				{!hasLegend && (
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
				)}
			</div>
		</ClayIconProvider>
	);
};

export default DonutChart;
