/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NotificationQueueEntry } from '../models/NotificationQueueEntry';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class NotificationQueueEntryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns NotificationQueueEntry
     * @throws ApiError
     */
    public getNotificationQueueEntriesPage({
        filter,
        page,
        pageSize,
        search,
        sort,
        acceptLanguage,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
        acceptLanguage?: string,
    }): CancelablePromise<Array<NotificationQueueEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/notification/v1.0/notification-queue-entries',
            headers: {
                'Accept-Language': acceptLanguage,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns NotificationQueueEntry
     * @throws ApiError
     */
    public postNotificationQueueEntry({
        requestBody,
    }: {
        requestBody?: NotificationQueueEntry,
    }): CancelablePromise<NotificationQueueEntry> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/notification/v1.0/notification-queue-entries',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteNotificationQueueEntry({
        notificationQueueEntryId,
    }: {
        notificationQueueEntryId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/notification/v1.0/notification-queue-entries/{notificationQueueEntryId}',
            path: {
                'notificationQueueEntryId': notificationQueueEntryId,
            },
        });
    }
    /**
     * @returns NotificationQueueEntry
     * @throws ApiError
     */
    public getNotificationQueueEntry({
        notificationQueueEntryId,
    }: {
        notificationQueueEntryId: number,
    }): CancelablePromise<NotificationQueueEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/notification/v1.0/notification-queue-entries/{notificationQueueEntryId}',
            path: {
                'notificationQueueEntryId': notificationQueueEntryId,
            },
        });
    }
    /**
     * @returns any
     * @throws ApiError
     */
    public putNotificationQueueEntryResend({
        notificationQueueEntryId,
    }: {
        notificationQueueEntryId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/notification/v1.0/notification-queue-entries/{notificationQueueEntryId}/resend',
            path: {
                'notificationQueueEntryId': notificationQueueEntryId,
            },
        });
    }
}
