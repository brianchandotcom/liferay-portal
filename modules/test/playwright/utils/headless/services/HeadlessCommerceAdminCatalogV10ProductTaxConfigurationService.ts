/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductTaxConfigurationService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdTaxConfiguration(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/taxConfiguration',
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
	public static headlessCommerceAdminCatalogV10PatchProductIdTaxConfiguration(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/taxConfiguration',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeTaxConfiguration(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/taxConfiguration',
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
	public static headlessCommerceAdminCatalogV10PatchProductByExternalReferenceCodeTaxConfiguration(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductTaxConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/taxConfiguration',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
