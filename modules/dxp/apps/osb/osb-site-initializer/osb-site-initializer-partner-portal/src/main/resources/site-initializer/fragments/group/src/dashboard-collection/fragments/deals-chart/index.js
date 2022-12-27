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
			await fetch('/o/c/opportunitysfs', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					setOpportunities(data?.items);
				})
				.catch(() => {
					Liferay.Util.openToast({
						message: 'An unexpected error occured.',
						type: 'danger',
					});
				});
		};
		const getLeads = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			await fetch('/o/c/leadsfs', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					setLeads(data?.items);
				})
				.catch(() => {
					Liferay.Util.openToast({
						message: 'An unexpected error occured.',
						type: 'danger',
					});
				});
		};
		getOpportunities();
		getLeads();
	}, []);

	const getFilteredDeals = useMemo(() => {
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

	const getDealsByQuarter = useMemo(() => {
		const quarters = ['q1', 'q2', 'q3', 'q4'];

		const getQuarter = (quarter) => {
			return (item) => {
				const month = item?.dateCreated?.slice(5, 7);

				switch (quarter) {
					case 'q1':
						return (
							month === '01' || month === '02' || month === '03'
						);
					case 'q2':
						return (
							month === '04' || month === '05' || month === '06'
						);
					case 'q3':
						return (
							month === '07' || month === '08' || month === '09'
						);
					case 'q4':
						return (
							month === '10' || month === '11' || month === '12'
						);
					default:
				}
			};
		};

		return quarters.map((quarter) => {
			return {
				quarter: {
					approved: getFilteredDeals.approvedDeals?.filter(
						getQuarter(quarter)
					).length,
					closedWon: getFilteredDeals.closedWonDeals?.filter(
						getQuarter(quarter)
					).length,
					rejected: getFilteredDeals.rejectedDeals?.filter(
						getQuarter(quarter)
					).length,
					submited: getFilteredDeals.submitedDeals?.filter(
						getQuarter(quarter)
					).length,
				},
			};
		});
	}, [getFilteredDeals]);

	const approvedAmount = getDealsByQuarter?.map((item) => {
		return item?.quarter?.approved;
	});
	const closedWonAmount = getDealsByQuarter?.map((item) => {
		return item?.quarter?.closedWon;
	});
	const rejectedAmount = getDealsByQuarter?.map((item) => {
		return item?.quarter?.rejected;
	});
	const submitedAmount = getDealsByQuarter?.map((item) => {
		return item?.quarter?.submited;
	});

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
				['submited', ...submitedAmount],
				['approved', ...approvedAmount],
				['rejected', ...rejectedAmount],
				['closedwon', ...closedWonAmount],
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
				approvedAmount ||
				closedWonAmount ||
				rejectedAmount ||
				submitedAmount
			).includes(undefined) && (
				<ClayLoadingIndicator className="mb-10" size="md" />
			)}

			{!approvedAmount.includes(undefined) && (
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
