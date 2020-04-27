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

export default function ({context, props}) {
	const {languageTag, namespace, page} = context;
	const {defaultTimeRange, defaultTimeSpanKey, timeSpans} = context;
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
		<ConnectionContext.Provider
			value={{
				validAnalyticsConnection,
			}}
		>
			<StoreContextProvider>
				<Navigation
					api={api}
					authorName={authorName}
					defaultTimeRange={defaultTimeRange}
					defaultTimeSpanKey={defaultTimeSpanKey}
					languageTag={languageTag}
					pagePublishDate={publishDate}
					pageTitle={title}
					timeSpanOptions={timeSpans}
					trafficSources={trafficSources}
				/>
			</StoreContextProvider>
		</ConnectionContext.Provider>
	);
}
