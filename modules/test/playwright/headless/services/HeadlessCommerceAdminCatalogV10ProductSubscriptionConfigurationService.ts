/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration} from '../models/HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10ProductSubscriptionConfigurationService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeSubscriptionConfiguration(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/subscriptionConfiguration',
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
	public static headlessCommerceAdminCatalogV10PatchProductByExternalReferenceCodeSubscriptionConfiguration(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/subscriptionConfiguration',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdSubscriptionConfiguration(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/subscriptionConfiguration',
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
	public static headlessCommerceAdminCatalogV10PatchProductIdSubscriptionConfiguration(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_ProductSubscriptionConfiguration
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/subscriptionConfiguration',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
