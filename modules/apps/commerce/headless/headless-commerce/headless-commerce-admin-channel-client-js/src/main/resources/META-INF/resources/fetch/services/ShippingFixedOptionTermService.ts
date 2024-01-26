/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ShippingFixedOptionTerm } from '../models/ShippingFixedOptionTerm';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ShippingFixedOptionTermService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Shipping Fixed Option Term by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminChannelV10ShippingFixedOptionTerms({
        shippingFixedOptionTermId,
    }: {
        shippingFixedOptionTermId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-terms/{shippingFixedOptionTermId}',
            path: {
                'shippingFixedOptionTermId': shippingFixedOptionTermId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Payment Method Group Rel Terms.
     * @returns ShippingFixedOptionTerm Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminChannelV10ShippingFixedOptionsShippingFixedOptionTerms({
        id,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        id: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ShippingFixedOptionTerm>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-options/{id}/shipping-fixed-option-terms',
            path: {
                'id': id,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Shipping Fixed Option Term.
     * @returns ShippingFixedOptionTerm Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminChannelV10ShippingFixedOptionsShippingFixedOptionTerms({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ShippingFixedOptionTerm,
    }): CancelablePromise<ShippingFixedOptionTerm | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-options/{id}/shipping-fixed-option-terms',
            path: {
                'id': id,
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
