/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_PageContactAccountGroup} from '../models/AnalyticsSettingsRest_v1_0_PageContactAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class AnalyticsSettingsRestV10ContactAccountGroupService {

	/**
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns AnalyticsSettingsRest_v1_0_PageContactAccountGroup default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetContactAccountGroupsPage(
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<AnalyticsSettingsRest_v1_0_PageContactAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/contacts/account-groups',
			query: {
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}
}
