/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_PageWorkflowDefinition} from '../models/HeadlessAdminWorkflow_v1_0_PageWorkflowDefinition';
import type {HeadlessAdminWorkflow_v1_0_WorkflowDefinition} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowDefinition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10WorkflowDefinitionService {

	/**
	 * @param name
	 * @param contentFormat
	 * @param version
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowDefinitionByName(
		name: string,
		contentFormat?: string,
		version?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/by-name/{name}',
			path: {
				name: name,
			},
			query: {
				contentFormat: contentFormat,
				version: version,
			},
		});
	}

	/**
	 * @param name
	 * @param version
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10DeleteWorkflowDefinitionUndeploy(
		name?: string,
		version?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/undeploy',
			query: {
				name: name,
				version: version,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PutWorkflowDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/batch',
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
	public static headlessAdminWorkflowV10PostWorkflowDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/batch',
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
	public static headlessAdminWorkflowV10DeleteWorkflowDefinitionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param active
	 * @param name
	 * @param version
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowDefinitionUpdateActive(
		active?: string,
		name?: string,
		version?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/update-active',
			query: {
				active: active,
				name: name,
				version: version,
			},
		});
	}

	/**
	 * @param workflowDefinitionId
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowDefinition(
		workflowDefinitionId: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/{workflowDefinitionId}',
			path: {
				workflowDefinitionId: workflowDefinitionId,
			},
		});
	}

	/**
	 * @param workflowDefinitionId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PutWorkflowDefinition(
		workflowDefinitionId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowDefinition
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/{workflowDefinitionId}',
			path: {
				workflowDefinitionId: workflowDefinitionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param workflowDefinitionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10DeleteWorkflowDefinition(
		workflowDefinitionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/{workflowDefinitionId}',
			path: {
				workflowDefinitionId: workflowDefinitionId,
			},
		});
	}

	/**
	 * @param active
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowDefinitionsPage(
		active?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-definitions',
			query: {
				active: active,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowDefinition(
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowDefinition
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-definitions',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowDefinitionDeploy(
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowDefinition
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/deploy',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowDefinition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowDefinitionSave(
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowDefinition
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowDefinition> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/save',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param active
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowDefinitionsPageExportBatch(
		active?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-definitions/export-batch',
			query: {
				active: active,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
