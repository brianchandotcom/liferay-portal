/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTRemote} from '../models/ChangeTrackingRest_v1_0_CTRemote';
import type {ChangeTrackingRest_v1_0_PageCTRemote} from '../models/ChangeTrackingRest_v1_0_PageCTRemote';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ChangeTrackingRestV10CtRemoteService {

	/**
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ChangeTrackingRest_v1_0_PageCTRemote default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtRemotesPage(
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ChangeTrackingRest_v1_0_PageCTRemote> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-remotes',
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTRemote default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtRemote(
		requestBody?: ChangeTrackingRest_v1_0_CTRemote
	): CancelablePromise<ChangeTrackingRest_v1_0_CTRemote> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-remotes',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtRemotesPageExportBatch(
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-remotes/export-batch',
			query: {
				search: search,
				sort: sort,
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
	public static changeTrackingRestV10PutCtRemoteBatch(
		callbackUrl?: string,
		requestBody?: ChangeTrackingRest_v1_0_CTRemote
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/change-tracking-rest/v1.0/ct-remotes/batch',
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
	public static changeTrackingRestV10PostCtRemoteBatch(
		callbackUrl?: string,
		requestBody?: ChangeTrackingRest_v1_0_CTRemote
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-remotes/batch',
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
	public static changeTrackingRestV10DeleteCtRemoteBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/change-tracking-rest/v1.0/ct-remotes/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns ChangeTrackingRest_v1_0_CTRemote default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtRemote(
		id: string
	): CancelablePromise<ChangeTrackingRest_v1_0_CTRemote> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTRemote default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PutCtRemote(
		id: string,
		requestBody?: ChangeTrackingRest_v1_0_CTRemote
	): CancelablePromise<ChangeTrackingRest_v1_0_CTRemote> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10DeleteCtRemote(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns ChangeTrackingRest_v1_0_CTRemote default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PatchCtRemote(
		id: string,
		requestBody?: ChangeTrackingRest_v1_0_CTRemote
	): CancelablePromise<ChangeTrackingRest_v1_0_CTRemote> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/change-tracking-rest/v1.0/ct-remotes/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
