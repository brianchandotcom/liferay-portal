/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_KnowledgeBaseArticle} from '../models/HeadlessDelivery_v1_0_KnowledgeBaseArticle';
import type {HeadlessDelivery_v1_0_PageKnowledgeBaseArticle} from '../models/HeadlessDelivery_v1_0_PageKnowledgeBaseArticle';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10KnowledgeBaseArticleService {

	/**
	 * @param knowledgeBaseArticleId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseArticlePermissionsPage(
		knowledgeBaseArticleId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/permissions',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param knowledgeBaseArticleId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseArticlePermissionsPage(
		knowledgeBaseArticleId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/permissions',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param knowledgeBaseFolderId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseFolderKnowledgeBaseArticlesPageExportBatch(
		knowledgeBaseFolderId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles/export-batch',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
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
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteKnowledgeBaseArticlePermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/permissions',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteKnowledgeBaseArticlePermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param knowledgeBaseArticleId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseArticleUnsubscribe(
		knowledgeBaseArticleId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
		});
	}

	/**
	 * @param siteId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteKnowledgeBaseArticleUnsubscribe(
		siteId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/unsubscribe',
			path: {
				siteId: siteId,
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
	public static headlessDeliveryV10PostSiteKnowledgeBaseArticleBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/batch',
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
	 * Retrieves the site's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted.
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
	 * @returns HeadlessDelivery_v1_0_PageKnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteKnowledgeBaseArticlesPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageKnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles',
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
	 * Creates a new knowledge base article.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteKnowledgeBaseArticle(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseArticle
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the knowledge base article's rating.
	 * @param knowledgeBaseArticleId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseArticleMyRating(
		knowledgeBaseArticleId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param knowledgeBaseArticleId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseArticleMyRating(
		knowledgeBaseArticleId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Creates a rating for the knowledge base article.
	 * @param knowledgeBaseArticleId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseArticleMyRating(
		knowledgeBaseArticleId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the knowledge base article's rating and returns a 204 if the operation succeeds.
	 * @param knowledgeBaseArticleId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteKnowledgeBaseArticleMyRating(
		knowledgeBaseArticleId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
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
	public static headlessDeliveryV10PostSiteKnowledgeBaseArticlesPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/export-batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseArticleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-articles/batch',
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
	public static headlessDeliveryV10DeleteKnowledgeBaseArticleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-articles/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the folder's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted.
	 * @param knowledgeBaseFolderId
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
	 * @returns HeadlessDelivery_v1_0_PageKnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
		knowledgeBaseFolderId: string,
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageKnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
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
	 * Creates a new knowledge base article in the folder.
	 * @param knowledgeBaseFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseFolderKnowledgeBaseArticle(
		knowledgeBaseFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseArticle
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param knowledgeBaseArticleId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseArticleSubscribe(
		knowledgeBaseArticleId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
		});
	}

	/**
	 * @param siteId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteKnowledgeBaseArticleSubscribe(
		siteId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/subscribe',
			path: {
				siteId: siteId,
			},
		});
	}

	/**
	 * Retrieves the site's knowledge base article by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteKnowledgeBaseArticleByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Updates the site's knowledge base article with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteKnowledgeBaseArticleByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseArticle
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the knowledge base article by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteKnowledgeBaseArticleByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the parent knowledge base article's child knowledge base articles. Results can be paginated, filtered, searched, and sorted.
	 * @param parentKnowledgeBaseArticleId
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
	 * @returns HeadlessDelivery_v1_0_PageKnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
		parentKnowledgeBaseArticleId: string,
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageKnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles',
			path: {
				parentKnowledgeBaseArticleId: parentKnowledgeBaseArticleId,
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
	 * Creates a child knowledge base article of the knowledge base article identified by `parentKnowledgeBaseArticleId`.
	 * @param parentKnowledgeBaseArticleId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseArticleKnowledgeBaseArticle(
		parentKnowledgeBaseArticleId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseArticle
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles',
			path: {
				parentKnowledgeBaseArticleId: parentKnowledgeBaseArticleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the knowledge base article.
	 * @param knowledgeBaseArticleId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseArticle(
		knowledgeBaseArticleId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the knowledge base article with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param knowledgeBaseArticleId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutKnowledgeBaseArticle(
		knowledgeBaseArticleId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseArticle
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the knowledge base article and returns a 204 if the operation succeeds.
	 * @param knowledgeBaseArticleId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteKnowledgeBaseArticle(
		knowledgeBaseArticleId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param knowledgeBaseArticleId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseArticle default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchKnowledgeBaseArticle(
		knowledgeBaseArticleId: string,
		requestBody?: HeadlessDelivery_v1_0_KnowledgeBaseArticle
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseArticle> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param knowledgeBaseFolderId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseFolderKnowledgeBaseArticleBatch(
		knowledgeBaseFolderId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles/batch',
			path: {
				knowledgeBaseFolderId: knowledgeBaseFolderId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
