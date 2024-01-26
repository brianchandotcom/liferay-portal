/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectField } from '../models/ObjectField';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectFieldService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ObjectField
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCodeObjectFieldsPage({
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
    }): CancelablePromise<Array<ObjectField>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-fields',
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
     * @returns ObjectField
     * @throws ApiError
     */
    public postObjectDefinitionByExternalReferenceCodeObjectField({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectField,
    }): CancelablePromise<ObjectField> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-fields',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectField
     * @throws ApiError
     */
    public getObjectDefinitionObjectFieldsPage({
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
    }): CancelablePromise<Array<ObjectField>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-fields',
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
     * @returns ObjectField
     * @throws ApiError
     */
    public postObjectDefinitionObjectField({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectField,
    }): CancelablePromise<ObjectField> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-fields',
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
    public deleteObjectField({
        objectFieldId,
    }: {
        objectFieldId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-fields/{objectFieldId}',
            path: {
                'objectFieldId': objectFieldId,
            },
        });
    }
    /**
     * @returns ObjectField
     * @throws ApiError
     */
    public getObjectField({
        objectFieldId,
    }: {
        objectFieldId: number,
    }): CancelablePromise<ObjectField> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-fields/{objectFieldId}',
            path: {
                'objectFieldId': objectFieldId,
            },
        });
    }
    /**
     * @returns ObjectField
     * @throws ApiError
     */
    public patchObjectField({
        objectFieldId,
        requestBody,
    }: {
        objectFieldId: number,
        requestBody?: ObjectField,
    }): CancelablePromise<ObjectField> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/object-admin/v1.0/object-fields/{objectFieldId}',
            path: {
                'objectFieldId': objectFieldId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectField
     * @throws ApiError
     */
    public putObjectField({
        objectFieldId,
        requestBody,
    }: {
        objectFieldId: number,
        requestBody?: ObjectField,
    }): CancelablePromise<ObjectField> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-fields/{objectFieldId}',
            path: {
                'objectFieldId': objectFieldId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
