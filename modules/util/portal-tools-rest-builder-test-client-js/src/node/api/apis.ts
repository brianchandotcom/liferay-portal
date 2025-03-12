/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as http from 'http';

	import {CompanyTestEntityApi} from './companyTestEntityApi';
	import {EntityModelResourceTestEntity1Api} from './entityModelResourceTestEntity1Api';
	import {EntityModelResourceTestEntity2Api} from './entityModelResourceTestEntity2Api';
	import {FilterApi} from './filterApi';
	import {SchemaApi} from './schemaApi';
	import {SiteTestEntityApi} from './siteTestEntityApi';
	import {SortApi} from './sortApi';
	import {TestEntityAddressApi} from './testEntityAddressApi';
	import {TestEntityApi} from './testEntityApi';

	export * from './companyTestEntityApi';
	export * from './entityModelResourceTestEntity1Api';
	export * from './entityModelResourceTestEntity2Api';
	export * from './filterApi';
	export * from './schemaApi';
	export * from './siteTestEntityApi';
	export * from './sortApi';
	export * from './testEntityAddressApi';
	export * from './testEntityApi';

/**
 * @author Alejandro Tardín
 * @generated
 */

export class HttpError extends Error {
	constructor(
		public response: http.IncomingMessage,
		public body: any,
		public statusCode?: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [
	CompanyTestEntityApi,
	EntityModelResourceTestEntity1Api,
	EntityModelResourceTestEntity2Api,
	FilterApi,
	SchemaApi,
	SiteTestEntityApi,
	SortApi,
	TestEntityAddressApi,
	TestEntityApi,
];