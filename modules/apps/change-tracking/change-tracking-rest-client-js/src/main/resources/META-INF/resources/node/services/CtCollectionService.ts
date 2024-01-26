/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CTCollection } from '../models/CTCollection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CtCollectionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public getCtCollectionsPage({
        status,
        page,
        pageSize,
        search,
        sort,
    }: {
        status?: Array<number>,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<CTCollection>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-collections',
            query: {
                'status': status,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public postCtCollection({
        requestBody,
    }: {
        requestBody?: CTCollection,
    }): CancelablePromise<CTCollection> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-collections',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteCtCollectionByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public getCtCollectionByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<CTCollection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public patchCtCollectionByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: CTCollection,
    }): CancelablePromise<CTCollection> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postCtCollectionByExternalReferenceCodePublish({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/publish',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postCtCollectionByExternalReferenceCodeSchedulePublish({
        externalReferenceCode,
        publishDate,
    }: {
        externalReferenceCode: string,
        publishDate?: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/schedule-publish',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'publishDate': publishDate,
            },
        });
    }
    /**
     * @returns string
     * @throws ApiError
     */
    public getCtCollectionByExternalReferenceCodeShareLink({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/share-link',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns string
     * @throws ApiError
     */
    public getCtCollectionShareLink({
        ctCollectionId,
    }: {
        ctCollectionId: number,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-collections/b{ctCollectionId}/share-link',
            path: {
                'ctCollectionId': ctCollectionId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteCtCollection({
        ctCollectionId,
    }: {
        ctCollectionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
            path: {
                'ctCollectionId': ctCollectionId,
            },
        });
    }
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public getCtCollection({
        ctCollectionId,
    }: {
        ctCollectionId: number,
    }): CancelablePromise<CTCollection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
            path: {
                'ctCollectionId': ctCollectionId,
            },
        });
    }
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public patchCtCollection({
        ctCollectionId,
        requestBody,
    }: {
        ctCollectionId: number,
        requestBody?: CTCollection,
    }): CancelablePromise<CTCollection> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
            path: {
                'ctCollectionId': ctCollectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns CTCollection
     * @throws ApiError
     */
    public putCtCollection({
        ctCollectionId,
        requestBody,
    }: {
        ctCollectionId: number,
        requestBody?: CTCollection,
    }): CancelablePromise<CTCollection> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}',
            path: {
                'ctCollectionId': ctCollectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postCtCollectionCheckout({
        ctCollectionId,
    }: {
        ctCollectionId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/checkout',
            path: {
                'ctCollectionId': ctCollectionId,
            },
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postCtCollectionPublish({
        ctCollectionId,
    }: {
        ctCollectionId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/publish',
            path: {
                'ctCollectionId': ctCollectionId,
            },
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public postCtCollectionSchedulePublish({
        ctCollectionId,
        publishDate,
    }: {
        ctCollectionId: number,
        publishDate?: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/schedule-publish',
            path: {
                'ctCollectionId': ctCollectionId,
            },
            query: {
                'publishDate': publishDate,
            },
        });
    }
}
