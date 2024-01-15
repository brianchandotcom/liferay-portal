/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_PageTaxCategory} from '../models/HeadlessCommerceAdminChannel_v1_0_PageTaxCategory';
import type {HeadlessCommerceAdminChannel_v1_0_TaxCategory} from '../models/HeadlessCommerceAdminChannel_v1_0_TaxCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10TaxCategoryService {

	/**
	 * @param search
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostTaxCategoriesPageExportBatch(
		search?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/tax-categories/export-batch',
			query: {
				search: search,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminChannel_v1_0_TaxCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetTaxCategory(
		id: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_TaxCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/tax-categories/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageTaxCategory default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetTaxCategoriesPage(
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageTaxCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/tax-categories',
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}
}
