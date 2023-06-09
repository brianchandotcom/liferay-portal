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

const queryString = window.location.search;
const urlParams = queryString.split('=');
const requestId = urlParams[1];
const liferayUrl = window.location.origin;
const requestIdInput = document.querySelector('.request-id div input');
const requestIdValueField = document.getElementsByName(
	'r_requestId_c_evpRequestId'
)[0];
const selectButton = document.querySelector('.btn-secondary');
requestIdInput.disabled = true;
requestIdInput.style.borderRadius = '8px';
requestIdInput.value = requestId;
requestIdValueField.value = requestId;

selectButton.remove();

const statusResponse = async () => {
	const payload = {
		requestStatus: {
			key: 'closed',
			name: 'Closed',
		},
	};

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	await fetch(`${liferayUrl}/o/c/evprequests/${requestId}`, {
		body: JSON.stringify(payload),
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PATCH',
	});
};

// eslint-disable-next-line no-undef
const formElement = fragmentElement.querySelector(
	'.lfr-layout-structure-item-form'
);

if (formElement) {
	formElement.onsubmit = () => statusResponse();
}
