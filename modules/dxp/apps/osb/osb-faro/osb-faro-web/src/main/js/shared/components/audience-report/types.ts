export type DataPoint = {
	color: string;
	count: number;
	label: string;
};

export type Dataset = {
	data: DataPoint[];
	empty: Empty;
	total: number;
};

export type Empty = {
	message: string;
	show: boolean;
};

export type TData = {
	audienceReport: {
		anonymousUsersCount: number;
		knownUsersCount: number;
		nonsegmentedKnownUsersCount: number;
		segmentedAnonymousUsersCount: number;
		segmentedKnownUsersCount: number;
	};
	segment: {
		metrics: {
			value: string;
			valueKey: string;
		}[];
		total: number;
	};
};

export enum Name {
	Page = 'page',
	Blog = 'blog',
	Form = 'form',
	Journal = 'journal',
	Document = 'document'
}

export interface IAudienceReportBaseCardProps {
	knownIndividualsTitle: string;
	segmentsTitle?: string;
	uniqueVisitorsTitle?: string;
	query: {
		metricName: string;
		name: Name;
	};
}
