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

import React, {createContext, useContext, useReducer} from 'react';

export const TYPES = {
	CATEGORY_ADD: 'CATEGORY_ADD',
	CATEGORY_REMOVE: 'CATEGORY_REMOVE',
	IMPACT_ADD: 'IMPACT_ADD',
	IMPACT_REMOVE: 'IMPACT_REMOVE',
};

const INITIAL_STATE = {
	filteredViolations: {},
	selectedCategories: [],
	selectedImpact: [],
	violations: {},
};

function filterByCategories({receivedTags, selectedCategories}) {
	return selectedCategories.some((category) =>
		receivedTags.includes(category)
	);
}

function filterByImpact({receivedImpact, selectedImpact}) {
	return selectedImpact.includes(receivedImpact);
}

function addItem(list, value) {
	return [...list, value];
}

function removeItem(list, value) {
	return list.filter((currentItem) => currentItem !== value);
}

function violationsReducer(state = INITIAL_STATE, action) {
	const {selectedCategories, selectedImpact, violations} = state;

	const {value} = action.payload;

	switch (action.type) {
		case TYPES.CATEGORY_ADD: {
			const newSelectedCategories = addItem(selectedCategories, value);

			return {
				...state,
				filteredViolations: newSelectedCategories.length
					? violations.filter(({tags}) =>
							filterByCategories({
								receivedTags: tags,
								selectedCategories: newSelectedCategories,
							})
					  )
					: violations,
				selectedCategories: newSelectedCategories,
			};
		}
		case TYPES.CATEGORY_REMOVE: {
			const newSelectedCategories = removeItem(selectedCategories, value);

			return {
				...state,
				filteredViolations: newSelectedCategories.length
					? violations.filter(({tags}) =>
							filterByCategories({
								receivedTags: tags,
								selectedCategories: newSelectedCategories,
							})
					  )
					: violations,
				selectedCategories: newSelectedCategories,
			};
		}
		case TYPES.IMPACT_ADD: {
			const newImpactSelected = addItem(selectedImpact, value);

			return {
				...state,
				filteredViolations: newImpactSelected.length
					? violations.filter(({impact}) =>
							filterByImpact({
								receivedImpact: impact,
								selectedImpact: newImpactSelected,
							})
					  )
					: violations,
				selectedImpact: newImpactSelected,
			};
		}
		case TYPES.IMPACT_REMOVE: {
			const newImpactSelected = removeItem(selectedImpact, value);

			return {
				...state,
				filteredViolations: newImpactSelected.length
					? violations.filter(({impact}) =>
							filterByImpact({
								receivedImpact: impact,
								selectedImpact: newImpactSelected,
							})
					  )
					: violations,
				selectedImpact: newImpactSelected,
			};
		}
		default:
			return state;
	}
}

const FilteredViolationsDispatchContext = createContext();

export const useFilteredViolationsDispatch = () =>
	useContext(FilteredViolationsDispatchContext);

export function FilteredViolationsContextProvider({children, value}) {
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
