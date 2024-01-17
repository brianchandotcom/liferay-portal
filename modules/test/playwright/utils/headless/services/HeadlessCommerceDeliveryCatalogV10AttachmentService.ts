/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10AttachmentService {

	/**
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductAttachmentsPage(
		channelId: string,
		productId: string,
		accountId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/attachments',
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
	 * @param channelId
	 * @param productId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelProductImagesPage(
		channelId: string,
		productId: string,
		accountId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/products/{productId}/images',
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
