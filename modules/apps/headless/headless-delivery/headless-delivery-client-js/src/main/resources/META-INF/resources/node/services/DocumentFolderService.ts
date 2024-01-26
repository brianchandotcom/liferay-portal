/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DocumentFolder } from '../models/DocumentFolder';
import type { Rating } from '../models/Rating';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DocumentFolderService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getAssetLibraryDocumentFoldersPage({
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
    }): CancelablePromise<Array<DocumentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders',
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
     * @returns DocumentFolder
     * @throws ApiError
     */
    public postAssetLibraryDocumentFolder({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: DocumentFolder,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getAssetLibraryDocumentFolderPermissionsPage({
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
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/permissions',
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
    public putAssetLibraryDocumentFolderPermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the document folders rated by the user.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getAssetLibraryDocumentFoldersRatedByMePage({
        assetLibraryId,
        page,
        pageSize,
    }: {
        assetLibraryId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DocumentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/rated-by-me',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Deletes the document folder and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteDocumentFolder({
        documentFolderId,
    }: {
        documentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
            path: {
                'documentFolderId': documentFolderId,
            },
        });
    }
    /**
     * Retrieves the document folder.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getDocumentFolder({
        documentFolderId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        documentFolderId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
            path: {
                'documentFolderId': documentFolderId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates only the fields received in the request body. Any other fields are left untouched.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public patchDocumentFolder({
        documentFolderId,
        requestBody,
    }: {
        documentFolderId: number,
        requestBody?: DocumentFolder,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
            path: {
                'documentFolderId': documentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the document folder with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public putDocumentFolder({
        documentFolderId,
        requestBody,
    }: {
        documentFolderId: number,
        requestBody?: DocumentFolder,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
            path: {
                'documentFolderId': documentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the document folder's rating and returns a 204 if the operation succeeded.
     * @returns void
     * @throws ApiError
     */
    public deleteDocumentFolderMyRating({
        documentFolderId,
    }: {
        documentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
            path: {
                'documentFolderId': documentFolderId,
            },
        });
    }
    /**
     * Retrieves the document folder's rating.
     * @returns Rating
     * @throws ApiError
     */
    public getDocumentFolderMyRating({
        documentFolderId,
        fields,
        restrictFields,
    }: {
        documentFolderId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
            path: {
                'documentFolderId': documentFolderId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a new rating for the document folder, by the user who authenticated the request.
     * @returns Rating
     * @throws ApiError
     */
    public postDocumentFolderMyRating({
        documentFolderId,
        requestBody,
    }: {
        documentFolderId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
            path: {
                'documentFolderId': documentFolderId,
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
    public putDocumentFolderMyRating({
        documentFolderId,
        requestBody,
    }: {
        documentFolderId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
            path: {
                'documentFolderId': documentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getDocumentFolderPermissionsPage({
        documentFolderId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        documentFolderId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/permissions',
            path: {
                'documentFolderId': documentFolderId,
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
    public putDocumentFolderPermissionsPage({
        documentFolderId,
        requestBody,
    }: {
        documentFolderId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/permissions',
            path: {
                'documentFolderId': documentFolderId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putDocumentFolderSubscribe({
        documentFolderId,
    }: {
        documentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/subscribe',
            path: {
                'documentFolderId': documentFolderId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putDocumentFolderUnsubscribe({
        documentFolderId,
    }: {
        documentFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/unsubscribe',
            path: {
                'documentFolderId': documentFolderId,
            },
        });
    }
    /**
     * Retrieves the folder's subfolders. Results can be paginated, filtered, searched, and sorted.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getDocumentFolderDocumentFoldersPage({
        parentDocumentFolderId,
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
        parentDocumentFolderId: number,
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
    }): CancelablePromise<Array<DocumentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/document-folders/{parentDocumentFolderId}/document-folders',
            path: {
                'parentDocumentFolderId': parentDocumentFolderId,
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
     * Creates a new folder in a folder identified by `parentDocumentFolderId`.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public postDocumentFolderDocumentFolder({
        parentDocumentFolderId,
        requestBody,
    }: {
        parentDocumentFolderId: number,
        requestBody?: DocumentFolder,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/document-folders/{parentDocumentFolderId}/document-folders',
            path: {
                'parentDocumentFolderId': parentDocumentFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the site's document folders. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getSiteDocumentFoldersPage({
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
    }): CancelablePromise<Array<DocumentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/document-folders',
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
     * Creates a new document folder.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public postSiteDocumentFolder({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: DocumentFolder,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/document-folders',
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
    public getSiteDocumentFolderPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/permissions',
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
    public putSiteDocumentFolderPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the document folders rated by the user.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getSiteDocumentFoldersRatedByMePage({
        siteId,
        page,
        pageSize,
    }: {
        siteId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DocumentFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/rated-by-me',
            path: {
                'siteId': siteId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Deletes the site's document folder by external reference code returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteDocumentsFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents-folder/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's document folder by external reference code.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public getSiteDocumentsFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents-folder/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Replaces the document folder by external reference code with the information sent in the request body, or replaces it if it not exists.
     * @returns DocumentFolder
     * @throws ApiError
     */
    public putSiteDocumentsFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: DocumentFolder,
    }): CancelablePromise<DocumentFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents-folder/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
