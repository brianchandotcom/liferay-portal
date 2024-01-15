/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Keyword} from '../models/HeadlessAdminTaxonomy_v1_0_Keyword';
import type {HeadlessAdminTaxonomy_v1_0_PageKeyword} from '../models/HeadlessAdminTaxonomy_v1_0_PageKeyword';
import type {HeadlessAdminTaxonomy_v1_0_PagePermission} from '../models/HeadlessAdminTaxonomy_v1_0_PagePermission';
import type {HeadlessAdminTaxonomy_v1_0_Permission} from '../models/HeadlessAdminTaxonomy_v1_0_Permission';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminTaxonomyV10KeywordService {

	/**
	 * @param assetLibraryId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostAssetLibraryKeywordBatch(
		assetLibraryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/batch',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostSiteKeywordsPageExportBatch(
		siteId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/export-batch',
			path: {
				siteId: siteId,
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
	 * @param assetLibraryId
	 * @param fields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetAssetLibraryKeywordPermissionsPage(
		assetLibraryId: string,
		fields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutAssetLibraryKeywordPermissionsPage(
		assetLibraryId: string,
		requestBody?: Array<HeadlessAdminTaxonomy_v1_0_Permission>
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param fields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetSiteKeywordPermissionsPage(
		siteId: string,
		fields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/permissions',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutSiteKeywordPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessAdminTaxonomy_v1_0_Permission>
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param assetLibraryId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostAssetLibraryKeywordsPageExportBatch(
		assetLibraryId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/export-batch',
			path: {
				assetLibraryId: assetLibraryId,
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
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostSiteKeywordBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param keywordId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutKeywordSubscribe(
		keywordId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}/subscribe',
			path: {
				keywordId: keywordId,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param aggregationTerms
	 * @param fields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminTaxonomy_v1_0_PageKeyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetAssetLibraryKeywordsPage(
		assetLibraryId: string,
		aggregationTerms?: string,
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageKeyword> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
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
	 * @param assetLibraryId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_Keyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostAssetLibraryKeyword(
		assetLibraryId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_Keyword
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_Keyword> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords',
			path: {
				assetLibraryId: assetLibraryId,
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
	public static headlessAdminTaxonomyV10PutKeywordBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/keywords/batch',
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
	public static headlessAdminTaxonomyV10DeleteKeywordBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/keywords/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a keyword.
	 * @param keywordId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessAdminTaxonomy_v1_0_Keyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetKeyword(
		keywordId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_Keyword> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}',
			path: {
				keywordId: keywordId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the keyword with the information sent in the request body. Any missing fields are deleted, unless required.
	 * @param keywordId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_Keyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutKeyword(
		keywordId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_Keyword
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_Keyword> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}',
			path: {
				keywordId: keywordId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the keyword and returns a 204 if the operation succeeds.
	 * @param keywordId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10DeleteKeyword(
		keywordId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}',
			path: {
				keywordId: keywordId,
			},
		});
	}

	/**
	 * Retrieves a Site's keywords. Results can be paginated, filtered, searched, and sorted.
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminTaxonomy_v1_0_PageKeyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetSiteKeywordsPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageKeyword> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords',
			path: {
				siteId: siteId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
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
	 * Inserts a new keyword in a Site.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_Keyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostSiteKeyword(
		siteId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_Keyword
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_Keyword> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param fields
	 * @param restrictFields
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns HeadlessAdminTaxonomy_v1_0_PageKeyword default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetKeywordsRankedPage(
		fields?: string,
		restrictFields?: string,
		siteId?: string,
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageKeyword> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/keywords/ranked',
			query: {
				fields: fields,
				restrictFields: restrictFields,
				siteId: siteId,
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * @param keywordId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutKeywordUnsubscribe(
		keywordId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/keywords/{keywordId}/unsubscribe',
			path: {
				keywordId: keywordId,
			},
		});
	}
}
