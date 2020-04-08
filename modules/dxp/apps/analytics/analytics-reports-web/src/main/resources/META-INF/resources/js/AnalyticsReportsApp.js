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
import React, {useContext, useState} from 'react';

import Detail from './components/Detail';
import Main from './components/Main';
import StateContext from './state/context';
import APIService from './utils/APIService';
import {numberFormat} from './utils/numberFormat';

export default function({context, props}) {
	const {languageTag, namespace, page} = context;
	const {defaultTimeSpanKey, timeSpans} = context;
	const {validAnalyticsConnection} = context;
	const {authorName, publishDate, title} = props;
	const {trafficSources} = props;

	const {
		getAnalyticsReportsHistoricalReadsURL,
		getAnalyticsReportsHistoricalViewsURL,
		getAnalyticsReportsTotalReadsURL,
		getAnalyticsReportsTotalViewsURL,
	} = context.endpoints;

	const api = APIService({
		endpoints: {
			getAnalyticsReportsHistoricalReadsURL,
			getAnalyticsReportsHistoricalViewsURL,
			getAnalyticsReportsTotalReadsURL,
			getAnalyticsReportsTotalViewsURL,
		},
		namespace,
		page,
	});

	return (
		<StateContext.Provider
			value={{
				validAnalyticsConnection,
			}}
		>
			<Navigation
				api={api}
				authorName={authorName}
				defaultTimeSpanKey={defaultTimeSpanKey}
				languageTag={languageTag}
				pagePublishDate={publishDate}
				pageTitle={title}
				timeSpanOptions={timeSpans}
				trafficSources={trafficSources}
			/>
		</StateContext.Provider>
	);
}

function Navigation({
	api,
	authorName,
	defaultTimeSpanKey,
	languageTag,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	trafficSources,
}) {
	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const {validAnalyticsConnection} = useContext(StateContext);

	const {getHistoricalReads, getHistoricalViews} = api;

	const warning = trafficSources.some(
		trafficSource => trafficSource.value === undefined
	);

	function handleCurrentPage(currentPage) {
		setCurrentPage({view: currentPage.view});
	}

	function handleTotalReads() {
		return api.getTotalReads().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalReads
			);
		});
	}

	function handleTotalViews() {
		return api.getTotalViews().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalViews
			);
		});
	}

	function handleTrafficShare() {
		const trafficSource = trafficSources.find(trafficSource => {
			return trafficSource['name'] === trafficSourceName;
		});

		return Promise.resolve(trafficSource ? trafficSource.share : '-');
	}

	function handleTrafficSourceClick(trafficSourceName) {
		setTrafficSourceName(trafficSourceName);

		api.getTrafficSourceDetails(trafficSourceName).then(
			trafficSourceData => {
				setCurrentPage({
					data: trafficSourceData,
					view: 'traffic-source-detail',
				});
			}
		);
	}

	function handleTrafficSourceName(trafficSourceName) {
		setTrafficSourceName(trafficSourceName);
	}

	function handleTrafficVolume() {
		const trafficSource = trafficSources.find(trafficSource => {
			return trafficSource['name'] === trafficSourceName;
		});

		return Promise.resolve(trafficSource ? trafficSource.value : '-');
	}

	return (
		<>
			{!validAnalyticsConnection && (
				<ClayAlert
					className="p-0"
					displayType="danger"
					variant="stripe"
				>
					{Liferay.Language.get('something-went-wrong')}
				</ClayAlert>
			)}

			{warning && (
				<ClayAlert
					className="p-0"
					displayType="warning"
					variant="stripe"
				>
					{Liferay.Language.get(
						'some-data-is-temporarily-unavailable'
					)}
				</ClayAlert>
			)}

			{currentPage.view === 'main' && (
				<div className="p-3">
					<Main
						authorName={authorName}
						chartDataProviders={[
							getHistoricalViews,
							getHistoricalReads,
						]}
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

			{currentPage.view === 'traffic-source-detail' && (
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
