import MetricBaseCard, {
	IGenericMetricBaseCardProps
} from 'shared/components/metric-card/MetricBaseCard';
import React from 'react';
import {
	AbandonmentsMetric,
	CompletionTimeMetric,
	Metric,
	SubmissionsMetric,
	ViewsMetric
} from 'shared/components/metric-card/metrics';
import {
	AssetMetricQuery,
	AssetTabsQuery
} from 'shared/components/metric-card/queries';
import {useAssetVariables} from 'shared/components/metric-card/hooks';

const NAME = 'form';

const FormMetricCard: React.FC<IGenericMetricBaseCardProps> = props => {
	const variables = commonVariables => useAssetVariables(commonVariables);

	const metrics: Metric[] = [
		SubmissionsMetric,
		ViewsMetric,
		AbandonmentsMetric,
		CompletionTimeMetric
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

export default FormMetricCard;
