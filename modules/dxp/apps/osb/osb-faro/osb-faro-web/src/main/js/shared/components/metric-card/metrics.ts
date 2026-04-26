import {MetricName} from 'shared/types/MetricName';

export enum MetricType {
	Number = 'number',
	Percentage = 'percentage',
	Ratings = 'ratings',
	Time = 'time'
}

export type Metric = {
	compositeMetrics?: Metric[];
	name: MetricName;
	sortField?: MetricName;
	title: string;
	tooltipTitle?: string;
	type: MetricType;
};

const metric = (metric: Metric): Metric => metric;

export const AbandonmentsMetric = metric({
	name: MetricName.Abandonments,
	sortField: MetricName.Abandonments,
	title: Liferay.Language.get('abandonment'),
	type: MetricType.Percentage
});

export const AvgTimeOnPageMetric = metric({
	name: MetricName.AvgTimeOnPage,
	sortField: MetricName.AvgTimeOnPage,
	title: Liferay.Language.get('time-on-page'),
	type: MetricType.Time
});

export const BounceRateMetric = metric({
	name: MetricName.BounceRate,
	sortField: MetricName.BounceRate,
	title: Liferay.Language.get('bounce-rate'),
	type: MetricType.Percentage
});

export const CommentsMetric = metric({
	name: MetricName.Comments,
	sortField: MetricName.Comments,
	title: Liferay.Language.get('comments'),
	type: MetricType.Number
});

export const CompletionTimeMetric = metric({
	name: MetricName.CompletionTime,
	sortField: MetricName.CompletionTime,
	title: Liferay.Language.get('completion-time'),
	type: MetricType.Time
});

export const CompositeMetric = metric({
	compositeMetrics: [
		{
			name: MetricName.AnonymousVisitors,
			title: Liferay.Language.get('anonymous-visitors'),
			tooltipTitle: Liferay.Language.get('anonymous'),
			type: MetricType.Number
		},
		{
			name: MetricName.KnownVisitors,
			title: Liferay.Language.get('known-visitors'),
			tooltipTitle: Liferay.Language.get('known'),
			type: MetricType.Number
		}
	],
	name: MetricName.Visitors,
	title: Liferay.Language.get('unique-visitors'),
	type: MetricType.Number
});

export const DownloadsMetric = metric({
	name: MetricName.Downloads,
	sortField: MetricName.Downloads,
	title: Liferay.Language.get('downloads'),
	type: MetricType.Number
});

export const EntrancesMetric = metric({
	name: MetricName.Entrances,
	sortField: MetricName.Entrances,
	title: Liferay.Language.get('entrances'),
	type: MetricType.Number
});

export const ExitRateMetric = metric({
	name: MetricName.ExitRate,
	sortField: MetricName.ExitRate,
	title: Liferay.Language.get('exit-rate'),
	type: MetricType.Percentage
});

export const ImpressionMadeMetric = metric({
	name: MetricName.Impressions,
	sortField: MetricName.Impressions,
	title: Liferay.Language.get('impressions'),
	type: MetricType.Number
});

export const RatingsMetric = metric({
	name: MetricName.Ratings,
	sortField: MetricName.Ratings,
	title: Liferay.Language.get('rating'),
	type: MetricType.Ratings
});

export const ReadingTimeMetric = metric({
	name: MetricName.ReadingTime,
	sortField: MetricName.ReadingTime,
	title: Liferay.Language.get('reading-time'),
	type: MetricType.Time
});

export const SessionDurationMetric = metric({
	name: MetricName.SessionsDuration,
	title: Liferay.Language.get('session-duration'),
	tooltipTitle: Liferay.Language.get('avg-duration'),
	type: MetricType.Time
});

export const SessionsPerVisitorMetric = metric({
	name: MetricName.SessionsPerVisitor,
	title: Liferay.Language.get('sessions-visitor'),
	tooltipTitle: Liferay.Language.get('avg-sessions'),
	type: MetricType.Number
});

export const SubmissionsMetric = metric({
	name: MetricName.Submissions,
	sortField: MetricName.Submissions,
	title: Liferay.Language.get('submissions'),
	type: MetricType.Number
});

export const ViewsMetric = metric({
	name: MetricName.Views,
	sortField: MetricName.Views,
	title: Liferay.Language.get('views'),
	type: MetricType.Number
});

export const VisitorsMetric = metric({
	name: MetricName.Visitors,
	sortField: MetricName.Visitors,
	title: Liferay.Language.get('unique-visitors'),
	type: MetricType.Number
});
