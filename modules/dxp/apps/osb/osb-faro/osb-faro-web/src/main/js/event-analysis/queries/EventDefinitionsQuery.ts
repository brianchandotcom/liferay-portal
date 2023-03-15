import {Event, EventTypes} from '../utils/types';
import {gql} from 'apollo-boost';

export interface EventDefinitionsData {
	eventDefinitions: {
		eventDefinitions: Event[];
		total: number;
	};
}

export interface EventDefinitionsVariables {
	eventType: EventTypes;
	keyword?: string;
	page?: number;
	size: number;
	sort: {
		column: string;
		type: 'ASC' | 'DESC';
	};
}

export default gql`
	query EventDefinitions(
		$eventType: EventDefinitionType!
		$hidden: Boolean
		$keyword: String
		$page: Int!
		$size: Int!
		$sort: Sort!
	) {
		eventDefinitions(
			eventType: $eventType
			hidden: $hidden
			keyword: $keyword
			page: $page
			size: $size
			sort: $sort
		) {
			eventDefinitions {
				description
				displayName
				hidden
				id
				name
				type
			}
			total
		}
	}
`;

export interface HideEventDefinitionsData {
	hideEventDefinitions: Event[];
}

export interface HideEventDefinitionsVariables {
	eventDefinitionIds: string[];
}

export const HideEventDefinitions = gql`
	mutation HideEventDefinitions($eventDefinitionIds: [String]!) {
		hideEventDefinitions(eventDefinitionIds: $eventDefinitionIds) {
			description
			displayName
			hidden
			id
			name
			type
		}
	}
`;

export interface UnhideEventDefinitionsData {
	unhideEventDefinitions: Event[];
}

export const UnhideEventDefinitions = gql`
	mutation UnhideEventDefinitions($eventDefinitionIds: [String]!) {
		unhideEventDefinitions(eventDefinitionIds: $eventDefinitionIds) {
			description
			displayName
			hidden
			id
			name
			type
		}
	}
`;
