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
import React, {useCallback, useEffect, useState} from 'react';

import Container from '../../common/components/container';
import {currencyFormat} from '../../common/utils';

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
	const [columnsMDFChart, setColumnsMDFChart] = useState([]);

	const getMDFRequests = async () => {
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch(
			`/o/c/mdfrequests?nestedFields=accountEntry,mdfRequestToActivities,activityToBudgets,mdfRequestToMdfClaims&nestedFieldsDepth=2&pageSize=9999`,
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		const chartColumns = [];

		if (response.ok) {
			const mdfRequests = await response.json();

			const totalMDFActivitiesAmount = mdfRequests?.items?.reduce(
				(prevValue, currValue) =>
					prevValue +
					(parseFloat(currValue.totalMDFRequestAmount) || 0),
				0
			);
			chartColumns.push(['Requested', totalMDFActivitiesAmount]);

			const mdfApprovedRequests = mdfRequests?.items?.filter(
				(request) => request.requestStatus === 'Approved'
			);
			const totalMDFApprovedRequestsAmount = mdfApprovedRequests?.reduce(
				(acc, value) => acc + parseFloat(value.totalMDFRequestAmount),
				0
			);
			chartColumns.push(['Approved', totalMDFApprovedRequestsAmount]);

			const totalClaimedRequestsAmount = mdfRequests?.items?.reduce(
				(acc, value) => acc + parseFloat(value.totalClaimedRequest),
				0
			);
			chartColumns.push(['Claimed', totalClaimedRequestsAmount]);

			const claimedRequests = mdfRequests?.items
				?.map((claim) =>
					claim.mdfRequestToMdfClaims.filter(
						(request) => request.claimStatus === 'Approved'
					)
				)
				.flat();

			const totalClaimedApprovedRequestsAmount = claimedRequests?.reduce(
				(acc, value) => acc + value?.amountClaimed || 0,
				0
			);
			chartColumns.push([
				'Claim Approved',
				totalClaimedApprovedRequestsAmount,
			]);

			const expiringSoonActivitiesDate = mdfRequests?.items
				?.map((activity) =>
					activity.mdfRequestToActivities.filter((request) =>
						Math.round(
							new Date(request.endDate).getTime() + 30 >
								new Date()
						)
					)
				)
				.flat();

			const totalExpiringSoonActivites = expiringSoonActivitiesDate?.reduce(
				(acc, value) => acc + parseFloat(value.mdfRequestAmount),
				0
			);
			chartColumns.push(['Expiring Soon', totalExpiringSoonActivites]);

			const expiredActivities = mdfRequests?.items
				?.map((activity) =>
					activity?.mdfRequestToActivities?.filter(
						(request) => new Date(request.endDate) < new Date()
					)
				)
				.flat();
			const totalExpiredActivities = expiredActivities?.reduce(
				(acc, value) => acc + parseFloat(value.mdfRequestAmount),
				0
			);
			chartColumns.push(['Expired', totalExpiredActivities]);
		}

		setColumnsMDFChart(chartColumns);
	};

	useEffect(() => {
		getMDFRequests();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const chart = {
		data: {
			colors,
			columns: columnsMDFChart,
			type: 'donut',
		},
		donut: {
			expand: false,
			label: {
				ratio: 1,
				show: false,
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

		// title: `USD ${currencyFormat(totalMDFActivitiesAmount)}\nTotal MDF`,
	};

	const legendTransformData = useCallback((newItems, colors) => {
		return newItems.map((item, index) => ({
			color: Object.entries(colors)[index][1],
			name: item[0],
			value: item[1],
		}));
	}, []);

	const legendItems = legendTransformData(columnsMDFChart, colors);

	return (
		<Container
			className="mdf-request-chart-card-height"
			footer={
				<>
					<ClayButton className="mr-4 mt-2" displayType="primary">
						New MDF Request
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
											{`$${currencyFormat(item.value)}`}
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
