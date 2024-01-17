/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PagePostalAddress} from '../models/HeadlessAdminUser_v1_0_PagePostalAddress';
import type {HeadlessAdminUser_v1_0_PostalAddress} from '../models/HeadlessAdminUser_v1_0_PostalAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10PostalAddressService {

	/**
	 * Retrieves the account's postal addresses.
	 * @param accountId
	 * @returns HeadlessAdminUser_v1_0_PagePostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountPostalAddressesPage(
		accountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PagePostalAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/postal-addresses',
			path: {
				accountId: accountId,
			},
		});
	}

	/**
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_PostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountPostalAddress(
		accountId: string,
		requestBody?: HeadlessAdminUser_v1_0_PostalAddress
	): CancelablePromise<HeadlessAdminUser_v1_0_PostalAddress> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/postal-addresses',
			path: {
				accountId: accountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param accountId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountPostalAddressBatch(
		accountId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/postal-addresses/batch',
			path: {
				accountId: accountId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the postal address.
	 * @param postalAddressId
	 * @returns HeadlessAdminUser_v1_0_PostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetPostalAddress(
		postalAddressId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PostalAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
			path: {
				postalAddressId: postalAddressId,
			},
		});
	}

	/**
	 * @param postalAddressId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_PostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutPostalAddress(
		postalAddressId: string,
		requestBody?: HeadlessAdminUser_v1_0_PostalAddress
	): CancelablePromise<HeadlessAdminUser_v1_0_PostalAddress> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
			path: {
				postalAddressId: postalAddressId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the postal address
	 * @param postalAddressId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeletePostalAddress(
		postalAddressId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
			path: {
				postalAddressId: postalAddressId,
			},
		});
	}

	/**
	 * @param postalAddressId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_PostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchPostalAddress(
		postalAddressId: string,
		requestBody?: HeadlessAdminUser_v1_0_PostalAddress
	): CancelablePromise<HeadlessAdminUser_v1_0_PostalAddress> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
			path: {
				postalAddressId: postalAddressId,
			},
			body: requestBody,
			mediaType: 'application/json',
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
	public static headlessAdminUserV10PostOrganizationPostalAddressesPageExportBatch(
		organizationId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/postal-addresses/export-batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutPostalAddressBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/postal-addresses/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeletePostalAddressBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/postal-addresses/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the user's postal addresses.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_PagePostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountPostalAddressesPage(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PagePostalAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/postal-addresses',
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
	public static headlessAdminUserV10PostUserAccountPostalAddressesPageExportBatch(
		userAccountId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/postal-addresses/export-batch',
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
	 * @param accountId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountPostalAddressesPageExportBatch(
		accountId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/postal-addresses/export-batch',
			path: {
				accountId: accountId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the organization's postal addresses.
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_PagePostalAddress default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationPostalAddressesPage(
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PagePostalAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/postal-addresses',
			path: {
				organizationId: organizationId,
			},
		});
	}
}
