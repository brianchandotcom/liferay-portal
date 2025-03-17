/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

	import {CTCollectionApi} from './cTCollectionApi';
	import {CTEntryApi} from './cTEntryApi';
	import {CTProcessApi} from './cTProcessApi';
	import {CTRemoteApi} from './cTRemoteApi';

	export * from './cTCollectionApi';
	export * from './cTEntryApi';
	export * from './cTProcessApi';
	export * from './cTRemoteApi';

/**
 * @author David Truong
 * @generated
 */

export class HttpError extends Error {
	constructor(
		public body: any,
		public response: Response,
		public statusCode: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [
	CTCollectionApi,
	CTEntryApi,
	CTProcessApi,
	CTRemoteApi,
];