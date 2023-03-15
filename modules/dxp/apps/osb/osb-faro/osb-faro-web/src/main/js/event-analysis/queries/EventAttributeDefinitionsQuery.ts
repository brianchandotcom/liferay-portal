import {Attribute, AttributeTypes} from '../utils/types';
import {gql} from 'apollo-boost';
import {Sort} from 'shared/types';

export interface EventAttributeDefinitionsData {
	eventAttributeDefinitions: Attribute[];
	total: number;
}

export interface EventAttributeDefinitionsVariables {
	eventDefinitionId?: string;
	keyword?: string;
	page?: number;
	size: number;
	sort: Sort;
	type: AttributeTypes;
}

export default gql`
	query EventAttributeDefinitions(
		$eventDefinitionId: String
		$keyword: String
		$page: Int!
		$size: Int!
		$sort: Sort!
		$type: EventAttributeDefinitionType!
	) {
		eventAttributeDefinitions(
			eventDefinitionId: $eventDefinitionId
			keyword: $keyword
			page: $page
			size: $size
			sort: $sort
			type: $type
		) {
			eventAttributeDefinitions {
				dataType
				description
				displayName
				id
				name
				sampleValue
				type
			}
			total
		}
	}
`;
