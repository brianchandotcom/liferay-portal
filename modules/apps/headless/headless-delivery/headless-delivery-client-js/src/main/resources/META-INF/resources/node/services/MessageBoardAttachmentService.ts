/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessageBoardAttachment } from '../models/MessageBoardAttachment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MessageBoardAttachmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the message board attachment and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteMessageBoardAttachment({
        messageBoardAttachmentId,
    }: {
        messageBoardAttachmentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}',
            path: {
                'messageBoardAttachmentId': messageBoardAttachmentId,
            },
        });
    }
    /**
     * Retrieves the message board attachment.
     * @returns MessageBoardAttachment
     * @throws ApiError
     */
    public getMessageBoardAttachment({
        messageBoardAttachmentId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        messageBoardAttachmentId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<MessageBoardAttachment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}',
            path: {
                'messageBoardAttachmentId': messageBoardAttachmentId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the message board message's attachments.
     * @returns MessageBoardAttachment
     * @throws ApiError
     */
    public getMessageBoardMessageMessageBoardAttachmentsPage({
        messageBoardMessageId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        messageBoardMessageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Array<MessageBoardAttachment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates an attachment for the message board message. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`MessageBoardAttachment`) with the metadata.
     * @returns MessageBoardAttachment
     * @throws ApiError
     */
    public postMessageBoardMessageMessageBoardAttachment({
        messageBoardMessageId,
        formData,
    }: {
        messageBoardMessageId: number,
        formData?: {
            MessageBoardAttachment?: MessageBoardAttachment;
            file?: Blob;
        },
    }): CancelablePromise<MessageBoardAttachment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Retrieves the message board thread's attachments.
     * @returns MessageBoardAttachment
     * @throws ApiError
     */
    public getMessageBoardThreadMessageBoardAttachmentsPage({
        messageBoardThreadId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        messageBoardThreadId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<Array<MessageBoardAttachment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a new attachment for the message board thread. The request body should be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`knowledgeBaseAttachment`) with the metadata.
     * @returns MessageBoardAttachment
     * @throws ApiError
     */
    public postMessageBoardThreadMessageBoardAttachment({
        messageBoardThreadId,
        formData,
    }: {
        messageBoardThreadId: number,
        formData?: {
            file?: Blob;
            messageBoardAttachment?: MessageBoardAttachment;
        },
    }): CancelablePromise<MessageBoardAttachment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * Delete the message board attachment by message board message's and message board attachment's external reference codes.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode({
        siteId,
        messageBoardMessageExternalReferenceCode,
        externalReferenceCode,
    }: {
        siteId: number,
        messageBoardMessageExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'messageBoardMessageExternalReferenceCode': messageBoardMessageExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the message board attachment by message board message's and message board attachment's external reference codes.
     * @returns MessageBoardAttachment
     * @throws ApiError
     */
    public getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode({
        siteId,
        messageBoardMessageExternalReferenceCode,
        externalReferenceCode,
        fields,
        restrictFields,
    }: {
        siteId: number,
        messageBoardMessageExternalReferenceCode: string,
        externalReferenceCode: string,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<MessageBoardAttachment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'messageBoardMessageExternalReferenceCode': messageBoardMessageExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
}
