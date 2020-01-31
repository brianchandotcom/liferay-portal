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
import PropTypes from 'prop-types';

function APIService({endpoints, namespace, page}) {
	const {getAnalyticsReportsTotalViewsURL} = endpoints;
	const {plid} = page;

	function getTotalViews() {
		const body = {plid};

		return _fetchWithError(getAnalyticsReportsTotalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST'
		});
	}

	return {
		getTotalViews
	};
}

APIService.propTypes = {
	endpoints: PropTypes.shape({
		getAnalyticsReportsTotalViewsURL: PropTypes.string.isRequired
	}),
	namespace: PropTypes.string.isRequired
};

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
			if (objectResponse.error) throw objectResponse.error;
			return objectResponse;
		});
}
