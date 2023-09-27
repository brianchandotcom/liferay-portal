/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';

function APIService({contentPageEditorNamespace, endpoints, namespace}) {
	const {
		calculateSegmentsExperimentEstimatedDurationURL,
		createSegmentsExperimentURL,
		createSegmentsVariantURL,
		deleteSegmentsExperimentURL,
		deleteSegmentsVariantURL,
		editSegmentsExperimentStatusURL,
		editSegmentsExperimentURL,
		editSegmentsVariantURL,
		runSegmentsExperimentURL,
	} = endpoints;

	function createExperiment(body) {
		return _fetchWithError(createSegmentsExperimentURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function createVariant(body) {
		return _fetchWithError(createSegmentsVariantURL, {
			body: _getFormDataRequest(body, contentPageEditorNamespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function deleteExperiment(body) {
		return _fetchWithError(deleteSegmentsExperimentURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function deleteVariant(body) {
		return _fetchWithError(deleteSegmentsVariantURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function editExperiment(body) {
		return _fetchWithError(editSegmentsExperimentURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function editExperimentStatus(body) {
		return _fetchWithError(editSegmentsExperimentStatusURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function editVariant(body) {
		return _fetchWithError(editSegmentsVariantURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function getEstimatedTime(body) {
		return _fetchWithError(
			calculateSegmentsExperimentEstimatedDurationURL,
			{
				body: _getFormDataRequest(body, namespace),
				credentials: 'include',
				method: 'POST',
			}
		);
	}

	function publishExperience(body) {

		// TODO somehow type this

		return _fetchWithError(editSegmentsExperimentStatusURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	function runExperiment(body) {
		return _fetchWithError(runSegmentsExperimentURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST',
		});
	}

	return {
		createExperiment,
		createVariant,
		deleteExperiment,
		deleteVariant,
		editExperiment,
		editExperimentStatus,
		editVariant,
		getEstimatedTime,
		publishExperience,
		runExperiment,
	};
}

APIService.propTypes = {
	contentPageEditorNamespace: PropTypes.string.isRequired,
	endpoints: PropTypes.shape({
		calculateSegmentsExperimentEstimatedDurationURL:
			PropTypes.string.isRequired,
		createSegmentsExperimentURL: PropTypes.string.isRequired,
		createSegmentsVariantURL: PropTypes.string.isRequired,
		deleteSegmentsExperimentURL: PropTypes.string.isRequired,
		deleteSegmentsVariantURL: PropTypes.string.isRequired,
		editSegmentsExperimentStatusURL: PropTypes.string.isRequired,
		editSegmentsExperimentURL: PropTypes.string.isRequired,
		editSegmentsVariantURL: PropTypes.string.isRequired,
		runSegmentsExperimentURL: PropTypes.string.isRequired,
	}),
	namespace: PropTypes.string.isRequired,
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
		.then((response) => response.json())
		.then((objectResponse) => {
			if (objectResponse.error) {
				throw objectResponse.error;
			}

			return objectResponse;
		});
}
