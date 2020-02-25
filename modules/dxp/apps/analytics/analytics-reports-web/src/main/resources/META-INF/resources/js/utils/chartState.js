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

import {useReducer} from 'react';

const ADD_DATA_SET_ITEM = 'add-data-key';
const NEXT_TIME_SPAN = 'next-time-span';
const PREV_TIME_SPAN = 'previous-time-span';
const CHANGE_TIME_SPAN_OPTION = 'change-time-span-option';
const SET_LOADING = 'set-loading';

export const useChartState = ({defaultTimeSpanOption}) => {
	const [state, dispatch] = useReducer(reducer, {
		loading: true,
		timeSpanOffset: 0,
		timeSpanOption: defaultTimeSpanOption,
	});

	const actions = {
		addDataSetItem: payload => dispatch({payload, type: ADD_DATA_SET_ITEM}),
		changeTimeSpanOption: payload =>
			dispatch({payload, type: CHANGE_TIME_SPAN_OPTION}),
		nextTimeSpan: () => dispatch({type: NEXT_TIME_SPAN}),
		previousTimeSpan: () => dispatch({type: PREV_TIME_SPAN}),
		setLoading: () => dispatch({type: SET_LOADING}),
	};

	return {actions, state};
};

/**
 * {
		"loading": false,
		"timeSpanOffset": 1,
		"timeSpanOption": "last-7-days",
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
		case ADD_DATA_SET_ITEM:
			nextState = addDataSetItem(state, action.payload);
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
		case CHANGE_TIME_SPAN_OPTION:
			nextState = {
				...state,
				timeSpanOption: action.payload.key,
			};
			break;
		case SET_LOADING:
			nextState = {
				...state,
				loading: true,
			};
			break;
		default:
			state = nextState;
			break;
	}

	return nextState;
}

function transformDataToDataSet(
	key,
	data,
	previousDataset = {histogram: [], keyList: [], totals: []}
) {
	const result = mergeDataSets(data, previousDataset, key);

	return result;
}

function mergeDataSets(newData, previousDataSet, key) {
	const resultDataSet = {};

	resultDataSet.keyList = [...previousDataSet.keyList, key];

	resultDataSet.totals = {
		...previousDataSet.totals,
		[key]: newData.value,
	};

	const newFormattedHistogram = newData.histogram.map(h => ({
		[key]: h.value,
		label: h.key,
	}));

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
 * 		}>,
 * 		values: number
 * 	},
 * 	key: string
 * }
 */
function addDataSetItem(state, payload) {
	/**
	 * The dataSetItem is recognized as substitutive when the
	 * previous state was in loading state.
	 */
	const previousDataSet = state.loading === true ? undefined : state.dataSet;

	return {
		...state,
		dataSet: transformDataToDataSet(
			payload.key,
			payload.dataSetItem,
			previousDataSet
		),
		loading: false,
	};
}
