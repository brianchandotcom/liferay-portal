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

import w from './window.es';

function serializeParams(params) {
	return Object.keys(params)
		.map(
			(key) =>
				`${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`
		)
		.join('&');
}

export function endpointBuilder({
	baseURL,
	id = '0',
	path = '',
	queryParams = {},
}) {
	if (!baseURL) {
		throw new Error('No API baseURL provided.');
	}

	const collection = path ? `/${path}` : '';
	const organizationId = `/${id}`;
	const parameters = `?${serializeParams(queryParams)}`;
	const root = `${baseURL}/organizations`;

	return `${root}${organizationId}${collection}${parameters}`;
}

export function callApi(parameters) {
	return fetch(endpointBuilder(parameters)).then((response) =>
		response.json()
	);
}

export const noop = () => {};

export const isNumber = (value) => typeof value === 'number';

export const isArray = (value) => Array.isArray(value);

export function truncateTextNode() {
	let text = this.innerHTML;
	let textLength = this.getComputedTextLength();

	while (textLength > 220) {
		text = text.slice(0, -3);
		this.innerHTML = `${text}...`;
		textLength = this.getComputedTextLength();
	}
}

export function bindAll() {
	const clazz = arguments[0];

	Object.keys(arguments).forEach((argNo, i) => {
		const method = arguments[argNo];

		if (i !== 0 && typeof clazz[method] === 'function') {
			clazz[method] = clazz[method].bind(clazz);
		}
	});

	return clazz;
}

function isInRange(value, lower, upper) {
	return value >= lower && value < upper;
}

export function getColorHue(prevHue) {
	const hue = Math.random() * 360;

	return !prevHue
		? hue
		: isInRange(hue, prevHue - 2, prevHue + 3)
		? hue + 15 > 360
			? hue - 15
			: hue + 15
		: hue;
}

export function setupDataset(data) {
	const sanitizedData = {...data};

	if (sanitizedData.organizations.length) {
		sanitizedData.organizations.forEach((orgObject, index) => {
			delete orgObject.organizations;

			const prevColor = index
				? sanitizedData.organizations[index - 1]
				: null;

			orgObject.colorIdentifier = `hsl(${getColorHue(
				prevColor
			)},75%,75%)`;
		});
	}

	return sanitizedData;
}

export function getLocalizedText(string) {
	try {
		return w.Liferay.Language.get(string);
	}
	catch (error) {
		return string;
	}
}
