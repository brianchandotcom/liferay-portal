/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {useState, useEffect} from 'react';

import ChartWrapper from '../ChartWrapper.es';
import {loadData} from '../utils/index.es';
export default function ForecastChart({
	APIBaseUrl,
	accountIds: initialAccountsIds = [],
	categoryIds = [],
	noAccountErrorMessage,
	noDataErrorMessage
}) {
	const [loading, setLoading] = useState(true);
	const [chartData, setChartData] = useState({});
	const [accountsId, setAccountId] = useState(initialAccountsIds);
	Liferay.on('accountSelected', ({accountId}) => setAccountId([accountId]));
	function updateData() {
		const formattedAccountIds = accountsId
			.map(id => `accountIds=${id}`)
			.join('&');
		const formattedCategoryIds = categoryIds.length
			? '&' + categoryIds.map(id => `categoryIds=${id}`).join('&')
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
