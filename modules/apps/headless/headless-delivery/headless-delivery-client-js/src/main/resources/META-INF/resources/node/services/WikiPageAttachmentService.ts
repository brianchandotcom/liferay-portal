/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WikiPageAttachment } from '../models/WikiPageAttachment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WikiPageAttachmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Delete the wiki page attachment by wiki page's and wiki page attachment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode({
        siteId,
        wikiPageExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        wikiPageExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'wikiPageExternalReferenceCode': wikiPageExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the wiki page attachment by wiki page's and wiki page attachment's external reference codes.
     * @returns WikiPageAttachment
     * @throws ApiError
     */
    public getSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode({
        siteId,
        wikiPageExternalReferenceCode,
        externalReferenceCode,
        fields,
        restrictFields,
    }: {
        siteId: number,
        wikiPageExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<WikiPageAttachment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'wikiPageExternalReferenceCode': wikiPageExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Deletes the wiki page attachment and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteWikiPageAttachment({
        wikiPageAttachmentId,
    }: {
        wikiPageAttachmentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}',
            path: {
                'wikiPageAttachmentId': wikiPageAttachmentId,
            },
        });
    }
    /**
     * Retrieves the wiki page attachment.
     * @returns WikiPageAttachment
     * @throws ApiError
     */
    public getWikiPageAttachment({
        wikiPageAttachmentId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        wikiPageAttachmentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<WikiPageAttachment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}',
            path: {
                'wikiPageAttachmentId': wikiPageAttachmentId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the wiki page's attachments.
     * @returns WikiPageAttachment
     * @throws ApiError
     */
    public getWikiPageWikiPageAttachmentsPage({
        wikiPageId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        wikiPageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Array<WikiPageAttachment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments',
            path: {
                'wikiPageId': wikiPageId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata.
     * @returns WikiPageAttachment
     * @throws ApiError
     */
    public postWikiPageWikiPageAttachment({
        wikiPageId,
        formData,
    }: {
        wikiPageId: number,
        formData?: {
            WikiPageAttachment?: WikiPageAttachment;
            file?: Blob;
        },
    }): CancelablePromise<WikiPageAttachment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments',
            path: {
                'wikiPageId': wikiPageId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
}
