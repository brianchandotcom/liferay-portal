/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectField} from '../models/ObjectAdmin_v1_0_ObjectField';
import type {ObjectAdmin_v1_0_PageObjectField} from '../models/ObjectAdmin_v1_0_PageObjectField';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectFieldService {

	/**
	 * @param objectDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectFieldBatch(
		objectDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-fields/batch',
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
	public static objectAdminV10PutObjectFieldBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-fields/batch',
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
	public static objectAdminV10DeleteObjectFieldBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-fields/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectFieldId
	 * @returns ObjectAdmin_v1_0_ObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectField(
		objectFieldId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-fields/{objectFieldId}',
			path: {
				objectFieldId: objectFieldId,
			},
		});
	}

	/**
	 * @param objectFieldId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectField(
		objectFieldId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectField
	): CancelablePromise<ObjectAdmin_v1_0_ObjectField> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-fields/{objectFieldId}',
			path: {
				objectFieldId: objectFieldId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectFieldId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectField(
		objectFieldId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-fields/{objectFieldId}',
			path: {
				objectFieldId: objectFieldId,
			},
		});
	}

	/**
	 * @param objectFieldId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10PatchObjectField(
		objectFieldId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectField
	): CancelablePromise<ObjectAdmin_v1_0_ObjectField> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/object-admin/v1.0/object-fields/{objectFieldId}',
			path: {
				objectFieldId: objectFieldId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionObjectFieldsPage(
		objectDefinitionId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-fields',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
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
	 * @param objectDefinitionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectField(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectField
	): CancelablePromise<ObjectAdmin_v1_0_ObjectField> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-fields',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCodeObjectFieldsPage(
		externalReferenceCode: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectField> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-fields',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
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
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectField default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionByExternalReferenceCodeObjectField(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectField
	): CancelablePromise<ObjectAdmin_v1_0_ObjectField> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-fields',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectFieldsPageExportBatch(
		objectDefinitionId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-fields/export-batch',
			path: {
				objectDefinitionId: objectDefinitionId,
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
}
