/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_PageShippingMethod} from '../models/HeadlessCommerceAdminChannel_v1_0_PageShippingMethod';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10ShippingMethodService {

	/**
	 * @param channelId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostChannelShippingMethodsPageExportBatch(
		channelId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/channels/{channelId}/shipping-methods/export-batch',
			path: {
				channelId: channelId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves channel shipping methods.
	 * @param channelId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageShippingMethod default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannelShippingMethodsPage(
		channelId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageShippingMethod> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channels/{channelId}/shipping-methods',
			path: {
				channelId: channelId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
