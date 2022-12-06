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

import ClayButton from '@clayui/button';
import ClayChart from '@clayui/charts';
import React, {useCallback} from 'react';

import Container from '../../common/components/container';
import {currencyFormat} from '../../common/utils';

const newdata = [
	['Requested', 3000],
	['Approved', 1500],
	['Claimed', 2000],
	['Claim Approved', 1000],
	['Expiring Soon', 1000],
	['Expired', 1000],
	['Paid', 500],
];

const colors = {
	'Approved': '#003EB3',
	'Claim Approved': '#377CFF',
	'Claimed': '#0053F0',
	'Expired': '#BBD2FF',
	'Expiring Soon': '#8FB5FF',
	'Paid': '#E7EFFF',
	'Requested': '#00256C',
};

export default function () {
	const mdfRequestTotal = newdata.reduce(
		(prevValue, currValue) => prevValue + currValue[1],
		0
	);

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
			title: `USD ${currencyFormat(mdfRequestTotal)}\nTotal MDF`,
			width: 65,
		},
		legend: {show: false},
		size: {
			height: 400,
			width: 300,
		},
		tooltip: {
			show: true,
		},
	};

	const legendTransformData = useCallback((newItems, colors) => {
		return newItems.map((item, index) => ({
			color: Object.entries(colors)[index][1],
			name: item[0],
			value: item[1],
		}));
	}, []);

	const legendItems = legendTransformData(newdata, colors);

	return (
		<Container
			className="mdf-request-chart-card-height"
			footer={
				<>
					<ClayButton className="mr-4 mt-2" displayType="primary">
						New MDF TESTE
					</ClayButton>
					<ClayButton
						className="border-brand-primary-darken-1 mt-2 text-brand-primary-darken-1"
						displayType="secondary"
					>
						View all
					</ClayButton>
				</>
			}
			title="Market Development Funds"
		>
			<div className="align-items-stretch d-flex">
				<div className="d-flex px-4">
					<div className="d-flex justify-content-start mdf-request-chart">
						<ClayChart
							className="d-flex justify-content-center"
							data={chart.data}
							donut={chart.donut}
							legend={chart.legend}
							size={chart.size}
							title={chart.donut.title}
						/>
					</div>
				</div>

				<div className="d-flex flex-column justify-content-between pb-4 pl-4">
					<div className="d-flex flex-wrap h-100 justify-content-between mb-1">
						{legendItems.map((item, index) => {
							return (
								<div key={index}>
									<div className="align-items-center d-flex">
										<span
											className="mr-2 rounded-xs square-status-legend"
											style={{
												backgroundColor: item.color,
											}}
										></span>

										<div className="mr-1">{item.name}</div>

										<div className="font-weight-semi-bold">
											{`USD ${currencyFormat(
												item.value
											)}`}
										</div>
									</div>
								</div>
							);
						})}
					</div>
				</div>
			</div>
		</Container>
	);
}
