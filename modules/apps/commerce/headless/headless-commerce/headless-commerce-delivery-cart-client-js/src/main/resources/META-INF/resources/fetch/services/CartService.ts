/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Cart } from '../models/Cart';
import type { CouponCode } from '../models/CouponCode';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CartService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Cart by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteCart({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
            path: {
                'cartId': cartId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Retrive information of the given Cart.
     * @returns Cart
     * @throws ApiError
     */
    public getCart({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<Cart> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
            path: {
                'cartId': cartId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Cart.
     * @returns Cart Updated
     * @throws ApiError
     */
    public patchCart({
        cartId,
        requestBody,
    }: {
        cartId: number,
        requestBody: Cart,
    }): CancelablePromise<Cart> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
            path: {
                'cartId': cartId,
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
     * Updates a Cart.
     * @returns Cart Updated
     * @throws ApiError
     */
    public putCart({
        cartId,
        requestBody,
    }: {
        cartId: number,
        requestBody: Cart,
    }): CancelablePromise<Cart> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
            path: {
                'cartId': cartId,
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
     * @returns Cart
     * @throws ApiError
     */
    public postCartCheckout({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<Cart> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/checkout',
            path: {
                'cartId': cartId,
            },
        });
    }
    /**
     * Add new Items to a Cart, return the whole Cart updated.
     * @returns Cart Coupon applied to the order
     * @throws ApiError
     */
    public postCartCouponCode({
        cartId,
        requestBody,
    }: {
        cartId: number,
        requestBody: CouponCode,
    }): CancelablePromise<Cart> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/coupon-code',
            path: {
                'cartId': cartId,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * @returns string
     * @throws ApiError
     */
    public getCartPaymentUrl({
        cartId,
        callbackUrl,
    }: {
        cartId: number,
        callbackUrl?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/payment-url',
            path: {
                'cartId': cartId,
            },
            query: {
                'callbackURL': callbackUrl,
            },
        });
    }
    /**
     * Retrieves carts for specific account in the given channel.
     * @returns Cart Successful operation
     * @throws ApiError
     */
    public getChannelCartsPage({
        accountId,
        channelId,
        page,
        pageSize,
        search,
    }: {
        accountId: number,
        channelId: number,
        page?: number,
        pageSize?: number,
        search?: string,
    }): CancelablePromise<Array<Cart>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/channels/{channelId}/account/{accountId}/carts',
            path: {
                'accountId': accountId,
                'channelId': channelId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates a Cart.
     * @returns Cart Created
     * @throws ApiError
     */
    public postChannelCart({
        channelId,
        requestBody,
    }: {
        channelId: number,
        requestBody: Cart,
    }): CancelablePromise<Cart> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-cart/v1.0/channels/{channelId}/carts',
            path: {
                'channelId': channelId,
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
