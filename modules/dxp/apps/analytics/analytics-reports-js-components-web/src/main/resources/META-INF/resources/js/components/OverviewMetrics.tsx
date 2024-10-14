/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect} from 'react';

import {AnalyticsReportsContext} from '../AnalyticsReportsContext';
import {AssetMetricProps, fetchAssetMetric} from '../apis/analytics-reports';
import OverviewMetric, {
	TrendClassification,
} from '../components/OverviewMetric';
import useFetch from '../hooks/useFetch';
import {AssetTypes, MetricType} from '../types/global';
import {assetMetrics} from '../utils/metrics';
import StateRenderer from './StateRenderer';

type MetricData = {
	metricType: MetricType;
	trend: {
		percentage?: number;
		trendClassification: TrendClassification;
	};
	value: number;
};

type Data = {
	assetId: string;
	assetType: AssetTypes;
	defaultMetric: MetricData;
	selectedMetrics: MetricData[];
};

type Metrics = {
	[key in MetricType]: string;
};

export const MetricsTitle: Metrics = {
	[MetricType.Comments]: Liferay.Language.get('comments'),
	[MetricType.Downloads]: Liferay.Language.get('downloads'),
	[MetricType.Previews]: Liferay.Language.get('previews'),
	[MetricType.Views]: Liferay.Language.get('views'),
	[MetricType.Undefined]: Liferay.Language.get('undefined'),
};

interface IOverviewMetricsWithDataProps {
	data: Data;
}

const OverviewMetricsWithData: React.FC<IOverviewMetricsWithDataProps> = ({
	data,
}) => {
	const {changeMetricFilter, filters} = useContext(AnalyticsReportsContext);

	useEffect(() => {
		if (!filters.metric) {
			changeMetricFilter(data.defaultMetric.metricType);
		}
	}, [changeMetricFilter, data.defaultMetric.metricType, filters.metric]);

	return (
		<div className="overview-metrics">
			{data.selectedMetrics.map(({metricType, trend, value}) => (
				<OverviewMetric
					key={metricType}
					name={MetricsTitle[metricType]}
					onSelectMetric={() => changeMetricFilter(metricType)}
					selected={filters.metric === metricType}
					trend={{
						percentage: trend.percentage ?? 0,
						trendClassification: trend.trendClassification,
					}}
					value={value}
				/>
			))}
		</div>
	);
};

const OverviewMetrics = () => {
	const {assetId, assetType, filters, groupId} = useContext(
		AnalyticsReportsContext
	);

	const {data, error, loading} = useFetch<Data, AssetMetricProps>(
		fetchAssetMetric,
		{
			variables: {
				assetId,
				assetType: assetType || AssetTypes.Undefined,
				groupId,
				individual: filters.individual,
				rangeSelector: filters.rangeSelector,
				selectedMetrics:
					assetMetrics[assetType || AssetTypes.Undefined],
			},
		}
	);

	return (
		<StateRenderer data={data} error={error} loading={loading}>
			{({data}) => <OverviewMetricsWithData data={data} />}
		</StateRenderer>
	);
};

export default OverviewMetrics;
