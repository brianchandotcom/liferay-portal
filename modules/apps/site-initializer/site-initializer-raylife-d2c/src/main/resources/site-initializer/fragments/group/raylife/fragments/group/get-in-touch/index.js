/* eslint-disable no-undef */
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

const applicationIdKey = 'raylife-application-id';
const btnBack = fragmentElement.querySelector('#contact-agent-btn-back');
const btnCall = fragmentElement.querySelector('#contact-agent-btn-call');
const contextualMessageIdKey = 'raylife-contextual-message';
const valueCall = fragmentElement.querySelector('#value-number-call')
	.textContent;

btnBack.onclick = function () {
	localStorage.setItem('raylife-back-to-edit', true);
	window.history.back();
};

btnCall.onclick = function () {
	window.location.href = 'tel:' + valueCall;
};

const applicationId = localStorage.getItem(applicationIdKey);

if (applicationId) {
	document.getElementById('content-agent-text-your-application').textContent =
		'Your Application #' + applicationId;
}

const contextualMessage = localStorage.getItem(contextualMessageIdKey);

if (contextualMessage) {
	document.getElementById(
		'contact-agent-contextual-message'
	).textContent = contextualMessage;
}

const fetchHeadless = async (url, options) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${window.location.origin}/${url}`, {
		...options,
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	const data = await response.json();

	return data;
};

const updateApplicationStatus = async () => {
	await fetchHeadless(`o/c/raylifeapplications/${applicationId}`, {
		body: JSON.stringify({
			applicationStatus: {
				key: 'incomplete',
				name: 'Incomplete',
			},
		}),
		method: 'PATCH',
	});
};

updateApplicationStatus();
