/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessageBoardThread } from '../models/MessageBoardThread';
import type { Rating } from '../models/Rating';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MessageBoardThreadService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the message board section's threads. Results can be paginated, filtered, searched, and sorted.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public getMessageBoardSectionMessageBoardThreadsPage({
        messageBoardSectionId,
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
        messageBoardSectionId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<MessageBoardThread>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
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
     * Creates a new message board thread inside a section.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public postMessageBoardSectionMessageBoardThread({
        messageBoardSectionId,
        requestBody,
    }: {
        messageBoardSectionId: number,
        requestBody?: MessageBoardThread,
    }): CancelablePromise<MessageBoardThread> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public getMessageBoardThreadsRankedPage({
        dateCreated,
        dateModified,
        fields,
        messageBoardSectionId,
        nestedFields,
        restrictFields,
        page,
        pageSize,
        sort,
    }: {
        dateCreated?: string,
        dateModified?: string,
        fields?: string,
        messageBoardSectionId?: number,
        nestedFields?: string,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<MessageBoardThread>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-threads/ranked',
            query: {
                'dateCreated': dateCreated,
                'dateModified': dateModified,
                'fields': fields,
                'messageBoardSectionId': messageBoardSectionId,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * Deletes the message board thread and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteMessageBoardThread({
        messageBoardThreadId,
    }: {
        messageBoardThreadId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
        });
    }
    /**
     * Retrieves the message board thread.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public getMessageBoardThread({
        messageBoardThreadId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        messageBoardThreadId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<MessageBoardThread> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
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
     * Updates only the fields received in the request body, leaving any other fields untouched.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public patchMessageBoardThread({
        messageBoardThreadId,
        requestBody,
    }: {
        messageBoardThreadId: number,
        requestBody?: MessageBoardThread,
    }): CancelablePromise<MessageBoardThread> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the message board thread with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public putMessageBoardThread({
        messageBoardThreadId,
        requestBody,
    }: {
        messageBoardThreadId: number,
        requestBody?: MessageBoardThread,
    }): CancelablePromise<MessageBoardThread> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the message board thread's rating and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteMessageBoardThreadMyRating({
        messageBoardThreadId,
    }: {
        messageBoardThreadId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
        });
    }
    /**
     * Retrieves the message board thread's rating.
     * @returns Rating
     * @throws ApiError
     */
    public getMessageBoardThreadMyRating({
        messageBoardThreadId,
        fields,
        restrictFields,
    }: {
        messageBoardThreadId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Creates the message board thread's rating.
     * @returns Rating
     * @throws ApiError
     */
    public postMessageBoardThreadMyRating({
        messageBoardThreadId,
        requestBody,
    }: {
        messageBoardThreadId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
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
    public putMessageBoardThreadMyRating({
        messageBoardThreadId,
        requestBody,
    }: {
        messageBoardThreadId: number,
        requestBody?: Rating,
    }): CancelablePromise<Rating> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getMessageBoardThreadPermissionsPage({
        messageBoardThreadId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        messageBoardThreadId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/permissions',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
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
    public putMessageBoardThreadPermissionsPage({
        messageBoardThreadId,
        requestBody,
    }: {
        messageBoardThreadId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/permissions',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putMessageBoardThreadSubscribe({
        messageBoardThreadId,
    }: {
        messageBoardThreadId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/subscribe',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putMessageBoardThreadUnsubscribe({
        messageBoardThreadId,
    }: {
        messageBoardThreadId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/unsubscribe',
            path: {
                'messageBoardThreadId': messageBoardThreadId,
            },
        });
    }
    /**
     * Retrieves the site's message board threads. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public getSiteMessageBoardThreadsPage({
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
    }): CancelablePromise<Array<MessageBoardThread>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads',
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
     * Creates a new message board thread.
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public postSiteMessageBoardThread({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: MessageBoardThread,
    }): CancelablePromise<MessageBoardThread> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns MessageBoardThread
     * @throws ApiError
     */
    public getSiteMessageBoardThreadByFriendlyUrlPath({
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
    }): CancelablePromise<MessageBoardThread> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/by-friendly-url-path/{friendlyUrlPath}',
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
    public getSiteMessageBoardThreadPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/permissions',
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
    public putSiteMessageBoardThreadPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
}
