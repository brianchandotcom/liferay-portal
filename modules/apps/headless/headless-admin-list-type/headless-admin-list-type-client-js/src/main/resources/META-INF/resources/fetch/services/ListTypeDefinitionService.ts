/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ListTypeDefinition } from '../models/ListTypeDefinition';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ListTypeDefinitionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public getListTypeDefinitionsPage({
        aggregationTerms,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        aggregationTerms?: Array<string>,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ListTypeDefinition>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-list-type/v1.0/list-type-definitions',
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
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public postListTypeDefinition({
        requestBody,
    }: {
        requestBody?: ListTypeDefinition,
    }): CancelablePromise<ListTypeDefinition> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-list-type/v1.0/list-type-definitions',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public getListTypeDefinitionByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ListTypeDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public putListTypeDefinitionByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ListTypeDefinition,
    }): CancelablePromise<ListTypeDefinition> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}',
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
    public deleteListTypeDefinition({
        listTypeDefinitionId,
    }: {
        listTypeDefinitionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
            path: {
                'listTypeDefinitionId': listTypeDefinitionId,
            },
        });
    }
    /**
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public getListTypeDefinition({
        listTypeDefinitionId,
    }: {
        listTypeDefinitionId: number,
    }): CancelablePromise<ListTypeDefinition> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
            path: {
                'listTypeDefinitionId': listTypeDefinitionId,
            },
        });
    }
    /**
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public patchListTypeDefinition({
        listTypeDefinitionId,
        requestBody,
    }: {
        listTypeDefinitionId: number,
        requestBody?: ListTypeDefinition,
    }): CancelablePromise<ListTypeDefinition> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
            path: {
                'listTypeDefinitionId': listTypeDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ListTypeDefinition
     * @throws ApiError
     */
    public putListTypeDefinition({
        listTypeDefinitionId,
        requestBody,
    }: {
        listTypeDefinitionId: number,
        requestBody?: ListTypeDefinition,
    }): CancelablePromise<ListTypeDefinition> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
            path: {
                'listTypeDefinitionId': listTypeDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
