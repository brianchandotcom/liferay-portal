/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_KnowledgeBaseFolder} from '../models/HeadlessDelivery_v1_0_KnowledgeBaseFolder';
import type {HeadlessDelivery_v1_0_PageKnowledgeBaseFolder} from '../models/HeadlessDelivery_v1_0_PageKnowledgeBaseFolder';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10KnowledgeBaseFolderService {

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteKnowledgeBaseFolderBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/batch',
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
	 * Retrieves the knowledge base folder.
	 * @param knowledgeBaseFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseFolder(
		knowledgeBaseFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the knowledge base folder with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param knowledgeBaseFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseFolder(
		knowledgeBaseFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseFolder
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the knowledge base folder and returns a 204 if the operation succeeds.
	 * @param knowledgeBaseFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteKnowledgeBaseFolder(
		knowledgeBaseFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param knowledgeBaseFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchKnowledgeBaseFolder(
		knowledgeBaseFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseFolder
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
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
	public static headlessDeliveryV10PutKnowledgeBaseFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-folders/batch',
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
	public static headlessDeliveryV10DeleteKnowledgeBaseFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-folders/batch',
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
	public static headlessDeliveryV10GetSiteKnowledgeBaseFolderPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/permissions',
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
	public static headlessDeliveryV10PutSiteKnowledgeBaseFolderPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteKnowledgeBaseFoldersPageExportBatch(
		siteId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/export-batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the site's knowledge base folders. Results can be paginated.
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageKnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteKnowledgeBaseFoldersPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageKnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Creates a new knowledge base folder.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteKnowledgeBaseFolder(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseFolder
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site's knowledge base folder by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteKnowledgeBaseFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}',
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
	 * Updates the site's knowledge base folder with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteKnowledgeBaseFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseFolder
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the knowledge base folder by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteKnowledgeBaseFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param knowledgeBaseFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseFolderPermissionsPage(
		knowledgeBaseFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/permissions',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
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
	 * @param knowledgeBaseFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseFolderPermissionsPage(
		knowledgeBaseFolderId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/permissions',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the knowledge base folder's subfolders.
	 * @param parentKnowledgeBaseFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageKnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseFolderKnowledgeBaseFoldersPage(
		parentKnowledgeBaseFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageKnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders',
			path: {
				parentKnowledgeBaseFolderId: parentKnowledgeBaseFolderId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Creates a knowledge base folder inside the parent folder.
	 * @param parentKnowledgeBaseFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseFolderKnowledgeBaseFolder(
		parentKnowledgeBaseFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseFolder
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders',
			path: {
				parentKnowledgeBaseFolderId: parentKnowledgeBaseFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
