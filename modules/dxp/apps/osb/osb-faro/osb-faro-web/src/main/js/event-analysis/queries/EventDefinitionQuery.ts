import {Event} from '../utils/types';
import {gql} from 'apollo-boost';

export interface EventDefinitionData {
	eventDefinition: Event;
}

export interface EventDefinitionVariables {
	displayName?: string;
	id?: string;
}

export default gql`
	query EventDefinition($displayName: String, $id: String) {
		eventDefinition(displayName: $displayName, id: $id) {
			description
			displayName
			eventAttributeDefinitions {
				dataType
				description
				displayName
				id
				name
				sampleValue
			}
			id
			name
			type
		}
	}
`;

export interface UpdateEventDefinitionVariables {
	description: string;
	displayName?: string;
	id?: string;
}

export const UPDATE_EVENT_DEFINITION = gql`
	mutation UpdateEventDefinition(
		$description: String
		$displayName: String
		$id: String!
	) {
		updateEventDefinition(
			description: $description
			displayName: $displayName
			eventDefinitionId: $id
		) {
			description
			displayName
			eventAttributeDefinitions {
				dataType
				description
				displayName
				id
				name
				sampleValue
			}
			id
			name
			type
		}
	}
`;
