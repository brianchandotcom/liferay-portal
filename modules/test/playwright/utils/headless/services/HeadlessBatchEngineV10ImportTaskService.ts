/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessBatchEngine_v1_0_ImportTask} from '../models/HeadlessBatchEngine_v1_0_ImportTask';
import type {HeadlessBatchEngine_v1_0_MultipartBody} from '../models/HeadlessBatchEngine_v1_0_MultipartBody';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessBatchEngineV10ImportTaskService {

	/**
	 * Retrieves the import task.
	 * @param importTaskId
	 * @returns HeadlessBatchEngine_v1_0_ImportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetImportTask(
		importTaskId: string
	): CancelablePromise<HeadlessBatchEngine_v1_0_ImportTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/import-task/{importTaskId}',
			path: {
				importTaskId: importTaskId,
			},
		});
	}

	/**
	 * Retrieves the exported content.
	 * @param importTaskId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetImportTaskContent(
		importTaskId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/import-task/{importTaskId}/content',
			path: {
				importTaskId: importTaskId,
			},
		});
	}

	/**
	 * @param importTaskId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetImportTaskFailedItemReport(
		importTaskId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/import-task/{importTaskId}/failed-items/report',
			path: {
				importTaskId: importTaskId,
			},
		});
	}

	/**
	 * Uploads a new file for updating items in batch.
	 * @param className
	 * @param callbackUrl
	 * @param externalReferenceCode
	 * @param importStrategy
	 * @param taskItemDelegateName
	 * @param updateStrategy
	 * @param requestBody
	 * @returns HeadlessBatchEngine_v1_0_ImportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10PutImportTask1(
		className: string,
		callbackUrl?: string,
		externalReferenceCode?: string,
		importStrategy?: string,
		taskItemDelegateName?: string,
		updateStrategy?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<HeadlessBatchEngine_v1_0_ImportTask> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-batch-engine/v1.0/import-task/{className}',
			path: {
				className: className,
			},
			query: {
				callbackURL: callbackUrl,
				externalReferenceCode: externalReferenceCode,
				importStrategy: importStrategy,
				taskItemDelegateName: taskItemDelegateName,
				updateStrategy: updateStrategy,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Uploads a new file for creating new items in batch.
	 * @param className
	 * @param callbackUrl
	 * @param createStrategy
	 * @param externalReferenceCode
	 * @param fieldNameMapping
	 * @param importStrategy
	 * @param taskItemDelegateName
	 * @param requestBody
	 * @returns HeadlessBatchEngine_v1_0_ImportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10PostImportTask1(
		className: string,
		callbackUrl?: string,
		createStrategy?: string,
		externalReferenceCode?: string,
		fieldNameMapping?: string,
		importStrategy?: string,
		taskItemDelegateName?: string,
		requestBody?: HeadlessBatchEngine_v1_0_MultipartBody
	): CancelablePromise<HeadlessBatchEngine_v1_0_ImportTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-batch-engine/v1.0/import-task/{className}',
			path: {
				className: className,
			},
			query: {
				callbackURL: callbackUrl,
				createStrategy: createStrategy,
				externalReferenceCode: externalReferenceCode,
				fieldNameMapping: fieldNameMapping,
				importStrategy: importStrategy,
				taskItemDelegateName: taskItemDelegateName,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Uploads a new file for deleting items in batch.
	 * @param className
	 * @param callbackUrl
	 * @param externalReferenceCode
	 * @param importStrategy
	 * @param taskItemDelegateName
	 * @param requestBody
	 * @returns HeadlessBatchEngine_v1_0_ImportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10DeleteImportTask1(
		className: string,
		callbackUrl?: string,
		externalReferenceCode?: string,
		importStrategy?: string,
		taskItemDelegateName?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<HeadlessBatchEngine_v1_0_ImportTask> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-batch-engine/v1.0/import-task/{className}',
			path: {
				className: className,
			},
			query: {
				callbackURL: callbackUrl,
				externalReferenceCode: externalReferenceCode,
				importStrategy: importStrategy,
				taskItemDelegateName: taskItemDelegateName,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetImportTaskByExternalReferenceCodeFailedItemReport(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/import-task/by-external-reference-code/{externalReferenceCode}/failed-items/report',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the import task by external reference code.
	 * @param externalReferenceCode
	 * @returns HeadlessBatchEngine_v1_0_ImportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetImportTaskByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessBatchEngine_v1_0_ImportTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/import-task/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the exported content by external reference code.
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetImportTaskByExternalReferenceCodeContent(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/import-task/by-external-reference-code/{externalReferenceCode}/content',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}
}
