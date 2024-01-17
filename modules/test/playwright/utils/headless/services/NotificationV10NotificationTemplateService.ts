/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Notification_v1_0_NotificationTemplate} from '../models/Notification_v1_0_NotificationTemplate';
import type {Notification_v1_0_PageNotificationTemplate} from '../models/Notification_v1_0_PageNotificationTemplate';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class NotificationV10NotificationTemplateService {

	/**
	 * @param externalReferenceCode
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10GetNotificationTemplateByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/notification/v1.0/notification-templates/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10PutNotificationTemplateByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: Notification_v1_0_NotificationTemplate
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/notification/v1.0/notification-templates/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
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
	public static notificationV10PostNotificationTemplatesPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-templates/export-batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10PutNotificationTemplateBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/notification/v1.0/notification-templates/batch',
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
	public static notificationV10PostNotificationTemplateBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-templates/batch',
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
	public static notificationV10DeleteNotificationTemplateBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/notification/v1.0/notification-templates/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param notificationTemplateId
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10GetNotificationTemplate(
		notificationTemplateId: string
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
			path: {
				notificationTemplateId: notificationTemplateId,
			},
		});
	}

	/**
	 * @param notificationTemplateId
	 * @param requestBody
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10PutNotificationTemplate(
		notificationTemplateId: string,
		requestBody?: Notification_v1_0_NotificationTemplate
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
			path: {
				notificationTemplateId: notificationTemplateId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param notificationTemplateId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10DeleteNotificationTemplate(
		notificationTemplateId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
			path: {
				notificationTemplateId: notificationTemplateId,
			},
		});
	}

	/**
	 * @param notificationTemplateId
	 * @param requestBody
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10PatchNotificationTemplate(
		notificationTemplateId: string,
		requestBody?: Notification_v1_0_NotificationTemplate
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/notification/v1.0/notification-templates/{notificationTemplateId}',
			path: {
				notificationTemplateId: notificationTemplateId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param aggregationTerms
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns Notification_v1_0_PageNotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10GetNotificationTemplatesPage(
		aggregationTerms?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<Notification_v1_0_PageNotificationTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/notification/v1.0/notification-templates',
			query: {
				aggregationTerms: aggregationTerms,
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
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10PostNotificationTemplate(
		requestBody?: Notification_v1_0_NotificationTemplate
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-templates',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param notificationTemplateId
	 * @returns Notification_v1_0_NotificationTemplate default response
	 * @throws ApiError
	 */
	public static notificationV10PostNotificationTemplateCopy(
		notificationTemplateId: string
	): CancelablePromise<Notification_v1_0_NotificationTemplate> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/notification/v1.0/notification-templates/{notificationTemplateId}/copy',
			path: {
				notificationTemplateId: notificationTemplateId,
			},
		});
	}
}
