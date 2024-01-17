/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_User} from '../models/HeadlessCommerceAdminAccount_v1_0_User';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10UserService {

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_User default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountMemberCreateUser(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_User
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_User> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/createUser',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
