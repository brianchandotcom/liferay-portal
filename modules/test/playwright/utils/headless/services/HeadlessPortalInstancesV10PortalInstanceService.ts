/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessPortalInstances_v1_0_PagePortalInstance} from '../models/HeadlessPortalInstances_v1_0_PagePortalInstance';
import type {HeadlessPortalInstances_v1_0_PortalInstance} from '../models/HeadlessPortalInstances_v1_0_PortalInstance';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessPortalInstancesV10PortalInstanceService {

	/**
	 * Deactivates the portal instance. When a portal instance is deactivated, its virtual host will not longer respond requests.
	 * @param portalInstanceId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10PutPortalInstanceDeactivate(
		portalInstanceId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}/deactivate',
			path: {
				portalInstanceId: portalInstanceId,
			},
		});
	}

	/**
	 * Retrieves the portal instance
	 * @param portalInstanceId
	 * @returns HeadlessPortalInstances_v1_0_PortalInstance default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10GetPortalInstance(
		portalInstanceId: string
	): CancelablePromise<HeadlessPortalInstances_v1_0_PortalInstance> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}',
			path: {
				portalInstanceId: portalInstanceId,
			},
		});
	}

	/**
	 * Removes the portal instance
	 * @param portalInstanceId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10DeletePortalInstance(
		portalInstanceId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}',
			path: {
				portalInstanceId: portalInstanceId,
			},
		});
	}

	/**
	 * Updates the portal instance with information sent in the request body. Only the provided fields are updated.
	 * @param portalInstanceId
	 * @param requestBody
	 * @returns HeadlessPortalInstances_v1_0_PortalInstance default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10PatchPortalInstance(
		portalInstanceId: string,
		requestBody?: HeadlessPortalInstances_v1_0_PortalInstance
	): CancelablePromise<HeadlessPortalInstances_v1_0_PortalInstance> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}',
			path: {
				portalInstanceId: portalInstanceId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the portal instances
	 * @param skipDefault
	 * @returns HeadlessPortalInstances_v1_0_PagePortalInstance default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10GetPortalInstancesPage(
		skipDefault?: string
	): CancelablePromise<HeadlessPortalInstances_v1_0_PagePortalInstance> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-portal-instances/v1.0/portal-instances',
			query: {
				skipDefault: skipDefault,
			},
		});
	}

	/**
	 * Adds a new portal instance
	 * @param requestBody
	 * @returns HeadlessPortalInstances_v1_0_PortalInstance default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10PostPortalInstance(
		requestBody?: HeadlessPortalInstances_v1_0_PortalInstance
	): CancelablePromise<HeadlessPortalInstances_v1_0_PortalInstance> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-portal-instances/v1.0/portal-instances',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Activates the portal instance
	 * @param portalInstanceId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10PutPortalInstanceActivate(
		portalInstanceId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-portal-instances/v1.0/portal-instances/{portalInstanceId}/activate',
			path: {
				portalInstanceId: portalInstanceId,
			},
		});
	}
}
