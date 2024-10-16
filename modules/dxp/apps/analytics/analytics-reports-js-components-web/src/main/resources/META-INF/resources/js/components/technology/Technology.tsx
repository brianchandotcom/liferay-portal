/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {AnalyticsReportsContext} from '../../AnalyticsReportsContext';
import {
	AssetDeviceMetric,
	fetchAssetDeviceMetric,
} from '../../apis/analytics-reports';
import useFetch from '../../hooks/useFetch';
import {AssetTypes, MetricName, MetricType} from '../../types/global';
import StateRenderer from '../StateRenderer';
import Title from '../Title';
import StackedBarChart from '../stacked-bar/StackedBarChart';
import {formatData} from './utils';

export type Data = {
	deviceMetrics: {
		metricName: MetricName;
		metrics: {value: number; valueKey: string}[];
	}[];
};

const TITLE: {
	[key in MetricType]: string;
} = {
	[MetricType.Comments]: Liferay.Language.get('comments-by-technology'),
	[MetricType.Downloads]: Liferay.Language.get('downloads-by-technology'),
	[MetricType.Previews]: Liferay.Language.get('previews-by-technology'),
	[MetricType.Undefined]: Liferay.Language.get('undefined'),
	[MetricType.Views]: Liferay.Language.get('views-by-technology'),
};

const Technology = () => {
	const {
		assetId,
		assetType: initialAssetType,
		filters,
		groupId,
	} = useContext(AnalyticsReportsContext);

	const {data, error, loading} = useFetch<Data, AssetDeviceMetric>(
		fetchAssetDeviceMetric,
		{
			variables: {
				assetId,
				assetType: initialAssetType || AssetTypes.Undefined,
				groupId,
				individual: filters?.individual,
				rangeSelector: filters.rangeSelector,
			},
		}
	);

	const title = TITLE[filters?.metric ?? MetricType.Undefined];

	return (
		<div>
			<Title
				description={Liferay.Language.get(
					'the-total-number-of-downloads-is-broken-down-by-device-types-during-the-selected-time-period'
				)}
				section
				value={title}
			/>

			<StateRenderer data={data} error={error} loading={loading}>
				{({data}) => {
					const formattedData = formatData(
						data,
						filters?.metric || MetricType.Undefined
					);

					return (
						<StackedBarChart
							data={formattedData}
							tooltipTitle={title}
						/>
					);
				}}
			</StateRenderer>
		</div>
	);
};

export default Technology;
