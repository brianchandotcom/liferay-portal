/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageRelatedProduct} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageRelatedProduct';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10RelatedProductService {

	/**
	 * Gets a list of Related Products of a Product.
	 * @param channelId
	 * @param productId
	 * @param type
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageRelatedProduct default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductRelatedProductsPage(
		channelId: string,
		productId: string,
		type?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageRelatedProduct> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/related-products',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				type: type,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
