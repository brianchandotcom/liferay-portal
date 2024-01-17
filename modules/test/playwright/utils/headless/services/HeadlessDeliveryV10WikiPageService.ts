/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_PageWikiPage} from '../models/HeadlessDelivery_v1_0_PageWikiPage';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_WikiPage} from '../models/HeadlessDelivery_v1_0_WikiPage';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10WikiPageService {

	/**
	 * Retrieves the wiki page's of a node. Results can be paginated, filtered, searched, and sorted.
	 * @param wikiNodeId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageWikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetWikiNodeWikiPagesPage(
		wikiNodeId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageWikiPage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages',
			path: {
				wikiNodeId: wikiNodeId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
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
	 * Creates a new wiki page
	 * @param wikiNodeId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_WikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiNodeWikiPage(
		wikiNodeId: string,
		requestBody?: HeadlessDelivery_v1_0_WikiPage
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPage> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages',
			path: {
				wikiNodeId: wikiNodeId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the wiki page by external reference code
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_WikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteWikiPageByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}',
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
	 * Updates the wiki page with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_WikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteWikiPageByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_WikiPage
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPage> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the wiki page by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteWikiPageByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the child wiki page's of a wiki page.
	 * @param parentWikiPageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_PageWikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetWikiPageWikiPagesPage(
		parentWikiPageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageWikiPage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/wiki-pages/{parentWikiPageId}/wiki-pages',
			path: {
				parentWikiPageId: parentWikiPageId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Creates a child wiki page of the parent wiki page.
	 * @param parentWikiPageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_WikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiPageWikiPage(
		parentWikiPageId: string,
		requestBody?: HeadlessDelivery_v1_0_WikiPage
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPage> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-pages/{parentWikiPageId}/wiki-pages',
			path: {
				parentWikiPageId: parentWikiPageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param wikiPageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutWikiPageSubscribe(
		wikiPageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/subscribe',
			path: {
				wikiPageId: wikiPageId,
			},
		});
	}

	/**
	 * @param wikiNodeId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiNodeWikiPagesPageExportBatch(
		wikiNodeId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages/export-batch',
			path: {
				wikiNodeId: wikiNodeId,
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
	 * Retrieves the wiki page
	 * @param wikiPageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_WikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetWikiPage(
		wikiPageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}',
			path: {
				wikiPageId: wikiPageId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the wiki page with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param wikiPageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_WikiPage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutWikiPage(
		wikiPageId: string,
		requestBody?: HeadlessDelivery_v1_0_WikiPage
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPage> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}',
			path: {
				wikiPageId: wikiPageId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the wiki page and returns a 204 if the operation succeeds.
	 * @param wikiPageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteWikiPage(
		wikiPageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}',
			path: {
				wikiPageId: wikiPageId,
			},
		});
	}

	/**
	 * @param wikiPageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetWikiPagePermissionsPage(
		wikiPageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/permissions',
			path: {
				wikiPageId: wikiPageId,
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
	 * @param wikiPageId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutWikiPagePermissionsPage(
		wikiPageId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/permissions',
			path: {
				wikiPageId: wikiPageId,
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
	public static headlessDeliveryV10PutWikiPageBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/wiki-pages/batch',
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
	public static headlessDeliveryV10DeleteWikiPageBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/wiki-pages/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param wikiPageId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutWikiPageUnsubscribe(
		wikiPageId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/unsubscribe',
			path: {
				wikiPageId: wikiPageId,
			},
		});
	}

	/**
	 * @param wikiNodeId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiNodeWikiPageBatch(
		wikiNodeId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-nodes/{wikiNodeId}/wiki-pages/batch',
			path: {
				wikiNodeId: wikiNodeId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
