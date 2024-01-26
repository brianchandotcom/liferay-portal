/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessageBoardMessage } from '../models/MessageBoardMessage';
import type { Rating } from '../models/Rating';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MessageBoardMessageService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the message board message and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteMessageBoardMessage({
        messageBoardMessageId,
    }: {
        messageBoardMessageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
        });
    }
    /**
     * Retrieves the message board message.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getMessageBoardMessage({
        messageBoardMessageId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        messageBoardMessageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
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
     * Updates only the fields received in the request body, leaving any other fields untouched.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public patchMessageBoardMessage({
        messageBoardMessageId,
        requestBody,
    }: {
        messageBoardMessageId: number,
        requestBody?: MessageBoardMessage,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the message board message with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public putMessageBoardMessage({
        messageBoardMessageId,
        requestBody,
    }: {
        messageBoardMessageId: number,
        requestBody?: MessageBoardMessage,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the message board message's rating and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteMessageBoardMessageMyRating({
        messageBoardMessageId,
    }: {
        messageBoardMessageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
        });
    }
    /**
     * Retrieves the message board message's rating.
     * @returns Rating
     * @throws ApiError
     */
    public getMessageBoardMessageMyRating({
        messageBoardMessageId,
        fields,
        restrictFields,
    }: {
        messageBoardMessageId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates a rating for the message board message.
     * @returns Rating
     * @throws ApiError
     */
    public postMessageBoardMessageMyRating({
        messageBoardMessageId,
        requestBody,
    }: {
        messageBoardMessageId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
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
    public putMessageBoardMessageMyRating({
        messageBoardMessageId,
        requestBody,
    }: {
        messageBoardMessageId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getMessageBoardMessagePermissionsPage({
        messageBoardMessageId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        messageBoardMessageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/permissions',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
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
    public putMessageBoardMessagePermissionsPage({
        messageBoardMessageId,
        requestBody,
    }: {
        messageBoardMessageId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/permissions',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putMessageBoardMessageSubscribe({
        messageBoardMessageId,
    }: {
        messageBoardMessageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/subscribe',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putMessageBoardMessageUnsubscribe({
        messageBoardMessageId,
    }: {
        messageBoardMessageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/unsubscribe',
            path: {
                'messageBoardMessageId': messageBoardMessageId,
            },
        });
    }
    /**
     * Retrieves the parent message board message's child messages. Results can be paginated, filtered, searched, and sorted.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getMessageBoardMessageMessageBoardMessagesPage({
        parentMessageBoardMessageId,
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
        parentMessageBoardMessageId: number,
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
    }): CancelablePromise<Array<MessageBoardMessage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages',
            path: {
                'parentMessageBoardMessageId': parentMessageBoardMessageId,
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
     * Creates a child message board message of the parent message.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public postMessageBoardMessageMessageBoardMessage({
        parentMessageBoardMessageId,
        requestBody,
    }: {
        parentMessageBoardMessageId: number,
        requestBody?: MessageBoardMessage,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages',
            path: {
                'parentMessageBoardMessageId': parentMessageBoardMessageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the message board thread's messages. Results can be paginated, filtered, searched, and sorted.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getMessageBoardThreadMessageBoardMessagesPage({
        messageBoardThreadId,
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
        messageBoardThreadId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<MessageBoardMessage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
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
     * Creates a new message in the message board thread.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public postMessageBoardThreadMessageBoardMessage({
        messageBoardThreadId,
        requestBody,
    }: {
        messageBoardThreadId: number,
        requestBody?: MessageBoardMessage,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the site's message board messages.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getSiteMessageBoardMessagesPage({
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
    }): CancelablePromise<Array<MessageBoardMessage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages',
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
     * Deletes the site's message board message by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteMessageBoardMessageByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's message board message by external reference code.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getSiteMessageBoardMessageByExternalReferenceCode({
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
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}',
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
     * Updates the site's message board message with the given external reference code, or creates it if it not exists.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public putSiteMessageBoardMessageByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: MessageBoardMessage,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getSiteMessageBoardMessageByFriendlyUrlPath({
        siteId,
        friendlyUrlPath,
        fields,
        nestedFields,
        restrictFields,
        acceptLanguage,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        acceptLanguage?: string,
    }): CancelablePromise<MessageBoardMessage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-friendly-url-path/{friendlyUrlPath}',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
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
    public getSiteMessageBoardMessagePermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/permissions',
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
    public putSiteMessageBoardMessagePermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves the site's message board messages user's activity.
     * @returns MessageBoardMessage
     * @throws ApiError
     */
    public getSiteUserMessageBoardMessagesActivityPage({
        siteId,
        userId,
        page,
        pageSize,
    }: {
        siteId: number,
        userId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<MessageBoardMessage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/{userId}/message-board-messages/activity',
            path: {
                'siteId': siteId,
                'userId': userId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
}
