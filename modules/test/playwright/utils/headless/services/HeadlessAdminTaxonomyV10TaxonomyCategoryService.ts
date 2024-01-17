/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_PagePermission} from '../models/HeadlessAdminTaxonomy_v1_0_PagePermission';
import type {HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory} from '../models/HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory';
import type {HeadlessAdminTaxonomy_v1_0_Permission} from '../models/HeadlessAdminTaxonomy_v1_0_Permission';
import type {HeadlessAdminTaxonomy_v1_0_TaxonomyCategory} from '../models/HeadlessAdminTaxonomy_v1_0_TaxonomyCategory';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminTaxonomyV10TaxonomyCategoryService {

	/**
	 * Retrieves a taxonomy category's child taxonomy categories. Results can be paginated, filtered, searched, and sorted.
	 * @param parentTaxonomyCategoryId
	 * @param aggregationTerms
	 * @param fields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyCategoryTaxonomyCategoriesPage(
		parentTaxonomyCategoryId: string,
		aggregationTerms?: string,
		fields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories',
			path: {
				parentTaxonomyCategoryId: parentTaxonomyCategoryId,
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
	 * Inserts a new child taxonomy category.
	 * @param parentTaxonomyCategoryId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostTaxonomyCategoryTaxonomyCategory(
		parentTaxonomyCategoryId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyCategory
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories',
			path: {
				parentTaxonomyCategoryId: parentTaxonomyCategoryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param taxonomyCategoryId
	 * @param fields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyCategoryPermissionsPage(
		taxonomyCategoryId: string,
		fields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}/permissions',
			path: {
				taxonomyCategoryId: taxonomyCategoryId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param taxonomyCategoryId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutTaxonomyCategoryPermissionsPage(
		taxonomyCategoryId: string,
		requestBody?: Array<HeadlessAdminTaxonomy_v1_0_Permission>
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}/permissions',
			path: {
				taxonomyCategoryId: taxonomyCategoryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site's taxonomy category by external reference code.
	 * @param taxonomyVocabularyId
	 * @param externalReferenceCode
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode(
		taxonomyVocabularyId: string,
		externalReferenceCode: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/by-external-reference-code/{externalReferenceCode}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the site's taxonomy category with the given external reference code, or creates it if it not exists.
	 * @param taxonomyVocabularyId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode(
		taxonomyVocabularyId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyCategory
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/by-external-reference-code/{externalReferenceCode}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the site's taxonomy category by external reference code.
	 * @param taxonomyVocabularyId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10DeleteTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode(
		taxonomyVocabularyId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/by-external-reference-code/{externalReferenceCode}',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param taxonomyVocabularyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostTaxonomyVocabularyTaxonomyCategoriesPageExportBatch(
		taxonomyVocabularyId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/export-batch',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostTaxonomyVocabularyTaxonomyCategoryBatch(
		taxonomyVocabularyId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/batch',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a vocabulary's taxonomy categories. Results can be paginated, filtered, searched, and sorted.
	 * @param taxonomyVocabularyId
	 * @param aggregationTerms
	 * @param fields
	 * @param flatten
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyVocabularyTaxonomyCategoriesPage(
		taxonomyVocabularyId: string,
		aggregationTerms?: string,
		fields?: string,
		flatten?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				flatten: flatten,
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
	 * Inserts a new taxonomy category in a taxonomy vocabulary.
	 * @param taxonomyVocabularyId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PostTaxonomyVocabularyTaxonomyCategory(
		taxonomyVocabularyId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyCategory
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories',
			path: {
				taxonomyVocabularyId: taxonomyVocabularyId,
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
	 * @returns HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyCategoriesRankedPage(
		fields?: string,
		restrictFields?: string,
		siteId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/ranked',
			query: {
				fields: fields,
				restrictFields: restrictFields,
				siteId: siteId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Retrieves a taxonomy category.
	 * @param taxonomyCategoryId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetTaxonomyCategory(
		taxonomyCategoryId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}',
			path: {
				taxonomyCategoryId: taxonomyCategoryId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the taxonomy category with the information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param taxonomyCategoryId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PutTaxonomyCategory(
		taxonomyCategoryId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyCategory
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}',
			path: {
				taxonomyCategoryId: taxonomyCategoryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the taxonomy category and returns a 204 if the operation succeeds.
	 * @param taxonomyCategoryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10DeleteTaxonomyCategory(
		taxonomyCategoryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}',
			path: {
				taxonomyCategoryId: taxonomyCategoryId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body. Other fields are left untouched.
	 * @param taxonomyCategoryId
	 * @param requestBody
	 * @returns HeadlessAdminTaxonomy_v1_0_TaxonomyCategory default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10PatchTaxonomyCategory(
		taxonomyCategoryId: string,
		requestBody?: HeadlessAdminTaxonomy_v1_0_TaxonomyCategory
	): CancelablePromise<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}',
			path: {
				taxonomyCategoryId: taxonomyCategoryId,
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
	public static headlessAdminTaxonomyV10PutTaxonomyCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/batch',
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
	public static headlessAdminTaxonomyV10DeleteTaxonomyCategoryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-taxonomy/v1.0/taxonomy-categories/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
