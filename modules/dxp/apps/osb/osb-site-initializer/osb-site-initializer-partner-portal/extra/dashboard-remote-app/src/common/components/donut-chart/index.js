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

import ClayAlert from '@clayui/alert';
import ClayChart from '@clayui/charts';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useCallback, useMemo} from 'react';

import {currencyFormat} from '../../utils';

const DonutChart = ({
	chartDataColumns,
	dataCurrency,
	hasLegend = false,
	height = 400,
	isLoading,
	LegendElement = () => null,
	showLabel = false,
	showLegend = false,
	titleChart = '',
	valueChart,
	width = 300,
}) => {
	const legendTransformData = useCallback((newItems, colors) => {
		return newItems.map((item, index) => ({
			color: Object.entries(colors)[index][1],
			name: item[0],
			value: item[1],
		}));
	}, []);

	const hasChartData = useMemo(
		() => chartDataColumns.columns.filter((column) => column[1]).length,
		[chartDataColumns.columns]
	);

	const legendItems = legendTransformData(
		chartDataColumns.columns,
		chartDataColumns.colors
	);

	const buildChart = () => {
		if (isLoading) {
			return <ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!hasChartData && !isLoading) {
			return (
				<ClayAlert
					className="mb-10 mt-9 text-center w-50"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			);
		}

		return (
			<>
				<span className="text-nowrap">
					{titleChart} <b>{valueChart}</b>
				</span>

				<div className="d-flex">
					<div className="d-flex flex-column flex-sm-row justify-content-start">
						<>
							<ClayChart
								data={chartDataColumns}
								donut={{
									label: {show: showLabel},
									title: ' ',
									width: 35,
								}}
								legend={{show: showLegend}}
								size={{height, width}}
								tooltip={{
									contents: (data) => {
										const chartColumnsData = chartDataColumns.columns.find(
											([key]) => key === data[0].id
										);

										if (titleChart === 'Total MDF') {
											return `<div class="bg-neutral-0 d-flex flex-column rounded-sm">
											<span class="font-weight-light w-100 text-primary">
											${chartColumnsData[0]}</span>
											<span class="font-weight-light text-primary ">${
												chartColumnsData[2]
											} Activities</span>
											<span class="text-weight-bold text-primary">Total ${currencyFormat(
												chartColumnsData[1],
												dataCurrency
											)}</span>
											</div>`;
										}

										return `<div class="bg-neutral-0 d-flex flex-column rounded-sm">
											<span class="font-weight-light w-100 text-primary">
											${chartColumnsData[0]}</span>
											<span class="text-weight-bold text-primary">Total ${currencyFormat(
												chartColumnsData[1],
												dataCurrency
											)}</span>
											</div>`;
									},
								}}
							/>

							<LegendElement />

							{!hasLegend && (
								<div className="d-flex flex-column justify-content-around pb-4 pl-4">
									<div className="d-flex flex-column flex-wrap h-100 justify-content-center mb-1">
										{legendItems?.map((item, index) => {
											return (
												<div key={index}>
													<div className="align-items-center d-flex mb-4">
														<span
															className="mr-2 rounded-xs square-status-legend"
															style={{
																backgroundColor:
																	item.color,
															}}
														></span>

														<div className="d-flex flex-wrap">
															<div className="mr-1">
																{item.name}
															</div>

															<div className="font-weight-semi-bold">
																{`${currencyFormat(
																	item.value,
																	dataCurrency
																)}`}
															</div>
														</div>
													</div>
												</div>
											);
										})}
									</div>
								</div>
							)}
						</>
					</div>
				</div>
			</>
		);
	};

	return (
		<div className="align-items-center d-flex flex-column justify-content-center">
			{buildChart()}
		</div>
	);
};

export default DonutChart;
