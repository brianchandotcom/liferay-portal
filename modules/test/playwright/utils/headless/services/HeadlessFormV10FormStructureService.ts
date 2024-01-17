/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormStructure} from '../models/HeadlessForm_v1_0_FormStructure';
import type {HeadlessForm_v1_0_PageFormStructure} from '../models/HeadlessForm_v1_0_PageFormStructure';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessFormV10FormStructureService {

	/**
	 * @deprecated
	 * @param formStructureId
	 * @returns HeadlessForm_v1_0_FormStructure default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetFormStructure(
		formStructureId: string
	): CancelablePromise<HeadlessForm_v1_0_FormStructure> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/form-structures/{formStructureId}',
			path: {
				formStructureId: formStructureId,
			},
		});
	}

	/**
	 * @deprecated
	 * @param siteId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10PostSiteFormStructuresPageExportBatch(
		siteId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/sites/{siteId}/form-structures/export-batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @deprecated
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessForm_v1_0_PageFormStructure default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetSiteFormStructuresPage(
		siteId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessForm_v1_0_PageFormStructure> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/sites/{siteId}/form-structures',
			path: {
				siteId: siteId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
