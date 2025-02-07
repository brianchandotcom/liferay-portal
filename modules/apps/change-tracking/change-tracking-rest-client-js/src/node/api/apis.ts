/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export * from './cTCollectionApi';
import { CTCollectionApi } from './cTCollectionApi';
export * from './cTEntryApi';
import { CTEntryApi } from './cTEntryApi';
export * from './cTProcessApi';
import { CTProcessApi } from './cTProcessApi';
export * from './cTRemoteApi';
import { CTRemoteApi } from './cTRemoteApi';

import * as http from 'http';
export class HttpError extends Error {
	constructor (public response: http.IncomingMessage, public body: any, public statusCode?: number) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [CTCollectionApi, CTEntryApi, CTProcessApi, CTRemoteApi];