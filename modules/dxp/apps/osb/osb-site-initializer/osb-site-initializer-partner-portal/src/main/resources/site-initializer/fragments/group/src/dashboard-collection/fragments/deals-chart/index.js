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
import getLeadsChartValues from '../../common/utils/getLeadsChartValues';
import {getOpportunitiesChartValues} from '../../common/utils/getOpportunitiesChartValues';
import {siteURL} from '../../common/utils/getSiteURL';

export default function () {
	const [opportunities, setOpportunities] = useState([]);
	const [leads, setLeads] = useState([]);
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

			return;
		}

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});

		setLoading(false);
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

			return;
		}

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
		setLoading(false);
	};

	useEffect(() => {
		getOpportunities();
		getLeads();
	}, []);

	const opportunitiesChartValues = useMemo(() => {
		return getOpportunitiesChartValues(opportunities);
	}, [opportunities]);

	const leadsChartValues = getLeadsChartValues(leads);

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
					['Submitted', ...leadsChartValues?.submitted],
					['Approved', ...opportunitiesChartValues?.approved],
					['Rejected', ...totalRejectedChartValues],
					['Closed Won', ...opportunitiesChartValues?.closedWon],
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
		if (loading) {
			<ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!loading && !(opportunitiesChartValues || leadsChartValues)) {
			<ClayAlert
				className="mx-auto w-50"
				displayType="info"
				title="Info:"
			>
				No Data Available
			</ClayAlert>;
		}

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
			{getChart()}
		</Container>
	);
}
