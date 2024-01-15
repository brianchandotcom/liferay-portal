/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Notification_v1_0_NotificationQueueEntry} from '../models/Notification_v1_0_NotificationQueueEntry';
import type {Notification_v1_0_PageNotificationQueueEntry} from '../models/Notification_v1_0_PageNotificationQueueEntry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class NotificationV10NotificationQueueEntryService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10PostNotificationQueueEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-queue-entries/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10DeleteNotificationQueueEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/notification/v1.0/notification-queue-entries/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param notificationQueueEntryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10PutNotificationQueueEntryResend(
		notificationQueueEntryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/notification/v1.0/notification-queue-entries/{notificationQueueEntryId}/resend',
			path: {
				notificationQueueEntryId: notificationQueueEntryId,
			},
		});
	}

	/**
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10PostNotificationQueueEntriesPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-queue-entries/export-batch',
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns Notification_v1_0_PageNotificationQueueEntry default response
	 * @throws ApiError
	 */
	public static notificationV10GetNotificationQueueEntriesPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<Notification_v1_0_PageNotificationQueueEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/notification/v1.0/notification-queue-entries',
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns Notification_v1_0_NotificationQueueEntry default response
	 * @throws ApiError
	 */
	public static notificationV10PostNotificationQueueEntry(
		requestBody?: Notification_v1_0_NotificationQueueEntry
	): CancelablePromise<Notification_v1_0_NotificationQueueEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-queue-entries',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param notificationQueueEntryId
	 * @returns Notification_v1_0_NotificationQueueEntry default response
	 * @throws ApiError
	 */
	public static notificationV10GetNotificationQueueEntry(
		notificationQueueEntryId: string
	): CancelablePromise<Notification_v1_0_NotificationQueueEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/notification/v1.0/notification-queue-entries/{notificationQueueEntryId}',
			path: {
				notificationQueueEntryId: notificationQueueEntryId,
			},
		});
	}

	/**
	 * @param notificationQueueEntryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10DeleteNotificationQueueEntry(
		notificationQueueEntryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/notification/v1.0/notification-queue-entries/{notificationQueueEntryId}',
			path: {
				notificationQueueEntryId: notificationQueueEntryId,
			},
		});
	}
}
