/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useReducer} from 'react';

import {Individuals, RangeSelectors} from './types/global';

type State = {
	changeIndividualFilter: (value: any) => void;
	changeRangeSelectorFilter: (value: any) => void;
	filters: {
		individual: Individuals;
		rangeSelector: RangeSelectors;
	};
};

enum Actions {
	ChangeIndividualFilter = 'CHANGE_INDIVIDUAL_FILTER',
	ChangeRangeSelectorFilter = 'CHANGE_RANGE_SELECTOR_FILTER',
}

type Action = {
	payload: any;
	type: Actions;
};

const initialState: State = {
	changeIndividualFilter: () => {},
	changeRangeSelectorFilter: () => {},
	filters: {
		individual: Individuals.AllIndividuals,
		rangeSelector: RangeSelectors.Last30Days,
	},
};

export const AnalyticsReportsContext = createContext(initialState);

AnalyticsReportsContext.displayName = 'AnalyticsReportsContext';

const reducer = (state: State, action: Action): State => {
	switch (action.type) {
		case Actions.ChangeIndividualFilter: {
			return {
				...state,
				filters: {
					...state.filters,
					individual: action.payload,
				},
			};
		}

		case Actions.ChangeRangeSelectorFilter: {
			return {
				...state,
				filters: {
					...state.filters,
					rangeSelector: action.payload,
				},
			};
		}

		default: {
			throw new Error('Unknown Action');
		}
	}
};

const AnalyticsReportsProvider: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	const changeIndividualFilter = (payload: any) => {
		dispatch({
			payload,
			type: Actions.ChangeIndividualFilter,
		});
	};

	const changeRangeSelectorFilter = (payload: any) => {
		dispatch({
			payload,
			type: Actions.ChangeRangeSelectorFilter,
		});
	};

	return (
		<AnalyticsReportsContext.Provider
			value={{
				...state,
				changeIndividualFilter,
				changeRangeSelectorFilter,
			}}
		>
			{children}
		</AnalyticsReportsContext.Provider>
	);
};

export {AnalyticsReportsProvider};
