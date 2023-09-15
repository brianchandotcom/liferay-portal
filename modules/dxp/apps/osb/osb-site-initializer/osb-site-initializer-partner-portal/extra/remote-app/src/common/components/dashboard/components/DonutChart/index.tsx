/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayChart from '@clayui/charts';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useCallback, useMemo} from 'react';

import formatCurrency from '../../utils/formatCurrency';

interface iProps {
	LegendElement?: () => null;
	chartDataColumns: any;
	dataCurrency: any;
	hasLegend?: boolean;
	height?: number;
	isLoading: boolean;
	showLabel?: boolean;
	showLegend?: boolean;
	titleChart?: string;
	valueChart: any;
	width?: number;
}

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
}: iProps) => {
	const legendTransformData = useCallback((newItems: any, colors: any) => {
		return newItems.map((item: any, index: any) => ({
			color: Object.entries(colors)[index][1],
			name: item[0],
			value: item[1],
		}));
	}, []);

	const hasChartData = useMemo(
		() =>
			chartDataColumns.columns.filter((column: any) => column[1]).length,
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
					{titleChart}

					<b>
						{formatCurrency(
							valueChart,
							dataCurrency,
							'lessPrecision'
						)}
					</b>
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
									contents: (data: any) => {
										const chartColumnsData = chartDataColumns.columns.find(
											([key]: any) => key === data[0].id
										);

										if (titleChart === 'Total MDF ') {
											return `<div class="bg-neutral-0 d-flex flex-column rounded-sm">
											<span class="font-weight-light w-100 text-primary">
											${chartColumnsData[0]}</span>
											<span class="font-weight-light text-primary ">${
												chartColumnsData[2]
											} Activities</span>
											<span class="text-weight-bold text-primary">Total ${formatCurrency(
												chartColumnsData[1],
												dataCurrency
											)}</span>
											</div>`;
										}

										return `<div class="bg-neutral-0 d-flex flex-column rounded-sm">
											<span class="font-weight-light w-100 text-primary">
											${chartColumnsData[0]}</span>
											<span class="text-weight-bold text-primary">Total ${formatCurrency(
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
										{legendItems?.map(
											(item: any, index: any) => {
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
																	{`${formatCurrency(
																		item.value,
																		dataCurrency,
																		'lessPrecision'
																	)}`}
																</div>
															</div>
														</div>
													</div>
												);
											}
										)}
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
