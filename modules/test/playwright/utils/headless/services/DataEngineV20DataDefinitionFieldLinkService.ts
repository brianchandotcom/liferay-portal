/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_PageDataDefinitionFieldLink} from '../models/DataEngine_v2_0_PageDataDefinitionFieldLink';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DataEngineV20DataDefinitionFieldLinkService {

	/**
	 * @param dataDefinitionId
	 * @param fieldName
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataDefinitionFieldLinksPageExportBatch(
		dataDefinitionId: string,
		fieldName?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-definition-field-links/export-batch',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				fieldName: fieldName,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param fieldName
	 * @returns DataEngine_v2_0_PageDataDefinitionFieldLink default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataDefinitionFieldLinksPage(
		dataDefinitionId: string,
		fieldName?: string
	): CancelablePromise<DataEngine_v2_0_PageDataDefinitionFieldLink> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-definition-field-links',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				fieldName: fieldName,
			},
		});
	}
}
