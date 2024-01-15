/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageWishList} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageWishList';
import type {HeadlessCommerceDeliveryCatalog_v1_0_WishList} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_WishList';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10WishListService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10DeleteWishListBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlists/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a wishlist by wishListId.
	 * @param wishListId
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_WishList default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetWishList(
		wishListId: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_WishList> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}',
			path: {
				wishListId: wishListId,
			},
		});
	}

	/**
	 * Deletes a wishlist by wishListId.
	 * @param wishListId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10DeleteWishList(
		wishListId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}',
			path: {
				wishListId: wishListId,
			},
		});
	}

	/**
	 * @param wishListId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_WishList default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PatchWishList(
		wishListId: string,
		requestBody?: HeadlessCommerceDeliveryCatalog_v1_0_WishList
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_WishList> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}',
			path: {
				wishListId: wishListId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves wishlists for a given channel.
	 * @param channelId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageWishList default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetChannelWishListsPage(
		channelId: string,
		accountId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageWishList> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/wishlists',
			path: {
				channelId: channelId,
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
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_WishList default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PostChannelWishList(
		channelId: string,
		accountId?: string,
		requestBody?: HeadlessCommerceDeliveryCatalog_v1_0_WishList
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_WishList> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/wishlists',
			path: {
				channelId: channelId,
			},
			query: {
				accountId: accountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
