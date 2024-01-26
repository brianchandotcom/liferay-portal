/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PortalInstance } from '../models/PortalInstance';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PortalInstanceService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the portal instances
     * @returns PortalInstance default response
     * @throws ApiError
     */
    public getPortalInstancesPage({
        skipDefault,
    }: {
        skipDefault?: boolean,
    }): CancelablePromise<Array<PortalInstance>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-portal-instances/v1.0/portal-instances',
            query: {
                'skipDefault': skipDefault,
            },
        });
    }
    /**
     * Adds a new portal instance
     * @returns PortalInstance default response
     * @throws ApiError
     */
    public postPortalInstance({
        requestBody,
    }: {
        requestBody?: PortalInstance,
    }): CancelablePromise<PortalInstance> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-portal-instances/v1.0/portal-instances',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes the portal instance
     * @returns void
     * @throws ApiError
     */
    public deletePortalInstance({
        portalInstanceId,
    }: {
        portalInstanceId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}',
            path: {
                'portalInstanceId': portalInstanceId,
            },
        });
    }
    /**
     * Retrieves the portal instance
     * @returns PortalInstance default response
     * @throws ApiError
     */
    public getPortalInstance({
        portalInstanceId,
    }: {
        portalInstanceId: string,
    }): CancelablePromise<PortalInstance> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}',
            path: {
                'portalInstanceId': portalInstanceId,
            },
        });
    }
    /**
     * Updates the portal instance with information sent in the request body. Only the provided fields are updated.
     * @returns PortalInstance Portal instance successfully updated
     * @throws ApiError
     */
    public patchPortalInstance({
        portalInstanceId,
        requestBody,
    }: {
        portalInstanceId: string,
        requestBody?: PortalInstance,
    }): CancelablePromise<PortalInstance> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}',
            path: {
                'portalInstanceId': portalInstanceId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Activates the portal instance
     * @returns any default response
     * @throws ApiError
     */
    public putPortalInstanceActivate({
        portalInstanceId,
    }: {
        portalInstanceId: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}/activate',
            path: {
                'portalInstanceId': portalInstanceId,
            },
        });
    }
    /**
     * Deactivates the portal instance. When a portal instance is deactivated, its virtual host will not longer respond requests.
     * @returns any default response
     * @throws ApiError
     */
    public putPortalInstanceDeactivate({
        portalInstanceId,
    }: {
        portalInstanceId: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}/deactivate',
            path: {
                'portalInstanceId': portalInstanceId,
            },
        });
    }
}
