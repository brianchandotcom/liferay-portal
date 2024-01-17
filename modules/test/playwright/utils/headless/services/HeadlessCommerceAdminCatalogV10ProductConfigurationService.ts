/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductConfigurationService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdConfiguration(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/configuration',
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
	public static headlessCommerceAdminCatalogV10PatchProductIdConfiguration(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/configuration',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeConfiguration(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/configuration',
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
	public static headlessCommerceAdminCatalogV10PatchProductByExternalReferenceCodeConfiguration(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/configuration',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
