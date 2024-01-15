/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountOrganization} from '../models/HeadlessCommerceAdminAccount_v1_0_AccountOrganization';
import type {HeadlessCommerceAdminAccount_v1_0_PageAccountOrganization} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAccountOrganization';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AccountOrganizationService {

	/**
	 * @param externalReferenceCode
	 * @param organizationId
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountOrganization default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountOrganization(
		externalReferenceCode: string,
		organizationId: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations/{organizationId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountByExternalReferenceCodeAccountOrganization(
		externalReferenceCode: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations/{organizationId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountOrganizationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/accountOrganizations/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param organizationId
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountOrganization default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountOrganization(
		id: string,
		organizationId: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations/{organizationId}',
			path: {
				id: id,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param id
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountIdAccountOrganization(
		id: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations/{organizationId}',
			path: {
				id: id,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountOrganization default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountOrganizationsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountOrganization default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountOrganization(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountOrganization
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountOrganization> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountOrganization default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountOrganizationsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountOrganization default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountOrganization(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountOrganization
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountOrganization> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
