export type ColorType = 'danger' | 'secondary' | 'success';

export type Metric = {
	previousValue: number;
	trend: Trend;
	value: number;
};

export enum MetricNames {
	AverageSegmentMembershipDurationMetric = 'averageSegmentMembershipDurationMetric',
	EntryRateMetric = 'entryRateMetric',
	ExitRateMetric = 'exitRate',
	TotalMembersMetric = 'totalMembersMetric'
}

export const METRICS_TEXT: Record<MetricNames, string> = {
	[MetricNames.AverageSegmentMembershipDurationMetric]: Liferay.Language.get(
		'this-metric-represents-the-average-amount-of-time-segment-members-have-belonged-to-the-segment-over-the-past-30-days'
	),
	[MetricNames.EntryRateMetric]: Liferay.Language.get(
		'this-is-the-number-of-unique-profiles-that-newly-qualify-for-the-segment-during-the-past-24-hours'
	),
	[MetricNames.ExitRateMetric]: Liferay.Language.get(
		'this-is-the-number-of-unique-profiles-that-no-longer-meet-the-segment-criteria-during-the-past-24-hours'
	),
	[MetricNames.TotalMembersMetric]: Liferay.Language.get(
		'this-is-the-total-number-of-individuals-included-in-this-segment,-whether-known-or-anonymous'
	)
};

export const METRICS_TITLES: Record<MetricNames, string> = {
	[MetricNames.AverageSegmentMembershipDurationMetric]: Liferay.Language.get(
		'average-segment-membership-duration'
	),
	[MetricNames.EntryRateMetric]: Liferay.Language.get('entry-rate'),
	[MetricNames.ExitRateMetric]: Liferay.Language.get('exit-rate'),
	[MetricNames.TotalMembersMetric]: Liferay.Language.get('total-members')
};

export type TotalMembersMetric = Metric & {
	totalIndividuals: number;
};

export type Trend = {
	percentage: number;
	trendClassification: TrendClassification;
};

export enum TrendClassification {
	Negative = 'NEGATIVE',
	Neutral = 'NEUTRAL',
	Positive = 'POSITIVE'
}
