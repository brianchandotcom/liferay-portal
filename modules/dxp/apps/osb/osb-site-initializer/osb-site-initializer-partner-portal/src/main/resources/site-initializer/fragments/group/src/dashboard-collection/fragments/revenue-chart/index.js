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
import classNames from 'classnames';
import React, {useCallback, useMemo} from 'react';

import Container from '../../common/components/container';
import {currencyFormat, percentFormat} from '../../common/utils';

const newdata = [
	['New Business', 4453],
	['Renewal Business', 3234.8],
	['Project Revenue', 2334.5],
	['Quota Target', 1323.99],
];

const oldData = [
	['New Business', 9462.9],
	['Renewal Business', 3334.55],
	['Project Revenue', 7333.9],
	['Quota Target', 424.99],
];

const colors = {
	'New Business': '#000239',
	'Project Revenue': '#83B6FE',
	'Quota Target': '#7B61FF',
	'Renewal Business': '#FF6060',
};

export default function () {
	const legendTransformData = useCallback((newItems, oldItems, colors) => {
		return newItems.map((item, index) => ({
			color: Object.entries(colors)[index][1],
			lasMonthValue: oldItems[index][1],
			name: item[0],
			value: item[1],
		}));
	}, []);

	const legendItems = legendTransformData(newdata, oldData, colors);

	const totalRevenue = useMemo(
		() =>
			newdata.reduce(
				(previousValue, currentValue) =>
					previousValue + currentValue[1],
				0
			),
		[]
	);

	const lastMonthRevenue = useMemo(
		() =>
			oldData.reduce(
				(previousValue, currentValue) =>
					previousValue + currentValue[1],
				0
			),
		[]
	);

	const revenueComparison = percentFormat(totalRevenue, lastMonthRevenue);

	const chart = {
		data: {
			colors,
			columns: newdata,
			type: 'donut',
		},
		donut: {
			expand: false,
			label: {
				ratio: 1,
				show: true,
			},
			legend: {
				show: false,
			},
			width: 65,
		},
		legend: {show: false},
		size: {
			height: 400,
			width: 300,
		},
	};

	return (
		<Container className="revenue-chart-card-height" title="Revenue">
			<div className="d-flex px-4">
				<div className="d-flex justify-content-start">
					<ClayChart
						className="d-flex justify-content-center"
						data={chart.data}
						donut={chart.donut}
						legend={chart.legend}
						size={chart.size}
					/>
				</div>

				<div className="d-flex flex-column justify-content-between pb-4 pl-4">
					<div>
						<div className="font-weight-semi-bold">
							Total Revenue
						</div>

						<div className="font-weight-bolder">
							{`USD ${currencyFormat(totalRevenue)}`}
						</div>

						<div className="d-flex">
							<div
								className={classNames(
									'font-weight-semi-bold ',
									{
										'text-danger': revenueComparison <= 0,
										'text-success': revenueComparison > 0,
									}
								)}
							>
								{`${
									revenueComparison <= 0 ? '-' : '+'
								}${revenueComparison.toFixed(0)} %`}
							</div>

							<div className="text-neutral-6">
								&nbsp; compared to last month
							</div>
						</div>

						<div>
							<hr />
						</div>
					</div>

					<div className="d-flex flex-column h-100 justify-content-between">
						<div className="d-flex flex-wrap h-100 justify-content-between mb-1">
							{legendItems.map((item, index) => {
								return (
									<div className="col-6 p-0" key={index}>
										<div className="align-items-center d-flex">
											<span
												className="mr-2 rounded-xs square-status-legend"
												style={{
													backgroundColor: item.color,
												}}
											></span>

											<div>{item.name}</div>
										</div>

										<div className="font-weight-semi-bold">
											{`USD ${currencyFormat(
												item.value
											)}`}
										</div>

										<div className="d-flex">
											<div
												className={classNames(
													'font-weight-semi-bold ',
													{
														'text-danger':
															percentFormat(
																item.value,
																item.lasMonthValue
															) <= 0,
														'text-success':
															percentFormat(
																item.value,
																item.lasMonthValue
															) > 0,
													}
												)}
											>
												{`${
													revenueComparison <= 0
														? '-'
														: '+'
												}${percentFormat(
													item.value,
													item.lasMonthValue
												).toFixed(0)} %`}
											</div>

											<div className="text-neutral-6">
												&nbsp; compared to last month
											</div>
										</div>
									</div>
								);
							})}
						</div>
					</div>
				</div>
			</div>
		</Container>
	);
}
