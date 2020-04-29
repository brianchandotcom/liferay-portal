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

const MOCK_TRAFFIC_SOURCES_DETAILS = {
	organic: {
		keywords: [
			{
				position: 1,
				title: 'commerce',
				value: 90000,
				volume: 12300,
			},
			{
				position: 2,
				title: 'e-commerce',
				value: 14800,
				volume: 9800,
			},
			{
				position: 3,
				title: 'what is commerce',
				value: 14000,
				volume: 9500,
			},
			{
				position: 4,
				title: 'what is e-commerce',
				value: 12100,
				volume: 8700,
			},
			{
				position: 5,
				title: 'commerce definition for new business strategy',
				value: 10100,
				volume: 7100,
			},
		],
		title: 'Organic Traffic',
	},
	paid: {
		keywords: [
			{
				position: 1,
				title: 'commerce',
				value: 90000,
				volume: 12300,
			},
			{
				position: 2,
				title: 'e-commerce',
				value: 14800,
				volume: 9800,
			},
			{
				position: 3,
				title: 'what is commerce',
				value: 14000,
				volume: 9500,
			},
			{
				position: 4,
				title: 'what is e-commerce',
				value: 12100,
				volume: 8700,
			},
			{
				position: 5,
				title: 'commerce definition for new business strategy',
				value: 10100,
				volume: 7100,
			},
		],
		title: 'Paid Traffic',
	},
};

function APIService({endpoints, namespace, page}) {
	const {
		getAnalyticsReportsHistoricalReadsURL,
		getAnalyticsReportsHistoricalViewsURL,
		getAnalyticsReportsTotalReadsURL,
		getAnalyticsReportsTotalViewsURL,
	} = endpoints;
	const {plid} = page;

	function getTotalReads() {
		const body = {plid};

		return _fetchWithError(getAnalyticsReportsTotalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function getTotalViews() {
		const body = {plid};

		return _fetchWithError(getAnalyticsReportsTotalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function getHistoricalReads({timeSpanKey, timeSpanOffset}) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(getAnalyticsReportsHistoricalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function getHistoricalViews({timeSpanKey, timeSpanOffset}) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(getAnalyticsReportsHistoricalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function getTrafficSourceDetails(name) {
		// TODO remove frontend mock

		return new Promise((resolve) =>
			setTimeout(() => {
				resolve(MOCK_TRAFFIC_SOURCES_DETAILS[name]);
			}, 900)
		);
	}

	return {
		getHistoricalReads,
		getHistoricalViews,
		getTotalReads,
		getTotalViews,
		getTrafficSourceDetails,
	};
}

export default APIService;

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
