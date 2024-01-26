/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectView } from '../models/ObjectView';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectViewService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCodeObjectViewsPage({
        externalReferenceCode,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<ObjectView>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-views',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public postObjectDefinitionByExternalReferenceCodeObjectView({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectView,
    }): CancelablePromise<ObjectView> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-views',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public getObjectDefinitionObjectViewsPage({
        objectDefinitionId,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        objectDefinitionId: number,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<ObjectView>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-views',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public postObjectDefinitionObjectView({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectView,
    }): CancelablePromise<ObjectView> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-views',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteObjectView({
        objectViewId,
    }: {
        objectViewId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-views/{objectViewId}',
            path: {
                'objectViewId': objectViewId,
            },
        });
    }
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public getObjectView({
        objectViewId,
    }: {
        objectViewId: number,
    }): CancelablePromise<ObjectView> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-views/{objectViewId}',
            path: {
                'objectViewId': objectViewId,
            },
        });
    }
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public putObjectView({
        objectViewId,
        requestBody,
    }: {
        objectViewId: number,
        requestBody?: ObjectView,
    }): CancelablePromise<ObjectView> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-views/{objectViewId}',
            path: {
                'objectViewId': objectViewId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectView
     * @throws ApiError
     */
    public postObjectViewCopy({
        objectViewId,
    }: {
        objectViewId: number,
    }): CancelablePromise<ObjectView> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-views/{objectViewId}/copy',
            path: {
                'objectViewId': objectViewId,
            },
        });
    }
}
