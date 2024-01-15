/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_DocumentBulkSelection} from '../models/Bulk_v1_0_DocumentBulkSelection';
import type {Bulk_v1_0_KeywordBulkSelection} from '../models/Bulk_v1_0_KeywordBulkSelection';
import type {Bulk_v1_0_PageKeyword} from '../models/Bulk_v1_0_PageKeyword';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BulkV10KeywordService {

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static bulkV10PutKeywordBatch(
		requestBody?: Bulk_v1_0_KeywordBulkSelection
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/bulk/v1.0/keywords/batch',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static bulkV10PatchKeywordBatch(
		requestBody?: Bulk_v1_0_KeywordBulkSelection
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/bulk/v1.0/keywords/batch',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns Bulk_v1_0_PageKeyword default response
	 * @throws ApiError
	 */
	public static bulkV10PostKeywordsCommonPage(
		requestBody?: Bulk_v1_0_DocumentBulkSelection
	): CancelablePromise<Bulk_v1_0_PageKeyword> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/bulk/v1.0/keywords/common',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
