/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskIds} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskIds';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskTransitions} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskTransitions';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10WorkflowTaskTransitionsService {

	/**
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTaskTransitions default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskTransition(
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTaskIds
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTaskTransitions> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/next-transitions',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
