/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {AnalyticsReportsContext} from '../../AnalyticsReportsContext';
import {fetchBlogPosting} from '../../apis/headless-dxp';
import useFetch from '../../hooks/useFetch';
import {MetricType} from '../../types/global';
import {metricNameByType} from '../../utils/metrics';
import StateRenderer from '../StateRenderer';
import {Data} from './VisitorsBehavior';
import VisitorsBehaviorChart from './VisitorsBehaviorChart';
import {
	formatPublishedDate,
	getSelectedHistogram,
	mapPublishedDatesToHistogram,
} from './utils';

type BlogPostingsData = {
	datePublished: string;
};

interface IBlogPostingsStateRendererProps {
	data: Data;
}

interface IVisitorsBehaviorWithBlogDataProps {
	data: BlogPostingsData;
	visitorsBehaviorData: Data;
}

const VisitorsBehaviorWithBlogData: React.FC<
	IVisitorsBehaviorWithBlogDataProps
> = ({data, visitorsBehaviorData}) => {
	const {filters} = useContext(AnalyticsReportsContext);

	const metricName =
		metricNameByType[filters?.metric || MetricType.Undefined];
	const selectedHistogram = getSelectedHistogram(
		visitorsBehaviorData,
		metricName
	);

	let publishedVersionData = null;

	if (selectedHistogram) {
		const dates = [
			{
				date: formatPublishedDate(data.datePublished),
				version: '1.0',
			},
		];

		publishedVersionData = {
			histogram: mapPublishedDatesToHistogram(dates, selectedHistogram),
			total: 1,
		};
	}

	return (
		<VisitorsBehaviorChart
			data={visitorsBehaviorData}
			publishedVersionData={publishedVersionData}
		/>
	);
};

const BlogPostingsStateRenderer: React.FC<IBlogPostingsStateRendererProps> = ({
	data: visitorsBehaviorData,
}) => {
	const {assetId} = useContext(AnalyticsReportsContext);

	const {data, error, loading} = useFetch<
		BlogPostingsData,
		{assetId: string}
	>(fetchBlogPosting, {
		variables: {
			assetId,
		},
	});

	return (
		<StateRenderer data={data} error={error} loading={loading}>
			{({data}) => (
				<VisitorsBehaviorWithBlogData
					data={data}
					visitorsBehaviorData={visitorsBehaviorData}
				/>
			)}
		</StateRenderer>
	);
};

export default BlogPostingsStateRenderer;
