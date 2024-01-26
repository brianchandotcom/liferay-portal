/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ContentStructure } from '../models/ContentStructure';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ContentStructureService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ContentStructure
     * @throws ApiError
     */
    public getAssetLibraryContentStructuresPage({
        assetLibraryId,
        aggregationTerms,
        fields,
        nestedFields,
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
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ContentStructure>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-structures',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'nestedFields': nestedFields,
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
     * @returns void
     * @throws ApiError
     */
    public getAssetLibraryContentStructurePermissionsPage({
        assetLibraryId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        assetLibraryId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-structures/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putAssetLibraryContentStructurePermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-structures/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the content structure.
     * @returns ContentStructure
     * @throws ApiError
     */
    public getContentStructure({
        contentStructureId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        contentStructureId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<ContentStructure> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/content-structures/{contentStructureId}',
            path: {
                'contentStructureId': contentStructureId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getContentStructurePermissionsPage({
        contentStructureId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        contentStructureId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/content-structures/{contentStructureId}/permissions',
            path: {
                'contentStructureId': contentStructureId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putContentStructurePermissionsPage({
        contentStructureId,
        requestBody,
    }: {
        contentStructureId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/content-structures/{contentStructureId}/permissions',
            path: {
                'contentStructureId': contentStructureId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the site's content structures. Results can be paginated, filtered, searched, and sorted.
     * @returns ContentStructure
     * @throws ApiError
     */
    public getSiteContentStructuresPage({
        siteId,
        aggregationTerms,
        fields,
        nestedFields,
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
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ContentStructure>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/content-structures',
            path: {
                'siteId': siteId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'nestedFields': nestedFields,
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
     * @returns void
     * @throws ApiError
     */
    public getSiteContentStructurePermissionsPage({
        siteId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        siteId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/content-structures/permissions',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteContentStructurePermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/content-structures/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
}
