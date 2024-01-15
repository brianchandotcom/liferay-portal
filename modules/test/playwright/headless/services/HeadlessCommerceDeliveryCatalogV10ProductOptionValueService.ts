/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuOption} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_SkuOption';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10ProductOptionValueService {

	/**
	 * @param channelId
	 * @param productId
	 * @param productOptionId
	 * @param accountId
	 * @param productOptionValueId
	 * @param skuId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductProductOptionProductOptionValuesPage(
		channelId: string,
		productId: string,
		productOptionId: string,
		accountId?: string,
		productOptionValueId?: string,
		skuId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/product-options/{productOptionId}/product-option-values',
			path: {
				channelId: channelId,
				productId: productId,
				productOptionId: productOptionId,
			},
			query: {
				accountId: accountId,
				productOptionValueId: productOptionValueId,
				skuId: skuId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Retrieves a list of ProductOptionValue from selected channel, product ID and product option ID.
	 * @param channelId
	 * @param productId
	 * @param productOptionId
	 * @param accountId
	 * @param productOptionValueId
	 * @param skuId
	 * @param page
	 * @param pageSize
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PostChannelProductProductOptionProductOptionValuesPage(
		channelId: string,
		productId: string,
		productOptionId: string,
		accountId?: string,
		productOptionValueId?: string,
		skuId?: string,
		page?: string,
		pageSize?: string,
		requestBody?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuOption>
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/product-options/{productOptionId}/product-option-values',
			path: {
				channelId: channelId,
				productId: productId,
				productOptionId: productOptionId,
			},
			query: {
				accountId: accountId,
				productOptionValueId: productOptionValueId,
				skuId: skuId,
				page: page,
				pageSize: pageSize,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
