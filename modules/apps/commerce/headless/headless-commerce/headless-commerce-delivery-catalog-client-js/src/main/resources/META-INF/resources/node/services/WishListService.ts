/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WishList } from '../models/WishList';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WishListService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves wishlists for a given channel.
     * @returns WishList Successful operation
     * @throws ApiError
     */
    public getChannelWishListsPage({
        channelId,
        accountId,
        page,
        pageSize,
    }: {
        channelId: number,
        accountId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WishList>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/wishlists',
            path: {
                'channelId': channelId,
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
     * Creates a WishList.
     * @returns WishList Created
     * @throws ApiError
     */
    public postChannelWishList({
        channelId,
        requestBody,
        accountId,
    }: {
        channelId: number,
        requestBody: WishList,
        accountId?: number,
    }): CancelablePromise<WishList> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-catalog/v1.0/channels/{channelId}/wishlists',
            path: {
                'channelId': channelId,
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
    /**
     * Deletes a WishList by ID.
     * Deletes a wishlist by wishListId.
     * @returns void
     * @throws ApiError
     */
    public deleteWishList({
        wishListId,
    }: {
        wishListId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}',
            path: {
                'wishListId': wishListId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Retrieves a wishlist by wishListId.
     * @returns WishList Successful operation
     * @throws ApiError
     */
    public getWishList({
        wishListId,
    }: {
        wishListId: number,
    }): CancelablePromise<WishList> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}',
            path: {
                'wishListId': wishListId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a WishList.
     * @returns WishList Updated
     * @throws ApiError
     */
    public patchWishList({
        wishListId,
        requestBody,
    }: {
        wishListId: number,
        requestBody: WishList,
    }): CancelablePromise<WishList> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-delivery-catalog/v1.0/wishlists/{wishListId}',
            path: {
                'wishListId': wishListId,
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
