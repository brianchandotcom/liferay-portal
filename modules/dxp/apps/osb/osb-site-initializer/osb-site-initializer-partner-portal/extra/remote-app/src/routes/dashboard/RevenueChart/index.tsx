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

import Container from '../../../common/components/dashboard/components/Container';
import DonutChart from '../../../common/components/dashboard/components/DonutChart';
import {revenueChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getRevenueChartColumns from '../../../common/components/dashboard/utils/getRevenueChartColumns';
import {Liferay} from '../../../common/services/liferay';

export default function () {
	const [titleChart, setTitleChart] = useState('');
	const [valueChart, setValueChart] = useState('');
	const [columnsRevenueChart, setColumnsRevenueChart] = useState([]);
	const [loading, setLoading] = useState(false);
	const [currencyData, setCurrencyData] = useState('');

	const getRevenueData = async () => {
		setLoading(true);
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/opportunitysfs?&pageSize=200', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});

		if (response.ok) {
			const revenueData = await response.json();

			const revenueCurrency = revenueData?.items[0]?.currency?.key;

			setCurrencyData(revenueCurrency);

			getRevenueChartColumns(
				revenueCurrency,
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
		colors: revenueChartColumnColors,
		columns: columnsRevenueChart,
		type: 'donut',
	};

	return (
		<Container className="dashboard-mdf-revenue-chart" title="Revenue">
			<DonutChart
				chartDataColumns={chartData}
				dataCurrency={currencyData}
				isLoading={loading}
				titleChart={titleChart}
				valueChart={valueChart}
			/>
		</Container>
	);
}
