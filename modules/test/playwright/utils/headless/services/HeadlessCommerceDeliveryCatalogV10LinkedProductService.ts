/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageLinkedProduct} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageLinkedProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10LinkedProductService {

	/**
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageLinkedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductLinkedProductsPage(
		channelId: string,
		productId: string,
		accountId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageLinkedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/linked-products',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				accountId: accountId,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
