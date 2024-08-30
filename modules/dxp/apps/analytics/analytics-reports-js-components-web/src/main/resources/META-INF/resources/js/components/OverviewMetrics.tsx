/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useState} from 'react';

import {AnalyticsReportsContext} from '../AnalyticsReportsContext';
import {fetchAssetMetric} from '../apis/asset-metrics';
import OverviewMetric, {
	TrendClassification,
} from '../components/OverviewMetric';
import {AssetTypes, MetricName, MetricType} from '../types/global';

type AssetMetrics = {
	[key in AssetTypes]: MetricName[];
};

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

const assetMetrics: AssetMetrics = {
	[AssetTypes.Blog]: [
		MetricName.Views,
		MetricName.ReadingTime,
		MetricName.Comments,
		MetricName.Ratings,
	],
	[AssetTypes.Document]: [
		MetricName.Downloads,
		MetricName.Previews,
		MetricName.Comments,
	],
	[AssetTypes.WebContent]: [MetricName.Views],
	[AssetTypes.Undefined]: [],
};

type Metrics = {
	[key in MetricType]: string;
};

export const MetricsTitle: Metrics = {
	[MetricType.Comments]: Liferay.Language.get('comments'),
	[MetricType.Downloads]: Liferay.Language.get('downloads'),
	[MetricType.Previews]: Liferay.Language.get('previews'),
	[MetricType.Ratings]: Liferay.Language.get('ratings'),
	[MetricType.ReadingTime]: Liferay.Language.get('reading-time'),
	[MetricType.Views]: Liferay.Language.get('views'),
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
			<OverviewMetric
				name={MetricsTitle[data.defaultMetric.metricType]}
				onSelectMetric={() =>
					changeMetricFilter(data.defaultMetric.metricType)
				}
				selected={filters.metric === data.defaultMetric.metricType}
				trend={{
					percentage: data.defaultMetric.trend.percentage ?? 0,
					trendClassification:
						data.defaultMetric.trend.trendClassification ??
						TrendClassification.Neutral,
				}}
				value={data.defaultMetric.value}
			/>

			<div className="overview-metrics__secondary-content">
				{data.selectedMetrics.map(
					({metricType, trend, value}, index) => {
						if (!index) {
							return null;
						}

						return (
							<OverviewMetric
								key={metricType}
								name={MetricsTitle[metricType]}
								onSelectMetric={() =>
									changeMetricFilter(metricType)
								}
								selected={filters.metric === metricType}
								trend={{
									percentage: trend.percentage ?? 0,
									trendClassification:
										trend.trendClassification,
								}}
								value={value}
							/>
						);
					}
				)}
			</div>
		</div>
	);
};

const OverviewMetrics = () => {
	const {assetId, assetType, changeMetricFilter, filters, groupId} =
		useContext(AnalyticsReportsContext);

	const [data, setData] = useState<Data | null>(null);
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		async function fetchData() {
			try {
				const response = await fetchAssetMetric({
					assetId,
					assetType: assetType || AssetTypes.Blog,
					groupId,
					individual: filters.individual,
					rangeSelector: filters.rangeSelector,
					selectedMetrics: assetMetrics[assetType || AssetTypes.Blog],
				});

				const data = await response.json();

				if (data.error) {
					throw new Error(data.error);
				}

				setData(data);
				setLoading(false);
			}
			catch (error: any) {
				console.error(error);

				setError(error.toString());
				setLoading(false);
			}
		}

		fetchData();
	}, [
		assetId,
		assetType,
		changeMetricFilter,
		filters.individual,
		filters.metric,
		filters.rangeSelector,
		groupId,
	]);

	if (loading) {
		return <ClayLoadingIndicator className="mt-10" />;
	}

	if (error) {
		return <ClayAlert displayType="danger" title={error} />;
	}

	return data ? <OverviewMetricsWithData data={data} /> : null;
};

export default OverviewMetrics;
