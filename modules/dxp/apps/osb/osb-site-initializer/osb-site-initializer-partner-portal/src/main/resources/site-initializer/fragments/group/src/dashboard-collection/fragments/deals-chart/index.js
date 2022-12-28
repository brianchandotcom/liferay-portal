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

	const getDealsByType = useMemo(() => {
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
						'CAM rejected')
			),
		};
	}, [leads, opportunities]);

	const getFilteredDealsByQuarter = useMemo(() => {
		const q1 = (item) =>
			item?.dateCreated?.slice(5, 7) === '01' ||
			item?.dateCreated?.slice(5, 7) === '02' ||
			item?.dateCreated?.slice(5, 7) === '03';
		const q2 = (item) =>
			item?.dateCreated?.slice(5, 7) === '04' ||
			item?.dateCreated?.slice(5, 7) === '05' ||
			item?.dateCreated?.slice(5, 7) === '06';
		const q3 = (item) =>
			item?.dateCreated?.slice(5, 7) === '07' ||
			item?.dateCreated?.slice(5, 7) === '08' ||
			item?.dateCreated?.slice(5, 7) === '09';
		const q4 = (item) =>
			item?.dateCreated?.slice(5, 7) === '10' ||
			item?.dateCreated?.slice(5, 7) === '11' ||
			item?.dateCreated?.slice(5, 7) === '12';

		return {
			q1: {
				approved: getDealsByType?.approvedDeals?.filter(q1).length,
				closedwon: getDealsByType?.closedWonDeals?.filter(q1).length,
				rejected: getDealsByType?.rejectedDeals?.filter(q1).length,
				submited: getDealsByType?.submitedDeals?.filter(q1).length,
			},
			q2: {
				approved: getDealsByType?.approvedDeals?.filter(q2).length,
				closedwon: getDealsByType?.closedWonDeals?.filter(q2).length,
				rejected: getDealsByType?.rejectedDeals?.filter(q2).length,
				submited: getDealsByType?.submitedDeals?.filter(q2).length,
			},
			q3: {
				approved: getDealsByType?.approvedDeals?.filter(q3).length,
				closedwon: getDealsByType?.closedWonDeals?.filter(q3).length,
				rejected: getDealsByType?.rejectedDeals?.filter(q3).length,
				submited: getDealsByType?.submitedDeals?.filter(q3).length,
			},
			q4: {
				approved: getDealsByType?.approvedDeals?.filter(q4).length,
				closedwon: getDealsByType?.closedWonDeals?.filter(q4).length,
				rejected: getDealsByType?.rejectedDeals?.filter(q4).length,
				submited: getDealsByType?.submitedDeals?.filter(q4).length,
			},
		};
	}, [
		getDealsByType?.approvedDeals,
		getDealsByType?.closedWonDeals,
		getDealsByType?.rejectedDeals,
		getDealsByType?.submitedDeals,
	]);

	const approvedChartValues = [
		getFilteredDealsByQuarter?.q1.approved,
		getFilteredDealsByQuarter?.q2.approved,
		getFilteredDealsByQuarter?.q3.approved,
		getFilteredDealsByQuarter?.q4.approved,
	];
	const closedWonChartValues = [
		getFilteredDealsByQuarter?.q1.closedwon,
		getFilteredDealsByQuarter?.q2.closedwon,
		getFilteredDealsByQuarter?.q3.closedwon,
		getFilteredDealsByQuarter?.q4.closedwon,
	];
	const rejectedChartValues = [
		getFilteredDealsByQuarter?.q1.rejected,
		getFilteredDealsByQuarter?.q2.rejected,
		getFilteredDealsByQuarter?.q3.rejected,
		getFilteredDealsByQuarter?.q4.rejected,
	];
	const submitedChartValues = [
		getFilteredDealsByQuarter?.q1.submited,
		getFilteredDealsByQuarter?.q2.submited,
		getFilteredDealsByQuarter?.q3.submited,
		getFilteredDealsByQuarter?.q4.submited,
	];

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
