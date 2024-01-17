/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataListView} from '../models/DataEngine_v2_0_DataListView';
import type {DataEngine_v2_0_PageDataListView} from '../models/DataEngine_v2_0_PageDataListView';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DataEngineV20DataListViewService {

	/**
	 * @param dataDefinitionId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataListViewBatch(
		dataDefinitionId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views/batch',
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
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataListViewsPageExportBatch(
		dataDefinitionId: string,
		keywords?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views/export-batch',
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
	 * @param dataDefinitionId
	 * @param keywords
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns DataEngine_v2_0_PageDataListView default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataDefinitionDataListViewsPage(
		dataDefinitionId: string,
		keywords?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<DataEngine_v2_0_PageDataListView> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views',
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
	 * @returns DataEngine_v2_0_DataListView default response
	 * @throws ApiError
	 */
	public static dataEngineV20PostDataDefinitionDataListView(
		dataDefinitionId: string,
		requestBody?: DataEngine_v2_0_DataListView
	): CancelablePromise<DataEngine_v2_0_DataListView> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views',
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
	public static dataEngineV20DeleteDataDefinitionDataListView(
		dataDefinitionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-list-views',
			path: {
				dataDefinitionId: dataDefinitionId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataListViewBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-list-views/batch',
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
	public static dataEngineV20DeleteDataListViewBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-list-views/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataListViewId
	 * @returns DataEngine_v2_0_DataListView default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetDataListView(
		dataListViewId: string
	): CancelablePromise<DataEngine_v2_0_DataListView> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/data-list-views/{dataListViewId}',
			path: {
				dataListViewId: dataListViewId,
			},
		});
	}

	/**
	 * @param dataListViewId
	 * @param requestBody
	 * @returns DataEngine_v2_0_DataListView default response
	 * @throws ApiError
	 */
	public static dataEngineV20PutDataListView(
		dataListViewId: string,
		requestBody?: DataEngine_v2_0_DataListView
	): CancelablePromise<DataEngine_v2_0_DataListView> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/data-engine/v2.0/data-list-views/{dataListViewId}',
			path: {
				dataListViewId: dataListViewId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dataListViewId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20DeleteDataListView(
		dataListViewId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/data-engine/v2.0/data-list-views/{dataListViewId}',
			path: {
				dataListViewId: dataListViewId,
			},
		});
	}
}
