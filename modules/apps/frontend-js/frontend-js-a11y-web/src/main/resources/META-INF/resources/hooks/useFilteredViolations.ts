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

import React, {useMemo, useReducer} from 'react';

import type {ImpactValue, Result} from 'axe-core';

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

type TFunctionRenderProps = {
	dispatch: React.Dispatch<TAction>;
	filteredViolations: Array<Result>;
	selectedCategories: Array<string> | [];
	selectedImpact: Array<ImpactValue>;
};

type TState = Omit<TFunctionRenderProps, 'dispatch' | 'filteredViolations'>;

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

export function useFilteredViolations(violations: Array<Result>) {
	const [{selectedCategories, selectedImpact}, dispatch] = useReducer(
		reducer,
		initialState
	);

	const filteredViolations = useMemo(() => {
		if (!selectedCategories.length && !selectedImpact.length) {
			return violations;
		}

		return violations.filter(({impact, tags}) => {
			const hasCategory = selectedCategories.some((category) =>
				tags.includes(category)
			);

			if (impact) {
				return hasCategory || selectedImpact.includes(impact);
			}

			return hasCategory;
		});
	}, [violations, selectedCategories, selectedImpact]);

	return [
		{filteredViolations, selectedCategories, selectedImpact},
		dispatch,
	] as const;
}
