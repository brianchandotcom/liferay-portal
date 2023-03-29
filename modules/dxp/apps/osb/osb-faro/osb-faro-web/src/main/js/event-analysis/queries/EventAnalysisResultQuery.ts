import {
	Breakdown,
	BreakdownDataItem,
	CalculationTypes,
	Filter
} from '../utils/types';
import {gql} from 'apollo-boost';

export interface EventAnalysisResultData {
	count: number;
	page: number;
	value: number;
	breakdownItems: BreakdownDataItem[];
}

export interface EventAnalysisResultVariables {
	analysisType: CalculationTypes;
	channelId: string;
	compareToPrevious: boolean;
	eventAnalysisBreakdowns?: Breakdown[];
	eventAnalysisFilters?: Filter[];
	eventDefinitionId: string;
	page: number;
	rangeEnd: string;
	rangeKey: number | null;
	rangeStart: string;
	size: number;
}
const BREAKDOWN_FIELDS = gql`
	fragment BreakdownFields on BreakdownItem {
		leafNode
		name
		previousValue
		value
	}
`;

const BREAKDOWN_FRAGMENT_RECURSIVE = gql`
	${BREAKDOWN_FIELDS}
	fragment BreakdownFragment on BreakdownItem {
		breakdownItems {
			...BreakdownFields
			breakdownItems {
				...BreakdownFields
				breakdownItems {
					...BreakdownFields
					breakdownItems {
						...BreakdownFields
					}
				}
			}
		}
	}
`;

export default gql`
	query EventAnalysisResult(
		$analysisType: AnalysisType!
		$channelId: String!
		$compareToPrevious: Boolean!
		$eventAnalysisBreakdowns: [EventAnalysisBreakdownInput]
		$eventAnalysisFilters: [EventAnalysisFilterInput]
		$eventDefinitionId: String!
		$page: Int!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
	) {
		eventAnalysisResult(
			analysisType: $analysisType
			channelId: $channelId
			compareToPrevious: $compareToPrevious
			eventAnalysisBreakdowns: $eventAnalysisBreakdowns
			eventAnalysisFilters: $eventAnalysisFilters
			eventDefinitionId: $eventDefinitionId
			page: $page
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
		) {
			breakdownItems {
				...BreakdownFields
				...BreakdownFragment
			}
			count
			page
			previousValue
			value
		}
	}
	${BREAKDOWN_FRAGMENT_RECURSIVE}
`;
