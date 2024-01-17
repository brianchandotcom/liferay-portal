/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_ProductVirtualSettings} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductVirtualSettings';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductVirtualSettingsService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductVirtualSettings default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdProductVirtualSettings(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductVirtualSettings> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/product-virtual-settings',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductVirtualSettings default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeProductVirtualSettings(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductVirtualSettings> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/product-virtual-settings',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}
}
