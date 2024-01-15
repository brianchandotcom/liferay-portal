/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_DisplayPageTemplate} from '../models/HeadlessAdminContent_v1_0_DisplayPageTemplate';
import type {HeadlessAdminContent_v1_0_PageDisplayPageTemplate} from '../models/HeadlessAdminContent_v1_0_PageDisplayPageTemplate';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminContentV10DisplayPageTemplateService {

	/**
	 * Retrieves the display page templates of a site
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessAdminContent_v1_0_PageDisplayPageTemplate default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10GetSiteDisplayPageTemplatesPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminContent_v1_0_PageDisplayPageTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-content/v1.0/sites/{siteId}/display-page-templates',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * Retrieves a display page template of a site
	 * @param siteId
	 * @param displayPageTemplateKey
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessAdminContent_v1_0_DisplayPageTemplate default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10GetSiteDisplayPageTemplate(
		siteId: string,
		displayPageTemplateKey: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessAdminContent_v1_0_DisplayPageTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-content/v1.0/sites/{siteId}/display-page-templates/{displayPageTemplateKey}',
			path: {
				siteId: siteId,
				displayPageTemplateKey: displayPageTemplateKey,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}
}
