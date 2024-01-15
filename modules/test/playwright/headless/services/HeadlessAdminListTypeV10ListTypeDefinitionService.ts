/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminListType_v1_0_ListTypeDefinition} from '../models/HeadlessAdminListType_v1_0_ListTypeDefinition';
import type {HeadlessAdminListType_v1_0_PageListTypeDefinition} from '../models/HeadlessAdminListType_v1_0_PageListTypeDefinition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminListTypeV10ListTypeDefinitionService {

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
	public static headlessAdminListTypeV10PostListTypeDefinitionsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/export-batch',
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
	 * @param listTypeDefinitionId
	 * @returns HeadlessAdminListType_v1_0_ListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetListTypeDefinition(
		listTypeDefinitionId: string
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
			},
		});
	}

	/**
	 * @param listTypeDefinitionId
	 * @param requestBody
	 * @returns HeadlessAdminListType_v1_0_ListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PutListTypeDefinition(
		listTypeDefinitionId: string,
		requestBody?: HeadlessAdminListType_v1_0_ListTypeDefinition
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param listTypeDefinitionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10DeleteListTypeDefinition(
		listTypeDefinitionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
			},
		});
	}

	/**
	 * @param listTypeDefinitionId
	 * @param requestBody
	 * @returns HeadlessAdminListType_v1_0_ListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PatchListTypeDefinition(
		listTypeDefinitionId: string,
		requestBody?: HeadlessAdminListType_v1_0_ListTypeDefinition
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
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
	 * @returns HeadlessAdminListType_v1_0_PageListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetListTypeDefinitionsPage(
		aggregationTerms?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminListType_v1_0_PageListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/list-type-definitions',
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
	 * @returns HeadlessAdminListType_v1_0_ListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PostListTypeDefinition(
		requestBody?: HeadlessAdminListType_v1_0_ListTypeDefinition
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions',
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
	public static headlessAdminListTypeV10PutListTypeDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/batch',
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
	public static headlessAdminListTypeV10PostListTypeDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/batch',
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
	public static headlessAdminListTypeV10DeleteListTypeDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessAdminListType_v1_0_ListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetListTypeDefinitionByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminListType_v1_0_ListTypeDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PutListTypeDefinitionByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminListType_v1_0_ListTypeDefinition
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeDefinition> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
