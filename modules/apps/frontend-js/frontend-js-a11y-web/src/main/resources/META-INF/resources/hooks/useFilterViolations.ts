/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useMemo, useReducer} from 'react';

import type {ImpactValue} from 'axe-core';

import type {NodeViolations, Violations} from './useA11y';

export const TYPES = {
	CATEGORY_ADD: 'CATEGORY_ADD',
	CATEGORY_REMOVE: 'CATEGORY_REMOVE',
	IMPACT_ADD: 'IMPACT_ADD',
	IMPACT_REMOVE: 'IMPACT_REMOVE',
} as const;

type TAction = {
	payload: {value: string};
	type: keyof typeof TYPES;
};

type TState = {
	selectedCategories: Array<string> | [];
	selectedImpact: Array<ImpactValue>;
};

const initialState: TState = {
	selectedCategories: [],
	selectedImpact: [],
};

function reducer(state: TState, action: TAction) {
	const {selectedCategories, selectedImpact} = state;

	const {value} = action.payload;

	switch (action.type) {
		case TYPES.CATEGORY_ADD:
			return {
				...state,
				selectedCategories: [...selectedCategories, value],
			};
		case TYPES.CATEGORY_REMOVE:
			return {
				...state,
				selectedCategories: selectedCategories.filter(
					(currentItem) => currentItem !== value
				),
			};
		case TYPES.IMPACT_ADD:
			return {
				...state,
				selectedImpact: [...selectedImpact, value] as ImpactValue[],
			};
		case TYPES.IMPACT_REMOVE:
			return {
				...state,
				selectedImpact: selectedImpact.filter(
					(currentItem) => currentItem !== value
				) as ImpactValue[],
			};
		default:
			return state;
	}
}

export function useFilterViolations(value: Violations) {
	const [{selectedCategories, selectedImpact}, dispatch] = useReducer(
		reducer,
		initialState
	);

	const rules = useMemo(() => {
		if (!selectedCategories.length && !selectedImpact.length) {
			return value.rules;
		}

		return Object.values(value.rules).reduce(
			(prev, {impact, tags, ...otherProps}) => {
				const hasCategory = selectedCategories.some((category) =>
					tags.includes(category)
				);

				if (
					hasCategory ||
					(impact && selectedImpact.includes(impact))
				) {
					prev[otherProps.id] = {impact, tags, ...otherProps};
				}

				return prev;
			},
			{} as Violations['rules']
		);
	}, [selectedCategories, selectedImpact, value]);

	const nodes = useMemo(() => {
		if (!selectedCategories.length && !selectedImpact.length) {
			return value.nodes;
		}

		const newNodes: Record<string, NodeViolations> = {};

		for (const property in value.nodes) {
			const rulesResults = value.nodes[property];
			const rulesIds = Object.keys(rulesResults);

			const results = Object.values(rulesResults).reduce(
				(prevResults, results, index) => {
					const ruleId = rulesIds[index];

					if (rules[ruleId]) {
						prevResults[ruleId] = results;
					}

					return prevResults;
				},
				{} as NodeViolations
			);

			if (Object.keys(results).length) {
				newNodes[property] = results;
			}
		}

		return newNodes;
	}, [selectedCategories, selectedImpact, rules, value.nodes]);

	return [
		{selectedCategories, selectedImpact, violations: {nodes, rules}},
		dispatch,
	] as const;
}
