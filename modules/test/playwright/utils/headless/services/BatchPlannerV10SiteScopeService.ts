/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BatchPlanner_v1_0_PageSiteScope} from '../models/BatchPlanner_v1_0_PageSiteScope';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class BatchPlannerV10SiteScopeService {

	/**
	 * @param internalClassNameKey
	 * @param _export
	 * @returns BatchPlanner_v1_0_PageSiteScope default response
	 * @throws ApiError
	 */
	public static batchPlannerV10GetPlanInternalClassNameKeySiteScopesPage(
		internalClassNameKey: string,
		_export?: string
	): CancelablePromise<BatchPlanner_v1_0_PageSiteScope> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/batch-planner/v1.0/plans/{internalClassNameKey}/site-scopes',
			path: {
				internalClassNameKey: internalClassNameKey,
			},
			query: {
				export: _export,
			},
		});
	}
}
