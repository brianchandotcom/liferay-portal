/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageSite} from '../models/HeadlessAdminUser_v1_0_PageSite';
import type {HeadlessAdminUser_v1_0_Site} from '../models/HeadlessAdminUser_v1_0_Site';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10SiteService {

	/**
	 * @param siteId
	 * @returns HeadlessAdminUser_v1_0_Site default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetSite(
		siteId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Site> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/sites/{siteId}',
			path: {
				siteId: siteId,
			},
		});
	}

	/**
	 * @param friendlyUrlPath
	 * @returns HeadlessAdminUser_v1_0_Site default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetSiteByFriendlyUrlPath(
		friendlyUrlPath: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Site> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/sites/by-friendly-url-path/{friendlyUrlPath}',
			path: {
				friendlyUrlPath: friendlyUrlPath,
			},
		});
	}

	/**
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminUser_v1_0_PageSite default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetMyUserAccountSitesPage(
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageSite> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/my-user-account/sites',
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
