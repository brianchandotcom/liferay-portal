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

import {fetch, navigate, openToast} from 'frontend-js-web';

const APPS_TO_IDS = new Map();

const APP_IDS = new Set();

const ERROR_CODES = {
	/* eslint-disable sort-keys */
	UNKNOWN: -100,
	ALREADY_REGISTERED: -101,
	/* eslint-enable sort-keys */
};

export const REMOTE_APP_PROTOCOL = 'com.liferay.remote.app.protocol';

export const VERSION = 0;

const RESPONSES = {};

function escape(untrusted) {
	return untrusted
		.replace(/&/g, '&amp;')
		.replace(/</g, '&lt;')
		.replace(/>/g, '&gt;')
		.replace(/"/g, '&quot;')
		.replace(/'/g, '&#39;');
}

function getNumber(object, property) {
	if (has(object, property) && typeof object[property] === 'number') {
		return object[property];
	}
}

function getString(object, property) {
	if (has(object, property) && typeof object[property] === 'string') {
		return object[property];
	}
}

function has(object, property) {
	return Object.prototype.hasOwnProperty.call(object, property);
}

const LOG_PREFIX = `[HOST: remote-app-support]`;

function warning(message, ...rest) {
	if (process.env.NODE_ENV === 'development') {
		const messages = [...rest];

		if (typeof message === 'string') {
			messages.unshift(`${LOG_PREFIX}: ${message}`);
		}
		else {
			messages.unshift(LOG_PREFIX, message);
		}

		console.warn(...messages);
	}
}

function postMessage(target, data) {
	target.postMessage(
		{
			...data,
			protocol: REMOTE_APP_PROTOCOL,
			role: 'host',
			version: VERSION,
		},
		'*'
	);
}

function receiveMessage(event) {
	const {data, source} = event;

	if (
		data &&
		getString(data, 'protocol') === REMOTE_APP_PROTOCOL &&
		getString(data, 'role') === 'client' &&
		getNumber(data, 'version') === 0
	) {
		const appID = getString(data, 'appID');

		if (APPS_TO_IDS.has(source) && APPS_TO_IDS.get(source) !== appID) {
			const message = `Attempted to use new UUID ${appID} with application that is already registered`;

			warning(message);

			postMessage(source, {
				appID,
				code: ERROR_CODES.ALREADY_REGISTERED,
				kind: 'error',
				message,
			});

			return;
		}

		const command = getString(data, 'command');

		switch (command) {
			case 'fetch':
				{
					const {requestID} = data;

					// TODO: more validation here

					const resource = data.resource;

					// LPS-145277: Prevent requests to other origins

					if (
						!(
							resource.startsWith(window.location.origin) ||
							resource.startsWith('/o/')
						)
					) {
						postMessage(source, {
							appID,
							kind: 'fetch:reject',
							requestID,
						});

						return;
					}

					const init = data.init;

					const {body} = init;

					if (body?.__FORM_DATA__) {
						const formData = new FormData();

						Object.entries(body.__FORM_DATA__).forEach(
							([key, value]) => {
								formData.append(key, value);
							}
						);

						init.body = formData;
					}

					fetch(resource, init)
						.then((response) => {
							RESPONSES[requestID] = response;

							postMessage(source, {
								appID,
								headers: Array.from(response.headers.entries()),
								kind: 'fetch:resolve',
								ok: response.ok,
								redirected: response.redirected,
								requestID,
								status: response.status,
								statusText: response.statusText,
								type: response.type,
								url: response.url,
							});
						})
						.catch((error) => {
							postMessage(source, {
								appID,
								error,
								kind: 'fetch:reject',
								requestID,
							});
						});
				}
				break;

			case 'fetch:response:blob':
				{
					const {requestID} = data;

					/* eslint-disable-next-line no-unused-expressions */
					RESPONSES[requestID]
						?.blob()
						.then((blob) => {
							postMessage(source, {
								appID,
								blob,
								kind: 'fetch:response:blob:resolve',
								requestID,
							});
						})
						.catch((error) => {
							postMessage(source, {
								appID,
								error,
								kind: 'fetch:response:blob:reject',
								requestID,
							});
						});
				}
				break;

			case 'fetch:response:json':
				{
					const {requestID} = data;

					/* eslint-disable-next-line no-unused-expressions */
					RESPONSES[requestID]
						?.json()
						.then((json) => {
							postMessage(source, {
								appID,
								json,
								kind: 'fetch:response:json:resolve',
								requestID,
							});
						})
						.catch((error) => {
							postMessage(source, {
								appID,
								error,
								kind: 'fetch:response:json:reject',
								requestID,
							});
						});
				}
				break;

			case 'fetch:response:text':
				{
					const {requestID} = data;

					/* eslint-disable-next-line no-unused-expressions */
					RESPONSES[requestID]
						?.text()
						.then((text) => {
							postMessage(source, {
								appID,
								kind: 'fetch:response:text:resolve',
								requestID,
								text,
							});
						})
						.catch((error) => {
							postMessage(source, {
								appID,
								error,
								kind: 'fetch:response:text:reject',
								requestID,
							});
						});
				}
				break;

			case 'get':
				{
					const property = getString(data, 'property');

					const {requestID} = data;

					let value;

					if (property === 'companyId') {
						value = Liferay.ThemeDisplay.getCompanyId();
					}
					else if (property === 'css') {
						value =
							Liferay.ThemeDisplay.getCDNBaseURL() +
							Liferay.ThemeDisplay.getPathThemeRoot() +
							'/css/clay.css';
					}
					else if (property === 'defaultLanguageId') {
						value = Liferay.ThemeDisplay.getDefaultLanguageId();
					}
					else if (property === 'isControlPanel') {

						// Note: likely to always be `false`, because we only
						// render remote apps in widgets on normal pages, for
						// now.

						value = Liferay.ThemeDisplay.isControlPanel();
					}
					else if (property === 'isSignedIn') {
						value = Liferay.ThemeDisplay.isSignedIn();
					}
					else if (property === 'languageId') {
						value = Liferay.ThemeDisplay.getLanguageId();
					}
					else if (property === 'siteGroupId') {
						value = Liferay.ThemeDisplay.getSiteGroupId();
					}
					else if (property === 'userId') {
						value = Liferay.ThemeDisplay.getUserId();
					}
					else if (property === 'userName') {
						value = Liferay.ThemeDisplay.getUserName();
					}

					if (value !== undefined) {
						postMessage(source, {
							appID,
							kind: 'get:resolve',
							property,
							requestID,
							value,
						});
					}
					else {
						postMessage(source, {
							appID,
							error: `Unsupported property ${property}`,
							kind: 'get:reject',
							property,
							requestID,
						});
					}
				}
				break;

			case 'navigate':
				{
					const url = getString(data, 'url');

					if (url) {
						navigate(url);
					}
				}
				break;

			case 'openToast':
				{
					const type = getString(data, 'type') || 'info';
					const message = getString(data, 'message');

					if (message) {

						// example of dealing with untrusted input

						openToast({
							message: escape(message),
							type: escape(type),
						});
					}
				}
				break;

			case 'register':
				{
					const uuid = getString(data, 'appID');

					if (uuid) {
						if (APP_IDS.has(uuid)) {
							const message = `Attempted to register already registered UUID ${uuid}`;

							warning(message);
						}
						else {
							APPS_TO_IDS.set(source, uuid);

							APP_IDS.add(uuid);

							postMessage(source, {
								appID: uuid,
								kind: 'registered',
							});
						}
					}
				}
				break;

			case 'unregister':
				{
					if (APPS_TO_IDS.has(source) && APP_IDS.has(appID)) {
						APPS_TO_IDS.delete(source);
						APP_IDS.delete(appID);
					}
					else {
						warning(
							'Unable to unregister invalid source/ID combination'
						);
					}
				}
				break;

			default:
				warning(`Unexpected command: ${command}`);

				break;
		}
	}
}

export default function init() {
	window.addEventListener('message', receiveMessage);
}
