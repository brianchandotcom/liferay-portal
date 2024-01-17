/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_PageTransition} from '../models/HeadlessAdminWorkflow_v1_0_PageTransition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10TransitionService {

	/**
	 * @param workflowTaskId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageTransition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTaskNextTransitionsPage(
		workflowTaskId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageTransition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/next-transitions',
			path: {
				workflowTaskId: workflowTaskId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageTransition default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstanceNextTransitionsPage(
		workflowInstanceId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageTransition> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/next-transitions',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
