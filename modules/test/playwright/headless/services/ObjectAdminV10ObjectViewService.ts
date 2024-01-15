/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectView} from '../models/ObjectAdmin_v1_0_ObjectView';
import type {ObjectAdmin_v1_0_PageObjectView} from '../models/ObjectAdmin_v1_0_PageObjectView';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectViewService {

	/**
	 * @param objectDefinitionId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionObjectViewsPage(
		objectDefinitionId: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectView> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-views',
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
	 * @returns ObjectAdmin_v1_0_ObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectView(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectView
	): CancelablePromise<ObjectAdmin_v1_0_ObjectView> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-views',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectViewId
	 * @returns ObjectAdmin_v1_0_ObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectViewCopy(
		objectViewId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectView> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-views/{objectViewId}/copy',
			path: {
				objectViewId: objectViewId,
			},
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectViewBatch(
		objectDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-views/batch',
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
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCodeObjectViewsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectView> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-views',
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
	 * @returns ObjectAdmin_v1_0_ObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionByExternalReferenceCodeObjectView(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectView
	): CancelablePromise<ObjectAdmin_v1_0_ObjectView> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-views',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	public static objectAdminV10PostObjectDefinitionObjectViewsPageExportBatch(
		objectDefinitionId: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-views/export-batch',
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
	 * @param objectViewId
	 * @returns ObjectAdmin_v1_0_ObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectView(
		objectViewId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectView> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-views/{objectViewId}',
			path: {
				objectViewId: objectViewId,
			},
		});
	}

	/**
	 * @param objectViewId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectView default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectView(
		objectViewId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectView
	): CancelablePromise<ObjectAdmin_v1_0_ObjectView> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-views/{objectViewId}',
			path: {
				objectViewId: objectViewId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectViewId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectView(
		objectViewId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-views/{objectViewId}',
			path: {
				objectViewId: objectViewId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectViewBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-views/batch',
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
	public static objectAdminV10DeleteObjectViewBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-views/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
