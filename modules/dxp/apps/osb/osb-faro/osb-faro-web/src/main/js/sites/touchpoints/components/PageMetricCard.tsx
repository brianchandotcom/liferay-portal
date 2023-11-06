import MetricBaseCard, {
	IGenericMetricBaseCardProps
} from 'shared/components/metric-card/MetricBaseCard';
import React from 'react';
import {
	AvgTimeOnPageMetric,
	BounceRateMetric,
	EntrancesMetric,
	ExitRateMetric,
	Metric,
	ViewsMetric,
	VisitorsMetric
} from 'shared/components/metric-card/metrics';
import {Containers} from 'shared/components/download-report/DownloadPDFReport';
import {
	PageMetricQuery,
	PageMetricTabsQuery
} from 'shared/components/metric-card/queries';
import {useAssetVariables} from 'shared/components/metric-card/hooks';

const PageMetricCard: React.FC<IGenericMetricBaseCardProps> = props => {
	const variables = commonVariables => useAssetVariables(commonVariables);

	const metrics: Metric[] = [
		VisitorsMetric,
		ViewsMetric,
		BounceRateMetric,
		AvgTimeOnPageMetric,
		EntrancesMetric,
		ExitRateMetric
	];

	return (
		<MetricBaseCard
			{...props}
			id={Containers.VisitorsBehaviorCard}
			metrics={metrics}
			queries={{
				MetricQuery: PageMetricQuery,
				name: 'page',
				TabsQuery: PageMetricTabsQuery
			}}
			variables={variables}
		/>
	);
};

export default PageMetricCard;
