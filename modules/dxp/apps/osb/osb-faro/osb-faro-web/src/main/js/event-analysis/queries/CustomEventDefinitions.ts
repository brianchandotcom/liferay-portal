import {gql} from '@apollo/client';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface BlockCustomEventDefinitionsData {}

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
