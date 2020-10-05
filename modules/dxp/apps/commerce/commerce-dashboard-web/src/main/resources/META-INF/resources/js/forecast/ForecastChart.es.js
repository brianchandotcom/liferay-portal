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

import ChartWrapper from '../ChartWrapper.es';
import {loadData} from '../utils/index.es';
export default function ForecastChart({
	APIBaseUrl,
	accountIds: initialAccountsIds = [],
	categoryIds = [],
	noAccountErrorMessage,
	noDataErrorMessage,
}) {
	const [loading, setLoading] = useState(true);
	const [chartData, setChartData] = useState({});
	const [accountsId, setAccountId] = useState(initialAccountsIds);
	Liferay.on('accountSelected', ({accountId}) => setAccountId([accountId]));
	function updateData() {
		const formattedAccountIds = accountsId
			.map((id) => `accountIds=${id}`)
			.join('&');
		const formattedCategoryIds = categoryIds.length
			? '&' + categoryIds.map((id) => `categoryIds=${id}`).join('&')
			: '';
		const APIUrl = `${APIBaseUrl}?${formattedAccountIds}${formattedCategoryIds}&pageSize=200`;
		startLoading();
		loadData(APIUrl).then(setChartData);
	}
	function stopLoading() {
		setLoading(!chartData.data);
	}
	function startLoading() {
		setLoading(true);
	}
	/* eslint-disable-next-line react-hooks/exhaustive-deps */
	useEffect(updateData, [accountsId]);
	useEffect(stopLoading, [chartData]);

	return !accountsId ? (
		<p>{noAccountErrorMessage}</p>
	) : (
		<ChartWrapper
			data={chartData}
			loading={loading}
			noDataErrorMessage={noDataErrorMessage}
		/>
	);
}
