import {Metric} from '../../contacts/pages/account/utils/types';

export enum OverviewMetricType {
	AtRisk = 'at-risk',
	NewPipeline = 'new-pipeline',
	Stalled = 'stalled'
}

export interface IOverviewMetric extends Metric {
	metricType: OverviewMetricType;
}
