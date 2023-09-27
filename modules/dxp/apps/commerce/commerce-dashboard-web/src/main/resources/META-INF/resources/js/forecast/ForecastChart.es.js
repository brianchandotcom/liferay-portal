/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import ChartWrapper from '../ChartWrapper.es';
import {loadData} from '../utils/index.es';
export default function ForecastChart({
	APIBaseUrl,
	accountIds: initialAccountsIds = [],
	categoryIds = [],
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
		<p>{Liferay.Language.get('no-account-selected')}</p>
	) : (
		<ChartWrapper data={chartData} loading={loading} />
	);
}
