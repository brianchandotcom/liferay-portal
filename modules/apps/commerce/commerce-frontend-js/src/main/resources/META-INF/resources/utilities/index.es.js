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

import ReactDOM from 'react-dom';
import React from 'react';

import {createOdataFilterStrings} from './odata.es';

export function debounce(func, wait, immediate) {
	let timeout;

	return () => {
		const context = this;
		const args = arguments;
		function later() {
			timeout = null;
			if (!immediate) func.apply(context, args);
		}
		const callNow = immediate && !timeout;

		clearTimeout(timeout);
		timeout = setTimeout(later, wait);
		if (callNow) func.apply(context, args);
	};
}

export function showNotification(
	message,
	type = 'success',
	closeable = true,
	duration = 500
) {
	if (!window.AUI) {
		return;
	}

	AUI().use('liferay-notification', () => {
		new Liferay.Notification({
			closeable,
			delay: {
				hide: 5000,
				show: 0
			},
			duration,
			message,
			render: true,
			title: Liferay.Language.get(type),
			type
		});
	});
}

export function showErrorNotification(
	e = Liferay.Language.get('unexpected-error')
) {
	showNotification(e, 'danger');
}

export function getSchemaString(object, path) {
	if (!Array.isArray(path)) {
		return object[path];
	} else {
		return path.reduce((acc, path) => acc[path], object);
	}
}

export function liferayNavigate(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	} else {
		window.location.href = url;
	}
}

export function slugify(str) {
	str = str.replace(/^\s+|\s+$/g, '');
	str = str.toLowerCase();

	var from = 'àáäâèéëêìíïîòóöôùúüûñç·/_,:;';
	var to = 'aaaaeeeeiiiioooouuuunc------';
	for (var i = 0, l = from.length; i < l; i++) {
		str = str.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
	}

	str = str
		.replace(/[^a-z0-9 -]/g, '')
		.replace(/\s+/g, '-')
		.replace(/-+/g, '-');

	return str;
}

export function getValueFromItem(item, fieldName) {
	if (Array.isArray(fieldName)) {
		return fieldName.reduce((acc, key) => acc[key], item);
	}
	return item[fieldName];
}

export function executeAsyncAction(url, method = 'GET') {
	return fetch(url, {
		...fetchParams,
		method
	});
}

export function formatActionUrl(url, item) {
	const regex = new RegExp('{(.*?)}', 'mg');

	return url.replace(regex, matched =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('|')
		)
	);
}

export function launcher(Component, componentId, containerId, props) {
	const {portletId} = props;

	const container = window.document.getElementById(containerId);

	const destroyOnNavigate = !portletId;

	if (Liferay && Liferay.component) {
		Liferay.component(
			componentId,
			{
				destroy: () => {
					ReactDOM.unmountComponentAtNode(container);
				}
			},
			{
				destroyOnNavigate,
				portletId
			}
		);
	}

	// eslint-disable-next-line liferay-portal/no-react-dom-render
	ReactDOM.render(<Component {...props} />, container);
}

export function getRandomId() {
	return Math.random()
		.toString(36)
		.substr(2, 9);
}

export function getLiferayJsModule(moduleUrl) {
	return new Promise((resolve, reject) => {
		Liferay.Loader.require(
			moduleUrl,
			jsModule => {
				return resolve(jsModule.defult || jsModule);
			},
			err => {
				return reject(err);
			}
		);
	});
}

export function getFakeJsModule() {
	return new Promise(resolve => {
		setTimeout(() => {
			resolve(() => {
				return <>fakely fetched component</>;
			});
		}, 500);
	});
}

export const fetchHeaders = new Headers({
	'x-csrf-token': window.Liferay && window.Liferay.authToken
});

export const fetchParams = {
	credentials: 'include',
	headers: (window.Liferay && window.Liferay.staticEnvHeaders) || fetchHeaders
};

export function createSortingString(values) {
	if (!values.length) return null;

	return `sort=${values
		.map(value => {
			return `${
				Array.isArray(value.fieldName)
					? value.fieldName[0]
					: value.fieldName
			}:${value.direction}`;
		})
		.join(',')}`;
}

export function loadData(
	apiUrl,
	currentUrl,
	filters = [],
	searchParam,
	delta,
	page = 1,
	sorting = []
) {
	const authString = `p_auth=${window.Liferay.authToken}`;
	const pagination = `pageSize=${delta}&page=${page}`;
	const currentUrlString = currentUrl
		? `&currentUrl=${encodeURIComponent(currentUrl)}`
		: null;
	const searchParamString = searchParam
		? `&search=${encodeURIComponent(searchParam)}`
		: null;
	const sortingString = createSortingString(sorting);
	const filterString = createOdataFilterStrings(filters);

	const urlParams = [
		authString,
		pagination,
		currentUrlString,
		searchParamString,
		sortingString,
		filterString
	]
		.filter(param => Boolean(param))
		.join('&');

	const url = `${apiUrl}${apiUrl.indexOf('?') > -1 ? '&' : '?'}${urlParams}`;

	return executeAsyncAction(url, 'GET').then(response => response.json());
}

export const getJsModule =
	Liferay.Loader && Liferay.Loader.require
		? getLiferayJsModule
		: getFakeJsModule;
