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

	const {getHistoricalReads, getHistoricalViews, getTrafficSources} = api;

	function handleTrafficSourceClick() {}

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

	return (
		<div className="overflow-hidden p-3">
			<Main
				authorName={authorName}
				chartDataProviders={[getHistoricalViews, getHistoricalReads]}
				defaultTimeSpanOption={defaultTimeSpanKey}
				languageTag={languageTag}
				onTrafficSourceClick={handleTrafficSourceClick}
				pagePublishDate={publishDate}
				pageTitle={title}
				timeSpanOptions={timeSpans}
				totalReadsDataProvider={handleTotalReads}
				totalViewsDataProvider={handleTotalViews}
				trafficSourcesDataProvider={getTrafficSources}
			/>
			)
		</div>
	);
}
