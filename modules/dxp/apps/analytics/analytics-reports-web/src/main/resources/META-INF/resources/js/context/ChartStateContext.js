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

import React, {createContext, useCallback, useContext, useReducer} from 'react';

import ConnectionContext from './ConnectionContext';

const ADD_DATA_SET_ITEMS = 'add-data-keys';
const CHANGE_TIME_SPAN_KEY = 'change-time-span-key';
const NEXT_TIME_SPAN = 'next-time-span';
const PREV_TIME_SPAN = 'previous-time-span';
const SET_LOADING = 'set-loading';

const INITIAL_STATE = {
	dataSet: {histogram: [], keyList: [], totals: []},
	loading: true,
	publishDate: null,
	timeRange: null,
	timeSpanKey: null,
	timeSpanOffset: 0,
};

const FALLBACK_DATA_SET_ITEM = {histogram: [], value: null};

const ChartStateContext = createContext(INITIAL_STATE);

export const ChartStateContextProvider = ({
	children,
	publishDate,
	timeRange,
	timeSpanKey,
}) => {
	const stateAndDispatch = useReducer(reducer, {
		...INITIAL_STATE,
		publishDate,
		timeRange,
		timeSpanKey,
	});

	return (
		<ChartStateContext.Provider value={stateAndDispatch}>
			{children}
		</ChartStateContext.Provider>
	);
};

export const useAddDataSetItems = () => {
	const [, dispatch] = useContext(ChartStateContext);
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	return useCallback(
		(payload) =>
			dispatch({
				payload,
				type: ADD_DATA_SET_ITEMS,
				validAnalyticsConnection,
			}),
		[dispatch, validAnalyticsConnection]
	);
};

export const useChangeTimeSpanKey = () => {
	const [, dispatch] = useContext(ChartStateContext);

	return (payload) => dispatch({payload, type: CHANGE_TIME_SPAN_KEY});
};

export const useChartState = () => {
	const [state] = useContext(ChartStateContext);

	return state;
};

export function useDateTitle() {
	const [state] = useContext(ChartStateContext);

	const {histogram} = state.dataSet;
	const {timeRange} = state;

	if (histogram.length) {
		const firstDateLabel = histogram[0].label;
		const lastDateLabel = histogram[histogram.length - 1].label;

		return {
			firstDate: new Date(firstDateLabel),
			lastDate: new Date(lastDateLabel),
		};
	}
	else {
		return {
			firstDate: new Date(timeRange.startDate),
			lastDate: new Date(timeRange.endDate),
		};
	}
}

export function useIsPreviousPeriodButtonDisabled() {
	const [state] = useContext(ChartStateContext);

	const {histogram} = state.dataSet;
	const {publishDate} = state;

	if (histogram.length) {
		const firstDateLabel = histogram[0].label;

		const firstDate = new Date(firstDateLabel);
		const publishedDate = new Date(publishDate);

		return firstDate < publishedDate;
	}

	return true;
}

export const useNextTimeSpan = () => {
	const [, dispatch] = useContext(ChartStateContext);

	return () => dispatch({type: NEXT_TIME_SPAN});
};

export const useSetLoading = () => {
	const [, dispatch] = useContext(ChartStateContext);

	return useCallback(() => dispatch({type: SET_LOADING}), [dispatch]);
};

export const usePreviousTimeSpan = () => {
	const [, dispatch] = useContext(ChartStateContext);

	return () => dispatch({type: PREV_TIME_SPAN});
};

/**
 * {
		"loading": false,
		"timeSpanOffset": 1,
		"timeSpanKey": "last-7-days",
		"dataSet": {
			"keyList": [
				"analyticsReportsHistoricalReads",
				"analyticsReportsHistoricalViews"
			],
			"totals": {
				"analyticsReportsHistoricalReads": 225000,
				"analyticsReportsHistoricalViews": 144245
			},
			"histogram": [
				{
					"analyticsReportsHistoricalViews": 5412,
					"label": "2020-02-02T00:00",
					"analyticsReportsHistoricalReads": 5000
				},
				...
			]
		}
	}
 *
 */
