/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {ApiBuilderHelper} from './ApiBuilderHelper';
import {DataEngineApiHelper} from './DataEngineApiHelper';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {HeadlessAdminContentApiHelper} from './HeadlessAdminContentApiHelper';
import {HeadlessAdminTaxonomyApiHelper} from './HeadlessAdminTaxonomyApiHelper';
import {HeadlessAdminUserApiHelper} from './HeadlessAdminUserApiHelper';
import {HeadlessAdminWorkflowApiHelper} from './HeadlessAdminWorkflowApiHelper';
import {HeadlessChangeTrackingApiHelper} from './HeadlessChangeTrackingApiHelper';
import {HeadlessCommerceAdminCatalogApiHelper} from './HeadlessCommerceAdminCatalogApiHelper';
import {HeadlessCommerceAdminChannelApiHelper} from './HeadlessCommerceAdminChannelApiHelper';
import {HeadlessCommerceAdminInventoryApiHelper} from './HeadlessCommerceAdminInventoryApiHelper';
import {HeadlessCommerceAdminOrderApiHelper} from './HeadlessCommerceAdminOrderApiHelper';
import {HeadlessCommerceAdminPaymentApiHelper} from './HeadlessCommerceAdminPaymentApiHelper';
import {HeadlessCommerceDeliveryCartApiHelper} from './HeadlessCommerceDeliveryCartApiHelper';
import {HeadlessCommerceDeliveryCatalogApiHelper} from './HeadlessCommerceDeliveryCatalogApiHelper';
import {HeadlessDeliveryApiHelper} from './HeadlessDeliveryApiHelper';
import {HeadlessSiteApiHelper} from './HeadlessSiteApiHelper';
import {ListTypeAdminApiHelper} from './ListTypeAdminApiHelper';
import {NotificationApiHelper} from './NotificationApiHelper';
import {ObjectAdminApiHelper} from './ObjectAdminApiHelper';
import {ObjectEntryApiHelper} from './ObjectEntryApiHelper';
import {JSONWebServicesAnnouncementsEntryApiHelper} from './json-web-services/JSONWebServicesAnnouncementsEntryApiHelper';
import {JSONWebServicesClassNameApiHelper} from './json-web-services/JSONWebServicesClassNameApiHelper';
import {JSONWebServicesCompanyApiHelper} from './json-web-services/JSONWebServicesCompanyApiHelper';
import {JSONWebServicesDDMApiHelper} from './json-web-services/JSONWebServicesDDMApiHelper';
import {JSONWebServicesGroupApiHelper} from './json-web-services/JSONWebServicesGroupApiHelper';
import {JSONWebServicesJournalApiHelper} from './json-web-services/JSONWebServicesJournalApiHelper';
import {JSONWebServicesLayoutApiHelper} from './json-web-services/JSONWebServicesLayoutApiHelper';
import {JSONWebServicesLayoutSetPrototypeApiHelper} from './json-web-services/JSONWebServicesLayoutSetPrototypeApiHelper';
import {JSONWebServicesMBApiHelper} from './json-web-services/JSONWebServicesMBApiHelper';

type TDataApiHelpersData = {
	id: any;
	type: string;
};

interface PostOptions<T> {
	data?: T;
	failOnStatusCode?: boolean;
	headers?: {[key: string]: string};
	multipart?: {[key: string]: any};
}

