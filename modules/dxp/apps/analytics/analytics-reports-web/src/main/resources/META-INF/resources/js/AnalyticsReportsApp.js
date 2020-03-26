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

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useState} from 'react';

import Detail from './components/Detail';
import Main from './components/Main';
import APIService from './utils/APIService';
import {numberFormat} from './utils/numberFormat';

export default function({context, props}) {
	const {languageTag, namespace, page} = context;
	const {defaultTimeSpanKey, timeSpans} = context;
	const {authorName, publishDate, title} = props;

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
		<Navigation
			api={api}
			authorName={authorName}
			defaultTimeSpanKey={defaultTimeSpanKey}
			languageTag={languageTag}
			pagePublishDate={publishDate}
			pageTitle={title}
			timeSpanOptions={timeSpans}
		/>
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
}) {
	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const {getHistoricalReads, getHistoricalViews, getTrafficSources} = api;

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
		return api
			.getTrafficSourcesDetails(trafficSourceName)
			.then(response => {
				return response.share;
			});
	}

	function handleTrafficVolume() {
		return api
			.getTrafficSourcesDetails(trafficSourceName)
			.then(response => {
				return numberFormat(languageTag, response.value);
			});
	}

	return (
		<>
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
						trafficSourcesDataProvider={getTrafficSources}
					/>
				</div>
			)}

			{currentPage.view === 'traffic-source-detail' && (
				<>
					<div className="d-flex p-2">
						<ClayButtonWithIcon
							className="text-secondary"
							displayType="unstyled"
							onClick={() => {
								setCurrentPage({view: 'main'});
								setTrafficSourceName('');
							}}
							small="true"
							symbol="angle-left"
						/>
						<div className="align-self-center flex-grow-1 mx-2">
							{currentPage.data.title}
						</div>
					</div>

					<hr className="my-0" />

					<Detail
						currentPage={currentPage}
						languageTag={languageTag}
						trafficShareDataProvider={handleTrafficShare}
						trafficVolumeDataProvider={handleTrafficVolume}
					/>
				</>
			)}
		</>
	);
}
