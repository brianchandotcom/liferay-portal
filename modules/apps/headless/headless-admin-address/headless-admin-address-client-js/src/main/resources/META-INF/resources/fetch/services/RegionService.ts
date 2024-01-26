/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Region } from '../models/Region';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class RegionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Region
     * @throws ApiError
     */
    public getCountryRegionsPage({
        countryId,
        active,
        page,
        pageSize,
        search,
        sort,
    }: {
        countryId: number,
        active?: boolean,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Region>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/{countryId}/regions',
            path: {
                'countryId': countryId,
            },
            query: {
                'active': active,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns Region
     * @throws ApiError
     */
    public postCountryRegion({
        countryId,
        requestBody,
    }: {
        countryId: number,
        requestBody?: Region,
    }): CancelablePromise<Region> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-address/v1.0/countries/{countryId}/regions',
            path: {
                'countryId': countryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Region
     * @throws ApiError
     */
    public getCountryRegionByRegionCode({
        countryId,
        regionCode,
    }: {
        countryId: number,
        regionCode: string,
    }): CancelablePromise<Region> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/{countryId}/regions/by-region-code/{regionCode}',
            path: {
                'countryId': countryId,
                'regionCode': regionCode,
            },
        });
    }
    /**
     * @returns Region
     * @throws ApiError
     */
    public getRegionsPage({
        active,
        page,
        pageSize,
        search,
        sort,
    }: {
        active?: boolean,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Region>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/regions',
            query: {
                'active': active,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteRegion({
        regionId,
    }: {
        regionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-address/v1.0/regions/{regionId}',
            path: {
                'regionId': regionId,
            },
        });
    }
    /**
     * @returns Region
     * @throws ApiError
     */
    public getRegion({
        regionId,
    }: {
        regionId: number,
    }): CancelablePromise<Region> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/regions/{regionId}',
            path: {
                'regionId': regionId,
            },
        });
    }
    /**
     * @returns Region
     * @throws ApiError
     */
    public patchRegion({
        regionId,
        requestBody,
    }: {
        regionId: number,
        requestBody?: Region,
    }): CancelablePromise<Region> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-address/v1.0/regions/{regionId}',
            path: {
                'regionId': regionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Region
     * @throws ApiError
     */
    public putRegion({
        regionId,
        requestBody,
    }: {
        regionId: number,
        requestBody?: Region,
    }): CancelablePromise<Region> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-address/v1.0/regions/{regionId}',
            path: {
                'regionId': regionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
