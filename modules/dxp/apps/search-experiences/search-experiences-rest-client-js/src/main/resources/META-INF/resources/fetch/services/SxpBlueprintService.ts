/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SXPBlueprint } from '../models/SXPBlueprint';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SxpBlueprintService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public getSxpBlueprintsPage({
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
    }): CancelablePromise<Array<SXPBlueprint>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-blueprints',
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
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public postSxpBlueprint({
        requestBody,
    }: {
        requestBody?: SXPBlueprint,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-blueprints',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public getSxpBlueprintByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public putSxpBlueprintByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: SXPBlueprint,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public postSxpBlueprintValidate({
        requestBody,
    }: {
        requestBody?: string,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/validate',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteSxpBlueprint({
        sxpBlueprintId,
    }: {
        sxpBlueprintId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/{sxpBlueprintId}',
            path: {
                'sxpBlueprintId': sxpBlueprintId,
            },
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public getSxpBlueprint({
        sxpBlueprintId,
    }: {
        sxpBlueprintId: number,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/{sxpBlueprintId}',
            path: {
                'sxpBlueprintId': sxpBlueprintId,
            },
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public patchSxpBlueprint({
        sxpBlueprintId,
        requestBody,
    }: {
        sxpBlueprintId: number,
        requestBody?: SXPBlueprint,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/{sxpBlueprintId}',
            path: {
                'sxpBlueprintId': sxpBlueprintId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public putSxpBlueprint({
        sxpBlueprintId,
        requestBody,
    }: {
        sxpBlueprintId: number,
        requestBody?: SXPBlueprint,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/{sxpBlueprintId}',
            path: {
                'sxpBlueprintId': sxpBlueprintId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns SXPBlueprint
     * @throws ApiError
     */
    public postSxpBlueprintCopy({
        sxpBlueprintId,
    }: {
        sxpBlueprintId: number,
    }): CancelablePromise<SXPBlueprint> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/{sxpBlueprintId}/copy',
            path: {
                'sxpBlueprintId': sxpBlueprintId,
            },
        });
    }
    /**
     * @returns binary
     * @throws ApiError
     */
    public getSxpBlueprintExport({
        sxpBlueprintId,
    }: {
        sxpBlueprintId: number,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/search-experiences-rest/v1.0/sxp-blueprints/{sxpBlueprintId}/export',
            path: {
                'sxpBlueprintId': sxpBlueprintId,
            },
        });
    }
}
