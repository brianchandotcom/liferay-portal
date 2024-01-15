/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectFolder} from '../models/ObjectAdmin_v1_0_ObjectFolder';
import type {ObjectAdmin_v1_0_PageObjectFolder} from '../models/ObjectAdmin_v1_0_PageObjectFolder';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ObjectAdminV10ObjectFolderService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-folders/batch',
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
	public static objectAdminV10PostObjectFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-folders/batch',
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
	public static objectAdminV10DeleteObjectFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-folders/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectFolderId
	 * @returns ObjectAdmin_v1_0_ObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectFolder(
		objectFolderId: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-folders/{objectFolderId}',
			path: {
				objectFolderId: objectFolderId,
			},
		});
	}

	/**
	 * @param objectFolderId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectFolder(
		objectFolderId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectFolder
	): CancelablePromise<ObjectAdmin_v1_0_ObjectFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-folders/{objectFolderId}',
			path: {
				objectFolderId: objectFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param objectFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10DeleteObjectFolder(
		objectFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/object-admin/v1.0/object-folders/{objectFolderId}',
			path: {
				objectFolderId: objectFolderId,
			},
		});
	}

	/**
	 * @param objectFolderId
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10PatchObjectFolder(
		objectFolderId: string,
		requestBody?: ObjectAdmin_v1_0_ObjectFolder
	): CancelablePromise<ObjectAdmin_v1_0_ObjectFolder> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/object-admin/v1.0/object-folders/{objectFolderId}',
			path: {
				objectFolderId: objectFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns ObjectAdmin_v1_0_ObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectFolderByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<ObjectAdmin_v1_0_ObjectFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10PutObjectFolderByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: ObjectAdmin_v1_0_ObjectFolder
	): CancelablePromise<ObjectAdmin_v1_0_ObjectFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/object-admin/v1.0/object-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns ObjectAdmin_v1_0_PageObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetObjectFoldersPage(
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<ObjectAdmin_v1_0_PageObjectFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/object-folders',
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns ObjectAdmin_v1_0_ObjectFolder default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectFolder(
		requestBody?: ObjectAdmin_v1_0_ObjectFolder
	): CancelablePromise<ObjectAdmin_v1_0_ObjectFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-folders',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param search
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10PostObjectFoldersPageExportBatch(
		search?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/object-admin/v1.0/object-folders/export-batch',
			query: {
				search: search,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
