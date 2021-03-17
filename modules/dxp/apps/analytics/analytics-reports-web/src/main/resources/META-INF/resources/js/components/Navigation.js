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
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {useChartState} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import APIService from '../utils/APIService';
import Detail from './Detail';
import Main from './Main';

const noop = () => {};

export default function Navigation({
	author,
	canonicalURL,
	onSelectedLanguageClick = noop,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	viewURLs,
}) {
	const dispatch = useContext(StoreDispatchContext);

	const {
		endpoints,
		loading,
		namespace,
		page,
		publishedToday,
		trafficSources,
		warning,
	} = useContext(StoreStateContext);

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const {plid} = page;

	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const chartState = useChartState();

	const {timeSpanKey, timeSpanOffset} = chartState;

	useEffect(() => {
		const requests = Object.keys(endpoints).map((request) => {
			if (request === 'analyticsReportsHistoricalReadsURL') {
				return APIService.getHistoricalReads(
					endpoints.analyticsReportsHistoricalReadsURL,
					{
						namespace,
						plid,
						timeSpanKey,
						timeSpanOffset,
					}
				);
			}
			else if (request === 'analyticsReportsHistoricalViewsURL') {
				return APIService.getHistoricalViews(
					endpoints.analyticsReportsHistoricalViewsURL,
					{
						namespace,
						plid,
						timeSpanKey,
						timeSpanOffset,
					}
				);
			}
			else if (request === 'analyticsReportsTotalReadsURL') {
				return APIService.getTotalReads(
					endpoints.analyticsReportsTotalReadsURL,
					{namespace, plid}
				);
			}
			else if (request === 'analyticsReportsTotalViewsURL') {
				return APIService.getTotalViews(
					endpoints.analyticsReportsTotalViewsURL,
					{namespace, plid}
				);
			}
			else if (request === 'analyticsReportsTrafficSourcesURL') {
				return APIService.getTrafficSources(
					endpoints.analyticsReportsTrafficSourcesURL,
					{namespace, plid}
				);
			}
		});

		allSettled(requests).then((data) => {
			let addWarning = false;

			const metrics = data.reduce((result, {status, value}) => {
				if (status === 'fulfilled') {
					return {...result, ...value};
				}
				else {
					addWarning = true;

					return result;
				}
			}, {});

			if (addWarning) {
				dispatch({type: 'ADD_WARNING'});
			}

			dispatch({...metrics, type: 'SET_METRICS'});
		});
	}, [dispatch, endpoints, namespace, plid, timeSpanKey, timeSpanOffset]);

	const handleCurrentPage = useCallback((currentPage) => {
		setCurrentPage({view: currentPage.view});
	}, []);

	const handleTrafficSourceClick = (trafficSourceName) => {
		dispatch({
			selectedTrafficSourceName: trafficSourceName,
			type: 'SET_SELECTED_TRAFFIC_SOURCE_NAME',
		});

		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource.name === trafficSourceName;
		});

		setCurrentPage({
			data: trafficSource,
			view: trafficSource.name,
		});
	};

	return loading ? (
		<ClayLoadingIndicator small />
	) : (
		<>
			{!validAnalyticsConnection && (
				<ClayAlert displayType="danger" variant="stripe">
					{Liferay.Language.get('an-unexpected-error-occurred')}
				</ClayAlert>
			)}

			{validAnalyticsConnection && warning && (
				<ClayAlert displayType="warning" variant="stripe">
					{Liferay.Language.get(
						'some-data-is-temporarily-unavailable'
					)}
				</ClayAlert>
			)}

			{validAnalyticsConnection && publishedToday && !warning && (
				<ClayAlert
					displayType="info"
					title={Liferay.Language.get('no-data-is-available-yet')}
					variant="stripe"
				>
					{Liferay.Language.get('content-has-just-been-published')}
				</ClayAlert>
			)}

			{currentPage.view === 'main' && (
				<div>
					<Main
						author={author}
						canonicalURL={canonicalURL}
						onSelectedLanguageClick={onSelectedLanguageClick}
						onTrafficSourceClick={handleTrafficSourceClick}
						pagePublishDate={pagePublishDate}
						pageTitle={pageTitle}
						timeSpanOptions={timeSpanOptions}
						viewURLs={viewURLs}
					/>
				</div>
			)}

			{currentPage.view !== 'main' && (
				<Detail
					currentPage={currentPage}
					onCurrentPageChange={handleCurrentPage}
					timeSpanOptions={timeSpanOptions}
				/>
			)}
		</>
	);
}

function allSettled(promises) {
	return Promise.all(
		promises.map((promise) => {
			return promise
				.then((value) => {
					return {status: 'fulfilled', value};
				})
				.catch((reason) => {
					return {reason, status: 'rejected'};
				});
		})
	);
}

Navigation.proptypes = {
	author: PropTypes.object.isRequired,
	canonicalURL: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	pagePublishDate: PropTypes.string.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
	viewURLs: PropTypes.arrayOf(
		PropTypes.shape({
			default: PropTypes.bool.isRequired,
			languageId: PropTypes.string.isRequired,
			languageLabel: PropTypes.string.isRequired,
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};
