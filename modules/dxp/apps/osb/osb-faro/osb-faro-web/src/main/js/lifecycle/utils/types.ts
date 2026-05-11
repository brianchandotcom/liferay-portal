import {LifecycleStages} from '../../contacts/pages/account/utils/constants';
import {Metric} from '../../contacts/pages/account/utils/types';

export enum OverviewMetricType {
	AtRisk = 'at-risk',
	NewPipeline = 'new-pipeline',
	Stalled = 'stalled'
}

export interface IOverviewMetric extends Metric {
	metricType: OverviewMetricType;
}

export interface ILifecycleStage {
	accountCount: number;
	averageDaysInStage: number;
	description: string;
	percentage: number;
	stageType: LifecycleStages;
}
