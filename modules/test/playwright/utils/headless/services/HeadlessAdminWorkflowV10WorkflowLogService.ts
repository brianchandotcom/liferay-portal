/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_PageWorkflowLog} from '../models/HeadlessAdminWorkflow_v1_0_PageWorkflowLog';
import type {HeadlessAdminWorkflow_v1_0_WorkflowLog} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowLog';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10WorkflowLogService {

	/**
	 * @param workflowInstanceId
	 * @param types
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowInstanceWorkflowLogsPageExportBatch(
		workflowInstanceId: string,
		types?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-logs/export-batch',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				types: types,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param types
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowLog default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstanceWorkflowLogsPage(
		workflowInstanceId: string,
		types?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowLog> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-logs',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				types: types,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @param types
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskWorkflowLogsPageExportBatch(
		workflowTaskId: string,
		types?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/workflow-logs/export-batch',
			path: {
				workflowTaskId: workflowTaskId,
			},
			query: {
				types: types,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @param types
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowLog default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTaskWorkflowLogsPage(
		workflowTaskId: string,
		types?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowLog> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/workflow-logs',
			path: {
				workflowTaskId: workflowTaskId,
			},
			query: {
				types: types,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowLogId
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowLog default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowLog(
		workflowLogId: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowLog> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-logs/{workflowLogId}',
			path: {
				workflowLogId: workflowLogId,
			},
		});
	}
}
