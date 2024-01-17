/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_DDMOption} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_DDMOption';
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageSku} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageSku';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Sku} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_Sku';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuOption} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_SkuOption';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10SkuService {

	/**
	 * Retrieves products from selected channel.
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageSku default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductSkusPage(
		channelId: string,
		productId: string,
		accountId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageSku> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus',
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

	/**
	 * Retrieves a SKU from selected channel and product ID.
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param quantity
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_Sku default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PostChannelProductSku(
		channelId: string,
		productId: string,
		accountId?: string,
		quantity?: string,
		requestBody?: Array<HeadlessCommerceDeliveryCatalog_v1_0_DDMOption>
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_Sku> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				accountId: accountId,
				quantity: quantity,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a SKU from selected channel and product ID.
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param quantity
	 * @param skuUnitOfMeasureKey
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_Sku default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PostChannelProductSkuBySkuOption(
		channelId: string,
		productId: string,
		accountId?: string,
		quantity?: string,
		skuUnitOfMeasureKey?: string,
		requestBody?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuOption>
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_Sku> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus/by-sku-option',
			path: {
				channelId: channelId,
				productId: productId,
			},
			query: {
				accountId: accountId,
				quantity: quantity,
				skuUnitOfMeasureKey: skuUnitOfMeasureKey,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a product from selected channel.
	 * @param channelId
	 * @param productId
	 * @param skuId
	 * @param accountId
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_Sku default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductSku(
		channelId: string,
		productId: string,
		skuId: string,
		accountId?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_Sku> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/skus/{skuId}',
			path: {
				channelId: channelId,
				productId: productId,
				skuId: skuId,
			},
			query: {
				accountId: accountId,
			},
		});
	}
}
