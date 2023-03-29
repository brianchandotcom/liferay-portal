import {gql} from 'apollo-boost';

export interface BlockCustomEventDefinitionsData {
	null;
}

export interface BlockCustomEventDefinitionsVariables {
	eventDefinitionIds: string[];
}

export const BlockCustomEventDefinitions = gql`
	mutation BlockCustomEventDefinitions($eventDefinitionIds: [String]!) {
		blockCustomEventDefinitions(eventDefinitionIds: $eventDefinitionIds)
	}
`;

export const UnblockCustomEventDefinitions = gql`
	mutation UnblockCustomEventDefinitions($eventDefinitionIds: [String]!) {
		unblockCustomEventDefinitions(eventDefinitionIds: $eventDefinitionIds)
	}
`;
