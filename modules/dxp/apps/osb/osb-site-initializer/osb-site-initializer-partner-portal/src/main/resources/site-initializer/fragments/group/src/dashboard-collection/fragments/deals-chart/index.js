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
	submitted: '#E7EFFF',
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

	const QUARTER_1_INDEX = 0;
	const QUARTER_2_INDEX = 1;
	const QUARTER_3_INDEX = 2;
	const QUARTER_4_INDEX = 3;

	const JANUARY = 1;
	const FEBRUARY = 2;
	const MARCH = 3;
	const APRIL = 4;
	const MAY = 5;
	const JUNE = 6;
	const JULY = 7;
	const AUGUST = 8;
	const SEPTEMBER = 9;
	const OCTOBER = 10;
	const NOVEMBER = 11;
	const DECEMBER = 12;

	const STAGE_OPEN = 'Open';
	const STAGE_CLOSEDWON = 'Closed Won';
	const STAGE_REJECTED = 'Rejected';
	const STATUS_CAMREJECTED = 'CAM rejected';
	const STATUS_SALES_QUALIFIED_OPPORTUNITY = 'Sales Qualified Opportunity';
	const TYPE_PARTNER_PROSPECT_LEAD = 'Partner Prospect Lead (PPL)';

	const getChartQuarterCount = (values, dateCreated) => {
		const month = new Date(dateCreated).getMonth() + 1;

		if (month === JANUARY || month === FEBRUARY || month === MARCH) {
			values[QUARTER_1_INDEX] = values[QUARTER_1_INDEX] + 1;
		}
		if (month === APRIL || month === MAY || month === JUNE) {
			values[QUARTER_2_INDEX] = values[QUARTER_2_INDEX] + 1;
		}
		if (month === JULY || month === AUGUST || month === SEPTEMBER) {
			values[QUARTER_3_INDEX] = values[QUARTER_3_INDEX] + 1;
		}
		if (month === OCTOBER || month === NOVEMBER || month === DECEMBER) {
			values[QUARTER_4_INDEX] = values[QUARTER_4_INDEX] + 1;
		}

		return values;
	};

	const opportunitiesChartValues = useMemo(() => {
		const INITIAL_OPPORTUNITIES_CHART_VALUES = {
			approved: [0, 0, 0, 0],
			closedWon: [0, 0, 0, 0],
			rejected: [0, 0, 0, 0],
		};

		return opportunities?.reduce(
			(accumulatedChartValues, currentOpportunity) => {
				if (currentOpportunity.stage === STAGE_OPEN) {
					accumulatedChartValues.approved = getChartQuarterCount(
						accumulatedChartValues.approved,
						currentOpportunity.dateCreated
					);
				}
				if (currentOpportunity.stage === STAGE_CLOSEDWON) {
					accumulatedChartValues.closedWon = getChartQuarterCount(
						accumulatedChartValues.closedWon,
						currentOpportunity.dateCreated
					);
				}
				if (currentOpportunity.stage === STAGE_REJECTED) {
					accumulatedChartValues.rejected = getChartQuarterCount(
						accumulatedChartValues.rejected,
						currentOpportunity.dateCreated
					);
				}

				return accumulatedChartValues;
			},
			INITIAL_OPPORTUNITIES_CHART_VALUES
		);
	}, [opportunities]);

	const leadsChartValues = useMemo(() => {
		const INITIAL_LEADS_CHART_VALUES = {
			rejected: [0, 0, 0, 0],
			submitted: [0, 0, 0, 0],
		};

		return leads?.reduce((accumulatedChartValues, item) => {
			if (item.leadStatus === STATUS_CAMREJECTED) {
				accumulatedChartValues.rejected = getChartQuarterCount(
					accumulatedChartValues.rejected,
					item.dateCreated
				);
			}
			if (
				item.leadType === TYPE_PARTNER_PROSPECT_LEAD &&
				(item.leadStatus !== STATUS_SALES_QUALIFIED_OPPORTUNITY ||
					item.leadStatus !== STATUS_CAMREJECTED)
			) {
				accumulatedChartValues.submitted = getChartQuarterCount(
					accumulatedChartValues.submitted,
					item.dateCreated
				);
			}

			return accumulatedChartValues;
		}, INITIAL_LEADS_CHART_VALUES);
	}, [leads]);

	const totalRejectedChartValues = useMemo(() => {
		return (
			opportunitiesChartValues?.rejected.map(
				(chartValue, index) =>
					chartValue + leadsChartValues.rejected[index]
			) || []
		);
	}, [leadsChartValues?.rejected, opportunitiesChartValues?.rejected]);

	const getChart = () => {
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
					['submitted', ...leadsChartValues.submitted],
					['approved', ...opportunitiesChartValues.approved],
					['rejected', ...totalRejectedChartValues],
					['closedwon', ...opportunitiesChartValues.closedWon],
				],
				groups: [['submitted', 'approved', 'closedwon']],
				order: 'desc',
				type: 'bar',
				types: {
					approved: 'bar',
					closedwon: 'bar',
					rejected: 'spline',
					submitted: 'bar',
				},
			},
			grid: {
				y: {
					lines: [
						{value: 100},
						{value: 200},
						{value: 300},
						{value: 400},
					],
				},
			},
		};

		return (
			<ClayChart bar={chart.bar} data={chart.data} grid={chart.grid} />
		);
	};

	return (
		<Container
			className="deals-chart-card-height"
			footer={
				<>
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
				</>
			}
			title="Deals"
		>
			{!(opportunitiesChartValues && leadsChartValues) && (
				<ClayLoadingIndicator className="mb-10 mt-9" size="md" />
			)}

			{opportunitiesChartValues && leadsChartValues && getChart()}
		</Container>
	);
}
