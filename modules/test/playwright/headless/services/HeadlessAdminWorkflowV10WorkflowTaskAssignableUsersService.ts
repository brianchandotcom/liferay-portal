/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignableUsers} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignableUsers';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskIds} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskIds';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10WorkflowTaskAssignableUsersService {

	/**
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignableUsers default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskAssignableUser(
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTaskIds
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignableUsers> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assignable-users',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
