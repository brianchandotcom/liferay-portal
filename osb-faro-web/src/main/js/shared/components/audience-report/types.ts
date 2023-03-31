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
	anonymousUsersCount: number;
	knownUsersCount: number;
	nonsegmentedKnownUsersCount: number;
	segment: {
		metrics: {
			value: string;
			valueKey: string;
		}[];
		total: number;
	};
	segmentedAnonymousUsersCount: number;
	segmentedKnownUsersCount: number;
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
	metricAction?: string;
	query: {
		metricName: string;
		name: Name;
	};
}
