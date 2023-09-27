/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useReducer} from 'react';

import {ChartStateContextProvider} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';
import {StoreContextProvider} from '../context/StoreContext';
import {dataReducer, initialState} from '../context/dataReducer';
import APIService from '../utils/APIService';
import ConnectToAC from './ConnectToAC';
import Navigation from './Navigation';

import '../../css/analytics-reports-app.scss';

export default function AnalyticsReports({
	analyticsReportsDataURL,
	hoverOrFocusEventTriggered,
	isPanelStateOpen,
}) {
	const isMounted = useIsMounted();

	const [state, dispatch] = useReducer(dataReducer, initialState);

	const safeDispatch = useCallback(
		(action) => {
			if (isMounted()) {
				dispatch(action);
			}
		},
		[isMounted]
	);

	const getData = useCallback(
		(fetchURL, timeSpanKey, timeSpanOffset) => {
			safeDispatch({type: 'LOAD_DATA'});

			const body =
				!timeSpanOffset && !!timeSpanKey
					? {timeSpanKey, timeSpanOffset}
					: {};

			APIService.getAnalyticsReportsData(fetchURL, body)
				.then((data) =>
					safeDispatch({
						data: data.context,
						type: 'SET_DATA',
					})
				)
				.catch(() => {
					safeDispatch({
						error: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'SET_ERROR',
					});
				});
		},
		[safeDispatch]
	);

	useEffect(() => {
		if (hoverOrFocusEventTriggered && !state.data) {
			getData(analyticsReportsDataURL);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [hoverOrFocusEventTriggered]);

	useEffect(() => {
		if (isPanelStateOpen && !state.data && !state.loading) {
			getData(analyticsReportsDataURL);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isPanelStateOpen]);

	const handleSelectedLanguageClick = useCallback(
		(url, timeSpanKey, timeSpanOffset) => {
			getData(url, timeSpanKey, timeSpanOffset);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	const {data, error, loading} = state;

	return loading ? (
		<ClayLoadingIndicator small />
	) : error ? (
		<ClayAlert displayType="danger" variant="stripe">
			{error}
		</ClayAlert>
	) : (
		data && (
			<ConnectionContext.Provider
				value={{
					validAnalyticsConnection:
						data?.analyticsData.hasValidConnection,
				}}
			>
				<StoreContextProvider
					value={{
						endpoints: {...data?.endpoints},
						languageTag: data?.languageTag,
						namespace: data?.namespace,
						page: data?.page,
						publishedToday: data?.publishedToday,
					}}
				>
					<ChartStateContextProvider
						publishDate={data?.publishDate}
						timeRange={data?.timeRange}
						timeSpanKey={data?.timeSpanKey}
					>
						{data?.analyticsData.isSynced ? (
							<div className="analytics-reports-app">
								<Navigation
									author={data?.author}
									canonicalURL={data?.canonicalURL}
									onSelectedLanguageClick={
										handleSelectedLanguageClick
									}
									pagePublishDate={data?.publishDate}
									pageTitle={data?.title}
									timeSpanOptions={data?.timeSpans}
									viewURLs={data?.viewURLs}
								/>
							</div>
						) : (
							<ConnectToAC
								analyticsCloudTrialURL={
									data?.analyticsData.cloudTrialURL
								}
								analyticsURL={data?.analyticsData.url}
								hideAnalyticsReportsPanelURL={
									data?.hideAnalyticsReportsPanelURL
								}
								isAnalyticsConnected={
									data?.analyticsData.hasValidConnection
								}
								pathToAssets={data?.pathToAssets}
							/>
						)}
					</ChartStateContextProvider>
				</StoreContextProvider>
			</ConnectionContext.Provider>
		)
	);
}

AnalyticsReports.propTypes = {
	analyticsReportsDataURL: PropTypes.string.isRequired,
	hoverOrFocusEventTriggered: PropTypes.bool.isRequired,
	isPanelStateOpen: PropTypes.bool.isRequired,
};
