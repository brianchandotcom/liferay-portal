/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectRelationship } from '../models/ObjectRelationship';
import type { PageObjectRelationship } from '../models/PageObjectRelationship';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectRelationshipService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns PageObjectRelationship
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCodeObjectRelationshipsPage({
        externalReferenceCode,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<PageObjectRelationship> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-relationships',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectRelationship
     * @throws ApiError
     */
    public postObjectDefinitionByExternalReferenceCodeObjectRelationship({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectRelationship,
    }): CancelablePromise<ObjectRelationship> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-relationships',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PageObjectRelationship
     * @throws ApiError
     */
    public getObjectDefinitionObjectRelationshipsPage({
        objectDefinitionId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        objectDefinitionId: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<PageObjectRelationship> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-relationships',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns ObjectRelationship
     * @throws ApiError
     */
    public postObjectDefinitionObjectRelationship({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectRelationship,
    }): CancelablePromise<ObjectRelationship> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-relationships',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectRelationship
     * @throws ApiError
     */
    public putObjectRelationshipByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectRelationship,
    }): CancelablePromise<ObjectRelationship> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-relationships/by-external-reference-code/{externalReferenceCode}',
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
    public deleteObjectRelationship({
        objectRelationshipId,
    }: {
        objectRelationshipId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-relationships/{objectRelationshipId}',
            path: {
                'objectRelationshipId': objectRelationshipId,
            },
        });
    }
    /**
     * @returns ObjectRelationship
     * @throws ApiError
     */
    public getObjectRelationship({
        objectRelationshipId,
    }: {
        objectRelationshipId: number,
    }): CancelablePromise<ObjectRelationship> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-relationships/{objectRelationshipId}',
            path: {
                'objectRelationshipId': objectRelationshipId,
            },
        });
    }
    /**
     * @returns ObjectRelationship
     * @throws ApiError
     */
    public putObjectRelationship({
        objectRelationshipId,
        requestBody,
    }: {
        objectRelationshipId: number,
        requestBody?: ObjectRelationship,
    }): CancelablePromise<ObjectRelationship> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-relationships/{objectRelationshipId}',
            path: {
                'objectRelationshipId': objectRelationshipId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
