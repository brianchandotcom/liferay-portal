/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageWebUrl} from '../models/HeadlessAdminUser_v1_0_PageWebUrl';
import type {HeadlessAdminUser_v1_0_WebUrl} from '../models/HeadlessAdminUser_v1_0_WebUrl';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10WebUrlService {

	/**
	 * Retrieves the organization's URLs.
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_PageWebUrl default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationWebUrlsPage(
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageWebUrl> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/web-urls',
			path: {
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param userAccountId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserAccountWebUrlsPageExportBatch(
		userAccountId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/web-urls/export-batch',
			path: {
				userAccountId: userAccountId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param organizationId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostOrganizationWebUrlsPageExportBatch(
		organizationId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/web-urls/export-batch',
			path: {
				organizationId: organizationId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the user's URLs.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_PageWebUrl default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountWebUrlsPage(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageWebUrl> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/web-urls',
			path: {
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Retrieves the web URL.
	 * @param webUrlId
	 * @returns HeadlessAdminUser_v1_0_WebUrl default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetWebUrl(
		webUrlId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_WebUrl> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/web-urls/{webUrlId}',
			path: {
				webUrlId: webUrlId,
			},
		});
	}
}
