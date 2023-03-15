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
	DownloadsMetric,
	Metric,
	PreviewsMetric,
	RatingsMetric
} from 'shared/components/metric-card/metrics';
import {useAssetVariables} from 'shared/components/metric-card/hooks';

const NAME = 'document';

const DocumentsAndMediaMetricCard: React.FC<IGenericMetricBaseCardProps> = props => {
	const variables = commonVariables => useAssetVariables(commonVariables);

	const metrics: Metric[] = [
		DownloadsMetric,
		PreviewsMetric,
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

export default DocumentsAndMediaMetricCard;
