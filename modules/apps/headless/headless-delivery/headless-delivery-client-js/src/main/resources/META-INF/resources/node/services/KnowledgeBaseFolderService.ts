/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { KnowledgeBaseFolder } from '../models/KnowledgeBaseFolder';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class KnowledgeBaseFolderService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the knowledge base folder and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteKnowledgeBaseFolder({
        knowledgeBaseFolderId,
    }: {
        knowledgeBaseFolderId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
            },
        });
    }
    /**
     * Retrieves the knowledge base folder.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public getKnowledgeBaseFolder({
        knowledgeBaseFolderId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        knowledgeBaseFolderId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
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
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public patchKnowledgeBaseFolder({
        knowledgeBaseFolderId,
        requestBody,
    }: {
        knowledgeBaseFolderId: number,
        requestBody?: KnowledgeBaseFolder,
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the knowledge base folder with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public putKnowledgeBaseFolder({
        knowledgeBaseFolderId,
        requestBody,
    }: {
        knowledgeBaseFolderId: number,
        requestBody?: KnowledgeBaseFolder,
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getKnowledgeBaseFolderPermissionsPage({
        knowledgeBaseFolderId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        knowledgeBaseFolderId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/permissions',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
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
    public putKnowledgeBaseFolderPermissionsPage({
        knowledgeBaseFolderId,
        requestBody,
    }: {
        knowledgeBaseFolderId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/permissions',
            path: {
                'knowledgeBaseFolderId': knowledgeBaseFolderId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the knowledge base folder's subfolders.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public getKnowledgeBaseFolderKnowledgeBaseFoldersPage({
        parentKnowledgeBaseFolderId,
        fields,
        nestedFields,
        restrictFields,
        page,
        pageSize,
    }: {
        parentKnowledgeBaseFolderId: number,
        fields?: string,
        nestedFields?: number,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<KnowledgeBaseFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders',
            path: {
                'parentKnowledgeBaseFolderId': parentKnowledgeBaseFolderId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Creates a knowledge base folder inside the parent folder.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public postKnowledgeBaseFolderKnowledgeBaseFolder({
        parentKnowledgeBaseFolderId,
        requestBody,
    }: {
        parentKnowledgeBaseFolderId: number,
        requestBody?: KnowledgeBaseFolder,
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders',
            path: {
                'parentKnowledgeBaseFolderId': parentKnowledgeBaseFolderId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the site's knowledge base folders. Results can be paginated.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public getSiteKnowledgeBaseFoldersPage({
        siteId,
        fields,
        nestedFields,
        restrictFields,
        page,
        pageSize,
    }: {
        siteId: number,
        fields?: string,
        nestedFields?: number,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<KnowledgeBaseFolder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Creates a new knowledge base folder.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public postSiteKnowledgeBaseFolder({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: KnowledgeBaseFolder,
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the knowledge base folder by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteKnowledgeBaseFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's knowledge base folder by external reference code.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public getSiteKnowledgeBaseFolderByExternalReferenceCode({
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
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}',
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
     * Updates the site's knowledge base folder with the given external reference code, or creates it if it not exists.
     * @returns KnowledgeBaseFolder
     * @throws ApiError
     */
    public putSiteKnowledgeBaseFolderByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: KnowledgeBaseFolder,
    }): CancelablePromise<KnowledgeBaseFolder> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}',
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
    public getSiteKnowledgeBaseFolderPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/permissions',
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
    public putSiteKnowledgeBaseFolderPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
}
