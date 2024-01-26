/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { KnowledgeBaseAttachment } from '../models/KnowledgeBaseAttachment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class KnowledgeBaseAttachmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the knowledge base article's attachments.
     * @returns KnowledgeBaseAttachment
     * @throws ApiError
     */
    public getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage({
        knowledgeBaseArticleId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        knowledgeBaseArticleId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Array<KnowledgeBaseAttachment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a new attachment for an existing knowledge base article. The request body must be `multipart/form-data` with two parts, a `file` part with the file's bytes, and an optional JSON string (`knowledgeBaseAttachment`) with the metadata.
     * @returns KnowledgeBaseAttachment
     * @throws ApiError
     */
    public postKnowledgeBaseArticleKnowledgeBaseAttachment({
        knowledgeBaseArticleId,
        formData,
    }: {
        knowledgeBaseArticleId: number,
        formData?: {
            file?: Blob;
            knowledgeBaseAttachment?: KnowledgeBaseAttachment;
        },
    }): CancelablePromise<KnowledgeBaseAttachment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments',
            path: {
                'knowledgeBaseArticleId': knowledgeBaseArticleId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Deletes the knowledge base file attachment and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteKnowledgeBaseAttachment({
        knowledgeBaseAttachmentId,
    }: {
        knowledgeBaseAttachmentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}',
            path: {
                'knowledgeBaseAttachmentId': knowledgeBaseAttachmentId,
            },
        });
    }
    /**
     * Retrieves the knowledge base attachment.
     * @returns KnowledgeBaseAttachment
     * @throws ApiError
     */
    public getKnowledgeBaseAttachment({
        knowledgeBaseAttachmentId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        knowledgeBaseAttachmentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<KnowledgeBaseAttachment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}',
            path: {
                'knowledgeBaseAttachmentId': knowledgeBaseAttachmentId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Delete the knowledge base attachment by knowledge base article's and knowledge base attachment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode({
        siteId,
        knowledgeBaseArticleExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        knowledgeBaseArticleExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'knowledgeBaseArticleExternalReferenceCode': knowledgeBaseArticleExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the knowledge base article attachment by external reference code.
     * @returns KnowledgeBaseAttachment
     * @throws ApiError
     */
    public getSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode({
        siteId,
        knowledgeBaseArticleExternalReferenceCode,
        externalReferenceCode,
        fields,
        nestedFields,
        restrictFields,
    }: {
        siteId: number,
        knowledgeBaseArticleExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<KnowledgeBaseAttachment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'knowledgeBaseArticleExternalReferenceCode': knowledgeBaseArticleExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
}
