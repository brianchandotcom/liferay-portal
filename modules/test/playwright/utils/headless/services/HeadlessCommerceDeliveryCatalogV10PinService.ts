/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PagePin} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PagePin';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10PinService {

	/**
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PagePin default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductPinsPage(
		channelId: string,
		productId: string,
		accountId?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PagePin> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/pins',
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
