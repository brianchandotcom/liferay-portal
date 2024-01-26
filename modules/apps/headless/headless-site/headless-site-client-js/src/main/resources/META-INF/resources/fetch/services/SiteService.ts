/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Site } from '../models/Site';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SiteService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a site and all of its associated content.
     * @returns void
     * @throws ApiError
     */
    public deleteSite({
        siteId,
    }: {
        siteId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-site/v1.0/sites/{siteId}',
            path: {
                'siteId': siteId,
            },
        });
    }
    /**
     * Adds a new site
     * @returns Site default response
     * @throws ApiError
     */
    public postSite({
        requestBody,
    }: {
        requestBody?: Site,
    }): CancelablePromise<Site> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-site/v1.0/sites',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes a site and all of its associated content.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-site/v1.0/sites/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Adds or update a new site
     * @returns Site default response
     * @throws ApiError
     */
    public putSiteByExternalReferenceCode({
        externalReferenceCode,
        formData,
    }: {
        externalReferenceCode: string,
        formData?: {
            file?: Blob;
        },
    }): CancelablePromise<Site> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-site/v1.0/sites/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
}
