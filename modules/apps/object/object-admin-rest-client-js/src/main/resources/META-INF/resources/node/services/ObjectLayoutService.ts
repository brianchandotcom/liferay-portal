/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectLayout } from '../models/ObjectLayout';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectLayoutService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ObjectLayout
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCodeObjectLayoutsPage({
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
    }): CancelablePromise<Array<ObjectLayout>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-layouts',
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
     * @returns ObjectLayout
     * @throws ApiError
     */
    public postObjectDefinitionByExternalReferenceCodeObjectLayout({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectLayout,
    }): CancelablePromise<ObjectLayout> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-layouts',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectLayout
     * @throws ApiError
     */
    public getObjectDefinitionObjectLayoutsPage({
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
    }): CancelablePromise<Array<ObjectLayout>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts',
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
     * @returns ObjectLayout
     * @throws ApiError
     */
    public postObjectDefinitionObjectLayout({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectLayout,
    }): CancelablePromise<ObjectLayout> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts',
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
    public deleteObjectLayout({
        objectLayoutId,
    }: {
        objectLayoutId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-layouts/{objectLayoutId}',
            path: {
                'objectLayoutId': objectLayoutId,
            },
        });
    }
    /**
     * @returns ObjectLayout
     * @throws ApiError
     */
    public getObjectLayout({
        objectLayoutId,
    }: {
        objectLayoutId: number,
    }): CancelablePromise<ObjectLayout> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-layouts/{objectLayoutId}',
            path: {
                'objectLayoutId': objectLayoutId,
            },
        });
    }
    /**
     * @returns ObjectLayout
     * @throws ApiError
     */
    public putObjectLayout({
        objectLayoutId,
        requestBody,
    }: {
        objectLayoutId: number,
        requestBody?: ObjectLayout,
    }): CancelablePromise<ObjectLayout> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-layouts/{objectLayoutId}',
            path: {
                'objectLayoutId': objectLayoutId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
