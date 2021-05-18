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
	selectedCategories: [],
	selectedImpact: [],
};

function violationsReducer(state = INITIAL_STATE, action) {
	let nextState = state;

	switch (action.type) {
		case TYPES.CATEGORY_ADD:
			nextState = {
				...state,
				selectedCategories: [
					...state.selectedCategories,
					action.payload,
				],
			};
			break;
		case TYPES.CATEGORY_REMOVE:
			nextState = {
				...state,
				selectedCategories: state.selectedCategories.filter(
					(currentItem) => currentItem !== action.payload
				),
			};
			break;
		case TYPES.IMPACT_ADD:
			nextState = {
				...state,
				selectedImpact: [...state.selectedImpact, action.payload],
			};
			break;
		case TYPES.IMPACT_REMOVE:
			nextState = {
				...state,
				selectedImpact: state.selectedImpact.filter(
					(currentItem) => currentItem !== action.payload
				),
			};
			break;
		default:
			return state;
	}

	return nextState;
}

const noop = () => {};

const FilteredViolationsDispatchContext = createContext(noop);

const FilteredViolationsValueContext = createContext(INITIAL_STATE);

export const useFilteredViolationsValues = () =>
	useContext(FilteredViolationsValueContext);

export const useFilteredViolationsDispatch = () =>
	useContext(FilteredViolationsDispatchContext);

export function FilteredViolationsContextProvider({children, value}) {
	const [state, dispatch] = useReducer(violationsReducer, {
		...INITIAL_STATE,
		...value,
	});

	return (
		<FilteredViolationsDispatchContext.Provider value={dispatch}>
			<FilteredViolationsValueContext.Provider value={state}>
				{children}
			</FilteredViolationsValueContext.Provider>
		</FilteredViolationsDispatchContext.Provider>
	);
}
