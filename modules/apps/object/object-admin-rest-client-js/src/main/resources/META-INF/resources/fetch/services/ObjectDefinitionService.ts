/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectDefinition } from '../models/ObjectDefinition';
import type { PageObjectDefinition } from '../models/PageObjectDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns PageObjectDefinition
     * @throws ApiError
     */
    public getObjectDefinitionsPage({
        aggregationTerms,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        aggregationTerms?: Array<string>,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<PageObjectDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions',
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public postObjectDefinition({
        requestBody,
    }: {
        requestBody?: ObjectDefinition,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public putObjectDefinitionByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectDefinition,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}',
            path: {
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
    public deleteObjectDefinition({
        objectDefinitionId,
    }: {
        objectDefinitionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public getObjectDefinition({
        objectDefinitionId,
    }: {
        objectDefinitionId: number,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public patchObjectDefinition({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectDefinition,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public putObjectDefinition({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectDefinition,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectDefinition
     * @throws ApiError
     */
    public postObjectDefinitionPublish({
        objectDefinitionId,
    }: {
        objectDefinitionId: number,
    }): CancelablePromise<ObjectDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/publish',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
        });
    }
}
