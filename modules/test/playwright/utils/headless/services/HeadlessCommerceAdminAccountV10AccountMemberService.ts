/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountMember} from '../models/HeadlessCommerceAdminAccount_v1_0_AccountMember';
import type {HeadlessCommerceAdminAccount_v1_0_PageAccountMember} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAccountMember';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AccountMemberService {

	/**
	 * @param externalReferenceCode
	 * @param userId
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountMember default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountMember(
		externalReferenceCode: string,
		userId: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountMember> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/{userId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				userId: userId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param userId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountByExternalReferenceCodeAccountMember(
		externalReferenceCode: string,
		userId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/{userId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				userId: userId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param userId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountByExternalReferenceCodeAccountMember(
		externalReferenceCode: string,
		userId: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountMember
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers/{userId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				userId: userId,
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
	public static headlessCommerceAdminAccountV10PostAccountIdAccountMemberBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/accountMembers/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountMember default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountMembersPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountMember> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountMember default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountMember(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountMember
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountMember> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountMember default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountMembersPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountMember> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountMember default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountMember(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountMember
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountMember> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountMembers',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param userId
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountMember default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountMember(
		id: string,
		userId: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountMember> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers/{userId}',
			path: {
				id: id,
				userId: userId,
			},
		});
	}

	/**
	 * @param id
	 * @param userId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountIdAccountMember(
		id: string,
		userId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers/{userId}',
			path: {
				id: id,
				userId: userId,
			},
		});
	}

	/**
	 * @param id
	 * @param userId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountIdAccountMember(
		id: string,
		userId: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountMember
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountMembers/{userId}',
			path: {
				id: id,
				userId: userId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
