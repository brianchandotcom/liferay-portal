/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {HeadlessDeliveryApiHelper} from './HeadlessDeliveryApiHelper';
import {ObjectAdminApiHelper} from './ObjectAdminApiHelper';
import {ObjectApiHelper} from './ObjectApiHelper';

export class ApiHelpers {
	readonly baseUrl: string;
	readonly headlessDelivery: HeadlessDeliveryApiHelper;
	readonly object: ObjectApiHelper;
	readonly featureFlag: FeatureFlagApiHelper;
	readonly objectAdmin: ObjectAdminApiHelper;
	readonly page: Page;

	constructor(page: Page) {
		this.baseUrl = liferayConfig.environment.baseUrl + '/o/';
		this.object = new ObjectApiHelper(this);
		this.featureFlag = new FeatureFlagApiHelper(page);
		this.headlessDelivery = new HeadlessDeliveryApiHelper(this);
		this.objectAdmin = new ObjectAdminApiHelper(this);
		this.page = page;
	}

	async delete(url: string) {
		return this.page.request.delete(url, {
			headers: await this.getHeader(),
		});
	}

	async get(url: string) {
		const response = await this.page.request.get(url, {
			headers: await this.getHeader(),
		});

		return response.json();
	}

	async post(url: string, data: DataObject) {
		const response = await this.page.request.post(url, {
			data,
			headers: await this.getHeader(),
		});

		return response.json();
	}

	async getHeader() {
		const authToken = await this.page.evaluate(() => Liferay.authToken);

		return {
			'Content-Type': 'application/json',
			'x-csrf-token': authToken,
		};
	}
}
