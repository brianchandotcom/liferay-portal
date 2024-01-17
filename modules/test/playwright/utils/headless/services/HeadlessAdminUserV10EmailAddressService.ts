/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_EmailAddress} from '../models/HeadlessAdminUser_v1_0_EmailAddress';
import type {HeadlessAdminUser_v1_0_PageEmailAddress} from '../models/HeadlessAdminUser_v1_0_PageEmailAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10EmailAddressService {

	/**
	 * Retrieves the user's email addresses.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_PageEmailAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountEmailAddressesPage(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageEmailAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/email-addresses',
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
	public static headlessAdminUserV10PostUserAccountEmailAddressesPageExportBatch(
		userAccountId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/email-addresses/export-batch',
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
	 * Retrieves the organization's email addresses.
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_PageEmailAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationEmailAddressesPage(
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageEmailAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/email-addresses',
			path: {
				organizationId: organizationId,
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
	public static headlessAdminUserV10PostOrganizationEmailAddressesPageExportBatch(
		organizationId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/email-addresses/export-batch',
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
	 * Retrieves the email address.
	 * @param emailAddressId
	 * @returns HeadlessAdminUser_v1_0_EmailAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetEmailAddress(
		emailAddressId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_EmailAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/email-addresses/{emailAddressId}',
			path: {
				emailAddressId: emailAddressId,
			},
		});
	}
}
