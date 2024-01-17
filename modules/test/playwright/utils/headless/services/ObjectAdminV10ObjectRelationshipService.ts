/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectRelationship} from '../models/ObjectAdmin_v1_0_ObjectRelationship';
import type {ObjectAdmin_v1_0_PageObjectRelationship} from '../models/ObjectAdmin_v1_0_PageObjectRelationship';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectRelationshipService {

	/**
	 * @param objectDefinitionId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ObjectAdmin_v1_0_PageObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionObjectRelationshipsPage(
		objectDefinitionId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectRelationship> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-relationships',
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
	 * @returns ObjectAdmin_v1_0_ObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionObjectRelationship(
		objectDefinitionId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectRelationship
	): CancelablePromise<ObjectAdmin_v1_0_ObjectRelationship> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-relationships',
			path: {
				objectDefinitionId: objectDefinitionId,
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
	public static objectAdminV10PutObjectRelationshipBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-relationships/batch',
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
	public static objectAdminV10DeleteObjectRelationshipBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-relationships/batch',
			query: {
				callbackURL: callbackUrl,
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
	 * @returns ObjectAdmin_v1_0_PageObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectDefinitionByExternalReferenceCodeObjectRelationshipsPage(
		externalReferenceCode: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectRelationship> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-relationships',
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
	 * @returns ObjectAdmin_v1_0_ObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectDefinitionByExternalReferenceCodeObjectRelationship(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectRelationship
	): CancelablePromise<ObjectAdmin_v1_0_ObjectRelationship> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-relationships',
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
	public static objectAdminV10PostObjectDefinitionObjectRelationshipsPageExportBatch(
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
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-relationships/export-batch',
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

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectRelationshipByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectRelationship
	): CancelablePromise<ObjectAdmin_v1_0_ObjectRelationship> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-relationships/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	public static objectAdminV10PostObjectDefinitionObjectRelationshipBatch(
		objectDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-relationships/batch',
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
	 * @param objectRelationshipId
	 * @returns ObjectAdmin_v1_0_ObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectRelationship(
		objectRelationshipId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectRelationship> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-relationships/{objectRelationshipId}',
			path: {
				objectRelationshipId: objectRelationshipId,
			},
		});
	}

	/**
	 * @param objectRelationshipId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectRelationship default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectRelationship(
		objectRelationshipId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectRelationship
	): CancelablePromise<ObjectAdmin_v1_0_ObjectRelationship> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-relationships/{objectRelationshipId}',
			path: {
				objectRelationshipId: objectRelationshipId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectRelationshipId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectRelationship(
		objectRelationshipId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-relationships/{objectRelationshipId}',
			path: {
				objectRelationshipId: objectRelationshipId,
			},
		});
	}
}
