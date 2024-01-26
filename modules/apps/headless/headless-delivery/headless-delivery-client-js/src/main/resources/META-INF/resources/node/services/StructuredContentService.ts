/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Rating } from '../models/Rating';
import type { StructuredContent } from '../models/StructuredContent';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class StructuredContentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns StructuredContent
     * @throws ApiError
     */
    public getAssetLibraryStructuredContentsPage({
        assetLibraryId,
        aggregationTerms,
        fields,
        flatten,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        assetLibraryId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        flatten?: boolean,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<StructuredContent>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'flatten': flatten,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns StructuredContent
     * @throws ApiError
     */
    public postAssetLibraryStructuredContent({
        assetLibraryId,
        acceptLanguage,
        requestBody,
    }: {
        assetLibraryId: number,
        acceptLanguage?: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the asset library's structured content by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteAssetLibraryStructuredContentByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns StructuredContent
     * @throws ApiError
     */
    public getAssetLibraryStructuredContentByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the asset library's structured content with the given external reference code, or creates it if it not exists.
     * @returns StructuredContent
     * @throws ApiError
     */
    public putAssetLibraryStructuredContentByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
        requestBody,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
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
    public getAssetLibraryStructuredContentPermissionsPage({
        assetLibraryId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        assetLibraryId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putAssetLibraryStructuredContentPermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves a list of the content structure's structured content. Results can be paginated, filtered, searched, and sorted.
     * @returns StructuredContent
     * @throws ApiError
     */
    public getContentStructureStructuredContentsPage({
        contentStructureId,
        aggregationTerms,
        fields,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        contentStructureId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<StructuredContent>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/content-structures/{contentStructureId}/structured-contents',
            path: {
                'contentStructureId': contentStructureId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Retrieves the site's structured content. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns StructuredContent
     * @throws ApiError
     */
    public getSiteStructuredContentsPage({
        siteId,
        aggregationTerms,
        fields,
        flatten,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        siteId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        flatten?: boolean,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<StructuredContent>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents',
            path: {
                'siteId': siteId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'flatten': flatten,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a new structured content.
     * @returns StructuredContent
     * @throws ApiError
     */
    public postSiteStructuredContent({
        siteId,
        acceptLanguage,
        requestBody,
    }: {
        siteId: number,
        acceptLanguage?: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents',
            path: {
                'siteId': siteId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the site's structured content by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteStructuredContentByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's structured content by external reference code.
     * @returns StructuredContent
     * @throws ApiError
     */
    public getSiteStructuredContentByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the site's structured content with the given external reference code, or creates it if it not exists.
     * @returns StructuredContent
     * @throws ApiError
     */
    public putSiteStructuredContentByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves a structured content by its key (`articleKey`).
     * @returns StructuredContent
     * @throws ApiError
     */
    public getSiteStructuredContentByKey({
        siteId,
        key,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        siteId: number,
        key: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-key/{key}',
            path: {
                'siteId': siteId,
                'key': key,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves a structured content by its UUID.
     * @returns StructuredContent
     * @throws ApiError
     */
    public getSiteStructuredContentByUuid({
        siteId,
        uuid,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        siteId: number,
        uuid: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-uuid/{uuid}',
            path: {
                'siteId': siteId,
                'uuid': uuid,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteStructuredContentPermissionsPage({
        siteId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        siteId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteStructuredContentPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the folder's structured content. Results can be paginated, filtered, searched, and sorted.
     * @returns StructuredContent
     * @throws ApiError
     */
    public getStructuredContentFolderStructuredContentsPage({
        structuredContentFolderId,
        aggregationTerms,
        fields,
        flatten,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        structuredContentFolderId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        flatten?: boolean,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<StructuredContent>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'flatten': flatten,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a new structured content in the folder.
     * @returns StructuredContent
     * @throws ApiError
     */
    public postStructuredContentFolderStructuredContent({
        structuredContentFolderId,
        acceptLanguage,
        requestBody,
    }: {
        structuredContentFolderId: number,
        acceptLanguage?: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the structured content and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteStructuredContent({
        structuredContentId,
    }: {
        structuredContentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
            path: {
                'structuredContentId': structuredContentId,
            },
        });
    }
    /**
     * Retrieves the structured content via its ID.
     * @returns StructuredContent
     * @throws ApiError
     */
    public getStructuredContent({
        structuredContentId,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        structuredContentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
            path: {
                'structuredContentId': structuredContentId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates only the fields received in the request body, leaving any other fields untouched.
     * @returns StructuredContent
     * @throws ApiError
     */
    public patchStructuredContent({
        structuredContentId,
        acceptLanguage,
        requestBody,
    }: {
        structuredContentId: number,
        acceptLanguage?: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
            path: {
                'structuredContentId': structuredContentId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the structured content with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns StructuredContent
     * @throws ApiError
     */
    public putStructuredContent({
        structuredContentId,
        acceptLanguage,
        requestBody,
    }: {
        structuredContentId: number,
        acceptLanguage?: string,
        requestBody?: StructuredContent,
    }): CancelablePromise<StructuredContent> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
            path: {
                'structuredContentId': structuredContentId,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the structured content's rating and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteStructuredContentMyRating({
        structuredContentId,
    }: {
        structuredContentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
            path: {
                'structuredContentId': structuredContentId,
            },
        });
    }
    /**
     * Retrieves the structured content's rating.
     * @returns Rating
     * @throws ApiError
     */
    public getStructuredContentMyRating({
        structuredContentId,
        fields,
        restrictFields,
    }: {
        structuredContentId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
            path: {
                'structuredContentId': structuredContentId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Create a rating for the structured content.
     * @returns Rating
     * @throws ApiError
     */
    public postStructuredContentMyRating({
        structuredContentId,
        requestBody,
    }: {
        structuredContentId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
            path: {
                'structuredContentId': structuredContentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns Rating
     * @throws ApiError
     */
    public putStructuredContentMyRating({
        structuredContentId,
        requestBody,
    }: {
        structuredContentId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
            path: {
                'structuredContentId': structuredContentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getStructuredContentPermissionsPage({
        structuredContentId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        structuredContentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions',
            path: {
                'structuredContentId': structuredContentId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putStructuredContentPermissionsPage({
        structuredContentId,
        requestBody,
    }: {
        structuredContentId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions',
            path: {
                'structuredContentId': structuredContentId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the structured content's rendered display page
     * @returns string
     * @throws ApiError
     */
    public getStructuredContentRenderedContentByDisplayPageDisplayPageKey({
        structuredContentId,
        displayPageKey,
        fields,
        nestedFields,
        restrictFields,
    }: {
        structuredContentId: number,
        displayPageKey: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content-by-display-page/{displayPageKey}',
            path: {
                'structuredContentId': structuredContentId,
                'displayPageKey': displayPageKey,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the structured content's rendered template (the result of applying the structure's values to a template).
     * @returns string
     * @throws ApiError
     */
    public getStructuredContentRenderedContentContentTemplate({
        structuredContentId,
        contentTemplateId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        structuredContentId: number,
        contentTemplateId: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content/{contentTemplateId}',
            path: {
                'structuredContentId': structuredContentId,
                'contentTemplateId': contentTemplateId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putStructuredContentSubscribe({
        structuredContentId,
    }: {
        structuredContentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/subscribe',
            path: {
                'structuredContentId': structuredContentId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putStructuredContentUnsubscribe({
        structuredContentId,
    }: {
        structuredContentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/unsubscribe',
            path: {
                'structuredContentId': structuredContentId,
            },
        });
    }
}
