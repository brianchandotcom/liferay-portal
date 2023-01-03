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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useMemo, useState} from 'react';

import Container from '../../common/components/container';
import getChartValues from './utils/getChartValues';
import getQuarter from './utils/getQuarter';

const colors = {
	approved: '#8FB5FF',
	closedwon: '#002C62',
	rejected: '#FF6060',
	submited: '#E7EFFF',
};
const siteURL = Liferay.ThemeDisplay.getLayoutRelativeURL()
	.split('/')
	.slice(0, 3)
	.join('/');
export default function () {
	const [opportunities, setOpportunities] = useState();
	const [leads, setLeads] = useState();

	useEffect(() => {
		const getOpportunities = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch('/o/c/opportunitysfs', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			});
			if (response.ok) {
				const data = await response.json();
				setOpportunities(data?.items);

				return;
			}
			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};
		const getLeads = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch('/o/c/leadsfs', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			});
			if (response.ok) {
				const data = await response.json();
				setLeads(data?.items);

				return;
			}
			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};
		getOpportunities();
		getLeads();
	}, []);

	const filteredDealsByType = useMemo(() => {
		return {
			approvedDeals: opportunities?.filter(
				(item) => item.stage === 'Open'
			),
			closedWonDeals: opportunities?.filter(
				(item) => item.stage === 'Closed Won'
			),
			rejectedDeals:
				leads?.filter((item) => item.leadStatus === 'CAM rejected') ||
				opportunities?.filter((item) => item.stage === 'Rejected'),
			submitedDeals: leads?.filter(
				(item) =>
					item.leadType === 'Partner Prospect Lead (PPL)' &&
					(item.leadStatus !== 'Sales Qualified Opportunity' ||
						item.leadStatus !== 'CAM rejected')
			),
		};
	}, [leads, opportunities]);

	const filteredDealsByQuarter = useMemo(() => {
		const quarters = ['quarter1', 'quarter2', 'quarter3', 'quarter4'];

		getQuarter();

		return quarters.map((quarter) => {
			const quarterData = {
				approved: filteredDealsByType?.approvedDeals?.filter(
					getQuarter(quarter)
				).length,
				closedwon: filteredDealsByType?.closedWonDeals?.filter(
					getQuarter(quarter)
				).length,
				rejected: filteredDealsByType?.rejectedDeals?.filter(
					getQuarter(quarter)
				).length,
				submited: filteredDealsByType?.submitedDeals?.filter(
					getQuarter(quarter)
				).length,
			};

			return quarterData;
		});
	}, [
		filteredDealsByType?.approvedDeals,
		filteredDealsByType?.closedWonDeals,
		filteredDealsByType?.rejectedDeals,
		filteredDealsByType?.submitedDeals,
	]);

	const [
		approvedChartValues,
		closedWonChartValues,
		rejectedChartValues,
		submitedChartValues,
	] = getChartValues({filteredDealsByQuarter});

	const chart = {
		bar: {
			radius: {
				ratio: 0.2,
			},
			width: {
				ratio: 0.3,
			},
		},
		data: {
			colors,
			columns: [
				['submited', ...submitedChartValues],
				['approved', ...approvedChartValues],
				['rejected', ...rejectedChartValues],
				['closedwon', ...closedWonChartValues],
			],
			groups: [['submited', 'approved', 'closedwon']],
			order: 'desc',
			type: 'bar',
			types: {
				approved: 'bar',
				closedwon: 'bar',
				rejected: 'spline',
				submited: 'bar',
			},
		},
		grid: {
			y: {
				lines: [{value: 100}, {value: 200}, {value: 300}, {value: 400}],
			},
		},
	};

	return (
		<Container className="deals-chart-card-height" title="Deals">
			{(
				approvedChartValues ||
				closedWonChartValues ||
				rejectedChartValues ||
				submitedChartValues
			).includes(undefined) && (
				<ClayLoadingIndicator className="mb-10" size="md" />
			)}

			{!(
				approvedChartValues ||
				closedWonChartValues ||
				rejectedChartValues ||
				submitedChartValues
			).includes(undefined) && (
				<ClayChart
					bar={chart?.bar}
					data={chart?.data}
					grid={chart?.grid}
				/>
			)}

			<div>
				<hr className="mb-3 mt-1" />

				<div className="d-flex">
					<ClayButton
						className="border-brand-primary-darken-1 mt-2 text-brand-primary-darken-1"
						displayType="secondary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/sales/deal-registrations`
							)
						}
						type="button"
					>
						View All
					</ClayButton>

					<ClayButton
						className="btn btn-primary ml-4 mt-2"
						displayType="primary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/sales/deal-registrations/new`
							)
						}
						type="button"
					>
						New Deal
					</ClayButton>
				</div>
			</div>
		</Container>
	);
}
