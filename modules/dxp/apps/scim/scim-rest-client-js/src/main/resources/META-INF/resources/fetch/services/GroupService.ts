/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Group } from '../models/Group';
import type { QueryAttributes } from '../models/QueryAttributes';
import type { QueryResponse } from '../models/QueryResponse';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class GroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Lists groups.
     * @returns any The list of groups.
     * @throws ApiError
     */
    public getV2Groups({
        count,
        startIndex,
    }: {
        count?: number,
        startIndex?: number,
    }): CancelablePromise<(Blob | QueryResponse)> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/scim/v1.0/v2/Groups',
            query: {
                'count': count,
                'startIndex': startIndex,
            },
        });
    }
    /**
     * Creates a group.
     * @returns any The Group was successfully created.
     * @throws ApiError
     */
    public postV2Group({
        requestBody,
    }: {
        requestBody?: Group,
    }): CancelablePromise<(Blob | Group)> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/scim/v1.0/v2/Groups',
            body: requestBody,
            mediaType: 'application/scim+json',
        });
    }
    /**
     * Query groups.
     * @returns any List of found groups based on query
     * @throws ApiError
     */
    public postV2GroupSearch({
        requestBody,
    }: {
        requestBody?: QueryAttributes,
    }): CancelablePromise<(Blob | QueryResponse)> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/scim/v1.0/v2/Groups/.search',
            body: requestBody,
            mediaType: 'application/scim+json',
        });
    }
    /**
     * Deletes a group.
     * @returns void
     * @throws ApiError
     */
    public deleteV2Group({
        id,
    }: {
        id: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/scim/v1.0/v2/Groups/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * Retrieves a group.
     * @returns any If the resource exists, the server responds with HTTP status code 200 (OK) and includes the result in the body of the response.
     * @throws ApiError
     */
    public getV2GroupById({
        id,
    }: {
        id: string,
    }): CancelablePromise<(Blob | Group)> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/scim/v1.0/v2/Groups/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * Updates a group.
     * @returns any The Group was successfully updated.
     * @throws ApiError
     */
    public putV2Group({
        id,
        requestBody,
    }: {
        id: string,
        requestBody?: Group,
    }): CancelablePromise<(Blob | Group)> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/scim/v1.0/v2/Groups/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/scim+json',
        });
    }
}
