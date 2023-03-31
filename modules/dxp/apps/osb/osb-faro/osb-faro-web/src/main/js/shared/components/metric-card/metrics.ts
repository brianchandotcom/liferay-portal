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
	title: string;
	tooltipTitle?: string;
	type: MetricType;
	sortField?: MetricName;
};

export const VisitorsMetric: Metric = {
	name: MetricName.Visitors,
	sortField: MetricName.Visitors,
	title: Liferay.Language.get('unique-visitors'),
	type: MetricType.Number
};

export const ViewsMetric: Metric = {
	name: MetricName.Views,
	sortField: MetricName.Views,
	title: Liferay.Language.get('views'),
	type: MetricType.Number
};

export const BounceRateMetric: Metric = {
	name: MetricName.BounceRate,
	sortField: MetricName.BounceRate,
	title: Liferay.Language.get('bounce-rate'),
	type: MetricType.Percentage
};

export const AvgTimeOnPageMetric: Metric = {
	name: MetricName.AvgTimeOnPage,
	sortField: MetricName.AvgTimeOnPage,
	title: Liferay.Language.get('time-on-page'),
	type: MetricType.Time
};

export const EntrancesMetric: Metric = {
	name: MetricName.Entrances,
	sortField: MetricName.Entrances,
	title: Liferay.Language.get('entrances'),
	type: MetricType.Number
};

export const ExitRateMetric: Metric = {
	name: MetricName.ExitRate,
	sortField: MetricName.ExitRate,
	title: Liferay.Language.get('exit-rate'),
	type: MetricType.Percentage
};

export const CompositeMetric: Metric = {
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
};

export const SessionsPerVisitorMetric: Metric = {
	name: MetricName.SessionsPerVisitor,
	title: Liferay.Language.get('sessions-visitor'),
	tooltipTitle: Liferay.Language.get('avg-sessions'),
	type: MetricType.Number
};

export const SessionDurationMetric: Metric = {
	name: MetricName.SessionsDuration,
	title: Liferay.Language.get('session-duration'),
	tooltipTitle: Liferay.Language.get('avg-duration'),
	type: MetricType.Time
};

export const SubmissionsMetric: Metric = {
	name: MetricName.Submissions,
	sortField: MetricName.Submissions,
	title: Liferay.Language.get('submissions'),
	type: MetricType.Number
};

export const AbandonmentsMetric: Metric = {
	name: MetricName.Abandonments,
	sortField: MetricName.Abandonments,
	title: Liferay.Language.get('abandonment'),
	type: MetricType.Percentage
};

export const CompletionTimeMetric: Metric = {
	name: MetricName.CompletionTime,
	sortField: MetricName.CompletionTime,
	title: Liferay.Language.get('completion-time'),
	type: MetricType.Time
};

export const DownloadsMetric: Metric = {
	name: MetricName.Downloads,
	sortField: MetricName.Downloads,
	title: Liferay.Language.get('downloads'),
	type: MetricType.Number
};

export const PreviewsMetric: Metric = {
	name: MetricName.Previews,
	sortField: MetricName.Previews,
	title: Liferay.Language.get('previews'),
	type: MetricType.Number
};

export const CommentsMetric: Metric = {
	name: MetricName.Comments,
	sortField: MetricName.Comments,
	title: Liferay.Language.get('comments'),
	type: MetricType.Number
};

export const RatingsMetric: Metric = {
	name: MetricName.Ratings,
	sortField: MetricName.Ratings,
	title: Liferay.Language.get('rating'),
	type: MetricType.Ratings
};

export const ReadingTimeMetric: Metric = {
	name: MetricName.ReadingTime,
	sortField: MetricName.ReadingTime,
	title: Liferay.Language.get('reading-time'),
	type: MetricType.Time
};
