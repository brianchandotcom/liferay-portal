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
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useState} from 'react';

import {useChartState} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';
import {StoreStateContext} from '../context/StoreContext';
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
	const {endpoints, namespace, page, publishedToday, warning} = useContext(
		StoreStateContext
	);

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [trafficSources, setTrafficSources] = useState([]);

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const chartState = useChartState();

	const {timeSpanKey, timeSpanOffset} = chartState;

	const handleCurrentPage = useCallback((currentPage) => {
		setCurrentPage({view: currentPage.view});
	}, []);

	const handleHistoricalReads = useCallback(() => {
		return APIService.getHistoricalReads(
			endpoints.analyticsReportsHistoricalReadsURL,
			{namespace, plid: page.plid, timeSpanKey, timeSpanOffset}
		).then((response) => response);
	}, [
		endpoints.analyticsReportsHistoricalReadsURL,
		namespace,
		page.plid,
		timeSpanKey,
		timeSpanOffset,
	]);

	const handleHistoricalViews = useCallback(() => {
		return APIService.getHistoricalReads(
			endpoints.analyticsReportsHistoricalViewsURL,
			{namespace, plid: page.plid, timeSpanKey, timeSpanOffset}
		).then((response) => response);
	}, [
		endpoints.analyticsReportsHistoricalViewsURL,
		namespace,
		page.plid,
		timeSpanKey,
		timeSpanOffset,
	]);

	const handleTotalReads = useCallback(() => {
		return APIService.getTotalReads(
			endpoints.analyticsReportsTotalReadsURL,
			{namespace, plid: page.plid}
		).then(({analyticsReportsTotalReads}) => analyticsReportsTotalReads);
	}, [endpoints.analyticsReportsTotalReadsURL, namespace, page.plid]);

	const handleTotalViews = useCallback(() => {
		return APIService.getTotalReads(
			endpoints.analyticsReportsTotalViewsURL,
			{namespace, plid: page.plid}
		).then(({analyticsReportsTotalViews}) => analyticsReportsTotalViews);
	}, [endpoints.analyticsReportsTotalViewsURL, namespace, page.plid]);

	const handleTrafficSources = useCallback(() => {
		return APIService.getTrafficSources(
			endpoints.analyticsReportsTrafficSourcesURL,
			{namespace, plid: page.plid}
		).then(({trafficSources}) => trafficSources);
	}, [endpoints.analyticsReportsTrafficSourcesURL, namespace, page.plid]);

	const handleTrafficSourceClick = (trafficSources, trafficSourceName) => {
		setTrafficSources(trafficSources);
		setTrafficSourceName(trafficSourceName);

		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource.name === trafficSourceName;
		});

		setCurrentPage({
			data: trafficSource,
			view: trafficSource.name,
		});
	};

	const handleTrafficSourceName = (trafficSourceName) =>
		setTrafficSourceName(trafficSourceName);

	const handleTrafficShare = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource.name === trafficSourceName;
		});

		return Promise.resolve(trafficSource?.share ?? '-');
	}, [trafficSourceName, trafficSources]);

	const handleTrafficVolume = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource.name === trafficSourceName;
		});

		return Promise.resolve(trafficSource?.value ?? '-');
	}, [trafficSourceName, trafficSources]);

	return (
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
						chartDataProviders={
							endpoints.analyticsReportsHistoricalReadsURL
								? [handleHistoricalViews, handleHistoricalReads]
								: [handleHistoricalViews]
						}
						onSelectedLanguageClick={onSelectedLanguageClick}
						onTrafficSourceClick={handleTrafficSourceClick}
						pagePublishDate={pagePublishDate}
						pageTitle={pageTitle}
						timeSpanOptions={timeSpanOptions}
						totalReadsDataProvider={
							endpoints.analyticsReportsTotalReadsURL &&
							handleTotalReads
						}
						totalViewsDataProvider={handleTotalViews}
						trafficSourcesDataProvider={handleTrafficSources}
						viewURLs={viewURLs}
					/>
				</div>
			)}

			{currentPage.view !== 'main' && (
				<Detail
					currentPage={currentPage}
					onCurrentPageChange={handleCurrentPage}
					onTrafficSourceNameChange={handleTrafficSourceName}
					timeSpanOptions={timeSpanOptions}
					trafficShareDataProvider={handleTrafficShare}
					trafficVolumeDataProvider={handleTrafficVolume}
				/>
			)}
		</>
	);
}

Navigation.propTypes = {
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
