/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_PageAssignee} from '../models/HeadlessAdminWorkflow_v1_0_PageAssignee';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10AssigneeService {

	/**
	 * @param workflowTaskId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageAssignee default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTaskAssignableUsersPage(
		workflowTaskId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageAssignee> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assignable-users',
			path: {
				workflowTaskId: workflowTaskId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
