/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Account} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_Account';
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageAccount} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10AccountService {

	/**
	 * @param channelId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelAccountsPage(
		channelId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/accounts',
			path: {
				channelId: channelId,
			},
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param channelId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PostChannelAccount(
		channelId: string,
		requestBody?: HeadlessCommerceDeliveryCatalog_v1_0_Account
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/accounts',
			path: {
				channelId: channelId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
