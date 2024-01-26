/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CartItem } from '../models/CartItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CartItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes an Cart Item by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteCartItem({
        cartItemId,
    }: {
        cartItemId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
            path: {
                'cartItemId': cartItemId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Retrive information of the given Cart
     * @returns CartItem
     * @throws ApiError
     */
    public getCartItem({
        cartItemId,
    }: {
        cartItemId: number,
    }): CancelablePromise<CartItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
            path: {
                'cartItemId': cartItemId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Retrive information of the given Cart.
     * @returns CartItem
     * @throws ApiError
     */
    public patchCartItem({
        cartItemId,
        requestBody,
    }: {
        cartItemId: number,
        requestBody: CartItem,
    }): CancelablePromise<CartItem> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
            path: {
                'cartItemId': cartItemId,
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
     * update the given Cart.
     * @returns CartItem
     * @throws ApiError
     */
    public putCartItem({
        cartItemId,
        requestBody,
    }: {
        cartItemId: number,
        requestBody: CartItem,
    }): CancelablePromise<CartItem> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
            path: {
                'cartItemId': cartItemId,
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
     * Retrive cart items of a Cart.
     * @returns CartItem Return the items of the cart
     * @throws ApiError
     */
    public getCartItemsPage({
        cartId,
        skuId,
        page,
        pageSize,
    }: {
        cartId: number,
        skuId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<CartItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/items',
            path: {
                'cartId': cartId,
            },
            query: {
                'skuId': skuId,
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
     * Add new Items to a Cart, return the whole Cart updated.
     * @returns CartItem Add cart item to the cart
     * @throws ApiError
     */
    public postCartItem({
        cartId,
        requestBody,
    }: {
        cartId: number,
        requestBody: CartItem,
    }): CancelablePromise<CartItem> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/items',
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
}
