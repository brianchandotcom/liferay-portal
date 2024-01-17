/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_MessageBoardMessage} from '../models/HeadlessDelivery_v1_0_MessageBoardMessage';
import type {HeadlessDelivery_v1_0_PageMessageBoardMessage} from '../models/HeadlessDelivery_v1_0_PageMessageBoardMessage';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10MessageBoardMessageService {

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
	public static headlessDeliveryV10PostSiteMessageBoardMessagesPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/export-batch',
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
	 * @param messageBoardMessageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardMessagePermissionsPage(
		messageBoardMessageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/permissions',
			path: {
				messageBoardMessageId: messageBoardMessageId,
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
	 * @param messageBoardMessageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardMessagePermissionsPage(
		messageBoardMessageId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/permissions',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param messageBoardMessageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardMessageUnsubscribe(
		messageBoardMessageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/unsubscribe',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardMessageBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-messages/batch',
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
	public static headlessDeliveryV10DeleteMessageBoardMessageBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-messages/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the message board message.
	 * @param messageBoardMessageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardMessage(
		messageBoardMessageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the message board message with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param messageBoardMessageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardMessage(
		messageBoardMessageId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardMessage
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the message board message and returns a 204 if the operation succeeds.
	 * @param messageBoardMessageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardMessage(
		messageBoardMessageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param messageBoardMessageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchMessageBoardMessage(
		messageBoardMessageId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardMessage
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the parent message board message's child messages. Results can be paginated, filtered, searched, and sorted.
	 * @param parentMessageBoardMessageId
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
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardMessageMessageBoardMessagesPage(
		parentMessageBoardMessageId: string,
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages',
			path: {
				parentMessageBoardMessageId: parentMessageBoardMessageId,
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
	 * Creates a child message board message of the parent message.
	 * @param parentMessageBoardMessageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardMessageMessageBoardMessage(
		parentMessageBoardMessageId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardMessage
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages',
			path: {
				parentMessageBoardMessageId: parentMessageBoardMessageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param messageBoardMessageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardMessageSubscribe(
		messageBoardMessageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/subscribe',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
		});
	}

	/**
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardMessageByFriendlyUrlPath(
		siteId: string,
		friendlyUrlPath: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-friendly-url-path/{friendlyUrlPath}',
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

	/**
	 * Retrieves the site's message board message by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardMessageByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the site's message board message with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteMessageBoardMessageByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardMessage
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the site's message board message by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteMessageBoardMessageByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the message board message's rating.
	 * @param messageBoardMessageId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardMessageMyRating(
		messageBoardMessageId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param messageBoardMessageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardMessageMyRating(
		messageBoardMessageId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Creates a rating for the message board message.
	 * @param messageBoardMessageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardMessageMyRating(
		messageBoardMessageId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the message board message's rating and returns a 204 if the operation succeeds.
	 * @param messageBoardMessageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardMessageMyRating(
		messageBoardMessageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMessageBoardMessagesPageExportBatch(
		messageBoardThreadId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages/export-batch',
			path: {
				messageBoardThreadId: messageBoardThreadId,
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
	 * Retrieves the message board thread's messages. Results can be paginated, filtered, searched, and sorted.
	 * @param messageBoardThreadId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardThreadMessageBoardMessagesPage(
		messageBoardThreadId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages',
			path: {
				messageBoardThreadId: messageBoardThreadId,
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
	 * Creates a new message in the message board thread.
	 * @param messageBoardThreadId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMessageBoardMessage(
		messageBoardThreadId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardMessage
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages',
			path: {
				messageBoardThreadId: messageBoardThreadId,
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
	public static headlessDeliveryV10GetSiteMessageBoardMessagePermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/permissions',
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
	public static headlessDeliveryV10PutSiteMessageBoardMessagePermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMessageBoardMessageBatch(
		messageBoardThreadId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages/batch',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site's message board messages user's activity.
	 * @param siteId
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteUserMessageBoardMessagesActivityPage(
		siteId: string,
		userId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/{userId}/message-board-messages/activity',
			path: {
				siteId: siteId,
				userId: userId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Retrieves the site's message board messages.
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
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardMessage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardMessagesPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardMessage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages',
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
}
