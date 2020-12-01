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
import {StoreContext} from '../context/StoreContext';
import APIService from '../utils/APIService';
import Detail from './Detail';
import Main from './Main';

export default function Navigation({
	author,
	canonicalURL,
	endpoints,
	languageTag,
	namespace,
	onSelectedLanguageClick = () => {},
	page,
	pagePublishDate,
	pageTitle,
	timeRange,
	timeSpanOptions,
	viewURLs,
}) {
	const [{historicalWarning, publishedToday, warning}] = useContext(
		StoreContext
	);

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [trafficSources, setTrafficSources] = useState([]);

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const api = APIService({
		endpoints,
		namespace,
		page,
	});

	const {getHistoricalReads, getHistoricalViews, getTrafficSources} = api;

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

			{validAnalyticsConnection && (historicalWarning || warning) && (
				<ClayAlert displayType="warning" variant="stripe">
					{Liferay.Language.get(
						'some-data-is-temporarily-unavailable'
					)}
				</ClayAlert>
			)}

			{validAnalyticsConnection &&
				publishedToday &&
				!historicalWarning &&
				!warning && (
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
						author={author}
						canonicalURL={canonicalURL}
						chartDataProviders={[
							getHistoricalViews,
							getHistoricalReads,
						]}
						languageTag={languageTag}
						onSelectedLanguageClick={onSelectedLanguageClick}
						onTrafficSourceClick={handleTrafficSourceClick}
						pagePublishDate={pagePublishDate}
						pageTitle={pageTitle}
						timeRange={timeRange}
						timeSpanOptions={timeSpanOptions}
						totalReadsDataProvider={handleTotalReads}
						totalViewsDataProvider={handleTotalViews}
						trafficSourcesDataProvider={getTrafficSources}
						viewURLs={viewURLs}
					/>
				</div>
			)}

			{currentPage.view !== 'main' && (
				<Detail
					currentPage={currentPage}
					languageTag={languageTag}
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

Navigation.proptypes = {
	author: PropTypes.object.isRequired,
	canonicalURL: PropTypes.string.isRequired,
	endpoints: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	page: PropTypes.objectOf(
		PropTypes.shape({
			plid: PropTypes.number.isRequired,
		})
	).isRequired,
	pagePublishDate: PropTypes.string.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeRange: PropTypes.object.isRequired,
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
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};
