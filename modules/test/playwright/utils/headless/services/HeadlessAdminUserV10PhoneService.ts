/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PagePhone} from '../models/HeadlessAdminUser_v1_0_PagePhone';
import type {HeadlessAdminUser_v1_0_Phone} from '../models/HeadlessAdminUser_v1_0_Phone';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10PhoneService {

	/**
	 * Retrieves the phone number.
	 * @param phoneId
	 * @returns HeadlessAdminUser_v1_0_Phone default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetPhone(
		phoneId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Phone> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/phones/{phoneId}',
			path: {
				phoneId: phoneId,
			},
		});
	}

	/**
	 * Retrieves the user's phone numbers.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_PagePhone default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountPhonesPage(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PagePhone> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/phones',
			path: {
				userAccountId: userAccountId,
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
	public static headlessAdminUserV10PostUserAccountPhonesPageExportBatch(
		userAccountId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/phones/export-batch',
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
	public static headlessAdminUserV10PostOrganizationPhonesPageExportBatch(
		organizationId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/phones/export-batch',
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
	 * Retrieves the organization's phone numbers.
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_PagePhone default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationPhonesPage(
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PagePhone> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/phones',
			path: {
				organizationId: organizationId,
			},
		});
	}
}
