/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_PagePermission} from '../models/HeadlessAdminTaxonomy_v1_0_PagePermission';
import type {HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary} from '../models/HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary';
import type {HeadlessAdminTaxonomy_v1_0_Permission} from '../models/HeadlessAdminTaxonomy_v1_0_Permission';
import type {HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary} from '../models/HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminTaxonomyV10TaxonomyVocabularyService {

	/**
	 * Retrieves the asset library's taxonomy vocabulary by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the asset library's taxonomy vocabulary with the given external reference code, or creates it if it not exists.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the asset library's taxonomy vocabulary by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10DeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutTaxonomyVocabularyBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/batch',
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
	public static headlessAdminTaxonomyV10DeleteTaxonomyVocabularyBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/batch',
			query: {
				callbackURL: callbackUrl,
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
	public static headlessAdminTaxonomyV10PostAssetLibraryTaxonomyVocabulariesPageExportBatch(
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
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/export-batch',
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
	 * @param assetLibraryId
	 * @param fields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetAssetLibraryTaxonomyVocabularyPermissionsPage(
		assetLibraryId: string,
		fields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/permissions',
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
	public static headlessAdminTaxonomyV10PutAssetLibraryTaxonomyVocabularyPermissionsPage(
		assetLibraryId: string,
		requestBody?: Array<HeadlessAdminTaxonomy_v1_0_Permission>
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/permissions',
			path: {
				assetLibraryId: assetLibraryId,
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
	public static headlessAdminTaxonomyV10PostSiteTaxonomyVocabulariesPageExportBatch(
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
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/export-batch',
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
	 * @param taxonomyVocabularyId
	 * @param fields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyVocabularyPermissionsPage(
		taxonomyVocabularyId: string,
		fields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/permissions',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param taxonomyVocabularyId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutTaxonomyVocabularyPermissionsPage(
		taxonomyVocabularyId: string,
		requestBody?: Array<HeadlessAdminTaxonomy_v1_0_Permission>
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/permissions',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a taxonomy vocabulary.
	 * @param taxonomyVocabularyId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyVocabulary(
		taxonomyVocabularyId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the taxonomy vocabulary with the information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param taxonomyVocabularyId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutTaxonomyVocabulary(
		taxonomyVocabularyId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the taxonomy vocabulary and returns a 204 if the operation succeeds.
	 * @param taxonomyVocabularyId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10DeleteTaxonomyVocabulary(
		taxonomyVocabularyId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body. Any other fields are left untouched.
	 * @param taxonomyVocabularyId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PatchTaxonomyVocabulary(
		taxonomyVocabularyId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site's taxonomy vocabulary by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetSiteTaxonomyVocabularyByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the site's taxonomy vocabulary with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutSiteTaxonomyVocabularyByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the site's taxonomy vocabulary by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10DeleteSiteTaxonomyVocabularyByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
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
	 * @returns HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetAssetLibraryTaxonomyVocabulariesPage(
		assetLibraryId: string,
		aggregationTerms?: string,
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies',
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
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostAssetLibraryTaxonomyVocabulary(
		assetLibraryId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostSiteTaxonomyVocabularyBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/batch',
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
	 * @param siteId
	 * @param fields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetSiteTaxonomyVocabularyPermissionsPage(
		siteId: string,
		fields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/permissions',
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
	public static headlessAdminTaxonomyV10PutSiteTaxonomyVocabularyPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessAdminTaxonomy_v1_0_Permission>
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param assetLibraryId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostAssetLibraryTaxonomyVocabularyBatch(
		assetLibraryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/batch',
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
	 * Retrieves a Site's taxonomy vocabularies. Results can be paginated, filtered, searched, and sorted.
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetSiteTaxonomyVocabulariesPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies',
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
	 * Inserts a new taxonomy vocabulary in a Site.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostSiteTaxonomyVocabulary(
		siteId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
