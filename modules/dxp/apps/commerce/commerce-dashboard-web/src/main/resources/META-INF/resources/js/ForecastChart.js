/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useState} from 'react';

import ChartWrapper from './ChartWrapper';
import {loadData} from './utils/index.es';
export default function ForecastChart({
	APIBaseUrl,
	accountIds: rawAccountIds = '[]',
	categoryIds: rawCategoryIds = '[]',
}) {
	let accountsIds;
	let categoryIds;

	try {
		accountsIds = JSON.parse(rawAccountIds);
		categoryIds = JSON.parse(rawCategoryIds);
	}
	catch (error) {
		console.error(`Parse error:`, error);
	}

	const [loading, setLoading] = useState(true);
	const [chartData, setChartData] = useState({});
	const [accountsId, setAccountId] = useState(accountsIds);

	const updateData = useCallback(() => {
		const formattedAccountIds = accountsId
			.map((id) => `accountIds=${id}`)
			.join('&');
		const formattedCategoryIds = categoryIds.length
			? '&' + categoryIds.map((id) => `categoryIds=${id}`).join('&')
			: '';

		const APIUrl = `${APIBaseUrl}?${formattedAccountIds}${formattedCategoryIds}&pageSize=200`;

		setLoading(true);

		loadData(APIUrl).then(setChartData);
	}, [APIBaseUrl, accountsId, categoryIds]);

	/* eslint-disable-next-line react-hooks/exhaustive-deps */
	useEffect(updateData, [accountsId]);

	useEffect(() => {
		setLoading(!chartData.data);
	}, [chartData]);

	useEffect(() => {
		const setter = ({accountId}) => setAccountId([accountId]);

		Liferay.on('accountSelected', setter);

		return () => {
			Liferay.detach('accountSelected', setter);
		};
	}, []);

	return !accountsId ? (
		<p>{Liferay.Language.get('no-account-selected')}</p>
	) : (
		<ChartWrapper data={chartData} loading={loading} />
	);
}
