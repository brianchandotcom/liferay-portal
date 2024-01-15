/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_PageSite} from '../models/AnalyticsSettingsRest_v1_0_PageSite';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10SiteService {

	/**
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageSite default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetSitesPage(
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageSite> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/sites',
			query: {
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}
}
