/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {ApiBuilderHelper} from './ApiBuilderHelper';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {HeadlessAdminUserApiHelper} from './HeadlessAdminUserApiHelper';
import {HeadlessCommerceAdminCatalogApiHelper} from './HeadlessCommerceAdminCatalogApiHelper';
import {HeadlessCommerceAdminChannelApiHelper} from './HeadlessCommerceAdminChannelApiHelper';
import {HeadlessCommerceDeliveryCartApiHelper} from './HeadlessCommerceDeliveryCartApiHelper';
import {HeadlessCommerceDeliveryCatalogApiHelper} from './HeadlessCommerceDeliveryCatalogApiHelper';
import {HeadlessDeliveryApiHelper} from './HeadlessDeliveryApiHelper';
import {HeadlessSiteApiHelper} from './HeadlessSiteApiHelper';
import {ObjectAdminApiHelper} from './ObjectAdminApiHelper';
import {ObjectApiHelper} from './ObjectApiHelper';

export class ApiHelpers {
	readonly apiBuilder: ApiBuilderHelper;
	readonly baseUrl: string;
	readonly featureFlag: FeatureFlagApiHelper;
	readonly headlessAdminUser: HeadlessAdminUserApiHelper;
	readonly headlessCommerceAdminCatalog: HeadlessCommerceAdminCatalogApiHelper;
	readonly headlessCommerceAdminChannel: HeadlessCommerceAdminChannelApiHelper;
	readonly headlessCommerceDeliveryCatalog: HeadlessCommerceDeliveryCatalogApiHelper;
	readonly headlessCommerceDeliveryCart: HeadlessCommerceDeliveryCartApiHelper;
	readonly headlessDelivery: HeadlessDeliveryApiHelper;
	readonly headlessSite: HeadlessSiteApiHelper;
	readonly object: ObjectApiHelper;
	readonly objectAdmin: ObjectAdminApiHelper;
	readonly page: Page;

	private static readonly _authorization = `Basic ${btoa(
		`test@liferay.com:test`
	)}`;

	constructor(page: Page) {
		this.apiBuilder = new ApiBuilderHelper(this);
		this.baseUrl = liferayConfig.environment.baseUrl + '/o/';
		this.featureFlag = new FeatureFlagApiHelper(page);
		this.headlessAdminUser = new HeadlessAdminUserApiHelper(this);
		this.headlessCommerceAdminCatalog =
			new HeadlessCommerceAdminCatalogApiHelper(this);
		this.headlessCommerceAdminChannel =
			new HeadlessCommerceAdminChannelApiHelper(this);
		this.headlessCommerceDeliveryCatalog =
			new HeadlessCommerceDeliveryCatalogApiHelper(this);
		this.headlessCommerceDeliveryCart =
			new HeadlessCommerceDeliveryCartApiHelper(this);
		this.headlessDelivery = new HeadlessDeliveryApiHelper(this);
		this.headlessSite = new HeadlessSiteApiHelper(this);
		this.object = new ObjectApiHelper(this);
		this.objectAdmin = new ObjectAdminApiHelper(this);
		this.page = page;
	}

	async postResponse(
		url: string,
		data: DataObject | any[] | string,
		failOnStatusCode?: boolean,
		headers?: {[key: string]: string}
	) {
		return await this.page.request.post(url, {
			data,
			failOnStatusCode: failOnStatusCode || false,
			headers: headers || (await this.getHeader()),
		});
	}

	async post(
		url: string,
		data: DataObject | any[] | string,
		failOnStatusCode?: boolean,
		headers?: {[key: string]: string}
	) {
		const response = await this.postResponse(
			url,
			data,
			failOnStatusCode,
			headers
		);

		return response.json();
	}

	async getResponse(
		url: string,
		failOnStatusCode?: boolean,
		headers?: {[key: string]: string}
	) {
		return await this.page.request.get(url, {
			failOnStatusCode: failOnStatusCode || false,
			headers: headers || (await this.getHeader()),
		});
	}

	async putResponse(url: string) {
		return await this.page.request.put(url, {
			headers: await this.getHeader(),
		});
	}

	async delete(url: string) {
		return this.page.request.delete(url, {
			headers: await this.getHeader(),
		});
	}

	async get(
		url: string,
		failOnStatusCode?: boolean,
		headers?: {[key: string]: string}
	) {
		const response = await this.getResponse(url, failOnStatusCode, headers);

		return response.json();
	}

	async patch(url: string, data: DataObject) {
		const response = await this.page.request.patch(url, {
			data,
			headers: await this.getHeader(),
		});

		const text = await response.text();

		if (!text) {
			return response;
		}

		return response.json();
	}

	async getJSONWebServicesHeaders() {
		return {
			'Authorization': ApiHelpers._authorization,
			'Content-Type': 'application/x-www-form-urlencoded',
			...(await this._getCSRFTokenHeader()),
		};
	}

	async getHeader() {
		return {
			'Content-Type': 'application/json',
			...(await this._getCSRFTokenHeader()),
		};
	}

	async _getCSRFTokenHeader() {
		const authToken = await this.page.evaluate(() => Liferay.authToken);

		return {
			'x-csrf-token': authToken,
		};
	}
}
