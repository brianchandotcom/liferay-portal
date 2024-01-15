/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class FrontendViewStateV10ActiveViewService {

	/**
	 * @param activeViewId
	 * @param pageLayoutId
	 * @param portletId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static frontendViewStateV10GetActiveViewPageLayoutPortlet(
		activeViewId: string,
		pageLayoutId: string,
		portletId: string
	): CancelablePromise<Record<string, any>> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/frontend-view-state/v1.0/active-view/{activeViewId}/page-layout/{pageLayoutId}/portlet/{portletId}',
			path: {
				activeViewId: activeViewId,
				pageLayoutId: pageLayoutId,
				portletId: portletId,
			},
		});
	}

	/**
	 * @param activeViewId
	 * @param pageLayoutId
	 * @param portletId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static frontendViewStateV10PutActiveViewPageLayoutPortlet(
		activeViewId: string,
		pageLayoutId: string,
		portletId: string,
		requestBody?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/frontend-view-state/v1.0/active-view/{activeViewId}/page-layout/{pageLayoutId}/portlet/{portletId}',
			path: {
				activeViewId: activeViewId,
				pageLayoutId: pageLayoutId,
				portletId: portletId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
