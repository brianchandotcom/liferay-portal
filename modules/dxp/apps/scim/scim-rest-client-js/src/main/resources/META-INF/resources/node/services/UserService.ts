/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { QueryAttributes } from '../models/QueryAttributes';
import type { QueryResponse } from '../models/QueryResponse';
import type { User } from '../models/User';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class UserService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Lists users.
     * @returns any The list of users.
     * @throws ApiError
     */
    public getV2Users({
        count,
        startIndex,
    }: {
        count?: number,
        startIndex?: number,
    }): CancelablePromise<(Blob | QueryResponse)> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/scim/v1.0/v2/Users',
            query: {
                'count': count,
                'startIndex': startIndex,
            },
        });
    }
    /**
     * Creates a user.
     * @returns any The User was successfully created.
     * @throws ApiError
     */
    public postV2User({
        requestBody,
    }: {
        requestBody?: User,
    }): CancelablePromise<(Blob | User)> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/scim/v1.0/v2/Users',
            body: requestBody,
            mediaType: 'application/scim+json',
        });
    }
    /**
     * Query users.
     * @returns any List of found users based on query
     * @throws ApiError
     */
    public postV2UserSearch({
        requestBody,
    }: {
        requestBody?: QueryAttributes,
    }): CancelablePromise<(Blob | QueryResponse)> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/scim/v1.0/v2/Users/.search',
            body: requestBody,
            mediaType: 'application/scim+json',
        });
    }
    /**
     * Deletes a user.
     * @returns void
     * @throws ApiError
     */
    public deleteV2User({
        id,
    }: {
        id: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/scim/v1.0/v2/Users/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * Retrieves a user.
     * @returns any If the resource exists, the server responds with HTTP status code 200 (OK) and includes the result in the body of the response.
     * @throws ApiError
     */
    public getV2UserById({
        id,
    }: {
        id: string,
    }): CancelablePromise<(Blob | User)> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/scim/v1.0/v2/Users/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * Updates a user.
     * @returns any The User was successfully updated.
     * @throws ApiError
     */
    public putV2User({
        id,
        requestBody,
    }: {
        id: string,
        requestBody?: User,
    }): CancelablePromise<(Blob | User)> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/scim/v1.0/v2/Users/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/scim+json',
        });
    }
}
