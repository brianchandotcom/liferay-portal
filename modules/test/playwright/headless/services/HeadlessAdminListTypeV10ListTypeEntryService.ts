/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminListType_v1_0_ListTypeEntry} from '../models/HeadlessAdminListType_v1_0_ListTypeEntry';
import type {HeadlessAdminListType_v1_0_PageListTypeEntry} from '../models/HeadlessAdminListType_v1_0_PageListTypeEntry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminListTypeV10ListTypeEntryService {

	/**
	 * @param listTypeEntryId
	 * @returns HeadlessAdminListType_v1_0_ListTypeEntry default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetListTypeEntry(
		listTypeEntryId: string
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/list-type-entries/{listTypeEntryId}',
			path: {
				listTypeEntryId: listTypeEntryId,
			},
		});
	}

	/**
	 * @param listTypeEntryId
	 * @param requestBody
	 * @returns HeadlessAdminListType_v1_0_ListTypeEntry default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PutListTypeEntry(
		listTypeEntryId: string,
		requestBody?: HeadlessAdminListType_v1_0_ListTypeEntry
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeEntry> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-list-type/v1.0/list-type-entries/{listTypeEntryId}',
			path: {
				listTypeEntryId: listTypeEntryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param listTypeEntryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10DeleteListTypeEntry(
		listTypeEntryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-list-type/v1.0/list-type-entries/{listTypeEntryId}',
			path: {
				listTypeEntryId: listTypeEntryId,
			},
		});
	}

	/**
	 * @param listTypeDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PostListTypeDefinitionListTypeEntryBatch(
		listTypeDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}/list-type-entries/batch',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
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
	public static headlessAdminListTypeV10PutListTypeEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-list-type/v1.0/list-type-entries/batch',
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
	public static headlessAdminListTypeV10DeleteListTypeEntryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-list-type/v1.0/list-type-entries/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param aggregationTerms
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminListType_v1_0_PageListTypeEntry default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetListTypeDefinitionByExternalReferenceCodeListTypeEntriesPage(
		externalReferenceCode: string,
		aggregationTerms?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminListType_v1_0_PageListTypeEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}/list-type-entries',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
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
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminListType_v1_0_ListTypeEntry default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PostListTypeDefinitionByExternalReferenceCodeListTypeEntry(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminListType_v1_0_ListTypeEntry
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/{externalReferenceCode}/list-type-entries',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param listTypeDefinitionId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PostListTypeDefinitionListTypeEntriesPageExportBatch(
		listTypeDefinitionId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}/list-type-entries/export-batch',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
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
	 * @param listTypeDefinitionId
	 * @param aggregationTerms
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminListType_v1_0_PageListTypeEntry default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetListTypeDefinitionListTypeEntriesPage(
		listTypeDefinitionId: string,
		aggregationTerms?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminListType_v1_0_PageListTypeEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}/list-type-entries',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
			},
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
	 * @param listTypeDefinitionId
	 * @param requestBody
	 * @returns HeadlessAdminListType_v1_0_ListTypeEntry default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10PostListTypeDefinitionListTypeEntry(
		listTypeDefinitionId: string,
		requestBody?: HeadlessAdminListType_v1_0_ListTypeEntry
	): CancelablePromise<HeadlessAdminListType_v1_0_ListTypeEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-list-type/v1.0/list-type-definitions/{listTypeDefinitionId}/list-type-entries',
			path: {
				listTypeDefinitionId: listTypeDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
