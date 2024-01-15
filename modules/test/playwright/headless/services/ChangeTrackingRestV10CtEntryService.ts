/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTEntry} from '../models/ChangeTrackingRest_v1_0_CTEntry';
import type {ChangeTrackingRest_v1_0_PageCTEntry} from '../models/ChangeTrackingRest_v1_0_PageCTEntry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class ChangeTrackingRestV10CtEntryService {

	/**
	 * @param ctEntryId
	 * @returns ChangeTrackingRest_v1_0_CTEntry default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtEntry(
		ctEntryId: string
	): CancelablePromise<ChangeTrackingRest_v1_0_CTEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-entries/{ctEntryId}',
			path: {
				ctEntryId: ctEntryId,
			},
		});
	}

	/**
	 * @param ctCollectionId
	 * @param showHideable
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns ChangeTrackingRest_v1_0_PageCTEntry default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetCtCollectionCtEntriesPage(
		ctCollectionId: string,
		showHideable?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<ChangeTrackingRest_v1_0_PageCTEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/ct-entries',
			path: {
				ctCollectionId: ctCollectionId,
			},
			query: {
				showHideable: showHideable,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}
}
