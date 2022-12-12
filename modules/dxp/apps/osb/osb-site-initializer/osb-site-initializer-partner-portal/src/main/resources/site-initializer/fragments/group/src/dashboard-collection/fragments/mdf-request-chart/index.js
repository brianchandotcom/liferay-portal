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
import React, {useEffect, useState} from 'react';

import Container from '../../common/components/container';
import DonutChart from '../../common/components/donut-chart';
import getChartColumns from '../../common/utils/getChartColumns';

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

	const [titleChart, setTitleChart] = useState('');

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

		if (response.ok) {
			const mdfRequests = await response.json();

			getChartColumns(mdfRequests, setColumnsMDFChart, setTitleChart);
		}
	};

	useEffect(() => {
		getMDFRequests();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const chartData = {
		colors,
		columns: columnsMDFChart,
		type: 'donut',
	};

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
			<DonutChart chartData={chartData} titleChart={titleChart} />
		</Container>
	);
}
