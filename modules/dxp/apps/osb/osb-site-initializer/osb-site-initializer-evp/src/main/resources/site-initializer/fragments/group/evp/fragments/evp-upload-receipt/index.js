/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const queryString = window.location.search;
const urlParams = queryString.split('=');
const requestId = urlParams[1];
const liferayUrl = window.location.origin;

const statusResponse = async () => {
	const payload = {
		requestStatus: {
			key: 'awaitingPaymentConfirmation',
			name: 'Awaiting Payment Confirmation',
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
