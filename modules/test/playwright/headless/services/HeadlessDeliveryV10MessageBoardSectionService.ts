/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_MessageBoardSection} from '../models/HeadlessDelivery_v1_0_MessageBoardSection';
import type {HeadlessDelivery_v1_0_PageMessageBoardSection} from '../models/HeadlessDelivery_v1_0_PageMessageBoardSection';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10MessageBoardSectionService {

	/**
	 * @param messageBoardSectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardSectionUnsubscribe(
		messageBoardSectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/unsubscribe',
			path: {
				messageBoardSectionId: messageBoardSectionId,
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
	public static headlessDeliveryV10PostSiteMessageBoardSectionsPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections/export-batch',
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
	 * @returns HeadlessDelivery_v1_0_MessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardSectionByFriendlyUrlPath(
		siteId: string,
		friendlyUrlPath: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardSection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-section/by-friendly-url-path/{friendlyUrlPath}',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
			},
		});
	}

	/**
	 * @param messageBoardSectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardSectionSubscribe(
		messageBoardSectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/subscribe',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
		});
	}

	/**
	 * @param messageBoardSectionId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardSectionPermissionsPage(
		messageBoardSectionId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/permissions',
			path: {
				messageBoardSectionId: messageBoardSectionId,
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
	 * @param messageBoardSectionId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardSectionPermissionsPage(
		messageBoardSectionId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/permissions',
			path: {
				messageBoardSectionId: messageBoardSectionId,
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
	public static headlessDeliveryV10PostSiteMessageBoardSectionBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections/batch',
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
	 * Retrieves the site's message board sections. Results can be paginated, filtered, searched, flattened, and sorted.
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
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardSectionsPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardSection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections',
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
	 * Creates a new message board section.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteMessageBoardSection(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardSection
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardSection> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections',
			path: {
				siteId: siteId,
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
	public static headlessDeliveryV10PutMessageBoardSectionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-sections/batch',
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
	public static headlessDeliveryV10DeleteMessageBoardSectionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-sections/batch',
			query: {
				callbackURL: callbackUrl,
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
	public static headlessDeliveryV10GetSiteMessageBoardSectionPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections/permissions',
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
	public static headlessDeliveryV10PutSiteMessageBoardSectionPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-sections/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the message board section.
	 * @param messageBoardSectionId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardSection(
		messageBoardSectionId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardSection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the message board section with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param messageBoardSectionId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutMessageBoardSection(
		messageBoardSectionId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardSection
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardSection> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the message board section and returns a 204 if the operation succeeds.
	 * @param messageBoardSectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardSection(
		messageBoardSectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param messageBoardSectionId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchMessageBoardSection(
		messageBoardSectionId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardSection
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardSection> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}',
			path: {
				messageBoardSectionId: messageBoardSectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the parent message board section's subsections. Results can be paginated, filtered, searched, and sorted.
	 * @param parentMessageBoardSectionId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardSectionMessageBoardSectionsPage(
		parentMessageBoardSectionId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardSection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-sections/{parentMessageBoardSectionId}/message-board-sections',
			path: {
				parentMessageBoardSectionId: parentMessageBoardSectionId,
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
	 * Creates a new message board section in the parent section.
	 * @param parentMessageBoardSectionId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_MessageBoardSection default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardSectionMessageBoardSection(
		parentMessageBoardSectionId: string,
		requestBody?: HeadlessDelivery_v1_0_MessageBoardSection
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardSection> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-sections/{parentMessageBoardSectionId}/message-board-sections',
			path: {
				parentMessageBoardSectionId: parentMessageBoardSectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
