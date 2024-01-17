/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageMappedProduct} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageMappedProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10MappedProductService {

	/**
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageMappedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductMappedProductsPage(
		channelId: string,
		productId: string,
		accountId?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageMappedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/mapped-products',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				accountId: accountId,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}
}
