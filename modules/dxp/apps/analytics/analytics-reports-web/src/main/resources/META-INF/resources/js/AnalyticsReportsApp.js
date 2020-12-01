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

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useReducer} from 'react';

import Navigation from './components/Navigation';
import {ChartStateContextProvider} from './context/ChartStateContext';
import ConnectionContext from './context/ConnectionContext';
import {StoreContextProvider} from './context/store';

import '../css/analytics-reports-app.scss';

const initialState = {
	data: null,
	error: null,
	loading: false,
};

const dataReducer = (state, action) => {
	switch (action.type) {
		case 'LOAD_DATA':
			return {
				...state,
				loading: true,
			};

		case 'SET_ERROR':
			return {
				...state,
				error: action.error,
				loading: false,
			};

		case 'SET_DATA':
			return {
				data: {
					...action.data,
					publishedToday:
						new Date().toDateString() ===
						new Date(action.data?.publishDate).toDateString(),
				},
				error: action.data?.error,
				loading: false,
			};

		default:
			return initialState;
	}
};

export default function ({context}) {
	const {analyticsReportsDataURL} = context;

	const isMounted = useIsMounted();

	const [state, dispatch] = useReducer(dataReducer, initialState);

	const safeDispatch = (action) => {
		if (isMounted()) {
			dispatch(action);
		}
	};

	const getData = (fetchURL, timeSpanKey, timeSpanOffset) => {
		safeDispatch({type: 'LOAD_DATA'});

		const body =
			!timeSpanOffset && !!timeSpanKey
				? {timeSpanKey, timeSpanOffset}
				: {};

		fetch(fetchURL, {
			body,
			method: 'POST',
		})
			.then((response) =>
				response.json().then((data) =>
					safeDispatch({
						data: data.context,
						type: 'SET_DATA',
					})
				)
			)
			.catch(() => {
				safeDispatch({
					error: Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'SET_ERROR',
				});
			});
	};

	useEffect(() => {
		getData(analyticsReportsDataURL);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [analyticsReportsDataURL]);

	const handleSelectedLanguageClick = useCallback(
		(url, timeSpanKey, timeSpanOffset) => {
			getData(url, timeSpanKey, timeSpanOffset);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return state.loading ? (
		<ClayLoadingIndicator small />
	) : state.error ? (
		<ClayAlert displayType="danger" variant="stripe">
			{state.error}
		</ClayAlert>
	) : (
		state.data && (
			<ConnectionContext.Provider
				value={{
					validAnalyticsConnection:
						state.data.validAnalyticsConnection,
				}}
			>
				<StoreContextProvider
					value={{
						publishedToday: state.data.publishedToday,
					}}
				>
					<ChartStateContextProvider
						publishDate={state.data.publishDate}
						timeSpanKey={state.data.timeSpanKey}
					>
						<div className="analytics-reports-app">
							<Navigation
								author={state.data.author}
								canonicalURL={state.data.canonicalURL}
								endpoints={state.data.endpoints}
								languageTag={state.data.languageTag}
								namespace={state.data.namespace}
								onSelectedLanguageClick={
									handleSelectedLanguageClick
								}
								page={state.data.page}
								pagePublishDate={state.data.publishDate}
								pageTitle={state.data.title}
								timeRange={state.data.timeRange}
								timeSpanOptions={state.data.timeSpans}
								viewURLs={state.data.viewURLs}
							/>
						</div>
					</ChartStateContextProvider>
				</StoreContextProvider>
			</ConnectionContext.Provider>
		)
	);
}
