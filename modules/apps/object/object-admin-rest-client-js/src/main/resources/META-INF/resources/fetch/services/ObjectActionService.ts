/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectAction } from '../models/ObjectAction';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ObjectActionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteObjectAction({
        objectActionId,
    }: {
        objectActionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/object-admin/v1.0/object-actions/{objectActionId}',
            path: {
                'objectActionId': objectActionId,
            },
        });
    }
    /**
     * @returns ObjectAction
     * @throws ApiError
     */
    public getObjectAction({
        objectActionId,
    }: {
        objectActionId: number,
    }): CancelablePromise<ObjectAction> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-actions/{objectActionId}',
            path: {
                'objectActionId': objectActionId,
            },
        });
    }
    /**
     * @returns ObjectAction
     * @throws ApiError
     */
    public patchObjectAction({
        objectActionId,
        requestBody,
    }: {
        objectActionId: number,
        requestBody?: ObjectAction,
    }): CancelablePromise<ObjectAction> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/object-admin/v1.0/object-actions/{objectActionId}',
            path: {
                'objectActionId': objectActionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectAction
     * @throws ApiError
     */
    public putObjectAction({
        objectActionId,
        requestBody,
    }: {
        objectActionId: number,
        requestBody?: ObjectAction,
    }): CancelablePromise<ObjectAction> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/object-admin/v1.0/object-actions/{objectActionId}',
            path: {
                'objectActionId': objectActionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectAction
     * @throws ApiError
     */
    public getObjectDefinitionByExternalReferenceCodeObjectActionsPage({
        externalReferenceCode,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ObjectAction>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-actions',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * @returns ObjectAction
     * @throws ApiError
     */
    public postObjectDefinitionByExternalReferenceCodeObjectAction({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ObjectAction,
    }): CancelablePromise<ObjectAction> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-actions',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ObjectAction
     * @throws ApiError
     */
    public getObjectDefinitionObjectActionsPage({
        objectDefinitionId,
        page,
        pageSize,
        search,
        sort,
    }: {
        objectDefinitionId: number,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ObjectAction>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions',
            path: {
                'objectDefinitionId': objectDefinitionId,
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
     * @returns ObjectAction
     * @throws ApiError
     */
    public postObjectDefinitionObjectAction({
        objectDefinitionId,
        requestBody,
    }: {
        objectDefinitionId: number,
        requestBody?: ObjectAction,
    }): CancelablePromise<ObjectAction> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions',
            path: {
                'objectDefinitionId': objectDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
