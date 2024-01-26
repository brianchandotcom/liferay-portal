/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WishListItem } from '../models/WishListItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WishListItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a WishListItem by ID.
     * Deletes a wishlist item by wishListItemId.
     * @returns void
     * @throws ApiError
     */
    public deleteWishListItem({
        wishListItemId,
    }: {
        wishListItemId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlist-items/{wishListItemId}',
            path: {
                'wishListItemId': wishListItemId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Retrieves wishlist item by wishListItemId for a specific channel and account
     * @returns WishListItem Successful operation
     * @throws ApiError
     */
    public getWishListItem({
        wishListItemId,
        accountId,
    }: {
        wishListItemId: number,
        accountId?: number,
    }): CancelablePromise<WishListItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlist-items/{wishListItemId}',
            path: {
                'wishListItemId': wishListItemId,
            },
            query: {
                'accountId': accountId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Retrieves wishlist items by wishListId for a specific channel and account
     * @returns WishListItem Successful operation
     * @throws ApiError
     */
    public getWishlistWishListWishListItemsPage({
        wishListId,
        accountId,
        page,
        pageSize,
    }: {
        wishListId: number,
        accountId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WishListItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}/wishlist-items',
            path: {
                'wishListId': wishListId,
            },
            query: {
                'accountId': accountId,
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates a WishList Item.
     * @returns WishListItem Created
     * @throws ApiError
     */
    public postWishlistWishListWishListItem({
        wishListId,
        requestBody,
        accountId,
    }: {
        wishListId: number,
        requestBody: WishListItem,
        accountId?: number,
    }): CancelablePromise<WishListItem> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}/wishlist-items',
            path: {
                'wishListId': wishListId,
            },
            query: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
