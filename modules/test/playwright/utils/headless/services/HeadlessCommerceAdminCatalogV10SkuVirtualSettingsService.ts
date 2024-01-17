/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings} from '../models/HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10SkuVirtualSettingsService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuIdSkuVirtualSettings(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/sku-virtual-settings',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuByExternalReferenceCodeSkuVirtualSettings(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/sku-virtual-settings',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}
}
