/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectDefinition} from '../models/ObjectAdmin_v1_0_ObjectDefinition';
import type {ObjectAdmin_v1_0_PageObjectDefinition} from '../models/ObjectAdmin_v1_0_PageObjectDefinition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectDefinitionService {

	/**
	 * @param objectDefinitionId
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionPublish(
		objectDefinitionId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/publish',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
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
	public static objectAdminV10PostObjectDefinitionsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/export-batch',
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
	public static objectAdminV10PutObjectDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-definitions/batch',
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
	public static objectAdminV10PostObjectDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/batch',
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
	public static objectAdminV10DeleteObjectDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-definitions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectDefinitionByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectDefinition
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	 * @returns ObjectAdmin_v1_0_PageObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionsPage(
		aggregationTerms?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions',
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
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinition(
		requestBody?: ObjectAdmin_v1_0_ObjectDefinition
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinition(
		objectDefinitionId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectDefinition(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectDefinition
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectDefinitionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectDefinition(
		objectDefinitionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
		});
	}

	/**
	 * @param objectDefinitionId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectDefinition default response
	 * @throws ApiError
	 */
	public static objectAdminV10PatchObjectDefinition(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectDefinition
	): CancelablePromise<ObjectAdmin_v1_0_ObjectDefinition> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}',
			path: {
				objectDefinitionId: objectDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
