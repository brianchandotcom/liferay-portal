/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SXPElement } from '../models/SXPElement';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SxpElementService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public getSxpElementsPage({
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<SXPElement>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-elements',
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
     * @returns SXPElement
     * @throws ApiError
     */
    public postSxpElement({
        requestBody,
    }: {
        requestBody?: SXPElement,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-elements',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public getSxpElementByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-elements/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public putSxpElementByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: SXPElement,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/search-experiences-rest/v1.0/sxp-elements/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public postSxpElementPreview({
        requestBody,
    }: {
        requestBody?: SXPElement,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-elements/preview',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public postSxpElementValidate({
        requestBody,
    }: {
        requestBody?: string,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-elements/validate',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteSxpElement({
        sxpElementId,
    }: {
        sxpElementId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/search-experiences-rest/v1.0/sxp-elements/{sxpElementId}',
            path: {
                'sxpElementId': sxpElementId,
            },
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public getSxpElement({
        sxpElementId,
    }: {
        sxpElementId: number,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-elements/{sxpElementId}',
            path: {
                'sxpElementId': sxpElementId,
            },
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public patchSxpElement({
        sxpElementId,
        requestBody,
    }: {
        sxpElementId: number,
        requestBody?: SXPElement,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/search-experiences-rest/v1.0/sxp-elements/{sxpElementId}',
            path: {
                'sxpElementId': sxpElementId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public putSxpElement({
        sxpElementId,
        requestBody,
    }: {
        sxpElementId: number,
        requestBody?: SXPElement,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/search-experiences-rest/v1.0/sxp-elements/{sxpElementId}',
            path: {
                'sxpElementId': sxpElementId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPElement
     * @throws ApiError
     */
    public postSxpElementCopy({
        sxpElementId,
    }: {
        sxpElementId: number,
    }): CancelablePromise<SXPElement> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-elements/{sxpElementId}/copy',
            path: {
                'sxpElementId': sxpElementId,
            },
        });
    }
    /**
     * @returns binary
     * @throws ApiError
     */
    public getSxpElementExport({
        sxpElementId,
    }: {
        sxpElementId: number,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-elements/{sxpElementId}/export',
            path: {
                'sxpElementId': sxpElementId,
            },
        });
    }
}
