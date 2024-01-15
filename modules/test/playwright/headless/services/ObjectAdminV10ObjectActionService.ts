/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectAction} from '../models/ObjectAdmin_v1_0_ObjectAction';
import type {ObjectAdmin_v1_0_PageObjectAction} from '../models/ObjectAdmin_v1_0_PageObjectAction';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectActionService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCodeObjectActionsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectAction> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-actions',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionByExternalReferenceCodeObjectAction(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectAction
	): CancelablePromise<ObjectAdmin_v1_0_ObjectAction> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-actions',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectActionId
	 * @returns ObjectAdmin_v1_0_ObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectAction(
		objectActionId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectAction> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-actions/{objectActionId}',
			path: {
				objectActionId: objectActionId,
			},
		});
	}

	/**
	 * @param objectActionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectAction(
		objectActionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectAction
	): CancelablePromise<ObjectAdmin_v1_0_ObjectAction> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-actions/{objectActionId}',
			path: {
				objectActionId: objectActionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectActionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectAction(
		objectActionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-actions/{objectActionId}',
			path: {
				objectActionId: objectActionId,
			},
		});
	}

	/**
	 * @param objectActionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10PatchObjectAction(
		objectActionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectAction
	): CancelablePromise<ObjectAdmin_v1_0_ObjectAction> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/object-admin/v1.0/object-actions/{objectActionId}',
			path: {
				objectActionId: objectActionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionObjectActionsPage(
		objectDefinitionId: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectAction> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectAction default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectAction(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectAction
	): CancelablePromise<ObjectAdmin_v1_0_ObjectAction> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectActionBatch(
		objectDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions/batch',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectActionsPageExportBatch(
		objectDefinitionId: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions/export-batch',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			query: {
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
	public static objectAdminV10PutObjectActionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-actions/batch',
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
	public static objectAdminV10DeleteObjectActionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-actions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
