/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../utils/getRandomString';
import {ApiHelpers} from './ApiHelpers';

export class HeadlessChangeTrackingApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'change-tracking-rest/v1.0';
	}

	async checkoutCTCollection(ctCollectionId: string) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/ct-collections/${ctCollectionId}/checkout`
		);
	}

	async createCTCollection(name: string) {
		const requestBody = {
			description: '',
			externalReferenceCode: getRandomString(),
			name,
			status: {code: 0, label: 'string', label_i18n: 'string'},
		};

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/ct-collections`,
			{data: requestBody}
		);
	}

	async deleteCTCollection(ctCollectionId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/ct-collections/${ctCollectionId}`
		);
	}

	async publishCTCollection(ctCollectionId: number) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/ct-collections/${ctCollectionId}/publish`
		);
	}
}
