/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessBatchEngine_v1_0_ExportTask} from '../models/HeadlessBatchEngine_v1_0_ExportTask';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessBatchEngineV10ExportTaskService {

	/**
	 * Retrieves the export task by external reference code.
	 * @param externalReferenceCode
	 * @returns HeadlessBatchEngine_v1_0_ExportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetExportTaskByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessBatchEngine_v1_0_ExportTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/export-task/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the exported content.
	 * @param exportTaskId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetExportTaskContent(
		exportTaskId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/export-task/{exportTaskId}/content',
			path: {
				exportTaskId: exportTaskId,
			},
		});
	}

	/**
	 * Submits a request for exporting items to a file.
	 * @param className
	 * @param contentType
	 * @param callbackUrl
	 * @param externalReferenceCode
	 * @param fieldNames
	 * @param taskItemDelegateName
	 * @returns HeadlessBatchEngine_v1_0_ExportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10PostExportTask(
		className: string,
		contentType: string,
		callbackUrl?: string,
		externalReferenceCode?: string,
		fieldNames?: string,
		taskItemDelegateName?: string
	): CancelablePromise<HeadlessBatchEngine_v1_0_ExportTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-batch-engine/v1.0/export-task/{className}/{contentType}',
			path: {
				className: className,
				contentType: contentType,
			},
			query: {
				callbackURL: callbackUrl,
				externalReferenceCode: externalReferenceCode,
				fieldNames: fieldNames,
				taskItemDelegateName: taskItemDelegateName,
			},
		});
	}

	/**
	 * Retrieves the exported content by external reference code.
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetExportTaskByExternalReferenceCodeContent(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/export-task/by-external-reference-code/{externalReferenceCode}/content',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the export task.
	 * @param exportTaskId
	 * @returns HeadlessBatchEngine_v1_0_ExportTask default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetExportTask(
		exportTaskId: string
	): CancelablePromise<HeadlessBatchEngine_v1_0_ExportTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/export-task/{exportTaskId}',
			path: {
				exportTaskId: exportTaskId,
			},
		});
	}
}
