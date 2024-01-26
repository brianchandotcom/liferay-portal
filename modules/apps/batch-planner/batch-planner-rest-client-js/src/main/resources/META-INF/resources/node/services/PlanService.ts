/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Plan } from '../models/Plan';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PlanService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Plan
     * @throws ApiError
     */
    public getPlansPage({
        page,
        pageSize,
    }: {
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Plan>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/batch-planner/v1.0/plans',
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns Plan
     * @throws ApiError
     */
    public postPlan({
        requestBody,
    }: {
        requestBody?: Plan,
    }): CancelablePromise<Plan> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/batch-planner/v1.0/plans',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns binary
     * @throws ApiError
     */
    public getPlanTemplate({
        internalClassNameKey,
    }: {
        internalClassNameKey: string,
    }): CancelablePromise<Blob> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/batch-planner/v1.0/plans/{internalClassNameKey}/template',
            path: {
                'internalClassNameKey': internalClassNameKey,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deletePlan({
        planId,
    }: {
        planId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/batch-planner/v1.0/plans/{planId}',
            path: {
                'planId': planId,
            },
        });
    }
    /**
     * @returns Plan
     * @throws ApiError
     */
    public getPlan({
        planId,
    }: {
        planId: number,
    }): CancelablePromise<Plan> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/batch-planner/v1.0/plans/{planId}',
            path: {
                'planId': planId,
            },
        });
    }
    /**
     * @returns Plan
     * @throws ApiError
     */
    public patchPlan({
        planId,
        requestBody,
    }: {
        planId: number,
        requestBody?: Plan,
    }): CancelablePromise<Plan> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/batch-planner/v1.0/plans/{planId}',
            path: {
                'planId': planId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
