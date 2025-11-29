/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useMemo, useState} from 'react';

import Chart from './Chart';
import {loadData} from './index';

export default function ForecastChart({
	accountIds: rawAccountIds = '[]',
	apiURL,
	categoryIds: rawCategoryIds = '[]',
}) {
	const categoryIds = useMemo(() => {
		try {
			return JSON.parse(rawCategoryIds);
		}
		catch (error) {
			console.error(`Parse error for categoryIds:`, error);

			return [];
		}
	}, [rawCategoryIds]);

	const initialAccountsIds = useMemo(() => {
		try {
			return JSON.parse(rawAccountIds);
		}
		catch (error) {
			console.error(`Parse error for accountsIds:`, error);

			return [];
		}
	}, [rawAccountIds]);

	const [loading, setLoading] = useState(true);
	const [chartData, setChartData] = useState(null);
	const [accountsId, setAccountId] = useState(initialAccountsIds);

	const updateData = useCallback(() => {
		const formattedAccountIds = accountsId
			.map((id) => `accountIds=${id}`)
			.join('&');
		const formattedCategoryIds = categoryIds.length
			? '&' + categoryIds.map((id) => `categoryIds=${id}`).join('&')
			: '';

		setLoading(true);

		loadData(
			`${apiURL}?${formattedAccountIds}${formattedCategoryIds}&pageSize=200`
		)
			.then(setChartData)
			.then(() => setLoading(false))
			.catch(() => setLoading(false));
	}, [apiURL, accountsId, categoryIds, setChartData, setLoading]);

	useEffect(updateData, [accountsId, updateData]);

	useEffect(() => {
		setLoading(chartData === null);
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
		<Chart data={chartData} loading={loading} />
	);
}
