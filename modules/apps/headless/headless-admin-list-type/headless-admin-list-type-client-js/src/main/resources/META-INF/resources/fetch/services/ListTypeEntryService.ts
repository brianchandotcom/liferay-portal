/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ListTypeEntry } from '../models/ListTypeEntry';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ListTypeEntryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns ListTypeEntry
     * @throws ApiError
     */
    public getListTypeDefinitionByExternalReferenceCodeListTypeEntriesPage({
        externalReferenceCode,
        aggregationTerms,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        aggregationTerms?: Array<string>,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ListTypeEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}/list-type-entries',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * @returns ListTypeEntry
     * @throws ApiError
     */
    public postListTypeDefinitionByExternalReferenceCodeListTypeEntry({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: ListTypeEntry,
    }): CancelablePromise<ListTypeEntry> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}/list-type-entries',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ListTypeEntry
     * @throws ApiError
     */
    public getListTypeDefinitionListTypeEntriesPage({
        listTypeDefinitionId,
        aggregationTerms,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        listTypeDefinitionId: number,
        aggregationTerms?: Array<string>,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<ListTypeEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}/list-type-entries',
            path: {
                'listTypeDefinitionId': listTypeDefinitionId,
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
     * @returns ListTypeEntry
     * @throws ApiError
     */
    public postListTypeDefinitionListTypeEntry({
        listTypeDefinitionId,
        requestBody,
    }: {
        listTypeDefinitionId: number,
        requestBody?: ListTypeEntry,
    }): CancelablePromise<ListTypeEntry> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}/list-type-entries',
            path: {
                'listTypeDefinitionId': listTypeDefinitionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteListTypeEntry({
        listTypeEntryId,
    }: {
        listTypeEntryId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-list-type/v1.0/list-type-entries/{listTypeEntryId}',
            path: {
                'listTypeEntryId': listTypeEntryId,
            },
        });
    }
    /**
     * @returns ListTypeEntry
     * @throws ApiError
     */
    public getListTypeEntry({
        listTypeEntryId,
    }: {
        listTypeEntryId: number,
    }): CancelablePromise<ListTypeEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-list-type/v1.0/list-type-entries/{listTypeEntryId}',
            path: {
                'listTypeEntryId': listTypeEntryId,
            },
        });
    }
    /**
     * @returns ListTypeEntry
     * @throws ApiError
     */
    public putListTypeEntry({
        listTypeEntryId,
        requestBody,
    }: {
        listTypeEntryId: number,
        requestBody?: ListTypeEntry,
    }): CancelablePromise<ListTypeEntry> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-list-type/v1.0/list-type-entries/{listTypeEntryId}',
            path: {
                'listTypeEntryId': listTypeEntryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
