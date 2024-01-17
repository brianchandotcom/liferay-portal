/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataRecord} from '../models/DataEngine_v2_0_DataRecord';
import type {DataEngine_v2_0_PageDataRecord} from '../models/DataEngine_v2_0_PageDataRecord';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DataEngineV20DataRecordService {

	/**
	 * @param dataDefinitionId
	 * @param dataListViewId
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns DataEngine_v2_0_PageDataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataRecordsPage(
		dataDefinitionId: string,
		dataListViewId?: string,
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<DataEngine_v2_0_PageDataRecord> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				dataListViewId: dataListViewId,
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataRecord(
		dataDefinitionId: string,
		requestBody?: DataEngine_v2_0_DataRecord
	): CancelablePromise<DataEngine_v2_0_DataRecord> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param dataListViewId
	 * @param keywords
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataRecordsPageExportBatch(
		dataDefinitionId: string,
		dataListViewId?: string,
		keywords?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records/export-batch',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				dataListViewId: dataListViewId,
				keywords: keywords,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataRecordCollectionDataRecordBatch(
		dataRecordCollectionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records/batch',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param dataListViewId
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns DataEngine_v2_0_PageDataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataRecordCollectionDataRecordsPage(
		dataRecordCollectionId: string,
		dataListViewId?: string,
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<DataEngine_v2_0_PageDataRecord> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			query: {
				dataListViewId: dataListViewId,
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataRecordCollectionDataRecord(
		dataRecordCollectionId: string,
		requestBody?: DataEngine_v2_0_DataRecord
	): CancelablePromise<DataEngine_v2_0_DataRecord> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param dataListViewId
	 * @param keywords
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataRecordCollectionDataRecordsPageExportBatch(
		dataRecordCollectionId: string,
		dataListViewId?: string,
		keywords?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records/export-batch',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			query: {
				dataListViewId: dataListViewId,
				keywords: keywords,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param dataRecordId
	 * @returns DataEngine_v2_0_DataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataRecord(
		dataRecordId: string
	): CancelablePromise<DataEngine_v2_0_DataRecord> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-records/{dataRecordId}',
			path: {
				dataRecordId: dataRecordId,
			},
		});
	}

	/**
	 * @param dataRecordId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataRecord(
		dataRecordId: string,
		requestBody?: DataEngine_v2_0_DataRecord
	): CancelablePromise<DataEngine_v2_0_DataRecord> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-records/{dataRecordId}',
			path: {
				dataRecordId: dataRecordId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20DeleteDataRecord(
		dataRecordId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-records/{dataRecordId}',
			path: {
				dataRecordId: dataRecordId,
			},
		});
	}

	/**
	 * @param dataRecordId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataRecord default response
	 * @throws ApiError
	 */
	public static dataEngineV20PatchDataRecord(
		dataRecordId: string,
		requestBody?: DataEngine_v2_0_DataRecord
	): CancelablePromise<DataEngine_v2_0_DataRecord> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/data-engine/v2.0/data-records/{dataRecordId}',
			path: {
				dataRecordId: dataRecordId,
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
	public static dataEngineV20PutDataRecordBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-records/batch',
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
	public static dataEngineV20DeleteDataRecordBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-records/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataRecordBatch(
		dataDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records/batch',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param page
	 * @param pageSize
	 * @returns string default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataRecordCollectionDataRecordExport(
		dataRecordCollectionId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records/export',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
