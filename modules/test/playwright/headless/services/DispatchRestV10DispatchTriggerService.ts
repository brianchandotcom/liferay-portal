/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DispatchRest_v1_0_DispatchTrigger} from '../models/DispatchRest_v1_0_DispatchTrigger';
import type {DispatchRest_v1_0_PageDispatchTrigger} from '../models/DispatchRest_v1_0_PageDispatchTrigger';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DispatchRestV10DispatchTriggerService {

	/**
	 * @returns DispatchRest_v1_0_PageDispatchTrigger default response
	 * @throws ApiError
	 */
	public static dispatchRestV10GetDispatchTriggersPage(): CancelablePromise<DispatchRest_v1_0_PageDispatchTrigger> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/dispatch-rest/v1.0/dispatch-triggers',
		});
	}

	/**
	 * @param requestBody
	 * @returns DispatchRest_v1_0_DispatchTrigger default response
	 * @throws ApiError
	 */
	public static dispatchRestV10PostDispatchTrigger(
		requestBody?: DispatchRest_v1_0_DispatchTrigger
	): CancelablePromise<DispatchRest_v1_0_DispatchTrigger> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/dispatch-rest/v1.0/dispatch-triggers',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dispatchRestV10PostDispatchTriggerBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/dispatch-rest/v1.0/dispatch-triggers/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param dispatchTriggerId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dispatchRestV10PostDispatchTriggerRun(
		dispatchTriggerId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/dispatch-rest/v1.0/dispatch-triggers/{dispatchTriggerId}/run',
			path: {
				dispatchTriggerId: dispatchTriggerId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dispatchRestV10PostDispatchTriggersPageExportBatch(
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/dispatch-rest/v1.0/dispatch-triggers/export-batch',
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
