/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {HeadlessAdminUserApiHelper} from './HeadlessAdminUserApiHelper';
import {HeadlessCommerceAdminCatalogApiHelper} from './HeadlessCommerceAdminCatalogApiHelper';
import {HeadlessCommerceAdminChannelApiHelper} from './HeadlessCommerceAdminChannelApiHelper';
import {ObjectApiHelper} from './ObjectApiHelper';

export class ApiHelpers {
	readonly baseUrl: string;
	readonly featureFlag: FeatureFlagApiHelper;
	readonly headlessAdminUser: HeadlessAdminUserApiHelper;
	readonly headlessCommerceAdminCatalog: HeadlessCommerceAdminCatalogApiHelper;
	readonly headlessCommerceAdminChannel: HeadlessCommerceAdminChannelApiHelper;
	readonly object: ObjectApiHelper;
	readonly page: Page;

	constructor(page: Page) {
		this.baseUrl = liferayConfig.environment.baseUrl + '/o/';
		this.featureFlag = new FeatureFlagApiHelper(page);
		this.headlessAdminUser = new HeadlessAdminUserApiHelper(this);
		this.headlessCommerceAdminCatalog =
			new HeadlessCommerceAdminCatalogApiHelper(this);
		this.headlessCommerceAdminChannel =
			new HeadlessCommerceAdminChannelApiHelper(this);
		this.object = new ObjectApiHelper(this);
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

	async patch(url: string, data: DataObject) {
		const response = await this.page.request.patch(url, {
			data,
			headers: await this.getHeader(),
		});

		return response.json();
	}

	async post(url: string, data: DataObject | any[]) {
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
