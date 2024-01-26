/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WikiNode } from '../models/WikiNode';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WikiNodeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the wiki node's of a site. Results can be paginated, filtered, searched, and sorted.
     * @returns any
     * @throws ApiError
     */
    public getSiteWikiNodesPage({
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
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes',
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
     * Creates a new wiki node
     * @returns WikiNode
     * @throws ApiError
     */
    public postSiteWikiNode({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: WikiNode,
    }): CancelablePromise<WikiNode> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the site's wiki node by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteWikiNodeByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's wiki node by external reference code.
     * @returns WikiNode
     * @throws ApiError
     */
    public getSiteWikiNodeByExternalReferenceCode({
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
    }): CancelablePromise<WikiNode> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}',
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
     * Updates the site's wiki node with the given external reference code, or creates it if it not exists.
     * @returns WikiNode
     * @throws ApiError
     */
    public putSiteWikiNodeByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: WikiNode,
    }): CancelablePromise<WikiNode> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteWikiNodePermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/permissions',
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
    public putSiteWikiNodePermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-nodes/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * Deletes the wiki node and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteWikiNode({
        wikiNodeId,
    }: {
        wikiNodeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}',
            path: {
                'wikiNodeId': wikiNodeId,
            },
        });
    }
    /**
     * Retrieves the wiki node
     * @returns WikiNode
     * @throws ApiError
     */
    public getWikiNode({
        wikiNodeId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        wikiNodeId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<WikiNode> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}',
            path: {
                'wikiNodeId': wikiNodeId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Replaces the wiki node with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns WikiNode
     * @throws ApiError
     */
    public putWikiNode({
        wikiNodeId,
        requestBody,
    }: {
        wikiNodeId: number,
        requestBody?: WikiNode,
    }): CancelablePromise<WikiNode> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}',
            path: {
                'wikiNodeId': wikiNodeId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getWikiNodePermissionsPage({
        wikiNodeId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        wikiNodeId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/permissions',
            path: {
                'wikiNodeId': wikiNodeId,
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
    public putWikiNodePermissionsPage({
        wikiNodeId,
        requestBody,
    }: {
        wikiNodeId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/permissions',
            path: {
                'wikiNodeId': wikiNodeId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putWikiNodeSubscribe({
        wikiNodeId,
    }: {
        wikiNodeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/subscribe',
            path: {
                'wikiNodeId': wikiNodeId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putWikiNodeUnsubscribe({
        wikiNodeId,
    }: {
        wikiNodeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/unsubscribe',
            path: {
                'wikiNodeId': wikiNodeId,
            },
        });
    }
}
