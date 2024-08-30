/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';
import {ApiBuilderHelper} from './ApiBuilderHelper';
import {DataEngineApiHelper} from './DataEngineApiHelper';
import {FeatureFlagApiHelper} from './FeatureFlagApiHelper';
import {HeadlessAdminContentApiHelper} from './HeadlessAdminContentApiHelper';
import {HeadlessAdminTaxonomyApiHelper} from './HeadlessAdminTaxonomyApiHelper';
import {HeadlessAdminUserApiHelper} from './HeadlessAdminUserApiHelper';
import {HeadlessAdminWorkflowApiHelper} from './HeadlessAdminWorkflowApiHelper';
import {HeadlessBatchEngineApiHelper} from './HeadlessBatchEngineApiHelper';
import {HeadlessChangeTrackingApiHelper} from './HeadlessChangeTrackingApiHelper';
import {HeadlessCommerceAdminAccountApiHelper} from './HeadlessCommerceAdminAccountApiHelper';
import {HeadlessCommerceAdminCatalogApiHelper} from './HeadlessCommerceAdminCatalogApiHelper';
import {HeadlessCommerceAdminChannelApiHelper} from './HeadlessCommerceAdminChannelApiHelper';
import {HeadlessCommerceAdminInventoryApiHelper} from './HeadlessCommerceAdminInventoryApiHelper';
import {HeadlessCommerceAdminOrderApiHelper} from './HeadlessCommerceAdminOrderApiHelper';
import {HeadlessCommerceAdminPaymentApiHelper} from './HeadlessCommerceAdminPaymentApiHelper';
import {HeadlessCommerceAdminPricingApiHelper} from './HeadlessCommerceAdminPricingApiHelper';
import {HeadlessCommerceAdminShipmentApiHelper} from './HeadlessCommerceAdminShipmentApiHelper';
import {HeadlessCommerceDeliveryCartApiHelper} from './HeadlessCommerceDeliveryCartApiHelper';
import {HeadlessCommerceDeliveryCatalogApiHelper} from './HeadlessCommerceDeliveryCatalogApiHelper';
import {HeadlessCommerceReturnApiHelper} from './HeadlessCommerceReturnApiHelper';
import {HeadlessDeliveryApiHelper} from './HeadlessDeliveryApiHelper';
import {HeadlessSiteApiHelper} from './HeadlessSiteApiHelper';
import {ListTypeAdminApiHelper} from './ListTypeAdminApiHelper';
import {NotificationApiHelper} from './NotificationApiHelper';
import {ObjectAdminApiHelper} from './ObjectAdminApiHelper';
import {ObjectEntryApiHelper} from './ObjectEntryApiHelper';
import {SCIMApiHelper} from './SCIMApiHelper';
import {SearchExperiencesApiHelper} from './SearchExperiencesApiHelper';
import {JSONWebServicesAnnouncementsEntryApiHelper} from './json-web-services/JSONWebServicesAnnouncementsEntryApiHelper';
import {JSONWebServicesAssetListEntryApiHelper} from './json-web-services/JSONWebServicesAssetListEntryApiHelper';
import {JSONWebServicesClassNameApiHelper} from './json-web-services/JSONWebServicesClassNameApiHelper';
import {JSONWebServicesClientExtensionApiHelper} from './json-web-services/JSONWebServicesClientExtensionApiHelper';
import {JSONWebServicesCompanyApiHelper} from './json-web-services/JSONWebServicesCompanyApiHelper';
import {JSONWebServicesDDMApiHelper} from './json-web-services/JSONWebServicesDDMApiHelper';
import {JSONWebServicesDepotApiHelper} from './json-web-services/JSONWebServicesDepotApiHelper';
import {JSONWebServicesDepotGroupRelApiHelper} from './json-web-services/JSONWebServicesDepotGroupRelApiHelper';
import {JSONWebServicesFragmentCollectionApiHelper} from './json-web-services/JSONWebServicesFragmentCollectionApiHelper';
import {JSONWebServicesFragmentEntryApiHelper} from './json-web-services/JSONWebServicesFragmentEntryApiHelper';
import {JSONWebServicesGroupApiHelper} from './json-web-services/JSONWebServicesGroupApiHelper';
import {JSONWebServicesJournalApiHelper} from './json-web-services/JSONWebServicesJournalApiHelper';
import {JSONWebServicesLayoutApiHelper} from './json-web-services/JSONWebServicesLayoutApiHelper';
import {JSONWebServicesLayoutPageTemplateEntryApiHelper} from './json-web-services/JSONWebServicesLayoutPageTemplateEntry';
import {JSONWebServicesLayoutSetPrototypeApiHelper} from './json-web-services/JSONWebServicesLayoutSetPrototypeApiHelper';
import {JSONWebServicesMBApiHelper} from './json-web-services/JSONWebServicesMBApiHelper';
import {JSONWebServicesOSBAsahApiHelper} from './json-web-services/JSONWebServicesOSBAsahApiHelper';
import {JSONWebServicesOSBFaroApiHelper} from './json-web-services/JSONWebServicesOSBFaroApiHelper';
import {JSONWebServicesUserApiHelper} from './json-web-services/JSONWebServicesUserApiHelper';

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
	readonly headlessBatchEngine: HeadlessBatchEngineApiHelper;
	readonly headlessChangeTracking: HeadlessChangeTrackingApiHelper;
	readonly headlessCommerceAdminAccount: HeadlessCommerceAdminAccountApiHelper;
	readonly headlessCommerceAdminCatalog: HeadlessCommerceAdminCatalogApiHelper;
	readonly headlessCommerceAdminChannel: HeadlessCommerceAdminChannelApiHelper;
	readonly headlessCommerceAdminInventoryApiHelper: HeadlessCommerceAdminInventoryApiHelper;
	readonly headlessCommerceAdminOrder: HeadlessCommerceAdminOrderApiHelper;
	readonly headlessCommerceAdminPaymentApiHelper: HeadlessCommerceAdminPaymentApiHelper;
	readonly headlessCommerceAdminPricing: HeadlessCommerceAdminPricingApiHelper;
	readonly headlessCommerceAdminShipment: HeadlessCommerceAdminShipmentApiHelper;
	readonly headlessCommerceDeliveryCatalog: HeadlessCommerceDeliveryCatalogApiHelper;
	readonly headlessCommerceDeliveryCart: HeadlessCommerceDeliveryCartApiHelper;
	readonly headlessCommerceReturn: HeadlessCommerceReturnApiHelper;
	readonly headlessDelivery: HeadlessDeliveryApiHelper;
	readonly headlessSite: HeadlessSiteApiHelper;
	readonly jsonWebServicesAnnouncementsEntryApiHelper: JSONWebServicesAnnouncementsEntryApiHelper;
	readonly jsonWebServicesAssetListEntry: JSONWebServicesAssetListEntryApiHelper;
	readonly jsonWebServicesClassName: JSONWebServicesClassNameApiHelper;
	readonly jsonWebServicesClientExtension: JSONWebServicesClientExtensionApiHelper;
	readonly jsonWebServicesCompany: JSONWebServicesCompanyApiHelper;
	readonly jsonWebServicesDDM: JSONWebServicesDDMApiHelper;
	readonly jsonWebServicesDepot: JSONWebServicesDepotApiHelper;
	readonly jsonWebServicesDepotGroupRel: JSONWebServicesDepotGroupRelApiHelper;
	readonly jsonWebServicesFragmentEntry: JSONWebServicesFragmentEntryApiHelper;
	readonly jsonWebServicesFragmentCollection: JSONWebServicesFragmentCollectionApiHelper;
	readonly jsonWebServicesGroup: JSONWebServicesGroupApiHelper;
	readonly jsonWebServicesJournal: JSONWebServicesJournalApiHelper;
	readonly jsonWebServicesLayout: JSONWebServicesLayoutApiHelper;
	readonly jsonWebServicesLayoutPageTemplateEntry: JSONWebServicesLayoutPageTemplateEntryApiHelper;
	readonly jsonWebServicesLayoutSetPrototype: JSONWebServicesLayoutSetPrototypeApiHelper;
	readonly jsonWebServicesMBApiHelper: JSONWebServicesMBApiHelper;
	readonly jsonWebServicesOSBAsah: JSONWebServicesOSBAsahApiHelper;
	readonly jsonWebServicesOSBFaro: JSONWebServicesOSBFaroApiHelper;
	readonly jsonWebServicesUser: JSONWebServicesUserApiHelper;
	readonly listTypeAdmin: ListTypeAdminApiHelper;
	readonly notification: NotificationApiHelper;
	readonly objectAdmin: ObjectAdminApiHelper;
	readonly objectEntry: ObjectEntryApiHelper;
	readonly page: Page;
	readonly scim: SCIMApiHelper;
	readonly searchExperiences: SearchExperiencesApiHelper;

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
		this.headlessBatchEngine = new HeadlessBatchEngineApiHelper(this);
		this.headlessChangeTracking = new HeadlessChangeTrackingApiHelper(this);
		this.headlessCommerceAdminAccount =
			new HeadlessCommerceAdminAccountApiHelper(this);
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
		this.headlessCommerceAdminPricing =
			new HeadlessCommerceAdminPricingApiHelper(this);
		this.headlessCommerceAdminShipment =
			new HeadlessCommerceAdminShipmentApiHelper(this);
		this.headlessCommerceDeliveryCatalog =
			new HeadlessCommerceDeliveryCatalogApiHelper(this);
		this.headlessCommerceDeliveryCart =
			new HeadlessCommerceDeliveryCartApiHelper(this);
		this.headlessCommerceReturn = new HeadlessCommerceReturnApiHelper(this);
		this.headlessDelivery = new HeadlessDeliveryApiHelper(this);
		this.headlessSite = new HeadlessSiteApiHelper(this);
		this.jsonWebServicesAnnouncementsEntryApiHelper =
			new JSONWebServicesAnnouncementsEntryApiHelper(this);
		this.jsonWebServicesAssetListEntry =
			new JSONWebServicesAssetListEntryApiHelper(this);
		this.jsonWebServicesClassName = new JSONWebServicesClassNameApiHelper(
			this
		);
		this.jsonWebServicesClientExtension =
			new JSONWebServicesClientExtensionApiHelper(this);
		this.jsonWebServicesCompany = new JSONWebServicesCompanyApiHelper(this);
		this.jsonWebServicesDDM = new JSONWebServicesDDMApiHelper(this);
		this.jsonWebServicesDepot = new JSONWebServicesDepotApiHelper(this);
		this.jsonWebServicesDepotGroupRel =
			new JSONWebServicesDepotGroupRelApiHelper(this);
		this.jsonWebServicesFragmentEntry =
			new JSONWebServicesFragmentEntryApiHelper(this);
		this.jsonWebServicesFragmentCollection =
			new JSONWebServicesFragmentCollectionApiHelper(this);
		this.jsonWebServicesGroup = new JSONWebServicesGroupApiHelper(this);
		this.jsonWebServicesJournal = new JSONWebServicesJournalApiHelper(this);
		this.jsonWebServicesLayout = new JSONWebServicesLayoutApiHelper(this);
		this.jsonWebServicesLayoutPageTemplateEntry =
			new JSONWebServicesLayoutPageTemplateEntryApiHelper(this);
		this.jsonWebServicesLayoutSetPrototype =
			new JSONWebServicesLayoutSetPrototypeApiHelper(this);
		this.jsonWebServicesMBApiHelper = new JSONWebServicesMBApiHelper(this);
		this.jsonWebServicesOSBFaro = new JSONWebServicesOSBFaroApiHelper(this);
		this.jsonWebServicesOSBAsah = new JSONWebServicesOSBAsahApiHelper(this);
		this.jsonWebServicesUser = new JSONWebServicesUserApiHelper(this);
		this.listTypeAdmin = new ListTypeAdminApiHelper(this);
		this.notification = new NotificationApiHelper(this);
		this.objectAdmin = new ObjectAdminApiHelper(this);
		this.objectEntry = new ObjectEntryApiHelper(this);
		this.page = page;
		this.scim = new SCIMApiHelper(this);
		this.searchExperiences = new SearchExperiencesApiHelper(this);
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

	async put<T>(url: string, options: PostOptions<T> = {}) {
		const response = await this.putResponse(url, options);

		if (response.status() === 204) {
			return;
		}

		return response.json();
	}

	async putResponse<T>(
		url: string,
		{data, failOnStatusCode, headers, multipart}: PostOptions<T> = {}
	) {
		return await this.page.request.put(url, {
			data,
			failOnStatusCode: failOnStatusCode || false,
			headers: headers || (await this.getHeader()),
			multipart,
		});
	}

	async delete(url: string, headers?: any) {
		return this.page.request.delete(url, {
			headers: {
				...(await this.getHeader()),
				...(headers || {}),
			},
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

	getAuthorizationHeader() {
		return ApiHelpers._authorization;
	}
}

export class DataApiHelpers extends ApiHelpers {
	readonly data: TDataApiHelpersData[];

	constructor(page: Page) {
		super(page);

		this.data = [];
	}

	async clearData() {
		for await (const item of this.data) {
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
				case 'commerceReturn':
					await this.headlessCommerceReturn.deleteCommerceReturn(
						item.id
					);

					break;
				case 'discount':
					await this.headlessCommerceAdminPricing.deleteDiscount(
						item.id
					);

					break;
				case 'objectDefinition':
					await this.objectAdmin.deleteObjectDefinition(item.id);

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
				case 'orderType':
					await this.headlessCommerceAdminOrder.deleteOrderTypes(
						item.id
					);

					break;
				case 'organization':
					await this.headlessAdminUser.deleteOrganization(item.id);

					break;
				case 'organizationUserAccountAssociation': {
					const [organizationId, emailAddress] = item.id.split('_');

					await this.headlessAdminUser.deleteOrganizationUserAccountAssociation(
						organizationId,
						emailAddress
					);

					break;
				}
				case 'payment':
					await this.headlessCommerceAdminPaymentApiHelper.deletePayment(
						item.id
					);

					break;
				case 'pin':
					await this.headlessCommerceAdminCatalog.deletePin(item.id);

					break;
				case 'price-entry':
					await this.headlessCommerceAdminPricing.deletePriceEntry(
						item.id
					);

					break;
				case 'product':
					await this.headlessCommerceAdminCatalog.deleteProduct(
						item.id
					);

					break;
				case 'relatedProduct':
					await this.headlessCommerceAdminCatalog.deleteRelatedProduct(
						item.id
					);

					break;
				case 'roleUserAccountAssociation': {
					const [roleId, userId] = item.id.split('_');

					await this.headlessAdminUser.deleteRoleUserAccountAssociation(
						roleId,
						userId
					);

					break;
				}
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
				case 'sxpElement':
					await this.searchExperiences.deleteSXPElement(item.id);

					break;
				case 'sxpBlueprint':
					await this.searchExperiences.deleteSXPBlueprint(item.id);

					break;
				case 'terms':
					await this.headlessCommerceAdminOrder.deleteTerms(item.id);

					break;
				case 'userAccount':
					await this.headlessAdminUser.deleteUserAccount(item.id);

					break;
				case 'userGroup':
					await this.headlessAdminUser.deleteUserGroup(item.id);

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

	setData(data: TDataApiHelpersData[]) {
		this.data.length = 0;

		while (data.length) {
			this.data.push(data.pop());
		}
	}
}
