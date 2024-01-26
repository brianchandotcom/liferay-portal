/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessageBoardSection } from '../models/MessageBoardSection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MessageBoardSectionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the message board section and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteMessageBoardSection({
        messageBoardSectionId,
    }: {
        messageBoardSectionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
        });
    }
    /**
     * Retrieves the message board section.
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public getMessageBoardSection({
        messageBoardSectionId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        messageBoardSectionId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<MessageBoardSection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
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
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public patchMessageBoardSection({
        messageBoardSectionId,
        requestBody,
    }: {
        messageBoardSectionId: number,
        requestBody?: MessageBoardSection,
    }): CancelablePromise<MessageBoardSection> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the message board section with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public putMessageBoardSection({
        messageBoardSectionId,
        requestBody,
    }: {
        messageBoardSectionId: number,
        requestBody?: MessageBoardSection,
    }): CancelablePromise<MessageBoardSection> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getMessageBoardSectionPermissionsPage({
        messageBoardSectionId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        messageBoardSectionId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/permissions',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
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
    public putMessageBoardSectionPermissionsPage({
        messageBoardSectionId,
        requestBody,
    }: {
        messageBoardSectionId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/permissions',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putMessageBoardSectionSubscribe({
        messageBoardSectionId,
    }: {
        messageBoardSectionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/subscribe',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putMessageBoardSectionUnsubscribe({
        messageBoardSectionId,
    }: {
        messageBoardSectionId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/unsubscribe',
            path: {
                'messageBoardSectionId': messageBoardSectionId,
            },
        });
    }
    /**
     * Retrieves the parent message board section's subsections. Results can be paginated, filtered, searched, and sorted.
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public getMessageBoardSectionMessageBoardSectionsPage({
        parentMessageBoardSectionId,
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
        parentMessageBoardSectionId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<MessageBoardSection>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/message-board-sections/{parentMessageBoardSectionId}/message-board-sections',
            path: {
                'parentMessageBoardSectionId': parentMessageBoardSectionId,
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
     * Creates a new message board section in the parent section.
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public postMessageBoardSectionMessageBoardSection({
        parentMessageBoardSectionId,
        requestBody,
    }: {
        parentMessageBoardSectionId: number,
        requestBody?: MessageBoardSection,
    }): CancelablePromise<MessageBoardSection> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/message-board-sections/{parentMessageBoardSectionId}/message-board-sections',
            path: {
                'parentMessageBoardSectionId': parentMessageBoardSectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public getSiteMessageBoardSectionByFriendlyUrlPath({
        siteId,
        friendlyUrlPath,
        acceptLanguage,
    }: {
        siteId: number,
        friendlyUrlPath: string,
        acceptLanguage?: string,
    }): CancelablePromise<MessageBoardSection> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-section/by-friendly-url-path/{friendlyUrlPath}',
            path: {
                'siteId': siteId,
                'friendlyUrlPath': friendlyUrlPath,
            },
            headers: {
                'Accept-Language': acceptLanguage,
            },
        });
    }
    /**
     * Retrieves the site's message board sections. Results can be paginated, filtered, searched, flattened, and sorted.
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public getSiteMessageBoardSectionsPage({
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
    }): CancelablePromise<Array<MessageBoardSection>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections',
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
     * Creates a new message board section.
     * @returns MessageBoardSection
     * @throws ApiError
     */
    public postSiteMessageBoardSection({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: MessageBoardSection,
    }): CancelablePromise<MessageBoardSection> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections',
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
    public getSiteMessageBoardSectionPermissionsPage({
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
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections/permissions',
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
    public putSiteMessageBoardSectionPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
}
