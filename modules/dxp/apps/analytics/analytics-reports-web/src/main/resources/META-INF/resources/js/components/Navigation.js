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

import ConnectionContext from '../context/ConnectionContext';
import {StoreContext, useHistoricalWarning, useWarning} from '../context/store';
import Detail from './Detail';
import Main from './Main';

export default function Navigation({
	api,
	authorName,
	defaultTimeRange,
	defaultTimeSpanKey,
	languageTag,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	trafficSources,
}) {
	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [hasWarning] = useWarning();

	const [hasHistoricalWarning] = useHistoricalWarning();

	const {getHistoricalReads, getHistoricalViews} = api;

	const handleCurrentPage = useCallback((currentPage) => {
		setCurrentPage({view: currentPage.view});
	}, []);

	const handleTotalReads = useCallback(() => {
		return api
			.getTotalReads()
			.then((response) => response.analyticsReportsTotalReads);
	}, [api]);

	const handleTotalViews = useCallback(() => {
		return api
			.getTotalViews()
			.then((response) => response.analyticsReportsTotalViews);
	}, [api]);

	const handleTrafficShare = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource['name'] === trafficSourceName;
		});

		return Promise.resolve(trafficSource?.share ?? '-');
	}, [trafficSourceName, trafficSources]);

	const handleTrafficSourceClick = (trafficSourceName) => {
		setTrafficSourceName(trafficSourceName);

		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource['name'] === trafficSourceName;
		});

		setCurrentPage({
			data: trafficSource,
			view: 'traffic-source-detail',
		});
	};

	const handleTrafficSourceName = useCallback((trafficSourceName) => {
		setTrafficSourceName(trafficSourceName);
	}, []);

	const handleTrafficVolume = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource['name'] === trafficSourceName;
		});

		return Promise.resolve(trafficSource?.value ?? '-');
	}, [trafficSourceName, trafficSources]);

	const [{publishedToday}] = useContext(StoreContext);

	return (
		<>
			{!validAnalyticsConnection && (
				<ClayAlert displayType="danger" variant="stripe">
					{Liferay.Language.get('an-unexpected-error-occurred')}
				</ClayAlert>
			)}

			{validAnalyticsConnection && (hasWarning || hasHistoricalWarning) && (
				<ClayAlert displayType="warning" variant="stripe">
					{Liferay.Language.get(
						'some-data-is-temporarily-unavailable'
					)}
				</ClayAlert>
			)}

			{validAnalyticsConnection &&
				publishedToday &&
				!hasWarning &&
				!hasHistoricalWarning && (
					<ClayAlert
						displayType="info"
						title={Liferay.Language.get('no-data-is-available-yet')}
						variant="stripe"
					>
						{Liferay.Language.get(
							'content-has-just-been-published'
						)}
					</ClayAlert>
				)}

			{currentPage.view === 'main' && (
				<div>
					<Main
						authorName={authorName}
						chartDataProviders={[
							getHistoricalViews,
							getHistoricalReads,
						]}
						defaultTimeRange={defaultTimeRange}
						defaultTimeSpanOption={defaultTimeSpanKey}
						languageTag={languageTag}
						onTrafficSourceClick={handleTrafficSourceClick}
						pagePublishDate={pagePublishDate}
						pageTitle={pageTitle}
						timeSpanOptions={timeSpanOptions}
						totalReadsDataProvider={handleTotalReads}
						totalViewsDataProvider={handleTotalViews}
						trafficSources={trafficSources}
					/>
				</div>
			)}

			{currentPage.view === 'traffic-source-detail' &&
				currentPage.data.countryKeywords.length > 0 && (
					<Detail
						currentPage={currentPage}
						languageTag={languageTag}
						onCurrentPageChange={handleCurrentPage}
						onTrafficSourceNameChange={handleTrafficSourceName}
						trafficShareDataProvider={handleTrafficShare}
						trafficVolumeDataProvider={handleTrafficVolume}
					/>
				)}
		</>
	);
}

Navigation.proptypes = {
	api: PropTypes.object.isRequired,
	authorName: PropTypes.string.isRequired,
	defaultTimeRange: PropTypes.objectOf(
		PropTypes.shape({
			endDate: PropTypes.string.isRequired,
			startDate: PropTypes.string.isRequired,
		})
	).isRequired,
	defaultTimeSpanKey: PropTypes.string.isRequired,
	languageTag: PropTypes.string.isRequired,
	pagePublishDate: PropTypes.number.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
	trafficSources: PropTypes.array.isRequired,
};
