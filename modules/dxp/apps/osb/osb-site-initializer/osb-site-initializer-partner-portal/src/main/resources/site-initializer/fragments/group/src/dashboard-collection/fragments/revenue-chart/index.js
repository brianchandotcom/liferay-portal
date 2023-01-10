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

import React, {useEffect, useState} from 'react';

import Container from '../../common/components/container';
import DonutChart from '../../common/components/donut-chart';
import getRevenueChartColumns from '../../common/utils/getRevenueChartColumns';

const colors = {
	'Growth Revenue': '#000239',
	'Renewal Revenue': '#83B6FE',
};

export default function () {
	const [titleChart, setTitleChart] = useState('');
	const [valueChart, setValueChart] = useState('');
	const [columnsRevenueChart, setColumnsRevenueChart] = useState([]);
	const [loading, setLoading] = useState(false);

	const getRevenueData = async () => {
		setLoading(true);
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/opportunitysfs', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});

		if (response.ok) {
			const revenueData = await response.json();

			getRevenueChartColumns(
				revenueData,
				setTitleChart,
				setValueChart,
				setColumnsRevenueChart
			);
			setLoading(false);

			return;
		}
		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
	};

	useEffect(() => {
		getRevenueData();
	}, []);

	const chartData = {
		colors,
		columns: columnsRevenueChart,
		type: 'donut',
	};

	return (
		<Container title="Revenue">
			<DonutChart
				chartData={chartData}
				isLoading={loading}
				titleChart={titleChart}
				valueChart={valueChart}
			/>
		</Container>
	);
}
