/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Country } from '../models/Country';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CountryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Country
     * @throws ApiError
     */
    public getCountriesPage({
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
    }): CancelablePromise<Array<Country>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries',
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
     * @returns Country
     * @throws ApiError
     */
    public postCountry({
        requestBody,
    }: {
        requestBody?: Country,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-address/v1.0/countries',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public getCountryByA2({
        a2,
    }: {
        a2: string,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/by-a2/{a2}',
            path: {
                'a2': a2,
            },
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public getCountryByA3({
        a3,
    }: {
        a3: string,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/by-a3/{a3}',
            path: {
                'a3': a3,
            },
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public getCountryByName({
        name,
    }: {
        name: string,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/by-name/{name}',
            path: {
                'name': name,
            },
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public getCountryByNumber({
        number,
    }: {
        number: number,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/by-number/{number}',
            path: {
                'number': number,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteCountry({
        countryId,
    }: {
        countryId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-address/v1.0/countries/{countryId}',
            path: {
                'countryId': countryId,
            },
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public getCountry({
        countryId,
    }: {
        countryId: number,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-address/v1.0/countries/{countryId}',
            path: {
                'countryId': countryId,
            },
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public patchCountry({
        countryId,
        requestBody,
    }: {
        countryId: number,
        requestBody?: Country,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-address/v1.0/countries/{countryId}',
            path: {
                'countryId': countryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Country
     * @throws ApiError
     */
    public putCountry({
        countryId,
        requestBody,
    }: {
        countryId: number,
        requestBody?: Country,
    }): CancelablePromise<Country> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-address/v1.0/countries/{countryId}',
            path: {
                'countryId': countryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
