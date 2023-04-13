import {pickBy} from 'lodash';
import {RangeKeyTimeRanges} from './constants';
import {RangeSelectors} from 'shared/types';

export type GQLQuery = {
	definitions: {
		variableDefinitions: {
			variable: {
				name: {
					value: string;
				};
			};
		}[];
	}[];
};

/**
 * Returns an object of variable keys used in the graphQL query.
 */
export const getVariableDefinitions = (gqlQuery: GQLQuery) =>
	gqlQuery.definitions.reduce((acc, {variableDefinitions}) => {
		variableDefinitions.forEach(({variable}) => {
			const {
				name: {value}
			} = variable;

			acc[value] = true;
		});

		return acc;
	}, {});

export const removeUnusedVariables = (variables, validVariables) =>
	pickBy(variables, (_, key) => validVariables[key]);

export const fetchPolicyDefinition = (rangeSelectors: RangeSelectors) =>
	rangeSelectors.rangeKey === RangeKeyTimeRanges.Last24Hours
		? 'network-only'
		: 'cache-first';
