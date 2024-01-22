/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataRecordCollection} from '../models/DataEngine_v2_0_DataRecordCollection';
import type {DataEngine_v2_0_PageDataRecordCollection} from '../models/DataEngine_v2_0_PageDataRecordCollection';
import type {DataEngine_v2_0_PagePermission} from '../models/DataEngine_v2_0_PagePermission';
import type {DataEngine_v2_0_Permission} from '../models/DataEngine_v2_0_Permission';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DataEngineV20DataRecordCollectionService {

	/**
	 * @param dataDefinitionId
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @returns DataEngine_v2_0_PageDataRecordCollection default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataRecordCollectionsPage(
		dataDefinitionId: string,
		keywords?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<DataEngine_v2_0_PageDataRecordCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				keywords: keywords,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataRecordCollection default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataRecordCollection(
		dataDefinitionId: string,
		requestBody?: DataEngine_v2_0_DataRecordCollection
	): CancelablePromise<DataEngine_v2_0_DataRecordCollection> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @returns DataEngine_v2_0_DataRecordCollection default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataRecordCollection(
		dataRecordCollectionId: string
	): CancelablePromise<DataEngine_v2_0_DataRecordCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataRecordCollection default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataRecordCollection(
		dataRecordCollectionId: string,
		requestBody?: DataEngine_v2_0_DataRecordCollection
	): CancelablePromise<DataEngine_v2_0_DataRecordCollection> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20DeleteDataRecordCollection(
		dataRecordCollectionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
		});
	}

	/**
	 * @param siteId
	 * @param dataRecordCollectionKey
	 * @returns DataEngine_v2_0_DataRecordCollection default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetSiteDataRecordCollectionByDataRecordCollectionKey(
		siteId: string,
		dataRecordCollectionKey: string
	): CancelablePromise<DataEngine_v2_0_DataRecordCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/sites/{siteId}/data-record-collections/by-data-record-collection-key/{dataRecordCollectionKey}',
			path: {
				siteId: siteId,
				dataRecordCollectionKey: dataRecordCollectionKey,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataRecordCollectionBatch(
		dataDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections/batch',
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
	 * @param dataDefinitionId
	 * @param keywords
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataRecordCollectionsPageExportBatch(
		dataDefinitionId: string,
		keywords?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collections/export-batch',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				keywords: keywords,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @returns DataEngine_v2_0_DataRecordCollection default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataRecordCollection(
		dataDefinitionId: string
	): CancelablePromise<DataEngine_v2_0_DataRecordCollection> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-record-collection',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @returns string default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataRecordCollectionPermissionByCurrentUser(
		dataRecordCollectionId: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions/by-current-user',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataRecordCollectionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-record-collections/batch',
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
	public static dataEngineV20DeleteDataRecordCollectionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-record-collections/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param roleNames
	 * @returns DataEngine_v2_0_PagePermission default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataRecordCollectionPermissionsPage(
		dataRecordCollectionId: string,
		roleNames?: string
	): CancelablePromise<DataEngine_v2_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			query: {
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param dataRecordCollectionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_PagePermission default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataRecordCollectionPermissionsPage(
		dataRecordCollectionId: string,
		requestBody?: Array<DataEngine_v2_0_Permission>
	): CancelablePromise<DataEngine_v2_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/permissions',
			path: {
				dataRecordCollectionId: dataRecordCollectionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
