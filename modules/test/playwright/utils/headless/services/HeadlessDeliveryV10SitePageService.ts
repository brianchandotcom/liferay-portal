/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_PageSitePage} from '../models/HeadlessDelivery_v1_0_PageSitePage';
import type {HeadlessDelivery_v1_0_SitePage} from '../models/HeadlessDelivery_v1_0_SitePage';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10SitePageService {

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteSitePageBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/batch',
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
	 * Retrieves the public pages of the site
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageSitePage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteSitePagesPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageSitePage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages',
			path: {
				siteId: siteId,
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
	 * Adds a new site page
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_SitePage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteSitePage(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_SitePage
	): CancelablePromise<HeadlessDelivery_v1_0_SitePage> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a specific public page of a site
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_SitePage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteSitePage(
		siteId: string,
		friendlyUrlPath: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_SitePage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the rendered content of a given public page.
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteSitePageRenderedPage(
		siteId: string,
		friendlyUrlPath: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/rendered-page',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves a specific public page of a site for a given experience
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param experienceKey
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_SitePage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteSitePageExperienceExperienceKey(
		siteId: string,
		friendlyUrlPath: string,
		experienceKey: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_SitePage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/experiences/{experienceKey}',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
				experienceKey: experienceKey,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
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
	public static headlessDeliveryV10PostSiteSitePagesPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/export-batch',
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
	 * Retrieves the experiences of a given Page
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_PageSitePage default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteSitePagesExperiencesPage(
		siteId: string,
		friendlyUrlPath: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageSitePage> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/experiences',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the rendered content of a given public page for a given experience.
	 * @param siteId
	 * @param friendlyUrlPath
	 * @param experienceKey
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteSitePageExperienceExperienceKeyRenderedPage(
		siteId: string,
		friendlyUrlPath: string,
		experienceKey: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/site-pages/{friendlyUrlPath}/experiences/{experienceKey}/rendered-page',
			path: {
				siteId: siteId,
				friendlyUrlPath: friendlyUrlPath,
				experienceKey: experienceKey,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}
}
