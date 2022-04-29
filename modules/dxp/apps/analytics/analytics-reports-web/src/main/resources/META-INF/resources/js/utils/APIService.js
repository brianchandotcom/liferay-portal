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

import {fetch} from 'frontend-js-web';

export default {
	getAnalyticsReportsData(analyticsReportsURL, body) {
		return _fetchWithError(analyticsReportsURL, {
			body,
			method: 'POST',
		});
	},
	getHistoricalReads(
		analyticsReportsHistoricalReadsURL,
		{namespace, plid, timeSpanKey, timeSpanOffset}
	) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsHistoricalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getHistoricalViews(
		analyticsReportsHistoricalViewsURL,
		{namespace, plid, timeSpanKey, timeSpanOffset}
	) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsHistoricalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getTotalReads(analyticsReportsTotalReadsURL, {namespace, plid}) {
		const body = {plid};

		return _fetchWithError(analyticsReportsTotalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getTotalViews(analyticsReportsTotalViewsURL, {namespace, plid}) {
		const body = {plid};

		return _fetchWithError(analyticsReportsTotalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getTrafficSources(
		analyticsReportsTrafficSourcesURL,
		{namespace, plid, timeSpanKey, timeSpanOffset}
	) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsTrafficSourcesURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},
};

/**
 *
 *
 * @export
 * @param {Object} body
 * @param {string} prefix
 * @param {FormData} [formData=new FormData()]
 * @returns {FormData}
 */
export function _getFormDataRequest(body, prefix, formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		formData.append(`${prefix}${key}`, value);
	});

	return formData;
}

/**
 * Wrapper to `fetch` function throwing an error when `error` is present in the response
 */
function _fetchWithError(url, options = {}) {
	return fetch(url, options)
		.then((response) => response.json())
		.then((objectResponse) => {
			if (objectResponse.error) {
				throw objectResponse.error;
			}

			return objectResponse;
		});
}
