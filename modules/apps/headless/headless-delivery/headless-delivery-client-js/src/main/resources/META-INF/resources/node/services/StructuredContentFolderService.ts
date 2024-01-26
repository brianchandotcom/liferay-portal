/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { StructuredContentFolder } from '../models/StructuredContentFolder';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class StructuredContentFolderService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public getAssetLibraryStructuredContentFoldersPage({
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
    }): CancelablePromise<Array<StructuredContentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders',
            path: {
                'assetLibraryId': assetLibraryId,
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
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public postAssetLibraryStructuredContentFolder({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the asset library's structured content folder by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteAssetLibraryStructuredContentFolderByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the asset library's structured content folder by external reference code.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public getAssetLibraryStructuredContentFolderByExternalReferenceCode({
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
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
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
     * Updates the asset library's structured content folder with the given external reference code, or creates it if it not exists.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public putAssetLibraryStructuredContentFolderByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
        requestBody,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
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
    public getAssetLibraryStructuredContentFolderPermissionsPage({
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
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/permissions',
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
    public putAssetLibraryStructuredContentFolderPermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the site's structured content folders. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public getSiteStructuredContentFoldersPage({
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
    }): CancelablePromise<Array<StructuredContentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders',
            path: {
                'siteId': siteId,
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
     * Creates a new structured content folder.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public postSiteStructuredContentFolder({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteSiteStructuredContentFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public getSiteStructuredContentFolderByExternalReferenceCode({
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
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
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
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public putSiteStructuredContentFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
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
    public getSiteStructuredContentFolderPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/permissions',
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
    public putSiteStructuredContentFolderPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getStructuredContentFolderPermissionsPage({
        structuredContentFolderId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        structuredContentFolderId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-content-folder/{structuredContentFolderId}/permissions',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
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
    public putStructuredContentFolderPermissionsPage({
        structuredContentFolderId,
        requestBody,
    }: {
        structuredContentFolderId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-content-folder/{structuredContentFolderId}/permissions',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the parent structured content folder's subfolders. Results can be paginated, filtered, searched, and sorted.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public getStructuredContentFolderStructuredContentFoldersPage({
        parentStructuredContentFolderId,
        aggregationTerms,
        fields,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        parentStructuredContentFolderId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<StructuredContentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders',
            path: {
                'parentStructuredContentFolderId': parentStructuredContentFolderId,
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
     * Creates a new structured content folder in an existing folder.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public postStructuredContentFolderStructuredContentFolder({
        parentStructuredContentFolderId,
        requestBody,
    }: {
        parentStructuredContentFolderId: number,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders',
            path: {
                'parentStructuredContentFolderId': parentStructuredContentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the structured content folder and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteStructuredContentFolder({
        structuredContentFolderId,
    }: {
        structuredContentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
        });
    }
    /**
     * Retrieves the structured content folder.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public getStructuredContentFolder({
        structuredContentFolderId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        structuredContentFolderId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
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
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public patchStructuredContentFolder({
        structuredContentFolderId,
        requestBody,
    }: {
        structuredContentFolderId: number,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the structured content folder with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns StructuredContentFolder
     * @throws ApiError
     */
    public putStructuredContentFolder({
        structuredContentFolderId,
        requestBody,
    }: {
        structuredContentFolderId: number,
        requestBody?: StructuredContentFolder,
    }): CancelablePromise<StructuredContentFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putStructuredContentFolderSubscribe({
        structuredContentFolderId,
    }: {
        structuredContentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/subscribe',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putStructuredContentFolderUnsubscribe({
        structuredContentFolderId,
    }: {
        structuredContentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/unsubscribe',
            path: {
                'structuredContentFolderId': structuredContentFolderId,
            },
        });
    }
}
