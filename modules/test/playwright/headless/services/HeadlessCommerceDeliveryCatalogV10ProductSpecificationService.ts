/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageProductSpecification} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageProductSpecification';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10ProductSpecificationService {

	/**
	 * @param channelId
	 * @param productId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageProductSpecification default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductProductSpecificationsPage(
		channelId: string,
		productId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageProductSpecification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/product-specifications',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
