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

import React from 'react';
declare type TimeSpan = 'last-7-days' | 'last-30-days' | null;
declare type Histogram = Array<Record<string, string | number | null>>;
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
	timeRange: {
		endDate: string;
		startDate: string;
	} | null;
	timeSpanKey?: TimeSpan;
	timeSpanOffset: number;
}
declare type Action =
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
	timeRange: {
		endDate: string;
		startDate: string;
	};
	timeSpanKey: TimeSpan;
}
export declare const ChartDispatchContext: React.Context<React.Dispatch<
	Action
>>;
export declare const ChartStateContext: React.Context<State>;
export declare function ChartStateContextProvider({
	children,
	publishDate,
	timeRange,
	timeSpanKey,
}: ChartStateContextProps): JSX.Element;
export declare function useDateTitle():
	| {
			firstDate: null;
			lastDate: null;
	  }
	| {
			firstDate: Date;
			lastDate: Date;
	  };
export declare function useIsPreviousPeriodButtonDisabled(): boolean;
export {};
