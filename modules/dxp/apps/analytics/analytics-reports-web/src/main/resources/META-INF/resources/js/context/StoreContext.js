/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {createContext, useReducer} from 'react';

const ADD_WARNING = 'ADD_WARNING';
const SET_METRICS = 'SET_METRICS';
const SET_SELECTED_TRAFFIC_SOURCE_NAME = 'SET_SELECTED_TRAFFIC_SOURCE_NAME';

const INITIAL_STATE = {
	loading: true,
	publishedToday: false,
	selectedTrafficSourceName: '',
	warning: false,
};

const noop = () => {};

export const StoreDispatchContext = createContext(() => {});
export const StoreStateContext = createContext([INITIAL_STATE, noop]);

function reducer(state = INITIAL_STATE, action) {
	let nextState = state;

	switch (action.type) {
		case ADD_WARNING:
			nextState = state.warning ? state : {...state, warning: true};
			break;
		case SET_METRICS:
			nextState = {
				...state,
				historicalReads: {
					analyticsReportsHistoricalReads:
						action.payload?.analyticsReportsHistoricalReads,
				},
				historicalViews: {
					analyticsReportsHistoricalViews:
						action.payload?.analyticsReportsHistoricalViews,
				},
				loading: false,
				totalReads: action.payload?.analyticsReportsTotalReads,
				totalViews: action.payload.analyticsReportsTotalViews,
				trafficSources: action.payload.trafficSources,
			};
			break;
		case SET_SELECTED_TRAFFIC_SOURCE_NAME: {
			const selectedTrafficSourceName = action.selectedTrafficSourceName;

			if (selectedTrafficSourceName === '') {
				nextState = {
					...state,
					selectedTrafficSourceName,
					trafficShare: undefined,
					trafficVolume: undefined,
				};
			}
			else {
				const trafficSource = state.trafficSources.find(
					(trafficSource) =>
						trafficSource.name === selectedTrafficSourceName
				);

				nextState = {
					...state,
					selectedTrafficSourceName,
					trafficShare: trafficSource?.share ?? '-',
					trafficVolume: trafficSource?.value ?? '-',
				};
			}
			break;
		}
		default:
			return state;
	}

	return nextState;
}

export function StoreContextProvider({children, value}) {
	const [state, dispatch] = useReducer(reducer, {...INITIAL_STATE, ...value});

	return (
		<StoreDispatchContext.Provider value={dispatch}>
			<StoreStateContext.Provider value={state}>
				{children}
			</StoreStateContext.Provider>
		</StoreDispatchContext.Provider>
	);
}
