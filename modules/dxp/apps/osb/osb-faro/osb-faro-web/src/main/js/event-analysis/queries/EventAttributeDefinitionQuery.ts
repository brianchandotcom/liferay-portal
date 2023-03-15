import {Attribute} from '../utils/types';
import {DataTypes} from 'event-analysis/utils/types';
import {gql} from 'apollo-boost';

export interface EventAttributeDefinitionData {
	eventDefinition: Attribute;
}

export interface EventAttributeDefinitionVariables {
	displayName?: string;
	id?: string;
}

export default gql`
	query EventAttributeDefinition($displayName: String, $id: String) {
		eventAttributeDefinition(displayName: $displayName, id: $id) {
			dataType
			description
			displayName
			id
			name
		}
	}
`;

export const EVENT_ATTRIBUTE_DEFINITION_WITH_RECENT_VALUES_QUERY = gql`
	query EventAttributeDefinition($displayName: String, $id: String) {
		eventAttributeDefinition(displayName: $displayName, id: $id) {
			dataType
			description
			displayName
			id
			name
			recentValues {
				lastSeenDate
				value
			}
		}
	}
`;

export interface UpdateEventAttributeDefinitionVariables {
	dataType: DataTypes;
	description: string;
	displayName?: string;
	id?: string;
}

export const UPDATE_EVENT_ATTRIBUTE_DEFINITION = gql`
	mutation UpdateEventAttributeDefinition(
		$dataType: EventAttributeDefinitionDataType
		$description: String
		$displayName: String
		$id: String!
	) {
		updateEventAttributeDefinition(
			dataType: $dataType
			description: $description
			displayName: $displayName
			eventAttributeDefinitionId: $id
		) {
			dataType
			description
			displayName
			id
			name
			recentValues {
				lastSeenDate
				value
			}
		}
	}
`;
