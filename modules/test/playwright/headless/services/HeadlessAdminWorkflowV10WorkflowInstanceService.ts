/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_ChangeTransition} from '../models/HeadlessAdminWorkflow_v1_0_ChangeTransition';
import type {HeadlessAdminWorkflow_v1_0_PageWorkflowInstance} from '../models/HeadlessAdminWorkflow_v1_0_PageWorkflowInstance';
import type {HeadlessAdminWorkflow_v1_0_WorkflowInstance} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowInstance';
import type {HeadlessAdminWorkflow_v1_0_WorkflowInstanceSubmit} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowInstanceSubmit';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10WorkflowInstanceService {

	/**
	 * @param assetClassName
	 * @param assetPrimaryKey
	 * @param completed
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowInstancesPageExportBatch(
		assetClassName?: string,
		assetPrimaryKey?: string,
		completed?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-instances/export-batch',
			query: {
				assetClassName: assetClassName,
				assetPrimaryKey: assetPrimaryKey,
				completed: completed,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param workflowInstanceId
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowInstance default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstance(
		workflowInstanceId: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowInstance> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
		});
	}

	/**
	 * @param workflowInstanceId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10DeleteWorkflowInstance(
		workflowInstanceId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
		});
	}

	/**
	 * @param assetClassName
	 * @param assetPrimaryKey
	 * @param completed
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowInstance default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstancesPage(
		assetClassName?: string,
		assetPrimaryKey?: string,
		completed?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowInstance> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances',
			query: {
				assetClassName: assetClassName,
				assetPrimaryKey: assetPrimaryKey,
				completed: completed,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowInstance default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowInstanceSubmit(
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowInstanceSubmit
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowInstance> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-instances/submit',
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
	public static headlessAdminWorkflowV10DeleteWorkflowInstanceBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-workflow/v1.0/workflow-instances/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowInstance default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowInstanceChangeTransition(
		workflowInstanceId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_ChangeTransition
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowInstance> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/change-transition',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
