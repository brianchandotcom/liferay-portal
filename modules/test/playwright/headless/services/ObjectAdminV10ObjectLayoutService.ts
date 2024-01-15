/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectLayout} from '../models/ObjectAdmin_v1_0_ObjectLayout';
import type {ObjectAdmin_v1_0_PageObjectLayout} from '../models/ObjectAdmin_v1_0_PageObjectLayout';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectLayoutService {

	/**
	 * @param objectDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectLayoutBatch(
		objectDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts/batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectLayoutBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-layouts/batch',
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
	public static objectAdminV10DeleteObjectLayoutBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-layouts/batch',
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
	public static objectAdminV10PostObjectDefinitionObjectLayoutsPageExportBatch(
		objectDefinitionId: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts/export-batch',
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
	 * @param objectDefinitionId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectLayout default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionObjectLayoutsPage(
		objectDefinitionId: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectLayout> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts',
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
	 * @returns ObjectAdmin_v1_0_ObjectLayout default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectLayout(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectLayout
	): CancelablePromise<ObjectAdmin_v1_0_ObjectLayout> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts',
			path: {
				objectDefinitionId: objectDefinitionId,
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
	 * @returns ObjectAdmin_v1_0_PageObjectLayout default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCodeObjectLayoutsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectLayout> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-layouts',
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
	 * @returns ObjectAdmin_v1_0_ObjectLayout default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionByExternalReferenceCodeObjectLayout(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectLayout
	): CancelablePromise<ObjectAdmin_v1_0_ObjectLayout> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-layouts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectLayoutId
	 * @returns ObjectAdmin_v1_0_ObjectLayout default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectLayout(
		objectLayoutId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectLayout> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-layouts/{objectLayoutId}',
			path: {
				objectLayoutId: objectLayoutId,
			},
		});
	}

	/**
	 * @param objectLayoutId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectLayout default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectLayout(
		objectLayoutId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectLayout
	): CancelablePromise<ObjectAdmin_v1_0_ObjectLayout> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-layouts/{objectLayoutId}',
			path: {
				objectLayoutId: objectLayoutId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectLayoutId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectLayout(
		objectLayoutId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-layouts/{objectLayoutId}',
			path: {
				objectLayoutId: objectLayoutId,
			},
		});
	}
}
