/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useReducer} from 'react';
type TimeSpan = 'last-7-days' | 'last-30-days' | null;
type Histogram = Array<Record<string, string | number | null>>;

interface DataSet {
	histogram: Histogram;
	keyList?: Array<string>;
	totals?: Record<string, number | null | undefined>;
	value?: number;
}

interface State {
	dataSet: DataSet;
	lineChartLoading: boolean;
	pieChartLoading: boolean;
	previousDataSet?: DataSet;
	publishDate: string | null;
	timeRange: {endDate: string; startDate: string} | null;
	timeSpanKey?: TimeSpan;
	timeSpanOffset: number;
}

type Action =
	| {
			payload: {
				loading: boolean;
			};
			type: 'SET_LOADING' | 'SET_PIE_CHART_LOADING';
	  }
	| {
			payload: {
				dataSetItems: Record<string, DataSet>;
				keys?: Set<string>;
				timeSpanComparator: number;
			};
			type: 'ADD_DATA_SET_ITEMS';
			validAnalyticsConnection: boolean;
	  }
	| {
			payload: {
				key: TimeSpan;
			};
			type: 'CHANGE_TIME_SPAN_KEY';
	  }
	| {
			type: 'NEXT_TIME_SPAN' | 'PREV_TIME_SPAN';
	  };

interface ChartStateContextProps {
	children: React.ReactNode;
	publishDate: string;
	timeRange: {endDate: string; startDate: string};
	timeSpanKey: TimeSpan;
}

interface Payload {
	dataSetItem: DataSet;
	dataSetItems: Record<string, DataSet>;
	key: string;
	timeSpanComparator?: number;
}

const INITIAL_STATE: State = {
	dataSet: {histogram: [], keyList: [], totals: {}},
	lineChartLoading: true,
	pieChartLoading: true,
	publishDate: null,
	timeRange: null,
	timeSpanKey: null,
	timeSpanOffset: 0,
};

const ADD_DATA_SET_ITEMS = 'ADD_DATA_SET_ITEMS' as const;
const CHANGE_TIME_SPAN_KEY = 'CHANGE_TIME_SPAN_KEY' as const;
const NEXT_TIME_SPAN = 'NEXT_TIME_SPAN' as const;
const PREV_TIME_SPAN = 'PREV_TIME_SPAN' as const;
const SET_LOADING = 'SET_LOADING' as const;
const SET_PIE_CHART_LOADING = 'SET_PIE_CHART_LOADING' as const;

const FALLBACK_DATA_SET_ITEM = {histogram: [], value: null};

const LAST_7_DAYS = 'last-7-days' as const;
const LAST_30_DAYS = 'last-30-days' as const;

export const ChartDispatchContext = createContext<React.Dispatch<Action>>(
	() => {}
);
export const ChartStateContext = createContext<State>(INITIAL_STATE);

function reducer(state: State, action: Action): State {
	let nextState: State;

	switch (action.type) {
		case ADD_DATA_SET_ITEMS:
			nextState = setLineChartLoadingState(state);
			if (action.payload.keys) {
				nextState = [...action.payload.keys].reduce((state, key) => {
					const dataSetItem: DataSet =
						action.payload.dataSetItems?.[key] ??
						FALLBACK_DATA_SET_ITEM;

					return addDataSetItem(
						state,
						{
							dataSetItem,
							dataSetItems: action.payload.dataSetItems,
							key,
						},
						action.validAnalyticsConnection
					);
				}, state);
			}

			break;
		case CHANGE_TIME_SPAN_KEY:
			nextState = {
				...state,
				lineChartLoading: true,
				timeSpanKey: action.payload.key,
				timeSpanOffset: 0,
			};
			break;
		case NEXT_TIME_SPAN:
			nextState = {
				...state,
				lineChartLoading: true,
				timeSpanOffset: state.timeSpanOffset - 1,
			};
			break;
		case PREV_TIME_SPAN:
			nextState = {
				...state,
				lineChartLoading: true,
				timeSpanOffset: state.timeSpanOffset + 1,
			};
			break;
		case SET_LOADING:
			nextState = setLineChartLoadingState(state);
			break;
		case SET_PIE_CHART_LOADING:
			nextState = {
				...state,
				pieChartLoading: Boolean(action.payload.loading),
			};
			break;
		default:
			nextState = state;
			break;
	}

	return nextState;
}

