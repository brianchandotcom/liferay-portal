/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BatchPlanner_v1_0_PagePlan} from '../models/BatchPlanner_v1_0_PagePlan';
import type {BatchPlanner_v1_0_Plan} from '../models/BatchPlanner_v1_0_Plan';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BatchPlannerV10PlanService {

	/**
	 * @param planId
	 * @returns BatchPlanner_v1_0_Plan default response
	 * @throws ApiError
	 */
	public static batchPlannerV10GetPlan(
		planId: string
	): CancelablePromise<BatchPlanner_v1_0_Plan> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/batch-planner/v1.0/plans/{planId}',
			path: {
				planId: planId,
			},
		});
	}

	/**
	 * @param planId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static batchPlannerV10DeletePlan(
		planId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/batch-planner/v1.0/plans/{planId}',
			path: {
				planId: planId,
			},
		});
	}

	/**
	 * @param planId
	 * @param requestBody
	 * @returns BatchPlanner_v1_0_Plan default response
	 * @throws ApiError
	 */
	public static batchPlannerV10PatchPlan(
		planId: string,
		requestBody?: BatchPlanner_v1_0_Plan
	): CancelablePromise<BatchPlanner_v1_0_Plan> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/batch-planner/v1.0/plans/{planId}',
			path: {
				planId: planId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param internalClassNameKey
	 * @returns any default response
	 * @throws ApiError
	 */
	public static batchPlannerV10GetPlanTemplate(
		internalClassNameKey: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/batch-planner/v1.0/plans/{internalClassNameKey}/template',
			path: {
				internalClassNameKey: internalClassNameKey,
			},
		});
	}

	/**
	 * @param page
	 * @param pageSize
	 * @returns BatchPlanner_v1_0_PagePlan default response
	 * @throws ApiError
	 */
	public static batchPlannerV10GetPlansPage(
		page?: string,
		pageSize?: string
	): CancelablePromise<BatchPlanner_v1_0_PagePlan> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/batch-planner/v1.0/plans',
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns BatchPlanner_v1_0_Plan default response
	 * @throws ApiError
	 */
	public static batchPlannerV10PostPlan(
		requestBody?: BatchPlanner_v1_0_Plan
	): CancelablePromise<BatchPlanner_v1_0_Plan> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/batch-planner/v1.0/plans',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
