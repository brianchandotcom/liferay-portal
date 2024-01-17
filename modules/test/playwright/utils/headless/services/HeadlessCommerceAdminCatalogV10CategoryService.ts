/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Category} from '../models/HeadlessCommerceAdminCatalog_v1_0_Category';
import type {HeadlessCommerceAdminCatalog_v1_0_PageCategory} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10CategoryService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductByExternalReferenceCodeCategoriesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/categories',
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
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductByExternalReferenceCodeCategory(
		externalReferenceCode: string,
		requestBody?: Array<HeadlessCommerceAdminCatalog_v1_0_Category>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/categories',
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
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetProductIdCategoriesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/categories',
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
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PatchProductIdCategory(
		id: string,
		requestBody?: Array<HeadlessCommerceAdminCatalog_v1_0_Category>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-catalog/v1.0/products/{id}/categories',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
