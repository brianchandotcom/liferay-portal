/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageProduct} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageProduct';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Product} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_Product';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10ProductService {

	/**
	 * Retrieves products from selected channel.
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_Product default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProduct(
		channelId: string,
		productId: string,
		accountId?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_Product> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				accountId: accountId,
			},
		});
	}

	/**
	 * Retrieves products from selected channel.
	 * @param channelId
	 * @param accountId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductsPage(
		channelId: string,
		accountId?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products',
			path: {
				channelId: channelId,
			},
			query: {
				accountId: accountId,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}
}
