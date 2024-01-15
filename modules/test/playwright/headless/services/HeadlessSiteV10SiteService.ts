/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessSite_v1_0_PutSiteByExternalReferenceCodeRequestBody} from '../models/HeadlessSite_v1_0_PutSiteByExternalReferenceCodeRequestBody';
import type {HeadlessSite_v1_0_Site} from '../models/HeadlessSite_v1_0_Site';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessSiteV10SiteService {

	/**
	 * Adds or update a new site
	 * @param externalReferenceCode
	 * @param formData
	 * @returns HeadlessSite_v1_0_Site default response
	 * @throws ApiError
	 */
	public static headlessSiteV10PutSiteByExternalReferenceCode(
		externalReferenceCode: string,
		formData?: HeadlessSite_v1_0_PutSiteByExternalReferenceCodeRequestBody
	): CancelablePromise<HeadlessSite_v1_0_Site> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-site/v1.0/sites/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Adds a new site
	 * @param requestBody
	 * @returns HeadlessSite_v1_0_Site default response
	 * @throws ApiError
	 */
	public static headlessSiteV10PostSite(
		requestBody?: HeadlessSite_v1_0_Site
	): CancelablePromise<HeadlessSite_v1_0_Site> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-site/v1.0/sites',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
