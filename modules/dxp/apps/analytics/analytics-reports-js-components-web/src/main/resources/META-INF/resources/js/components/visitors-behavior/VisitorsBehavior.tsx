/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';

import {AnalyticsReportsContext} from '../../AnalyticsReportsContext';
import {fetchAssetMetricHistogram} from '../../apis/analytics-reports';
import {fetchBlogPosting} from '../../apis/headless-dxp';
import {AssetTypes, MetricName} from '../../types/global';
import {assetMetrics} from '../../utils/metrics';
import StateRenderer from '../StateRenderer';
import Title from '../Title';
import VisitorsBehaviorChart from './VisitorsBehaviorChart';
import {formatPublishedDate, mapPublishedDatesToHistogram} from './utils';

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
	publishedVersionData: PublishedVersionData | null;
};

type BlogPostingsData = {
	datePublished: string;
};

type PublishedVersionData = {
	histogram: (string | null)[];
	total: number;
};

const VisitorsBehavior = () => {
	const {
		assetId,
		assetType: initialAssetType,
		filters,
		groupId,
		versions,
	} = useContext(AnalyticsReportsContext);

	const [data, setData] = useState<Data | null>(null);
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(true);

	const assetType = initialAssetType || AssetTypes.Undefined;

	useEffect(() => {
		async function fetchData() {
			setLoading(true);

			try {
				const visitorsBehaviorResponse =
					await fetchAssetMetricHistogram({
						assetId,
						assetType,
						groupId,
						individual: filters.individual,
						rangeSelector: filters.rangeSelector,
						selectedMetrics: assetMetrics[assetType],
					});

				if (!visitorsBehaviorResponse.ok) {
					throw new Error();
				}

				const visitorsBehaviorData: Data & {
					error: string;
				} = await visitorsBehaviorResponse.json();

				if (visitorsBehaviorData.error) {
					throw new Error(visitorsBehaviorData.error);
				}

				let publishedVersionData: PublishedVersionData | null = null;
				let dates = [];

				if (versions) {
					dates = versions.map(({createDate, version}) => ({
						date: formatPublishedDate(createDate),
						version,
					}));

					publishedVersionData = {
						histogram: mapPublishedDatesToHistogram(
							dates,
							visitorsBehaviorData.histograms[0]
						),
						total: versions.length,
					};
				}
				else if (assetType === AssetTypes.Blog) {
					const responseBlogPostings = await fetchBlogPosting({
						assetId,
					});

					if (!responseBlogPostings.ok) {
						throw new Error();
					}

					const blogPostingsData: BlogPostingsData & {
						error: string;
					} = await responseBlogPostings.json();

					if (blogPostingsData.error) {
						throw new Error(blogPostingsData.error);
					}

					dates = [
						{
							date: formatPublishedDate(
								blogPostingsData.datePublished
							),
							version: '1.0',
						},
					];

					publishedVersionData = {
						histogram: mapPublishedDatesToHistogram(
							dates,
							visitorsBehaviorData.histograms[0]
						),
						total: 1,
					};
				}

				setData({...visitorsBehaviorData, publishedVersionData});
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
		versions,
	]);

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
				{({data}) => <VisitorsBehaviorChart data={data} />}
			</StateRenderer>
		</div>
	);
};

export default VisitorsBehavior;
