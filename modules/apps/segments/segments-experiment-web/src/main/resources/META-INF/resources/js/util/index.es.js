/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import PropTypes from 'prop-types';

function SegmentsExperimentsUtil({
	endpoints,
	namespace,
	contentPageEditorNamespace
}) {
	const {
		createSegmentsExperimentURL,
		createSegmentsVariantURL,
		deleteSegmentsVariantURL,
		editSegmentsExperimentStatusURL,
		editSegmentsExperimentURL,
		editSegmentsVariantURL
	} = endpoints;

	function editVariant(body) {
		return fetch(editSegmentsVariantURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(response => {
				if (response.error) throw response.error;
				return response;
			});
	}

	function deleteVariant(body) {
		return fetch(deleteSegmentsVariantURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(response => {
				if (response.error) throw response.error;
				return response;
			});
	}

	function createVariant(body) {
		return fetch(createSegmentsVariantURL, {
			body: _getFormDataRequest(body, contentPageEditorNamespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(objectResponse => {
				if (objectResponse.error) throw objectResponse.error;
				return objectResponse;
			});
	}

	function createExperiment(body) {
		return fetch(createSegmentsExperimentURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(objectResponse => {
				if (objectResponse.error) throw objectResponse.error;
				return objectResponse;
			});
	}

	function editExperiment(body) {
		return fetch(editSegmentsExperimentURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(objectResponse => {
				if (objectResponse.error) throw objectResponse.error;
				return objectResponse;
			});
	}

	function editExperimentStatus(body) {
		return fetch(editSegmentsExperimentStatusURL, {
			body: _getFormDataRequest(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(objectResponse => {
				if (objectResponse.error) throw objectResponse.error;
				return objectResponse;
			});
	}

	return {
		createExperiment,
		createVariant,
		deleteVariant,
		editExperiment,
		editExperimentStatus,
		editVariant
	};
}

SegmentsExperimentsUtil.propTypes = {
	contentPageEditorNamespace: PropTypes.string.isRequired,
	endpoints: PropTypes.shape({
		createSegmentsExperimentURL: PropTypes.string.isRequired,
		createSegmentsVariantURL: PropTypes.string.isRequired,
		deleteSegmentsVariantURL: PropTypes.string.isRequired,
		editSegmentsExperimentStatusURL: PropTypes.string.isRequired,
		editSegmentsExperimentURL: PropTypes.string.isRequired,
		editSegmentsVariantURL: PropTypes.string.isRequired
	}),
	namespace: PropTypes.string.isRequired
};

export default SegmentsExperimentsUtil;

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
