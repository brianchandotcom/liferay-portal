/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayChart from '@clayui/charts';
import React, {useCallback, useEffect, useRef} from 'react';

import {currencyFormat} from '../../utils';

const DonutChart = ({
	chartData,
	hasLegend = false,
	height = 400,
	LegendElement = () => null,
	showLabel = false,
	showLegend = false,
	titleChart = '',
	width = 300,
}) => {
	const chartRef = useRef();

	useEffect(() => {
		if (titleChart) {
			const titleElement = chartRef?.current?.element?.querySelector(
				'.bb-chart-arcs-title'
			);

			titleElement.textContent = titleChart;
		}
	}, [titleChart]);

	const legendTransformData = useCallback((newItems, colors) => {
		return newItems.map((item, index) => ({
			color: Object.entries(colors)[index][1],
			name: item[0],
			value: item[1],
		}));
	}, []);

	const legendItems = legendTransformData(
		chartData.columns,
		chartData.colors
	);

	return (
		<div className="align-items-stretch d-flex">
			<div className="d-flex px-4">
				<div className="d-flex justify-content-start mdf-request-chart">
					<ClayChart
						data={chartData}
						donut={{
							label: {show: showLabel},
							title: '0',
							width: 65,
						}}
						legend={{show: showLegend}}
						ref={chartRef}
						size={{height, width}}
						tooltip={{
							contents: (data) => {
								const title = data[0].id;
								const value = data[0].value;

								return `<div class="donut-chart-tooltip bg-neutral-0 d-flex font-weight-bold p-2 rounded-sm text-capitalize"><span class="d-flex mr-2 w-100 text-capitalize">${title}</span> $${currencyFormat(
									value
								)}</div>`;
							},
						}}
					/>

					<LegendElement />

					{!hasLegend && (
						<div className="d-flex flex-column justify-content-between pb-4 pl-4">
							<div className="d-flex flex-column flex-wrap h-100 justify-content-between mb-1">
								{legendItems?.map((item, index) => {
									return (
										<div key={index}>
											<div className="align-items-center d-flex">
												<span
													className="mr-2 rounded-xs square-status-legend"
													style={{
														backgroundColor:
															item.color,
													}}
												></span>

												<div className="mr-1">
													{item.name}
												</div>

												<div className="font-weight-semi-bold">
													{`$${currencyFormat(
														item.value
													)}`}
												</div>
											</div>
										</div>
									);
								})}
							</div>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default DonutChart;
