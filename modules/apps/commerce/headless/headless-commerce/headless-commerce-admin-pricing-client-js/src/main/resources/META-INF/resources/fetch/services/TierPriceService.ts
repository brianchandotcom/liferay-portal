/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TierPrice } from '../models/TierPrice';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TierPriceService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Tier Price related to a Price Entry.
     * @returns TierPrice Successful operation
     * @throws ApiError
     */
    public getPriceEntryByExternalReferenceCodeTierPricesPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<TierPrice>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/priceEntries/by-externalReferenceCode/{externalReferenceCode}/tierPrices',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or Updates a Tier Price.
     * @returns TierPrice Created
     * @returns any Async
     * @throws ApiError
     */
    public postPriceEntryByExternalReferenceCodeTierPrice({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: TierPrice,
    }): CancelablePromise<TierPrice | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/priceEntries/by-externalReferenceCode/{externalReferenceCode}/tierPrices',
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
     * Gets a list of Tier Price related to a Price Entry.
     * @returns TierPrice Successful operation
     * @throws ApiError
     */
    public getPriceEntryIdTierPricesPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<TierPrice>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/priceEntries/{id}/tierPrices',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or Updates a Tier Price.
     * @returns TierPrice Created
     * @returns any Async
     * @throws ApiError
     */
    public postPriceEntryIdTierPrice({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: TierPrice,
    }): CancelablePromise<TierPrice | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/priceEntries/{id}/tierPrices',
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
    /**
     * Deletes a Tier Price by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteTierPriceByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/tierPrices/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Tier Price by external reference code.
     * @returns TierPrice Successful operation
     * @throws ApiError
     */
    public getTierPriceByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<TierPrice> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/tierPrices/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Tier Price by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchTierPriceByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: TierPrice,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-pricing/v1.0/tierPrices/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a Tier Price by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteTierPrice({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/tierPrices/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Tier Price by ID.
     * @returns TierPrice Successful operation
     * @throws ApiError
     */
    public getTierPrice({
        id,
    }: {
        id: number,
    }): CancelablePromise<TierPrice> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/tierPrices/{id}',
            path: {
                'id': id,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Tier Price by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchTierPrice({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: TierPrice,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-pricing/v1.0/tierPrices/{id}',
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
