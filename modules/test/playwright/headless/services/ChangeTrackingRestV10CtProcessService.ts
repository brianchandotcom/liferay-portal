/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTProcess} from '../models/ChangeTrackingRest_v1_0_CTProcess';
import type {ChangeTrackingRest_v1_0_PageCTProcess} from '../models/ChangeTrackingRest_v1_0_PageCTProcess';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ChangeTrackingRestV10CtProcessService {

	/**
	 * @param status
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ChangeTrackingRest_v1_0_PageCTProcess default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtProcessesPage(
		status?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ChangeTrackingRest_v1_0_PageCTProcess> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-processes',
			query: {
				status: status,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param ctProcessId
	 * @param description
	 * @param name
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtProcessRevert(
		ctProcessId: string,
		description?: string,
		name?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-processes/{ctProcessId}/revert',
			path: {
				ctProcessId: ctProcessId,
			},
			query: {
				description: description,
				name: name,
			},
		});
	}

	/**
	 * @param ctProcessId
	 * @returns ChangeTrackingRest_v1_0_CTProcess default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtProcess(
		ctProcessId: string
	): CancelablePromise<ChangeTrackingRest_v1_0_CTProcess> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-processes/{ctProcessId}',
			path: {
				ctProcessId: ctProcessId,
			},
		});
	}

	/**
	 * @param status
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10PostCtProcessesPageExportBatch(
		status?: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/change-tracking-rest/v1.0/ct-processes/export-batch',
			query: {
				status: status,
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
