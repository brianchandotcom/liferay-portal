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

import BasicInformation from './components/BasicInformation';
import Chart from './components/Chart';
import Hint from './components/Hint';
import TotalCount from './components/TotalCount';
import TrafficSources from './components/TrafficSources';
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

	const {getHistoricalReads, getHistoricalViews} = api;

	function _handleTotalReads() {
		return api.getTotalReads().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalReads
			);
		});
	}
	function _handleTotalViews() {
		return api.getTotalViews().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalViews
			);
		});
	}

	return (
		<div className="p-3">
			<BasicInformation
				authorName={authorName}
				languageTag={languageTag}
				publishDate={publishDate}
				title={title}
			/>

			<h5 className="mt-4 sheet-subtitle text-secondary">
				{Liferay.Language.get('views-and-reads')}
			</h5>

			<TotalCount
				className="mt-2"
				dataProvider={_handleTotalViews}
				label={Liferay.Util.sub(Liferay.Language.get('total-views'))}
				popoverHeader={Liferay.Language.get('total-views')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
				)}
			/>

			<TotalCount
				className="mb-3 mt-2"
				dataProvider={_handleTotalReads}
				label={Liferay.Util.sub(Liferay.Language.get('total-reads'))}
				popoverHeader={Liferay.Language.get('total-reads')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-reads-since-the-content-was-published'
				)}
			/>

			<Chart
				dataProviders={[getHistoricalViews, getHistoricalReads]}
				defaultTimeSpanOption={defaultTimeSpanKey}
				languageTag={languageTag}
				publishDate={publishDate}
				timeSpanOptions={timeSpans}
			/>

			<h5 className="mt-4 sheet-subtitle text-secondary">
				{Liferay.Language.get('traffic-sources')}
				<Hint
					message={Liferay.Language.get(
						'traffic-sources-help-message'
					)}
					title={Liferay.Language.get('traffic-sources')}
				/>
			</h5>

			<TrafficSources
				dataProvider={api.getTrafficSources}
				languageTag={languageTag}
			/>
		</div>
	);
}
