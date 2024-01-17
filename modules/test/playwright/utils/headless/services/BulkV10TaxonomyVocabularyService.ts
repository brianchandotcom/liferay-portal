/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_DocumentBulkSelection} from '../models/Bulk_v1_0_DocumentBulkSelection';
import type {Bulk_v1_0_PageTaxonomyVocabulary} from '../models/Bulk_v1_0_PageTaxonomyVocabulary';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BulkV10TaxonomyVocabularyService {

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns Bulk_v1_0_PageTaxonomyVocabulary default response
	 * @throws ApiError
	 */
	public static bulkV10PostSiteTaxonomyVocabulariesCommonPage(
		siteId: string,
		requestBody?: Bulk_v1_0_DocumentBulkSelection
	): CancelablePromise<Bulk_v1_0_PageTaxonomyVocabulary> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/bulk/v1.0/sites/{siteId}/taxonomy-vocabularies/common',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
