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

	function getTrafficSources() {
		// TODO remove frontend mock
		return new Promise(resolve =>
			setTimeout(
				() =>
					resolve({
						analyticsReportsTrafficSources: [
							{
								helpMessage: Liferay.Language.get(
									'organic-help-message'
								),
								name: 'organic',
								title: Liferay.Language.get('organic'),
								value: 32178,
							},
							{
								helpMessage: Liferay.Language.get(
									'paid-help-message'
								),
								name: 'paid',
								title: Liferay.Language.get('paid'),
								value: 278256,
							},
						],
					}),
				300
			)
		);
	}

	return {
		getHistoricalReads,
		getHistoricalViews,
		getTotalReads,
		getTotalViews,
		getTrafficSources,
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
		.then(response => response.json())
		.then(objectResponse => {
			if (objectResponse.error) {
				throw objectResponse.error;
			}

			return objectResponse;
		});
}
