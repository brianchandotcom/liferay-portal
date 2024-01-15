/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageSegment} from '../models/HeadlessAdminUser_v1_0_PageSegment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10SegmentService {

	/**
	 * Gets a site's segments.
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminUser_v1_0_PageSegment default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetSiteSegmentsPage(
		siteId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageSegment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/sites/{siteId}/segments',
			path: {
				siteId: siteId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Gets a user's segments. The set of available headers are: Accept-Language (string), Host (string), User-Agent (string), X-Browser (string), X-Cookies (collection string), X-Device-Brand (string), X-Device-Model (string), X-Device-Screen-Resolution-Height (double), X-Device-Screen-Resolution-Width (double), X-Last-Sign-In-Date-Time (date time) and X-Signed-In (boolean). Local date will be always present in the request.
	 * @param siteId
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_PageSegment default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetSiteUserAccountSegmentsPage(
		siteId: string,
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageSegment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/sites/{siteId}/user-accounts/{userAccountId}/segments',
			path: {
				siteId: siteId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostSiteSegmentsPageExportBatch(
		siteId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/sites/{siteId}/segments/export-batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