export class ApiHelpers {
	readonly apiBuilder: ApiBuilderHelper;
	readonly baseUrl: string;
	readonly featureFlag: FeatureFlagApiHelper;
	readonly dataEngine: DataEngineApiHelper;
	readonly headlessAdminContent: HeadlessAdminContentApiHelper;
	readonly headlessAdminTaxonomy: HeadlessAdminTaxonomyApiHelper;
	readonly headlessAdminUser: HeadlessAdminUserApiHelper;
	readonly headlessAdminWorkflow: HeadlessAdminWorkflowApiHelper;
	readonly headlessChangeTracking: HeadlessChangeTrackingApiHelper;
	readonly headlessCommerceAdminCatalog: HeadlessCommerceAdminCatalogApiHelper;
	readonly headlessCommerceAdminChannel: HeadlessCommerceAdminChannelApiHelper;
	readonly headlessCommerceAdminInventoryApiHelper: HeadlessCommerceAdminInventoryApiHelper;
	readonly headlessCommerceAdminOrder: HeadlessCommerceAdminOrderApiHelper;
	readonly headlessCommerceAdminPaymentApiHelper: HeadlessCommerceAdminPaymentApiHelper;
	readonly headlessCommerceDeliveryCatalog: HeadlessCommerceDeliveryCatalogApiHelper;
	readonly headlessCommerceDeliveryCart: HeadlessCommerceDeliveryCartApiHelper;
	readonly headlessDelivery: HeadlessDeliveryApiHelper;
	readonly headlessSite: HeadlessSiteApiHelper;
	readonly jsonWebServicesAnnouncementsEntryApiHelper: JSONWebServicesAnnouncementsEntryApiHelper;
	readonly jsonWebServicesClassName: JSONWebServicesClassNameApiHelper;
	readonly jsonWebServicesCompany: JSONWebServicesCompanyApiHelper;
	readonly jsonWebServicesDDM: JSONWebServicesDDMApiHelper;
	readonly jsonWebServicesGroup: JSONWebServicesGroupApiHelper;
	readonly jsonWebServicesJournal: JSONWebServicesJournalApiHelper;
	readonly jsonWebServicesLayout: JSONWebServicesLayoutApiHelper;
	readonly jsonWebServicesLayoutSetPrototype: JSONWebServicesLayoutSetPrototypeApiHelper;
	readonly jsonWebServicesMBApiHelper: JSONWebServicesMBApiHelper;
	readonly listTypeAdmin: ListTypeAdminApiHelper;
	readonly notification: NotificationApiHelper;
	readonly objectAdmin: ObjectAdminApiHelper;
	readonly objectEntry: ObjectEntryApiHelper;
	readonly page: Page;

	private static readonly _authorization = `Basic ${btoa(
		`test@liferay.com:test`
	)}`;

	constructor(page: Page) {
		this.apiBuilder = new ApiBuilderHelper(this);
		this.baseUrl = liferayConfig.environment.baseUrl + '/o/';
		this.featureFlag = new FeatureFlagApiHelper(page);
		this.dataEngine = new DataEngineApiHelper(this);
		this.headlessAdminContent = new HeadlessAdminContentApiHelper(this);
		this.headlessAdminTaxonomy = new HeadlessAdminTaxonomyApiHelper(this);
		this.headlessAdminUser = new HeadlessAdminUserApiHelper(this);
		this.headlessAdminWorkflow = new HeadlessAdminWorkflowApiHelper(this);
		this.headlessChangeTracking = new HeadlessChangeTrackingApiHelper(this);
		this.headlessCommerceAdminCatalog =
			new HeadlessCommerceAdminCatalogApiHelper(this);
		this.headlessCommerceAdminChannel =
			new HeadlessCommerceAdminChannelApiHelper(this);
		this.headlessCommerceAdminInventoryApiHelper =
			new HeadlessCommerceAdminInventoryApiHelper(this);
		this.headlessCommerceAdminOrder =
			new HeadlessCommerceAdminOrderApiHelper(this);
		this.headlessCommerceAdminPaymentApiHelper =
			new HeadlessCommerceAdminPaymentApiHelper(this);
		this.headlessCommerceDeliveryCatalog =
			new HeadlessCommerceDeliveryCatalogApiHelper(this);
		this.headlessCommerceDeliveryCart =
			new HeadlessCommerceDeliveryCartApiHelper(this);
		this.headlessDelivery = new HeadlessDeliveryApiHelper(this);
		this.headlessSite = new HeadlessSiteApiHelper(this);
		this.jsonWebServicesAnnouncementsEntryApiHelper =
			new JSONWebServicesAnnouncementsEntryApiHelper(this);
		this.jsonWebServicesClassName = new JSONWebServicesClassNameApiHelper(
			this
		);
		this.jsonWebServicesCompany = new JSONWebServicesCompanyApiHelper(this);
		this.jsonWebServicesDDM = new JSONWebServicesDDMApiHelper(this);
		this.jsonWebServicesGroup = new JSONWebServicesGroupApiHelper(this);
		this.jsonWebServicesJournal = new JSONWebServicesJournalApiHelper(this);
		this.jsonWebServicesLayout = new JSONWebServicesLayoutApiHelper(this);
		this.jsonWebServicesLayoutSetPrototype =
			new JSONWebServicesLayoutSetPrototypeApiHelper(this);
		this.jsonWebServicesMBApiHelper = new JSONWebServicesMBApiHelper(this);
		this.listTypeAdmin = new ListTypeAdminApiHelper(this);
		this.notification = new NotificationApiHelper(this);
		this.objectAdmin = new ObjectAdminApiHelper(this);
		this.objectEntry = new ObjectEntryApiHelper(this);
		this.page = page;
	}