export function ChartStateContextProvider({
	children,
	publishDate,
	timeRange,
	timeSpanKey,
}: ChartStateContextProps) {
	const [state, dispatch] = useReducer<React.Reducer<State, Action>>(
		reducer,
		{
			...INITIAL_STATE,
			publishDate,
			timeRange,
			timeSpanKey,
		}
	);

	return (
		<ChartDispatchContext.Provider value={dispatch}>
			<ChartStateContext.Provider value={state}>
				{children}
			</ChartStateContext.Provider>
		</ChartDispatchContext.Provider>
	);
}

export function useDateTitle() {
	const {timeRange, timeSpanKey, timeSpanOffset} = useContext(
		ChartStateContext
	);

	if (!timeRange) {
		return {firstDate: null, lastDate: null};
	}

	const firstDate = new Date(timeRange.startDate.concat('T00:00:00'));
	const lastDate = new Date(timeRange.endDate.concat('T00:00:00'));

	const increment =
		timeSpanKey === LAST_7_DAYS ? 7 : timeSpanKey === LAST_30_DAYS ? 30 : 0;

	// Default interval between firstDate and lastDate is 7 days.
	// First date must be calculated from last date if timespan is 30.

	if (timeSpanKey === LAST_30_DAYS) {
		firstDate.setDate(firstDate.getDate() + 6 - (increment - 1));
	}

	if (timeSpanOffset && timeSpanOffset > 0) {
		lastDate.setDate(lastDate.getDate() - increment * timeSpanOffset);
		firstDate.setDate(firstDate.getDate() - increment * timeSpanOffset);

		return {
			firstDate,
			lastDate,
		};
	}
	else {
		return {
			firstDate,
			lastDate,
		};
	}
}

export function useIsPreviousPeriodButtonDisabled() {
	const {publishDate} = useContext(ChartStateContext);
	const {firstDate} = useDateTitle();

	if (!publishDate || !firstDate) {
		return false;
	}

	const publishedDate = publishDate && new Date(publishDate);

	return firstDate < publishedDate;
}

/**
 * Declares the state as loading and resets the dataSet histogram values
 */
function setLineChartLoadingState(state: State) {

	/**
	 * The dataSet does not need to be reset
	 */
	if (!state.dataSet) {
		return {...state, lineChartLoading: true};
	}

	/**
	 * The dataSet is already formatted
	 */
	if (state.dataSet.histogram.length) {
		return {...state, lineChartLoading: false};
	}

	const histogram = state.dataSet.histogram.map((set) => {
		const newSet: Record<string, string | null> = {};

		const setArray = (Object.entries(set) as unknown) as string[];

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

	const arrayTotals = state.dataSet.totals
		? ((Object.entries(state.dataSet.totals) as unknown) as Array<string>)
		: [];

	const totals: Record<string, number | null> = {};

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
		lineChartLoading: true,
	};

	return nextState;
}

interface MergeDataSetsParams {
	key: string;
	newData: DataSet;
	previousDataSet?: DataSet;
	publishDate: string | null;
	timeSpanComparator?: number;
	validAnalyticsConnection: boolean;
}

function mergeDataSets({
	key,
	newData,
	previousDataSet = {histogram: [], keyList: [], totals: {}},
	publishDate,
	timeSpanComparator,
	validAnalyticsConnection,
}: MergeDataSetsParams): DataSet {
	const resultDataSet: DataSet = {
		histogram: [],
		keyList: [...previousDataSet.keyList!, key],
		totals: {
			...previousDataSet.totals,
			[key]: newData.value,
		},
	};

	if (!publishDate) {
		return {
			...resultDataSet,
			histogram: previousDataSet.histogram,
		};
	}

	const publishDateObject = new Date(publishDate);
	const newFormattedHistogram: Histogram = newData.histogram.map((h) => {
		const valueDataObject = h.key ? new Date(h.key) : null;
		if (
			(valueDataObject &&
				timeSpanComparator &&
				valueDataObject < publishDateObject &&
				publishDateObject.getTime() - valueDataObject.getTime() >
					timeSpanComparator) ||
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

	if (!newFormattedHistogram.length) {
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

function addDataSetItem(
	state: State,
	payload: Payload,
	validAnalyticsConnection: boolean
): State {

	/**
	 * The dataSetItem is recognized as substitutive when the
	 * previous state was in loading state.
	 */
	const previousDataSet =
		state.lineChartLoading === true ? undefined : state.dataSet;

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
		lineChartLoading: false,
	};
}
