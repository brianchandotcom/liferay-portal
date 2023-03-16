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
import ClayButton from '@clayui/button';
import ClayChart from '@clayui/charts';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useMemo, useState} from 'react';

import Container from '../../common/components/container';
import {dealsChartColumnColors} from '../../common/utils/constants/chartColumnsColors';
import {dealsChartStatus} from '../../common/utils/constants/dealsChartStatus';
import {siteURL} from '../../common/utils/getSiteURL';

export default function () {
	const [opportunities, setOpportunities] = useState();
	const [leads, setLeads] = useState();
	const [loading, setLoading] = useState(false);

	const getOpportunities = async () => {
		setLoading(true);
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/opportunitysfs?pageSize=200', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});
		if (response.ok) {
			const data = await response.json();
			setOpportunities(data?.items);
			setLoading(false);

			return;
		}
		setLoading(false);

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
	};

	const getLeads = async () => {
		setLoading(true);
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/leadsfs?pageSize=200', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});
		if (response.ok) {
			const data = await response.json();
			setLeads(data?.items);
			setLoading(false);

			return;
		}
		setLoading(false);

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
	};

	useEffect(() => {
		getOpportunities();
		getLeads();
	}, []);

	const QUARTER_1_INDEX = 0;
	const QUARTER_2_INDEX = 1;
	const QUARTER_3_INDEX = 2;
	const QUARTER_4_INDEX = 3;

	const getChartQuarterCount = (values, dateCreated) => {
		const quarter = Math.ceil((new Date(dateCreated).getMonth() + 1) / 3);

		if (quarter === 1) {
			values[QUARTER_1_INDEX]++;
		}
		if (quarter === 2) {
			values[QUARTER_2_INDEX]++;
		}
		if (quarter === 3) {
			values[QUARTER_3_INDEX]++;
		}
		if (quarter === 4) {
			values[QUARTER_4_INDEX]++;
		}

		return values;
	};

	const isNotOpportunity = (opportunity) => {
		const stagesToSkip = [
			dealsChartStatus.STAGE_CLOSEDLOST,
			dealsChartStatus.STAGE_CLOSEDWON,
			dealsChartStatus.STAGE_DISQUALIFIED,
			dealsChartStatus.STAGE_REJECTED,
			dealsChartStatus.STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY,
		];

		return stagesToSkip.includes(opportunity.stage);
	};

	const opportunitiesChartValues = useMemo(() => {
		const INITIAL_OPPORTUNITIES_CHART_VALUES = {
			approved: [0, 0, 0, 0],
			closedWon: [0, 0, 0, 0],
			rejected: [0, 0, 0, 0],
		};

		return opportunities?.reduce(
			(accumulatedChartValues, currentOpportunity) => {
				if (!isNotOpportunity(currentOpportunity)) {
					accumulatedChartValues.approved = getChartQuarterCount(
						accumulatedChartValues.approved,
						currentOpportunity.dateCreated
					);
				}

				if (
					currentOpportunity.stage ===
					dealsChartStatus.STAGE_CLOSEDWON
				) {
					accumulatedChartValues.closedWon = getChartQuarterCount(
						accumulatedChartValues.closedWon,
						currentOpportunity.dateCreated
					);
				}
				if (
					currentOpportunity.stage === dealsChartStatus.STAGE_REJECTED
				) {
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
			if (item.leadStatus === dealsChartStatus.STATUS_CAMREJECTED) {
				accumulatedChartValues.rejected = getChartQuarterCount(
					accumulatedChartValues.rejected,
					item.dateCreated
				);
			}
			if (
				item.leadType ===
					dealsChartStatus.TYPE_PARTNER_QUALIFIED_LEAD &&
				(item.leadStatus !==
					dealsChartStatus.STATUS_SALES_QUALIFIED_OPPORTUNITY ||
					item.leadStatus !== dealsChartStatus.STATUS_CAMREJECTED)
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
			opportunitiesChartValues?.rejected?.map(
				(chartValue, index) =>
					chartValue + leadsChartValues?.rejected[index]
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
				colors: dealsChartColumnColors,
				columns: [
					['x', '1', '2', '3', '4'],
					['Submitted', ...leadsChartValues.submitted],
					['Approved', ...opportunitiesChartValues.approved],
					['Rejected', ...totalRejectedChartValues],
					['Closed Won', ...opportunitiesChartValues.closedWon],
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
				x: 'x',
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
			{loading && !(opportunitiesChartValues && leadsChartValues) && (
				<ClayLoadingIndicator className="mb-10 mt-9" size="md" />
			)}

			{!loading && !(opportunitiesChartValues || leadsChartValues) && (
				<ClayAlert
					className="mx-auto w-50"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			)}

			{opportunitiesChartValues && leadsChartValues && getChart()}
		</Container>
	);
}
