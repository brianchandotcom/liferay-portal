/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PriceListAccountGroup } from '../models/PriceListAccountGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PriceListAccountGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Price List Account Group by ID.
     * @returns void
     * @throws ApiError
     */
    public deletePriceListAccountGroup({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/priceListAccountGroups/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Price List Account Groups.
     * @returns PriceListAccountGroup Successful operation
     * @throws ApiError
     */
    public getPriceListByExternalReferenceCodePriceListAccountGroupPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<PriceListAccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}/priceListAccountGroup',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
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
     * Creates or updates a Price List Account Group.
     * @returns PriceListAccountGroup Created
     * @returns any Async
     * @throws ApiError
     */
    public postPriceListByExternalReferenceCodePriceListAccountGroup({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: PriceListAccountGroup,
    }): CancelablePromise<PriceListAccountGroup | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/priceLists/by-externalReferenceCode/{externalReferenceCode}/priceListAccountGroup',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * Gets a list of Price List Account Groups.
     * @returns PriceListAccountGroup Successful operation
     * @throws ApiError
     */
    public getPriceListIdPriceListAccountGroupsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<PriceListAccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}/priceListAccountGroups',
            path: {
                'id': id,
            },
            query: {
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
     * Creates or updates a Price List Account Group.
     * @returns PriceListAccountGroup Created
     * @returns any Async
     * @throws ApiError
     */
    public postPriceListIdPriceListAccountGroup({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: PriceListAccountGroup,
    }): CancelablePromise<PriceListAccountGroup | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/priceLists/{id}/priceListAccountGroups',
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
