import {gql} from 'apollo-boost';

export interface EventAttributeValuesData {
	eventAttributeValues: string[];
	total: number;
}

export interface EventAttributeValuesVariables {
	channelId: string;
	eventAttributeDefinitionId: string;
	eventDefinitionId: string;
	keywords: string;
	start: number;
	size: number;
}

export default gql`
	query EventAttributeValues(
		$channelId: String!
		$eventAttributeDefinitionId: String!
		$eventDefinitionId: String!
		$keywords: String!
		$size: Int!
		$start: Int!
	) {
		eventAttributeValues(
			channelId: $channelId
			eventAttributeDefinitionId: $eventAttributeDefinitionId
			eventDefinitionId: $eventDefinitionId
			keywords: $keywords
			size: $size
			start: $start
		) {
			eventAttributeValues
			total
		}
	}
`;
