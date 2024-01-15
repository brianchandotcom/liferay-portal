/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup} from '../models/HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup';
import type {HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AdminAccountGroupService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountGroupsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountGroups',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountGroupByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountGroupByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountGroupByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountGroup(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountGroup(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountGroup(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountGroupsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accountGroups',
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountGroup(
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AdminAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accountGroups',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountGroupsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAdminAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountGroups',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
