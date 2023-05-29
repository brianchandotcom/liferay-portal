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

interface Parameters {
	namespace: string;
	plid: number;
	timeSpanKey?: 'last-7-days' | 'last-30-days';
	timeSpanOffset?: number;
}

export default {
	getAnalyticsReportsData(analyticsReportsURL: string, body: object) {
		return _fetchWithError(analyticsReportsURL, {
			body,
			method: 'POST',
		});
	},
	getHistoricalReads(
		analyticsReportsHistoricalReadsURL: string,
		{namespace, plid, timeSpanKey, timeSpanOffset}: Parameters
	) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsHistoricalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getHistoricalViews(
		analyticsReportsHistoricalViewsURL: string,
		{namespace, plid, timeSpanKey, timeSpanOffset}: Parameters
	) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsHistoricalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getTotalReads(
		analyticsReportsTotalReadsURL: string,
		{namespace, plid}: Parameters
	) {
		const body = {plid};

		return _fetchWithError(analyticsReportsTotalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getTotalViews(
		analyticsReportsTotalViewsURL: string,
		{namespace, plid}: Parameters
	) {
		const body = {plid};

		return _fetchWithError(analyticsReportsTotalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	},

	getTrafficSources(
		analyticsReportsTrafficSourcesURL: string,
		{namespace, plid, timeSpanKey, timeSpanOffset}: Parameters
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
export function _getFormDataRequest(
	body: object,
	prefix: string,
	formData = new FormData()
) {
	Object.entries(body).forEach(([key, value]) => {
		formData.append(`${prefix}${key}`, value);
	});

	return formData;
}

/**
 * Wrapper to `fetch` function throwing an error when `error` is present in the response
 */
function _fetchWithError(url: string, options = {}) {
	return fetch(url, options)
		.then((response) => response.json())
		.then((objectResponse) => {
			if (objectResponse.error) {
				throw objectResponse.error;
			}

			return objectResponse;
		});
}