function reducer(state, action) {
	let nextState;

	switch (action.type) {
		case ADD_DATA_SET_ITEMS:
			nextState = action.payload.keys.reduce((state, key) => {
				const dataSetItem =
					action.payload.dataSetItems?.[key] ??
					FALLBACK_DATA_SET_ITEM;

				return addDataSetItem(
					state,
					{
						...action.payload,
						dataSetItem,
						key,
					},
					action.validAnalyticsConnection
				);
			}, state);
			break;
		case CHANGE_TIME_SPAN_KEY:
			nextState = {
				...state,
				timeSpanKey: action.payload.key,
				timeSpanOffset: 0,
			};
			break;
		case NEXT_TIME_SPAN:
			nextState = {
				...state,
				timeSpanOffset: state.timeSpanOffset - 1,
			};
			break;
		case PREV_TIME_SPAN:
			nextState = {
				...state,
				timeSpanOffset: state.timeSpanOffset + 1,
			};
			break;
		case SET_LOADING:
			nextState = setLoadingState(state);
			break;
		default:
			state = nextState;
			break;
	}

	return nextState;
}

/**
 * Declares the state as loading and resets the dataSet histogram values
 */
function setLoadingState(state) {

	/**
	 * The dataSet does not need to be reset
	 */
	if (!state.dataSet) {
		return {...state, loading: true};
	}

	const histogram = state.dataSet.histogram.map((set) => {
		const newSet = {};

		const setArray = Object.entries(set);

		for (const index in setArray) {
			const [key, value] = setArray[index];

			if (key === 'label') {
				newSet[key] = value;
			}
			else {
				newSet[key] = null;
			}
		}

		return newSet;
	});

	const arrayTotals = Object.entries(state.dataSet.totals);

	const totals = {};

	for (const index in arrayTotals) {
		const [key] = arrayTotals[index];

		totals[key] = null;
	}

	const nextState = {
		...state,
		dataSet: {
			...state.dataSet,
			histogram,
			totals,
		},
		loading: true,
	};

	return nextState;
}

function mergeDataSets({
	key,
	newData,
	previousDataSet = {histogram: [], keyList: [], totals: []},
	publishDate,
	timeSpanComparator,
	validAnalyticsConnection,
}) {
	const resultDataSet = {
		keyList: [...previousDataSet.keyList, key],
		totals: {
			...previousDataSet.totals,
			[key]: newData.value,
		},
	};

	const publishDateObject = new Date(publishDate);

	const newFormattedHistogram = newData.histogram.map((h) => {
		const valueDataObject = new Date(h.key);

		if (
			(valueDataObject < publishDateObject &&
				publishDateObject - valueDataObject > timeSpanComparator) ||
			!validAnalyticsConnection
		) {
			return {
				[key]: null,
				label: h.key,
			};
		}

		return {
			[key]: h.value,
			label: h.key,
		};
	});

	if (newFormattedHistogram.length === 0) {
		return {
			...resultDataSet,
			histogram: previousDataSet.histogram,
		};
	}

	let start = 0;
	const mergeHistogram = [];

	while (start < newData.histogram.length) {
		if (!previousDataSet.histogram[start]) {
			mergeHistogram.push({
				...newFormattedHistogram[start],
			});
		}
		else if (
			newFormattedHistogram[start].label ===
			previousDataSet.histogram[start].label
		) {
			mergeHistogram.push({
				...newFormattedHistogram[start],
				...previousDataSet.histogram[start],
			});
		}

		start = start + 1;
	}

	resultDataSet.histogram = mergeHistogram;

	return resultDataSet;
}

/**
 * Adds dataSetItem to the dataSet
 *
 * payload = {
 * 	dataSet: {
 * 		histogram: Array<{
 *			key: string, // '2020-01-24T00:00'
 *			value: number
 * 		}>
 * 		values: number
 * 	},
 * 	key: string,
 *  timeSpanComparator: number,
 * }
 */
function addDataSetItem(state, payload, validAnalyticsConnection) {

	/**
	 * The dataSetItem is recognized as substitutive when the
	 * previous state was in loading state.
	 */
	const previousDataSet = state.loading === true ? undefined : state.dataSet;

	return {
		...state,
		dataSet: mergeDataSets({
			key: payload.key,
			newData: payload.dataSetItem,
			previousDataSet,
			publishDate: state.publishDate,
			timeSpanComparator: payload.timeSpanComparator,
			validAnalyticsConnection,
		}),
		loading: false,
	};
}
