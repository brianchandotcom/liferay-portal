/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ContentSetElement } from '../models/ContentSetElement';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ContentSetElementService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ContentSetElement
     * @throws ApiError
     */
    public getAssetLibraryContentSetByKeyContentSetElementsPage({
        assetLibraryId,
        key,
        fields,
        restrictFields,
        page,
        pageSize,
    }: {
        assetLibraryId: number,
        key: string,
        fields?: string,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ContentSetElement>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-sets/by-key/{key}/content-set-elements',
            path: {
                'assetLibraryId': assetLibraryId,
                'key': key,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns ContentSetElement
     * @throws ApiError
     */
    public getAssetLibraryContentSetByUuidContentSetElementsPage({
        assetLibraryId,
        uuid,
        fields,
        restrictFields,
        page,
        pageSize,
    }: {
        assetLibraryId: number,
        uuid: string,
        fields?: string,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ContentSetElement>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-sets/by-uuid/{uuid}/content-set-elements',
            path: {
                'assetLibraryId': assetLibraryId,
                'uuid': uuid,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Retrieves the content set's elements (e.g., structured content, blogs, etc.). Results can be paginated. The set of available headers are: Accept-Language (string), Host (string), User-Agent (string), X-Browser (string), X-Cookies (collection string), X-Device-Brand (string), X-Device-Model (string), X-Device-Screen-Resolution-Height (double), X-Device-Screen-Resolution-Width (double), X-Last-Sign-In-Date-Time (date time) and X-Signed-In (boolean). Local date will be always present in the request.
     * @returns ContentSetElement
     * @throws ApiError
     */
    public getContentSetContentSetElementsPage({
        contentSetId,
        fields,
        restrictFields,
        page,
        pageSize,
    }: {
        contentSetId: number,
        fields?: string,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ContentSetElement>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/content-sets/{contentSetId}/content-set-elements',
            path: {
                'contentSetId': contentSetId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Retrieves the content set elements by key. Results can be paginated.
     * @returns ContentSetElement
     * @throws ApiError
     */
    public getSiteContentSetByKeyContentSetElementsPage({
        siteId,
        key,
        fields,
        restrictFields,
        page,
        pageSize,
    }: {
        siteId: number,
        key: string,
        fields?: string,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ContentSetElement>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/content-sets/by-key/{key}/content-set-elements',
            path: {
                'siteId': siteId,
                'key': key,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Retrieves the content set elements by UUID. Results can be paginated.
     * @returns ContentSetElement
     * @throws ApiError
     */
    public getSiteContentSetByUuidContentSetElementsPage({
        siteId,
        uuid,
        fields,
        restrictFields,
        page,
        pageSize,
    }: {
        siteId: number,
        uuid: string,
        fields?: string,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ContentSetElement>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/content-sets/by-uuid/{uuid}/content-set-elements',
            path: {
                'siteId': siteId,
                'uuid': uuid,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
}
