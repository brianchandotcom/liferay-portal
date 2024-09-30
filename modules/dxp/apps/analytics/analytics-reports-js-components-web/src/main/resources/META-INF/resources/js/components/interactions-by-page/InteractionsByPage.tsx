/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';

import {AnalyticsReportsContext} from '../../AnalyticsReportsContext';
import {fetchAssetAppearsOnHistogram} from '../../apis/analytics-reports';
import {AssetTypes, MetricName} from '../../types/global';
import StateRenderer from '../StateRenderer';
import Title from '../Title';
import InteractionsByPageChart from './InteractionsByPageChart';

export type Data = {
	assetAppearsOnHistograms: {
		appearsOnHistograms: {
			canonicalUrl: string;
			metrics:
				| {
						value: number;
						valueKey: string;
				  }[]
				| [];
			pageTitle: string;
			totalValue: number;
		}[];
		metricName: MetricName;
	}[];
};

const InteractionsByPage = () => {
	const {
		assetId,
		assetType: initialAssetType,
		filters,
		groupId,
	} = useContext(AnalyticsReportsContext);

	const [data, setData] = useState<Data | null>(null);
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(true);

	const assetType = initialAssetType || AssetTypes.Undefined;

	useEffect(() => {
		async function fetchData() {
			setLoading(true);

			try {
				const response = await fetchAssetAppearsOnHistogram({
					assetId,
					assetType,
					groupId,
					individual: filters.individual,
					rangeSelector: filters.rangeSelector,
				});

				if (!response?.ok) {
					throw new Error();
				}

				const data: Data & {error: string} = await response.json();

				if (data.error) {
					throw new Error(data.error);
				}

				setData(data);
				setLoading(false);
				setError('');
			}
			catch (error: any) {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				setData(null);
				setLoading(false);
				setError(error.toString());
			}
		}

		fetchData();
	}, [
		assetId,
		assetType,
		filters.individual,
		filters.rangeSelector,
		groupId,
	]);

	return (
		<div>
			<Title
				description={Liferay.Language.get(
					'top-three-pages-with-the-highest-individual-interactions-during-the-selected-time-period'
				)}
				section
				value={Liferay.Language.get('top-pages-asset-appears-on')}
			/>

			<StateRenderer data={data} error={error} loading={loading}>
				{({data}) => <InteractionsByPageChart data={data} />}
			</StateRenderer>
		</div>
	);
};

export default InteractionsByPage;
