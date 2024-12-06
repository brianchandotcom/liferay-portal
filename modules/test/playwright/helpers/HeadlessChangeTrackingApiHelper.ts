/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CTCollectionApi} from '@liferay/change-tracking-rest-client-js'

import {ApiHelpers} from './ApiHelpers';
import getRandomString from '../utils/getRandomString';

export class HeadlessChangeTrackingApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {

		this.apiHelpers = apiHelpers;
		this.basePath = 'change-tracking-rest/v1.0';
	}

	async checkoutCTCollection(ctCollectionId: number) {
		const ctCollectionApiClient = await this.apiHelpers.buildRestClient(CTCollectionApi);

		return (await ctCollectionApiClient.postCTCollectionCheckout(ctCollectionId));
	}

	async createCTCollection(name: string) {
		const requestBody = {
			description: '',
			externalReferenceCode: getRandomString(),
			name,
			status: {code: 0, label: 'string', label_i18n: 'string'},
		};

		const ctCollectionApiClient = await this.apiHelpers.buildRestClient(CTCollectionApi);

		return (await ctCollectionApiClient.postCTCollection(requestBody));
	}

	async deleteCTCollection(ctCollectionId: number) {
		const ctCollectionApiClient = await this.apiHelpers.buildRestClient(CTCollectionApi);

		return (await ctCollectionApiClient.deleteCTCollection(ctCollectionId));
	}

	async publishCTCollection(ctCollectionId: number) {
		const ctCollectionApiClient = await this.apiHelpers.buildRestClient(CTCollectionApi);

		return (await ctCollectionApiClient.postCTCollectionPublish(ctCollectionId));
	}
}
