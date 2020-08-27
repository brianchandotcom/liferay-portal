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

/* eslint-disable sort-keys */

export function createFilterObj(field, value = null, operations = 'contains') {
	return {
		field,
		value,
		operations,
	};
}

export function compose(...fns) {
	return (...args) =>
		fns.reduceRight(
			(params, f) => (Array.isArray(params) ? f(...params) : f(params)),
			args
		);
}

export function pipe(...fns) {
	return (...args) =>
		fns.reduce(
			(params, f) => (Array.isArray(params) ? f(...params) : f(params)),
			args
		);
}

export function isPromise(p) {
	return !!p && p instanceof Promise;
}

export function wrapIntoPromise(action) {
	return (...params) => {
		const result = action.apply(null, params);

		return isPromise(result)
			? result
			: new Promise((resolve, _) => resolve(result));
	};
}

export const crudMethodsMapping = new Map([
	['read', 'get'],
	['create', 'post'],
	['update', 'put'],
	['delete', 'delete'],
]);

export function getFetchParams(crudOperation, remoteOption, serializedData) {
	const method =
		(remoteOption && remoteOption.method) ||
		crudMethodsMapping.get(crudOperation);

	const baseUrl = remoteOption.url || remoteOption;

	const url = method === 'get' ? baseUrl + serializedData : baseUrl;

	const body = method !== 'get' ? serializedData : null;

	const credential = remoteOption.credential;

	return [
		url,
		{
			...(body || {}),
			...(credential || {}),
		},
	];
}

export function formatRemoteKey(remoteKey, remoteValue) {
	const remoteDefaultValue = {
		method: crudMethodsMapping.get(remoteKey),
		credential: 'include',
	};

	if (typeof remoteValue === 'string') {
		remoteDefaultValue.url = remoteValue;
	}
	else {
		remoteDefaultValue.url = remoteValue.url;
		remoteDefaultValue.method =
			remoteValue.method || remoteDefaultValue.method;
		remoteDefaultValue.credential =
			remoteValue.credential || remoteDefaultValue.credential;
	}

	return remoteDefaultValue;
}

export function formatSetting(settingName, settingValue) {
	switch (settingName) {
		case 'remote': {
			const remoteKeys = Object.keys(settingValue);
			const formattedRemote = remoteKeys.reduce((acc, remoteKey) => {
				const remoteValue = settingValue[remoteKey];

				return {
					...acc,
					[remoteKey]: formatRemoteKey(remoteKey, remoteValue),
				};
			}, {});

			return [settingName, formattedRemote];
		}
		default:
			return [settingName, settingValue];
	}
}

export function isSettingValid(settingName) {
	switch (settingName) {
		case 'componentType':
		case 'remote':
		case 'on':
			return true;
		default:
			throw new Error(`Setting "${settingName}" not supported`);
	}
}

export function serializeParams(params, type = 'json') {
	let serializedParams = null;
	switch (type) {
		case 'json':
			serializedParams = JSON.stringify(params);
			break;
		default:
			throw new Error(`Serialization type "${type}" not supported`);
	}

	return serializedParams;
}
