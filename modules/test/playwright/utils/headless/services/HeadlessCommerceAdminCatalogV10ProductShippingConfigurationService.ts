/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductShippingConfigurationService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdShippingConfiguration(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/shippingConfiguration',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductIdShippingConfiguration(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/shippingConfiguration',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeShippingConfiguration(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/shippingConfiguration',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductByExternalReferenceCodeShippingConfiguration(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductShippingConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/shippingConfiguration',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
