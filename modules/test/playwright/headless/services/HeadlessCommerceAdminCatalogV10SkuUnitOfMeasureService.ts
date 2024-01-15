/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure';
import type {HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure} from '../models/HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10SkuUnitOfMeasureService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteSkuUnitOfMeasureBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostSkuIdSkuUnitOfMeasureBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/skus/sku-unit-of-measures/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/sku-unit-of-measures',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostSkuByExternalReferenceCodeSkuUnitOfMeasure(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/sku-unit-of-measures',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuIdSkuUnitOfMeasuresPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/sku-unit-of-measures',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostSkuIdSkuUnitOfMeasure(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/sku-unit-of-measures',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetSkuUnitOfMeasure(
		id: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10DeleteSkuUnitOfMeasure(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchSkuUnitOfMeasure(
		id: string,
		requestBody?: HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
