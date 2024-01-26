/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Language } from '../models/Language';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class LanguageService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the asset libraries languages.
     * @returns Language
     * @throws ApiError
     */
    public getAssetLibraryLanguagesPage({
        assetLibraryId,
        fields,
        restrictFields,
    }: {
        assetLibraryId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Array<Language>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/languages',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the site's languages.
     * @returns Language
     * @throws ApiError
     */
    public getSiteLanguagesPage({
        siteId,
        fields,
        restrictFields,
    }: {
        siteId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Array<Language>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/languages',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
}
