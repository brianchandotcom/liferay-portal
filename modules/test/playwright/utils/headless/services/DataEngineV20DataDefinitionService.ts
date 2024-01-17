/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataDefinition} from '../models/DataEngine_v2_0_DataDefinition';
import type {DataEngine_v2_0_PageDataDefinition} from '../models/DataEngine_v2_0_PageDataDefinition';
import type {DataEngine_v2_0_PagePermission} from '../models/DataEngine_v2_0_PagePermission';
import type {DataEngine_v2_0_Permission} from '../models/DataEngine_v2_0_Permission';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DataEngineV20DataDefinitionService {

	/**
	 * @param dataDefinitionId
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinition(
		dataDefinitionId: string
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataDefinition(
		dataDefinitionId: string,
		requestBody?: DataEngine_v2_0_DataDefinition
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataDefinitionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20DeleteDataDefinition(
		dataDefinitionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20PatchDataDefinition(
		dataDefinitionId: string,
		requestBody?: DataEngine_v2_0_DataDefinition
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @returns string default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataDefinitionFieldFieldTypes(): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/data-definition-fields/field-types',
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param roleNames
	 * @returns DataEngine_v2_0_PagePermission default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionPermissionsPage(
		dataDefinitionId: string,
		roleNames?: string
	): CancelablePromise<DataEngine_v2_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/permissions',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_PagePermission default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataDefinitionPermissionsPage(
		dataDefinitionId: string,
		requestBody?: Array<DataEngine_v2_0_Permission>
	): CancelablePromise<DataEngine_v2_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/permissions',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param contentType
	 * @param dataDefinitionKey
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetSiteDataDefinitionByContentTypeByDataDefinitionKey(
		siteId: string,
		contentType: string,
		dataDefinitionKey: string
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}/by-data-definition-key/{dataDefinitionKey}',
			path: {
				siteId: siteId,
				contentType: contentType,
				dataDefinitionKey: dataDefinitionKey,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionCopy(
		dataDefinitionId: string
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/copy',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
		});
	}

	/**
	 * @param siteId
	 * @param contentType
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns DataEngine_v2_0_PageDataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetSiteDataDefinitionByContentTypeContentTypePage(
		siteId: string,
		contentType: string,
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<DataEngine_v2_0_PageDataDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}',
			path: {
				siteId: siteId,
				contentType: contentType,
			},
			query: {
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param siteId
	 * @param contentType
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostSiteDataDefinitionByContentType(
		siteId: string,
		contentType: string,
		requestBody?: DataEngine_v2_0_DataDefinition
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/sites/{siteId}/data-definitions/by-content-type/{contentType}',
			path: {
				siteId: siteId,
				contentType: contentType,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param contentType
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns DataEngine_v2_0_PageDataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionByContentTypeContentTypePage(
		contentType: string,
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<DataEngine_v2_0_PageDataDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/by-content-type/{contentType}',
			path: {
				contentType: contentType,
			},
			query: {
				keywords: keywords,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param contentType
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataDefinition default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionByContentType(
		contentType: string,
		requestBody?: DataEngine_v2_0_DataDefinition
	): CancelablePromise<DataEngine_v2_0_DataDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/by-content-type/{contentType}',
			path: {
				contentType: contentType,
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
	public static dataEngineV20PutDataDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-definitions/batch',
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
	public static dataEngineV20DeleteDataDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-definitions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