	async postResponse<T>(
		url: string,
		{data, failOnStatusCode, headers, multipart}: PostOptions<T> = {}
	) {
		return await this.page.request.post(url, {
			data,
			failOnStatusCode: failOnStatusCode || false,
			headers: headers || (await this.getHeader()),
			multipart,
		});
	}

	async post<T>(url: string, options: PostOptions<T> = {}) {
		const response = await this.postResponse(url, options);

		if (response.status() === 204) {
			return;
		}

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
			...(await this.getCSRFTokenHeader()),
		};
	}

	async getHeader() {
		return {
			'Content-Type': 'application/json',
			...(await this.getCSRFTokenHeader()),
		};
	}

	async getCSRFTokenHeader() {
		const authToken = await this.page.evaluate(() => Liferay.authToken);

		return {
			'x-csrf-token': authToken,
		};
	}
}

export class DataApiHelpers extends ApiHelpers {
	readonly data: TDataApiHelpersData[];

	constructor(page: Page) {
		super(page);

		this.data = [];
	}

	async clearData() {
		for await (const item of this.data.reverse()) {
			switch (item.type) {
				case 'account':
					await this.headlessAdminUser.deleteAccount(item.id);

					break;
				case 'announcement':
					await this.jsonWebServicesAnnouncementsEntryApiHelper.deleteEntry(
						item.id
					);

					break;
				case 'accountGroup':
					await this.headlessAdminUser.deleteAccountGroup(item.id);

					break;
				case 'catalog':
					await this.headlessCommerceAdminCatalog.deleteCatalog(
						item.id
					);

					break;
				case 'channel':
					await this.headlessCommerceAdminChannel.deleteChannel(
						item.id
					);

					break;
				case 'option':
					await this.headlessCommerceAdminCatalog.deleteOption(
						item.id
					);

					break;
				case 'optionCategory':
					await this.headlessCommerceAdminCatalog.deleteOptionCategory(
						item.id
					);

					break;
				case 'order':
					await this.headlessCommerceAdminOrder.deleteOrder(item.id);

					break;
				case 'payment':
					await this.headlessCommerceAdminPaymentApiHelper.deletePayment(
						item.id
					);

					break;
				case 'product':
					await this.headlessCommerceAdminCatalog.deleteProduct(
						item.id
					);

					break;
				case 'site':
					await this.headlessSite.deleteSite(item.id);

					break;
				case 'skuUnitOfMeasure':
					await this.headlessCommerceAdminCatalog.deleteSkuUnitOfMeasure(
						item.id
					);

					break;
				case 'specification':
					await this.headlessCommerceAdminCatalog.deleteSpecification(
						item.id
					);

					break;
				case 'warehouse':
					await this.headlessCommerceAdminInventoryApiHelper.deleteWarehouse(
						item.id
					);

					break;
				default:
					break;
			}
		}
	}
}
