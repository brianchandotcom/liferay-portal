/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {AnalyticsReportsContext} from '../../AnalyticsReportsContext';
import {
	AssetMetricHistogramProps,
	fetchAssetMetricHistogram,
} from '../../apis/analytics-reports';
import useFetch from '../../hooks/useFetch';
import {AssetTypes, MetricName} from '../../types/global';
import {assetMetrics} from '../../utils/metrics';
import StateRenderer from '../StateRenderer';
import Title from '../Title';
import BlogPostingsStateRenderer from './BlogPostingsStateRenderer';
import VisitorsBehaviorStateRenderer from './VisitorsBehaviorStateRenderer';

export type Histogram = {
	metricName: MetricName;
	metrics:
		| {
				value: number;
				valueKey: string;
		  }[]
		| [];
	totalValue: number;
};

export type Data = {
	histograms: Histogram[];
};

export type PublishedVersionData = {
	histogram: (string | null)[];
	total: number;
};

const VisitorsBehavior = () => {
	const {
		assetId,
		assetType: initialAssetType,
		filters,
		groupId,
	} = useContext(AnalyticsReportsContext);

	const assetType = initialAssetType || AssetTypes.Undefined;

	const {data, error, loading} = useFetch<
		{histograms: Histogram[]},
		AssetMetricHistogramProps
	>(fetchAssetMetricHistogram, {
		variables: {
			assetId,
			assetType,
			groupId,
			individual: filters.individual,
			rangeSelector: filters.rangeSelector,
			selectedMetrics: assetMetrics[assetType],
		},
	});

	let Component = VisitorsBehaviorStateRenderer;

	if (assetType === AssetTypes.Blog) {
		Component = BlogPostingsStateRenderer;
	}

	return (
		<div>
			<Title
				description={Liferay.Language.get(
					'total-daily-interactions-and-asset-updates'
				)}
				section
				value={Liferay.Language.get('visitors-behavior')}
			/>

			<StateRenderer data={data} error={error} loading={loading}>
				{({data}) => <Component data={data} />}
			</StateRenderer>
		</div>
	);
};

export default VisitorsBehavior;
