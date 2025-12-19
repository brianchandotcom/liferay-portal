export type ColorType = 'danger' | 'secondary' | 'success';

export enum MetricNames {
	AverageSegmentMembershipDurationMetric = 'averageSegmentMembershipDurationMetric',
	EntryRateMetric = 'entryRateMetric',
	ExitRateMetric = 'exitRate',
	TotalMembersMetric = 'totalMembersMetric'
}

export enum TrendClassification {
	Negative = 'NEGATIVE',
	Neutral = 'NEUTRAL',
	Positive = 'POSITIVE'
}

export type Trend = {
	percentage: number;
	trendClassification: TrendClassification;
};

export type Metric = {
	previousValue: number;
	trend: Trend;
	value: number;
};

export type TotalMembersMetric = Metric & {
	totalIndividuals: number;
};

export const metricsDescription: Record<MetricNames, string> = {
	[MetricNames.AverageSegmentMembershipDurationMetric]: Liferay.Language.get(
		'average-amount-of-time-segment-members-have-belonged-to-the-segment-over-the-past-30-days'
	),
	[MetricNames.EntryRateMetric]: Liferay.Language.get(
		'the-number-of-unique-profiles-that-newly-qualify-for-the-segment-during-the-past-24-hours'
	),
	[MetricNames.ExitRateMetric]: Liferay.Language.get(
		'the-number-of-unique-profiles-that-no-longer-meet-the-segment-criteria-during-the-past-24-hours'
	),
	[MetricNames.TotalMembersMetric]: Liferay.Language.get(
		'the-total-number-of-individuals-included-in-this-segment-whether-known-or-anonymous'
	)
};

export const metricsTitles: Record<MetricNames, string> = {
	[MetricNames.AverageSegmentMembershipDurationMetric]: Liferay.Language.get(
		'average-segment-membership-duration'
	),
	[MetricNames.EntryRateMetric]: Liferay.Language.get('entry-rate'),
	[MetricNames.ExitRateMetric]: Liferay.Language.get('exit-rate'),
	[MetricNames.TotalMembersMetric]: Liferay.Language.get('total-members')
};
