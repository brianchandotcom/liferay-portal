/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as http from 'http';

	import {CompanyTestEntityApi} from './companyTestEntityApi';
	import {EntityModelResourceTestEntity1Api} from './entityModelResourceTestEntity1Api';
	import {EntityModelResourceTestEntity2Api} from './entityModelResourceTestEntity2Api';
	import {SiteTestEntityApi} from './siteTestEntityApi';
	import {TestEntityApi} from './testEntityApi';
	import {TestEntityAddressApi} from './testEntityAddressApi';

	export * from './companyTestEntityApi';
	export * from './entityModelResourceTestEntity1Api';
	export * from './entityModelResourceTestEntity2Api';
	export * from './siteTestEntityApi';
	export * from './testEntityApi';
	export * from './testEntityAddressApi';

/**
 * @author Alejandro Tardín
 * @generated
 */

export class HttpError extends Error {
	constructor (
		public response: http.IncomingMessage,
		public body: any,
		public statusCode?: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [CompanyTestEntityApi, EntityModelResourceTestEntity1Api, EntityModelResourceTestEntity2Api, SiteTestEntityApi, TestEntityApi, TestEntityAddressApi];