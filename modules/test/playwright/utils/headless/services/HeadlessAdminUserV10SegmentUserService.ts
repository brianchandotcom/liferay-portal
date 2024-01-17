/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageSegmentUser} from '../models/HeadlessAdminUser_v1_0_PageSegmentUser';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10SegmentUserService {

	/**
	 * Gets a segment's users.
	 * @param segmentId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminUser_v1_0_PageSegmentUser default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetSegmentUserAccountsPage(
		segmentId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageSegmentUser> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/segments/{segmentId}/user-accounts',
			path: {
				segmentId: segmentId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
