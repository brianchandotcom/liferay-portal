/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Document } from '../models/Document';
import type { Rating } from '../models/Rating';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DocumentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Document
     * @throws ApiError
     */
    public getAssetLibraryDocumentsPage({
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
    }): CancelablePromise<Array<Document>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents',
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
     * @returns Document
     * @throws ApiError
     */
    public postAssetLibraryDocument({
        assetLibraryId,
        formData,
    }: {
        assetLibraryId: number,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Deletes the asset library's document by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteAssetLibraryDocumentByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the asset library's document by external reference code.
     * @returns Document
     * @throws ApiError
     */
    public getAssetLibraryDocumentByExternalReferenceCode({
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
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/by-external-reference-code/{externalReferenceCode}',
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
     * Replaces the document by external reference code with the information sent in the request body, or replaces it if it not exists. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file'sbytes (`file`), and an optional JSON string (`document`) with the metadata.
     * @returns Document
     * @throws ApiError
     */
    public putAssetLibraryDocumentByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
        formData,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getAssetLibraryDocumentPermissionsPage({
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
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/permissions',
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
    public putAssetLibraryDocumentPermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the documents rated by the user.
     * @returns Document
     * @throws ApiError
     */
    public getAssetLibraryDocumentsRatedByMePage({
        assetLibraryId,
        page,
        pageSize,
    }: {
        assetLibraryId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Document>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/rated-by-me',
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
     * Retrieves the folder's documents. Results can be paginated, filtered, searched, and sorted.
     * @returns Document
     * @throws ApiError
     */
    public getDocumentFolderDocumentsPage({
        documentFolderId,
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
        documentFolderId: number,
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
    }): CancelablePromise<Array<Document>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/documents',
            path: {
                'documentFolderId': documentFolderId,
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
     * Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
     * @returns Document
     * @throws ApiError
     */
    public postDocumentFolderDocument({
        documentFolderId,
        formData,
    }: {
        documentFolderId: number,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/documents',
            path: {
                'documentFolderId': documentFolderId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Deletes the document and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteDocument({
        documentId,
    }: {
        documentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/documents/{documentId}',
            path: {
                'documentId': documentId,
            },
        });
    }
    /**
     * Retrieves the document.
     * @returns Document
     * @throws ApiError
     */
    public getDocument({
        documentId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        documentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/documents/{documentId}',
            path: {
                'documentId': documentId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
     * @returns Document
     * @throws ApiError
     */
    public patchDocument({
        documentId,
        formData,
    }: {
        documentId: number,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/documents/{documentId}',
            path: {
                'documentId': documentId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Replaces the document with the information sent in the request body. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
     * @returns Document
     * @throws ApiError
     */
    public putDocument({
        documentId,
        formData,
    }: {
        documentId: number,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/documents/{documentId}',
            path: {
                'documentId': documentId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Deletes the document's rating and returns a 204 if the operation succeeded.
     * @returns void
     * @throws ApiError
     */
    public deleteDocumentMyRating({
        documentId,
    }: {
        documentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
            path: {
                'documentId': documentId,
            },
        });
    }
    /**
     * Retrieves the document's rating.
     * @returns Rating
     * @throws ApiError
     */
    public getDocumentMyRating({
        documentId,
        fields,
        restrictFields,
    }: {
        documentId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
            path: {
                'documentId': documentId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a new rating for the document, by the user who authenticated the request.
     * @returns Rating
     * @throws ApiError
     */
    public postDocumentMyRating({
        documentId,
        requestBody,
    }: {
        documentId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
            path: {
                'documentId': documentId,
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
    public putDocumentMyRating({
        documentId,
        requestBody,
    }: {
        documentId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
            path: {
                'documentId': documentId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getDocumentPermissionsPage({
        documentId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        documentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/documents/{documentId}/permissions',
            path: {
                'documentId': documentId,
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
    public putDocumentPermissionsPage({
        documentId,
        requestBody,
    }: {
        documentId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/documents/{documentId}/permissions',
            path: {
                'documentId': documentId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the document's rendered display page
     * @returns string
     * @throws ApiError
     */
    public getDocumentRenderedContentByDisplayPageDisplayPageKey({
        documentId,
        displayPageKey,
        fields,
        nestedFields,
        restrictFields,
    }: {
        documentId: number,
        displayPageKey: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/documents/{documentId}/rendered-content-by-display-page/{displayPageKey}',
            path: {
                'documentId': documentId,
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
     * Retrieves the documents in the site's root folder. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns Document
     * @throws ApiError
     */
    public getSiteDocumentsPage({
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
    }): CancelablePromise<Array<Document>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents',
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
     * Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
     * @returns Document
     * @throws ApiError
     */
    public postSiteDocument({
        siteId,
        formData,
    }: {
        siteId: number,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents',
            path: {
                'siteId': siteId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Deletes the site's document by external reference code returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteDocumentByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's document by external reference code.
     * @returns Document
     * @throws ApiError
     */
    public getSiteDocumentByExternalReferenceCode({
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
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{externalReferenceCode}',
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
     * Replaces the document by external reference code with the information sent in the request body, or replaces it if it not exists. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file'sbytes (`file`), and an optional JSON string (`document`) with the metadata.
     * @returns Document
     * @throws ApiError
     */
    public putSiteDocumentByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        formData,
    }: {
        siteId: number,
        externalReferenceCode: string,
        formData?: {
            document?: Document;
            file?: Blob;
        },
    }): CancelablePromise<Document> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteDocumentPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/permissions',
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
    public putSiteDocumentPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the documents rated by the user.
     * @returns Document
     * @throws ApiError
     */
    public getSiteDocumentsRatedByMePage({
        siteId,
        page,
        pageSize,
    }: {
        siteId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Document>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/documents/rated-by-me',
            path: {
                'siteId': siteId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
}
