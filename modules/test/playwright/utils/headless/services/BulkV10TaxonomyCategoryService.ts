/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_TaxonomyCategoryBulkSelection} from '../models/Bulk_v1_0_TaxonomyCategoryBulkSelection';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BulkV10TaxonomyCategoryService {

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static bulkV10PutTaxonomyCategoryBatch(
		requestBody?: Bulk_v1_0_TaxonomyCategoryBulkSelection
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/bulk/v1.0/taxonomy-categories/batch',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static bulkV10PatchTaxonomyCategoryBatch(
		requestBody?: Bulk_v1_0_TaxonomyCategoryBulkSelection
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/bulk/v1.0/taxonomy-categories/batch',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
