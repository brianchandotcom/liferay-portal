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

import Navigation from './components/Navigation';
import ConnectionContext from './context/ConnectionContext';
import {StoreContextProvider} from './context/store';
import APIService from './utils/APIService';

import '../css/analytics-reports-app.scss';

export default function ({context, props}) {
	const {languageTag, namespace, page} = context;
	const {defaultTimeRange, defaultTimeSpanKey, timeSpans} = context;
	const {validAnalyticsConnection} = context;

	const {
		authorName,
		authorPortraitURL,
		authorUserId,
		publishDate,
		title,
	} = props;
	const {trafficSources} = props;
	const languages = [
		{default: true, languageId: 'en-US'},
		{default: false, languageId: 'pt-BR'},
		{default: false, languageId: 'fr-FR'},
		{default: false, languageId: 'ja-JP'},
		{default: false, languageId: 'ca-ES'},
		{default: false, languageId: 'es-ES'},
	];

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

	const publishedToday =
		new Date().toDateString() === new Date(publishDate).toDateString();

	return (
		<ConnectionContext.Provider
			value={{
				validAnalyticsConnection,
			}}
		>
			<StoreContextProvider value={{publishedToday}}>
				<div className="analytics-reports-app">
					<Navigation
						api={api}
						authorName={authorName}
						authorPortraitURL={authorPortraitURL}
						authorUserId={authorUserId}
						defaultTimeRange={defaultTimeRange}
						defaultTimeSpanKey={defaultTimeSpanKey}
						languages={languages}
						languageTag={languageTag}
						pagePublishDate={publishDate}
						pageTitle={title}
						timeSpanOptions={timeSpans}
						trafficSources={trafficSources}
					/>
				</div>
			</StoreContextProvider>
		</ConnectionContext.Provider>
	);
}
