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

import React, {Dispatch, createContext, useContext, useReducer} from 'react';

import type {ImpactValue, Result} from 'axe-core';

export const TYPES = {
	CATEGORY_ADD: 'CATEGORY_ADD',
	CATEGORY_REMOVE: 'CATEGORY_REMOVE',
	IMPACT_ADD: 'IMPACT_ADD',
	IMPACT_REMOVE: 'IMPACT_REMOVE',
} as const;

type TAction = {
	payload: {value: string};
	type: 'CATEGORY_ADD' | 'CATEGORY_REMOVE' | 'IMPACT_ADD' | 'IMPACT_REMOVE';
};

type TState = {
	filteredViolations: Array<Result>;
	selectedCategories: Array<String> | [];
	selectedImpact: Array<ImpactValue> | [];
	violations: Array<Result>;
};

const INITIAL_STATE: TState = {
	filteredViolations: [],
	selectedCategories: [],
	selectedImpact: [],
	violations: [],
};

function filterByCategories(
	receivedTags: Array<String>,
	selectedCategories: Array<String>
) {
	return selectedCategories.some((category) =>
		receivedTags.includes(category)
	);
}

function filterByImpact(
	receivedImpact: ImpactValue,
	selectedImpact: Array<ImpactValue>
) {
	return selectedImpact.includes(receivedImpact);
}

function addItem(list: Array<any>, value: any) {
	return [...list, value];
}

function removeItem(list: Array<any>, value: any) {
	return list.filter((currentItem) => currentItem !== value);
}

function violationsReducer(state = INITIAL_STATE, action: TAction) {
	const {selectedCategories, selectedImpact, violations} = state;

	const {value} = action.payload;

	switch (action.type) {
		case TYPES.CATEGORY_ADD: {
			const newSelectedCategories = addItem(selectedCategories, value);

			return {
				...state,
				filteredViolations: newSelectedCategories.length
					? violations.filter(({tags}) => {
							if (tags) {
								return filterByCategories(
									tags,
									newSelectedCategories
								);
							}
					  })
					: violations,
				selectedCategories: newSelectedCategories,
			};
		}
		case TYPES.CATEGORY_REMOVE: {
			const newSelectedCategories = removeItem(selectedCategories, value);

			return {
				...state,
				filteredViolations: newSelectedCategories.length
					? violations.filter(({tags}) => {
							if (tags) {
								return filterByCategories(
									tags,
									newSelectedCategories
								);
							}
					  })
					: violations,
				selectedCategories: newSelectedCategories,
			};
		}
		case TYPES.IMPACT_ADD: {
			const newImpactSelected = addItem(selectedImpact, value);

			return {
				...state,
				filteredViolations: newImpactSelected.length
					? violations.filter(({impact}) => {
							if (impact) {
								return filterByImpact(
									impact,
									newImpactSelected
								);
							}
					  })
					: violations,
				selectedImpact: newImpactSelected,
			};
		}
		case TYPES.IMPACT_REMOVE: {
			const newImpactSelected = removeItem(selectedImpact, value);

			return {
				...state,
				filteredViolations: newImpactSelected.length
					? violations.filter(({impact}) => {
							if (impact) {
								return filterByImpact(
									impact,
									newImpactSelected
								);
							}
					  })
					: violations,
				selectedImpact: newImpactSelected,
			};
		}
		default:
			return state;
	}
}

const FilteredViolationsDispatchContext = createContext(
	{} as Dispatch<TAction>
);

export const useFilteredViolationsDispatch = () =>
	useContext(FilteredViolationsDispatchContext);

type FilteredViolationsContextProviderProps = {
	children: (props: TState) => React.ReactNode;
	value: {
		filteredViolations: Array<Result>;
		violations: Array<Result>;
	};
};

export function FilteredViolationsContextProvider({
	children,
	value,
}: FilteredViolationsContextProviderProps) {
	const [state, dispatch] = useReducer(violationsReducer, {
		...INITIAL_STATE,
		...value,
	});

	return (
		<FilteredViolationsDispatchContext.Provider value={dispatch}>
			{children(state)}
		</FilteredViolationsDispatchContext.Provider>
	);
}
