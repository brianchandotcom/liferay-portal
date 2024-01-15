/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_PageStructuredContent} from '../models/HeadlessAdminContent_v1_0_PageStructuredContent';
import type {HeadlessAdminContent_v1_0_StructuredContent} from '../models/HeadlessAdminContent_v1_0_StructuredContent';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminContentV10StructuredContentService {

	/**
	 * Retrieves the site's structured contents latest version. Results can be paginated, filtered, searched, flattened, and sorted.
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param flatten
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminContent_v1_0_PageStructuredContent default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10GetSiteStructuredContentsPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		flatten?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminContent_v1_0_PageStructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-content/v1.0/sites/{siteId}/structured-contents',
			path: {
				siteId: siteId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				flatten: flatten,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Retrieves all versions of a structured content via its ID.
	 * @param structuredContentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessAdminContent_v1_0_PageStructuredContent default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10GetStructuredContentsVersionsPage(
		structuredContentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminContent_v1_0_PageStructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-content/v1.0/structured-contents/{structuredContentId}/versions',
			path: {
				structuredContentId: structuredContentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves a version of a structured content
	 * @param structuredContentId
	 * @param version
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessAdminContent_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10GetStructuredContentByVersion(
		structuredContentId: string,
		version: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminContent_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-content/v1.0/structured-contents/{structuredContentId}/by-version/{version}',
			path: {
				structuredContentId: structuredContentId,
				version: version,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Deletes a version of a structured content via its ID.
	 * @param structuredContentId
	 * @param version
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10DeleteStructuredContentByVersion(
		structuredContentId: string,
		version: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-content/v1.0/structured-contents/{structuredContentId}/by-version/{version}',
			path: {
				structuredContentId: structuredContentId,
				version: version,
			},
		});
	}

	/**
	 * Creates a draft of a structured content
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessAdminContent_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10PostSiteStructuredContentDraft(
		siteId: string,
		requestBody?: HeadlessAdminContent_v1_0_StructuredContent
	): CancelablePromise<HeadlessAdminContent_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-content/v1.0/sites/{siteId}/structured-contents/draft',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
