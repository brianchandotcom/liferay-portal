/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_Status} from '../models/Bulk_v1_0_Status';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BulkV10StatusService {

	/**
	 * @returns Bulk_v1_0_Status default response
	 * @throws ApiError
	 */
	public static bulkV10GetStatus(): CancelablePromise<Bulk_v1_0_Status> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/bulk/v1.0/status',
		});
	}
}
