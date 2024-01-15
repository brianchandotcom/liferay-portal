/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataLayout} from '../models/DataEngine_v2_0_DataLayout';
import type {DataEngine_v2_0_DataLayoutRenderingContext} from '../models/DataEngine_v2_0_DataLayoutRenderingContext';
import type {DataEngine_v2_0_PageDataLayout} from '../models/DataEngine_v2_0_PageDataLayout';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DataEngineV20DataLayoutService {

	/**
	 * @param dataDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataLayoutBatch(
		dataDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts/batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataLayoutBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-layouts/batch',
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
	public static dataEngineV20DeleteDataLayoutBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-layouts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataLayoutId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataLayoutContext(
		dataLayoutId: string,
		requestBody?: DataEngine_v2_0_DataLayoutRenderingContext
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-layouts/{dataLayoutId}/context',
			path: {
				dataLayoutId: dataLayoutId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param keywords
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataLayoutsPageExportBatch(
		dataDefinitionId: string,
		keywords?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts/export-batch',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
			query: {
				keywords: keywords,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param contentType
	 * @param dataLayoutKey
	 * @returns DataEngine_v2_0_DataLayout default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetSiteDataLayoutByContentTypeByDataLayoutKey(
		siteId: string,
		contentType: string,
		dataLayoutKey: string
	): CancelablePromise<DataEngine_v2_0_DataLayout> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/sites/{siteId}/data-layouts/by-content-type/{contentType}/by-data-layout-key/{dataLayoutKey}',
			path: {
				siteId: siteId,
				contentType: contentType,
				dataLayoutKey: dataLayoutKey,
			},
		});
	}

	/**
	 * @param dataDefinitionId
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns DataEngine_v2_0_PageDataLayout default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataLayoutsPage(
		dataDefinitionId: string,
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<DataEngine_v2_0_PageDataLayout> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts',
			path: {
				dataDefinitionId: dataDefinitionId,
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
	 * @param dataDefinitionId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataLayout default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataLayout(
		dataDefinitionId: string,
		requestBody?: DataEngine_v2_0_DataLayout
	): CancelablePromise<DataEngine_v2_0_DataLayout> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts',
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
	public static dataEngineV20DeleteDataDefinitionDataLayout(
		dataDefinitionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-layouts',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
		});
	}

	/**
	 * @param dataLayoutId
	 * @returns DataEngine_v2_0_DataLayout default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataLayout(
		dataLayoutId: string
	): CancelablePromise<DataEngine_v2_0_DataLayout> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-layouts/{dataLayoutId}',
			path: {
				dataLayoutId: dataLayoutId,
			},
		});
	}

	/**
	 * @param dataLayoutId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataLayout default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataLayout(
		dataLayoutId: string,
		requestBody?: DataEngine_v2_0_DataLayout
	): CancelablePromise<DataEngine_v2_0_DataLayout> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-layouts/{dataLayoutId}',
			path: {
				dataLayoutId: dataLayoutId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataLayoutId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20DeleteDataLayout(
		dataLayoutId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-layouts/{dataLayoutId}',
			path: {
				dataLayoutId: dataLayoutId,
			},
		});
	}
}
