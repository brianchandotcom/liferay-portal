import MetricBaseCard, {
	IGenericMetricBaseCardProps
} from 'shared/components/metric-card/MetricBaseCard';
import React from 'react';

import {
	AssetMetricQuery,
	AssetTabsQuery
} from 'shared/components/metric-card/queries';
import {
	CommentsMetric,
	Metric,
	RatingsMetric,
	ReadingTimeMetric,
	ViewsMetric
} from 'shared/components/metric-card/metrics';
import {useAssetVariables} from 'shared/components/metric-card/hooks';

const NAME = 'blog';

const BlogMetricCard: React.FC<IGenericMetricBaseCardProps> = props => {
	const variables = commonVariables => useAssetVariables(commonVariables);

	const metrics: Metric[] = [
		ViewsMetric,
		ReadingTimeMetric,
		CommentsMetric,
		RatingsMetric
	];

	return (
		<MetricBaseCard
			{...props}
			metrics={metrics}
			queries={{
				MetricQuery: AssetMetricQuery(NAME),
				name: NAME,
				TabsQuery: AssetTabsQuery(metrics, NAME)
			}}
			variables={variables}
		/>
	);
};

export default BlogMetricCard;
