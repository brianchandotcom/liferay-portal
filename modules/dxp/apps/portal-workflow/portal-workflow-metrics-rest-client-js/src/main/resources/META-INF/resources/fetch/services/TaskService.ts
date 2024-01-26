/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Task } from '../models/Task';
import type { TaskBulkSelection } from '../models/TaskBulkSelection';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TaskService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Task
     * @throws ApiError
     */
    public getProcessTasksPage({
        processId,
    }: {
        processId: number,
    }): CancelablePromise<Array<Task>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/tasks',
            path: {
                'processId': processId,
            },
        });
    }
    /**
     * @returns Task
     * @throws ApiError
     */
    public postProcessTask({
        processId,
        requestBody,
    }: {
        processId: number,
        requestBody?: Task,
    }): CancelablePromise<Task> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/tasks',
            path: {
                'processId': processId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteProcessTask({
        processId,
        taskId,
    }: {
        processId: number,
        taskId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/tasks/{taskId}',
            path: {
                'processId': processId,
                'taskId': taskId,
            },
        });
    }
    /**
     * @returns Task
     * @throws ApiError
     */
    public getProcessTask({
        processId,
        taskId,
    }: {
        processId: number,
        taskId: number,
    }): CancelablePromise<Task> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/tasks/{taskId}',
            path: {
                'processId': processId,
                'taskId': taskId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchProcessTask({
        processId,
        taskId,
        requestBody,
    }: {
        processId: number,
        taskId: number,
        requestBody?: Task,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/tasks/{taskId}',
            path: {
                'processId': processId,
                'taskId': taskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchProcessTaskComplete({
        processId,
        taskId,
        requestBody,
    }: {
        processId: number,
        taskId: number,
        requestBody?: Task,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/portal-workflow-metrics/v1.0/processes/{processId}/tasks/{taskId}/complete',
            path: {
                'processId': processId,
                'taskId': taskId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Task
     * @throws ApiError
     */
    public postTasksPage({
        page,
        pageSize,
        requestBody,
    }: {
        page?: number,
        pageSize?: number,
        requestBody?: TaskBulkSelection,
    }): CancelablePromise<Array<Task>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/portal-workflow-metrics/v1.0/tasks',
            query: {
                'page': page,
                'pageSize': pageSize,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
