/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_PageWishListItem} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_PageWishListItem';
import type {HeadlessCommerceDeliveryCatalog_v1_0_WishListItem} from '../models/HeadlessCommerceDeliveryCatalog_v1_0_WishListItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCatalogV10WishListItemService {

	/**
	 * Retrieves wishlist item by wishListItemId for a specific channel and account
	 * @param wishListItemId
	 * @param accountId
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_WishListItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetWishListItem(
		wishListItemId: string,
		accountId?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_WishListItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlist-items/{wishListItemId}',
			path: {
				wishListItemId: wishListItemId,
			},
			query: {
				accountId: accountId,
			},
		});
	}

	/**
	 * Deletes a wishlist item by wishListItemId.
	 * @param wishListItemId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10DeleteWishListItem(
		wishListItemId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlist-items/{wishListItemId}',
			path: {
				wishListItemId: wishListItemId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10DeleteWishListItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlist-items/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves wishlist items by wishListId for a specific channel and account
	 * @param wishListId
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_PageWishListItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetWishlistWishListWishListItemsPage(
		wishListId: string,
		accountId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_PageWishListItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}/wishlist-items',
			path: {
				wishListId: wishListId,
			},
			query: {
				accountId: accountId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param wishListId
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCatalog_v1_0_WishListItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10PostWishlistWishListWishListItem(
		wishListId: string,
		accountId?: string,
		requestBody?: HeadlessCommerceDeliveryCatalog_v1_0_WishListItem
	): CancelablePromise<HeadlessCommerceDeliveryCatalog_v1_0_WishListItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}/wishlist-items',
			path: {
				wishListId: wishListId,
			},
			query: {
				accountId: accountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
