/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_ChangeTransition} from '../models/HeadlessAdminWorkflow_v1_0_ChangeTransition';
import type {HeadlessAdminWorkflow_v1_0_PageWorkflowTask} from '../models/HeadlessAdminWorkflow_v1_0_PageWorkflowTask';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTask} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTask';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToMe} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToMe';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToRole} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToRole';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToUser} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToUser';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTasksBulkSelection} from '../models/HeadlessAdminWorkflow_v1_0_WorkflowTasksBulkSelection';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminWorkflowV10WorkflowTaskService {

	/**
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTasksAssignedToMyRolesPage(
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles',
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTasksAssignedToRolePage(
		roleId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-role',
			query: {
				roleId: roleId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTasksPage(
		page?: string,
		pageSize?: string,
		sort?: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTasksBulkSelection
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks',
			query: {
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param workflowTaskId
	 * @returns boolean default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTaskHasAssignableUsers(
		workflowTaskId: string
	): CancelablePromise<boolean> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/has-assignable-users',
			path: {
				workflowTaskId: workflowTaskId,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskUpdateDueDate(
		workflowTaskId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToMe
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/update-due-date',
			path: {
				workflowTaskId: workflowTaskId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param completed
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstanceWorkflowTasksAssignedToMePage(
		workflowInstanceId: string,
		completed?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-me',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				completed: completed,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param assigneeId
	 * @param completed
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstanceWorkflowTasksAssignedToUserPage(
		workflowInstanceId: string,
		assigneeId?: string,
		completed?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-user',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				assigneeId: assigneeId,
				completed: completed,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param completed
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowInstanceWorkflowTasksPageExportBatch(
		workflowInstanceId: string,
		completed?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/export-batch',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				completed: completed,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTask(
		workflowTaskId: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}',
			path: {
				workflowTaskId: workflowTaskId,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskAssignToUser(
		workflowTaskId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToUser
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-user',
			path: {
				workflowTaskId: workflowTaskId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param workflowInstanceId
	 * @param completed
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowInstanceWorkflowTasksPage(
		workflowInstanceId: string,
		completed?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks',
			path: {
				workflowInstanceId: workflowInstanceId,
			},
			query: {
				completed: completed,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param creatorId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTasksSubmittingUserPage(
		creatorId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/submitting-user',
			query: {
				creatorId: creatorId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskChangeTransition(
		workflowTaskId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_ChangeTransition
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/change-transition',
			path: {
				workflowTaskId: workflowTaskId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PatchWorkflowTaskUpdateDueDate(
		requestBody?: Array<HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToMe>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/update-due-date',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param assigneeId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTasksAssignedToUserPage(
		assigneeId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user',
			query: {
				assigneeId: assigneeId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param workflowTaskId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskAssignToMe(
		workflowTaskId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToMe
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-me',
			path: {
				workflowTaskId: workflowTaskId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param workflowTaskId
	 * @param requestBody
	 * @returns HeadlessAdminWorkflow_v1_0_WorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PostWorkflowTaskAssignToRole(
		workflowTaskId: string,
		requestBody?: HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToRole
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_WorkflowTask> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-role',
			path: {
				workflowTaskId: workflowTaskId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTasksAssignedToMePage(
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me',
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PatchWorkflowTaskAssignToUser(
		requestBody?: Array<HeadlessAdminWorkflow_v1_0_WorkflowTaskAssignToUser>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assign-to-user',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10PatchWorkflowTaskChangeTransition(
		requestBody?: Array<HeadlessAdminWorkflow_v1_0_ChangeTransition>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/change-transition',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param assigneeId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminWorkflow_v1_0_PageWorkflowTask default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetWorkflowTasksAssignedToUserRolesPage(
		assigneeId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminWorkflow_v1_0_PageWorkflowTask> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user-roles',
			query: {
				assigneeId: assigneeId,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
