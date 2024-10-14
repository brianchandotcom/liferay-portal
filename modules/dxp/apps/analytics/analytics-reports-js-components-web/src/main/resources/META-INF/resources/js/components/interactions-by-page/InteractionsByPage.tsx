/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {AnalyticsReportsContext} from '../../AnalyticsReportsContext';
import {
	AssetAppearsOnHistogramProps,
	fetchAssetAppearsOnHistogram,
} from '../../apis/analytics-reports';
import useFetch from '../../hooks/useFetch';
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

	const assetType = initialAssetType || AssetTypes.Undefined;

	const {data, error, loading} = useFetch<Data, AssetAppearsOnHistogramProps>(
		fetchAssetAppearsOnHistogram,
		{
			variables: {
				assetId,
				assetType,
				groupId,
				individual: filters.individual,
				rangeSelector: filters.rangeSelector,
			},
		}
	);

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
