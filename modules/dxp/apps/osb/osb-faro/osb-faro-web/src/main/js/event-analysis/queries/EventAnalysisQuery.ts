import {
	Attribute,
	Breakdown,
	CalculationTypes,
	Event,
	Filter
} from 'event-analysis/utils/types';
import {gql} from 'apollo-boost';
import {RawRangeSelectors, Sort} from 'shared/types';

interface EventAnalysis extends RawRangeSelectors {
	compareToPrevious: boolean;
	eventAnalysisBreakdowns: Breakdown[];
	eventAnalysisFilters: Filter[];
	eventAnalysisId: number;
	eventDefinitionId: number;
	name: string;
	referencedObjects: {
		eventDefinition: Event;
		eventAttributeDefinitions: Attribute[];
	};
}

export interface EventAnalysisData {
	eventAnalysis: EventAnalysis;
}

export interface EventAnalysisListData {
	total: number;
	eventAnalyses: {
		id: number;
		modifiedDate: number;
		name: string;
		userName: string;
	}[];
}

export interface EventAnalysisMutationData {
	id: string;
}

export interface EventAnalysisVariables {
	eventAnalysisId: string;
}

export interface DeleteEventAnalysisData {
	null;
}

export interface EventAnalysisListVariables {
	channelId: string;
	keywords?: string;
	page: number;
	size: number;
	sort: Sort;
}

export interface EventAnalysisMutationVariables extends RawRangeSelectors {
	analysisType: CalculationTypes;
	channelId: string;
	compareToPrevious: boolean;
	eventAnalysisBreakdowns?: Breakdown[];
	eventAnalysisFilters?: Filter[];
	eventDefinitionId: string;
	name: string;
	userId: string;
	userName: string;
	eventAnalysisId?: string | null;
}

export interface DeleteEventAnalysisVariables {
	eventAnalysisIds: Array<string>;
}

export const EventAnalysisQuery = gql`
	query EventAnalysis($eventAnalysisId: String!) {
		eventAnalysis(eventAnalysisId: $eventAnalysisId) {
			analysisType
			channelId
			compareToPrevious
			eventAnalysisBreakdowns {
				attributeId
				attributeType
				binSize
				dataType
				dateGrouping
				description
				displayName
				sortType
			}
			eventAnalysisFilters {
				attributeId
				attributeType
				dataType
				description
				displayName
				operator
				values
			}
			eventDefinitionId
			name
			rangeEnd
			rangeKey
			rangeStart
			referencedObjects {
				eventAttributeDefinitions {
					dataType
					description
					displayName
					id
					name
					sampleValue
					type
				}
				eventDefinition {
					description
					hidden
					id
					name
					type
				}
			}
		}
	}
`;

export const EventAnalysisListQuery = gql`
	query EventAnalysisList(
		$channelId: String!
		$keywords: String
		$page: Int!
		$size: Int!
		$sort: Sort!
	) {
		eventAnalyses(
			channelId: $channelId
			keywords: $keywords
			page: $page
			size: $size
			sort: $sort
		) {
			eventAnalyses {
				... on EventAnalysis {
					dateModified: modifiedDate
					id
					name
					userName: createdByUserName
				}
			}
			total
		}
	}
`;

export const DeleteEventAnalysisMutation = gql`
	mutation DeleteEventAnalysisMutation($eventAnalysisIds: [String]!) {
		deleteEventAnalyses(eventAnalysisIds: $eventAnalysisIds)
	}
`;

export const CreateEventAnalysisMutation = gql`
	mutation CreateEventAnalysis(
		$analysisType: AnalysisType!
		$channelId: String!
		$compareToPrevious: Boolean!
		$eventAnalysisBreakdowns: [EventAnalysisBreakdownInput]
		$eventAnalysisFilters: [EventAnalysisFilterInput]
		$eventDefinitionId: String!
		$name: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$userId: String!
		$userName: String!
	) {
		createEventAnalysis(
			analysisType: $analysisType
			channelId: $channelId
			compareToPrevious: $compareToPrevious
			eventAnalysisBreakdowns: $eventAnalysisBreakdowns
			eventAnalysisFilters: $eventAnalysisFilters
			eventDefinitionId: $eventDefinitionId
			name: $name
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			userId: $userId
			userName: $userName
		) {
			id
		}
	}
`;

export const UpdateEventAnalysisMutation = gql`
	mutation UpdateEventAnalysis(
		$analysisType: AnalysisType!
		$channelId: String!
		$compareToPrevious: Boolean!
		$eventAnalysisBreakdowns: [EventAnalysisBreakdownInput]
		$eventAnalysisFilters: [EventAnalysisFilterInput]
		$eventAnalysisId: String!
		$eventDefinitionId: String!
		$name: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$userId: String!
		$userName: String!
	) {
		updateEventAnalysis(
			analysisType: $analysisType
			channelId: $channelId
			compareToPrevious: $compareToPrevious
			eventAnalysisBreakdowns: $eventAnalysisBreakdowns
			eventAnalysisFilters: $eventAnalysisFilters
			eventAnalysisId: $eventAnalysisId
			eventDefinitionId: $eventDefinitionId
			name: $name
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			userId: $userId
			userName: $userName
		) {
			id
		}
	}
`;
