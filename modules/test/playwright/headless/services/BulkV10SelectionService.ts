/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_DocumentBulkSelection} from '../models/Bulk_v1_0_DocumentBulkSelection';
import type {Bulk_v1_0_Selection} from '../models/Bulk_v1_0_Selection';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BulkV10SelectionService {

	/**
	 * @param requestBody
	 * @returns Bulk_v1_0_Selection default response
	 * @throws ApiError
	 */
	public static bulkV10PostBulkSelection(
		requestBody?: Bulk_v1_0_DocumentBulkSelection
	): CancelablePromise<Bulk_v1_0_Selection> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/bulk/v1.0/bulk-selection',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
