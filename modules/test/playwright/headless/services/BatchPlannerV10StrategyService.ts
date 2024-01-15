/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BatchPlanner_v1_0_PageStrategy} from '../models/BatchPlanner_v1_0_PageStrategy';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BatchPlannerV10StrategyService {

	/**
	 * @param internalClassNameKey
	 * @returns BatchPlanner_v1_0_PageStrategy default response
	 * @throws ApiError
	 */
	public static batchPlannerV10GetPlanInternalClassNameKeyStrategiesPage(
		internalClassNameKey: string
	): CancelablePromise<BatchPlanner_v1_0_PageStrategy> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/batch-planner/v1.0/plans/{internalClassNameKey}/strategies',
			path: {
				internalClassNameKey: internalClassNameKey,
			},
		});
	}
}
