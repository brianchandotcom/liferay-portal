/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_MessageBoardThread} from '../models/HeadlessDelivery_v1_0_MessageBoardThread';
import type {HeadlessDelivery_v1_0_PageMessageBoardThread} from '../models/HeadlessDelivery_v1_0_PageMessageBoardThread';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10MessageBoardThreadService {

	/**
	 * Retrieves the site's message board threads. Results can be paginated, filtered, searched, flattened, and sorted.
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param flatten
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardThreadsPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		flatten?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardThread> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads',
			path: {
				siteId: siteId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				flatten: flatten,
				nestedFields: nestedFields,
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
	 * Creates a new message board thread.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteMessageBoardThread(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardThread
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardThread> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dateCreated
	 * @param dateModified
	 * @param fields
	 * @param messageBoardSectionId
	 * @param nestedFields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardThreadsRankedPage(
		dateCreated?: string,
		dateModified?: string,
		fields?: string,
		messageBoardSectionId?: string,
		nestedFields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardThread> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-threads/ranked',
			query: {
				dateCreated: dateCreated,
				dateModified: dateModified,
				fields: fields,
				messageBoardSectionId: messageBoardSectionId,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * Retrieves the message board thread's rating.
	 * @param messageBoardThreadId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardThreadMyRating(
		messageBoardThreadId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param messageBoardThreadId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardThreadMyRating(
		messageBoardThreadId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Creates the message board thread's rating.
	 * @param messageBoardThreadId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMyRating(
		messageBoardThreadId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the message board thread's rating and returns a 204 if the operation succeeds.
	 * @param messageBoardThreadId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardThreadMyRating(
		messageBoardThreadId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardThreadPermissionsPage(
		messageBoardThreadId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/permissions',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardThreadPermissionsPage(
		messageBoardThreadId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/permissions',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteMessageBoardThreadBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the message board section's threads. Results can be paginated, filtered, searched, and sorted.
	 * @param messageBoardSectionId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardSectionMessageBoardThreadsPage(
		messageBoardSectionId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardThread> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				nestedFields: nestedFields,
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
	 * Creates a new message board thread inside a section.
	 * @param messageBoardSectionId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardSectionMessageBoardThread(
		messageBoardSectionId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardThread
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardThread> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardThreadPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/permissions',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteMessageBoardThreadPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param messageBoardSectionId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardSectionMessageBoardThreadsPageExportBatch(
		messageBoardSectionId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads/export-batch',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
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
	 * @param messageBoardSectionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardSectionMessageBoardThreadBatch(
		messageBoardSectionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads/batch',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
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
	public static headlessDeliveryV10PutMessageBoardThreadBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-threads/batch',
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
	public static headlessDeliveryV10DeleteMessageBoardThreadBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-threads/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the message board thread.
	 * @param messageBoardThreadId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardThread(
		messageBoardThreadId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardThread> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the message board thread with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param messageBoardThreadId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardThread(
		messageBoardThreadId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardThread
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardThread> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the message board thread and returns a 204 if the operation succeeds.
	 * @param messageBoardThreadId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardThread(
		messageBoardThreadId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param messageBoardThreadId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchMessageBoardThread(
		messageBoardThreadId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardThread
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardThread> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardThreadSubscribe(
		messageBoardThreadId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/subscribe',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardThreadUnsubscribe(
		messageBoardThreadId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/unsubscribe',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
		});
	}

	/**
	 * @param siteId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteMessageBoardThreadsPageExportBatch(
		siteId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/export-batch',
			path: {
				siteId: siteId,
			},
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
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardThread default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardThreadByFriendlyUrlPath(
		siteId: string,
		friendlyUrlPath: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardThread> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-threads/by-friendly-url-path/{friendlyUrlPath}',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}
}
