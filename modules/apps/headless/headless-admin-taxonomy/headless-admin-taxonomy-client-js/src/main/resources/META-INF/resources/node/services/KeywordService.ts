/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Keyword } from '../models/Keyword';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class KeywordService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Keyword
     * @throws ApiError
     */
    public getAssetLibraryKeywordsPage({
        assetLibraryId,
        aggregationTerms,
        fields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        assetLibraryId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Keyword>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns Keyword
     * @throws ApiError
     */
    public postAssetLibraryKeyword({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: Keyword,
    }): CancelablePromise<Keyword> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getAssetLibraryKeywordPermissionsPage({
        assetLibraryId,
        fields,
        restrictFields,
        roleNames,
    }: {
        assetLibraryId: number,
        fields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putAssetLibraryKeywordPermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns Keyword
     * @throws ApiError
     */
    public getKeywordsRankedPage({
        fields,
        restrictFields,
        siteId,
        page,
        pageSize,
        search,
    }: {
        fields?: string,
        restrictFields?: string,
        siteId?: number,
        page?: number,
        pageSize?: number,
        search?: string,
    }): CancelablePromise<Array<Keyword>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/keywords/ranked',
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'siteId': siteId,
                'page': page,
                'pageSize': pageSize,
                'search': search,
            },
        });
    }
    /**
     * Deletes the keyword and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteKeyword({
        keywordId,
    }: {
        keywordId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}',
            path: {
                'keywordId': keywordId,
            },
        });
    }
    /**
     * Retrieves a keyword.
     * @returns Keyword
     * @throws ApiError
     */
    public getKeyword({
        keywordId,
        fields,
        restrictFields,
    }: {
        keywordId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Keyword> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}',
            path: {
                'keywordId': keywordId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Replaces the keyword with the information sent in the request body. Any missing fields are deleted, unless required.
     * @returns Keyword
     * @throws ApiError
     */
    public putKeyword({
        keywordId,
        requestBody,
    }: {
        keywordId: number,
        requestBody?: Keyword,
    }): CancelablePromise<Keyword> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}',
            path: {
                'keywordId': keywordId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putKeywordSubscribe({
        keywordId,
    }: {
        keywordId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}/subscribe',
            path: {
                'keywordId': keywordId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putKeywordUnsubscribe({
        keywordId,
    }: {
        keywordId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}/unsubscribe',
            path: {
                'keywordId': keywordId,
            },
        });
    }
    /**
     * Retrieves a Site's keywords. Results can be paginated, filtered, searched, and sorted.
     * @returns Keyword
     * @throws ApiError
     */
    public getSiteKeywordsPage({
        siteId,
        aggregationTerms,
        fields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        siteId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Keyword>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords',
            path: {
                'siteId': siteId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Inserts a new keyword in a Site.
     * @returns Keyword
     * @throws ApiError
     */
    public postSiteKeyword({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: Keyword,
    }): CancelablePromise<Keyword> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteKeywordPermissionsPage({
        siteId,
        fields,
        restrictFields,
        roleNames,
    }: {
        siteId: number,
        fields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/permissions',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteKeywordPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
}
