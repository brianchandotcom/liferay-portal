/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WikiPage } from '../models/WikiPage';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WikiPageService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the wiki page by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteWikiPageByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the wiki page by external reference code
     * @returns WikiPage
     * @throws ApiError
     */
    public getSiteWikiPageByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<WikiPage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the wiki page with the given external reference code, or creates it if it not exists.
     * @returns WikiPage
     * @throws ApiError
     */
    public putSiteWikiPageByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: WikiPage,
    }): CancelablePromise<WikiPage> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the wiki page's of a node. Results can be paginated, filtered, searched, and sorted.
     * @returns any
     * @throws ApiError
     */
    public getWikiNodeWikiPagesPage({
        wikiNodeId,
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
        wikiNodeId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages',
            path: {
                'wikiNodeId': wikiNodeId,
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
     * Creates a new wiki page
     * @returns WikiPage
     * @throws ApiError
     */
    public postWikiNodeWikiPage({
        wikiNodeId,
        requestBody,
    }: {
        wikiNodeId: number,
        requestBody?: WikiPage,
    }): CancelablePromise<WikiPage> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages',
            path: {
                'wikiNodeId': wikiNodeId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the child wiki page's of a wiki page.
     * @returns any
     * @throws ApiError
     */
    public getWikiPageWikiPagesPage({
        parentWikiPageId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        parentWikiPageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-pages/{parentWikiPageId}/wiki-pages',
            path: {
                'parentWikiPageId': parentWikiPageId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a child wiki page of the parent wiki page.
     * @returns WikiPage
     * @throws ApiError
     */
    public postWikiPageWikiPage({
        parentWikiPageId,
        requestBody,
    }: {
        parentWikiPageId: number,
        requestBody?: WikiPage,
    }): CancelablePromise<WikiPage> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/wiki-pages/{parentWikiPageId}/wiki-pages',
            path: {
                'parentWikiPageId': parentWikiPageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the wiki page and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteWikiPage({
        wikiPageId,
    }: {
        wikiPageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}',
            path: {
                'wikiPageId': wikiPageId,
            },
        });
    }
    /**
     * Retrieves the wiki page
     * @returns WikiPage
     * @throws ApiError
     */
    public getWikiPage({
        wikiPageId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        wikiPageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<WikiPage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}',
            path: {
                'wikiPageId': wikiPageId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Replaces the wiki page with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns WikiPage
     * @throws ApiError
     */
    public putWikiPage({
        wikiPageId,
        requestBody,
    }: {
        wikiPageId: number,
        requestBody?: WikiPage,
    }): CancelablePromise<WikiPage> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}',
            path: {
                'wikiPageId': wikiPageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getWikiPagePermissionsPage({
        wikiPageId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        wikiPageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/permissions',
            path: {
                'wikiPageId': wikiPageId,
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
    public putWikiPagePermissionsPage({
        wikiPageId,
        requestBody,
    }: {
        wikiPageId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/permissions',
            path: {
                'wikiPageId': wikiPageId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putWikiPageSubscribe({
        wikiPageId,
    }: {
        wikiPageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/subscribe',
            path: {
                'wikiPageId': wikiPageId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putWikiPageUnsubscribe({
        wikiPageId,
    }: {
        wikiPageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/unsubscribe',
            path: {
                'wikiPageId': wikiPageId,
            },
        });
    }
}
