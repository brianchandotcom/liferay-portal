/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessUserNotification_v1_0_PageUserNotification} from '../models/HeadlessUserNotification_v1_0_PageUserNotification';
import type {HeadlessUserNotification_v1_0_UserNotification} from '../models/HeadlessUserNotification_v1_0_UserNotification';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessUserNotificationV10UserNotificationService {

	/**
	 * Mark the user notification as unread.
	 * @param userNotificationId
	 * @param fields
	 * @param restrictFields
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessUserNotificationV10PutUserNotificationUnread(
		userNotificationId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-user-notification/v1.0/user-notifications/{userNotificationId}/unread',
			path: {
				userNotificationId: userNotificationId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the current user's notifications. Results can be paginated, filtered, searched and sorted.
	 * @param fields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessUserNotification_v1_0_PageUserNotification default response
	 * @throws ApiError
	 */
	public static headlessUserNotificationV10GetMyUserNotificationsPage(
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessUserNotification_v1_0_PageUserNotification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-user-notification/v1.0/my-user-notifications',
			query: {
				fields: fields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Mark the user notification as read.
	 * @param userNotificationId
	 * @param fields
	 * @param restrictFields
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessUserNotificationV10PutUserNotificationRead(
		userNotificationId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-user-notification/v1.0/user-notifications/{userNotificationId}/read',
			path: {
				userNotificationId: userNotificationId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the user notification.
	 * @param userNotificationId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessUserNotification_v1_0_UserNotification default response
	 * @throws ApiError
	 */
	public static headlessUserNotificationV10GetUserNotification(
		userNotificationId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessUserNotification_v1_0_UserNotification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-user-notification/v1.0/user-notifications/{userNotificationId}',
			path: {
				userNotificationId: userNotificationId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the user account's notifications. Results can be paginated, filtered, searched and sorted.
	 * @param userAccountId
	 * @param fields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessUserNotification_v1_0_PageUserNotification default response
	 * @throws ApiError
	 */
	public static headlessUserNotificationV10GetUserAccountUserNotificationsPage(
		userAccountId: string,
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessUserNotification_v1_0_PageUserNotification> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-user-notification/v1.0/user-accounts/{userAccountId}/user-notifications',
			path: {
				userAccountId: userAccountId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}
}
