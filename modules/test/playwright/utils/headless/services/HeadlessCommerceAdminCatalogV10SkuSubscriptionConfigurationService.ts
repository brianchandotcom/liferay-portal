/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration} from '../models/HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10SkuSubscriptionConfigurationService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuByExternalReferenceCodeSkuSubscriptionConfiguration(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/skuSubscriptionConfiguration',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuIdSkuSubscriptionConfiguration(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/skuSubscriptionConfiguration',
			path: {
				id: id,
			},
		});
	}
}
