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
import {
	PageMetricQuery,
	PageMetricTabsQuery
} from 'shared/components/metric-card/queries';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
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
			metrics={metrics}
			queries={{
				MetricQuery: PageMetricQuery,
				name: 'page',
				TabsQuery: PageMetricTabsQuery
			}}
			reportContainer={ReportContainer.VisitorsBehaviorCard}
			variables={variables}
		/>
	);
};

export default PageMetricCard;
