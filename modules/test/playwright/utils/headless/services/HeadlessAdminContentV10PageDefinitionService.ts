/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_PageDefinition} from '../models/HeadlessAdminContent_v1_0_PageDefinition';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminContentV10PageDefinitionService {

	/**
	 * Renders and retrieves HTML for the page definition using the theme of specified site.
	 * @param siteId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10PostSitePageDefinitionPreview(
		siteId: string,
		requestBody?: HeadlessAdminContent_v1_0_PageDefinition
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-content/v1.0/sites/{siteId}/page-definitions/preview',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
