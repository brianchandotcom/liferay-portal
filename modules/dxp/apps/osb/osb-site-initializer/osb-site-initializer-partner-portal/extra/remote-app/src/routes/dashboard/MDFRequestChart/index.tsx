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
import {useEffect, useState} from 'react';

import './index.css';
import Container from '../../../common/components/dashboard/components/Container';
import DonutChart from '../../../common/components/dashboard/components/DonutChart';
import {mdfChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getChartColumns from '../../../common/components/dashboard/utils/getChartColumns';
import {siteURL} from '../../../common/components/dashboard/utils/siteURL';
import {Liferay} from '../../../common/services/liferay';

const MDFRequestChart = () => {
	const [columnsMDFChart, setColumnsMDFChart] = useState([]);
	const [titleChart, setTitleChart] = useState('');
	const [valueChart, setValueChart] = useState('');
	const [currencyData, setCurrencyData] = useState('');

	const [loading, setLoading] = useState(false);

	const getMDFRequests = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch(
			`/o/c/mdfrequests?nestedFields=accountEntry,mdfReqToActs,actToBgts,mdfReqToMDFClms&nestedFieldsDepth=2&pageSize=9999&filter=mdfRequestStatus ne 'draft'`,
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (response.ok) {
			const mdfRequests = await response.json();

			const mdfCurrency = mdfRequests?.items[0]?.currency?.key;
			setCurrencyData(mdfCurrency);

			getChartColumns(
				mdfCurrency,
				mdfRequests,
				setColumnsMDFChart,
				setTitleChart,
				setValueChart
			);

			setLoading(false);

			return;
		}

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
		setLoading(false);
	};

	useEffect(() => {
		getMDFRequests();
	}, []);

	const chartData = {
		colors: mdfChartColumnColors,
		columns: columnsMDFChart,
		type: 'donut',
	};

	return (
		<Container
			className="dashboard-mdf-request-chart"
			footer={
				<>
					<ClayButton
						className="border-brand-primary-darken-1 mr-4 text-brand-primary-darken-1"
						displayType="secondary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/marketing/mdf-requests`
							)
						}
					>
						View all
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/marketing/mdf-requests/new`
							)
						}
					>
						New MDF Request
					</ClayButton>
				</>
			}
			title="Market Development Funds"
		>
			<DonutChart
				chartDataColumns={chartData}
				dataCurrency={currencyData}
				isLoading={loading}
				titleChart={titleChart}
				valueChart={valueChart}
			/>
		</Container>
	);
};

export default MDFRequestChart;
