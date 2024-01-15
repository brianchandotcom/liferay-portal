/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_Form} from '../models/HeadlessForm_v1_0_Form';
import type {HeadlessForm_v1_0_FormContext} from '../models/HeadlessForm_v1_0_FormContext';
import type {HeadlessForm_v1_0_FormDocument} from '../models/HeadlessForm_v1_0_FormDocument';
import type {HeadlessForm_v1_0_MultipartBody} from '../models/HeadlessForm_v1_0_MultipartBody';
import type {HeadlessForm_v1_0_PageForm} from '../models/HeadlessForm_v1_0_PageForm';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessFormV10FormService {

	/**
	 * @deprecated
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessForm_v1_0_PageForm default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetSiteFormsPage(
		siteId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessForm_v1_0_PageForm> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/sites/{siteId}/forms',
			path: {
				siteId: siteId,
			},
			query: {
				page: page,
				pageSize: pageSize,
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
	public static headlessFormV10PostSiteFormsPageExportBatch(
		siteId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/sites/{siteId}/forms/export-batch',
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
	 * @param formId
	 * @param requestBody
	 * @returns HeadlessForm_v1_0_FormContext default response
	 * @throws ApiError
	 */
	public static headlessFormV10PostFormEvaluateContext(
		formId: string,
		requestBody?: HeadlessForm_v1_0_FormContext
	): CancelablePromise<HeadlessForm_v1_0_FormContext> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/forms/{formId}/evaluate-context',
			path: {
				formId: formId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @deprecated
	 * @param formId
	 * @param formData
	 * @returns HeadlessForm_v1_0_FormDocument default response
	 * @throws ApiError
	 */
	public static headlessFormV10PostFormFormDocument(
		formId: string,
		formData?: HeadlessForm_v1_0_MultipartBody
	): CancelablePromise<HeadlessForm_v1_0_FormDocument> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/forms/{formId}/form-document',
			path: {
				formId: formId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @deprecated
	 * @param formId
	 * @returns HeadlessForm_v1_0_Form default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetForm(
		formId: string
	): CancelablePromise<HeadlessForm_v1_0_Form> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/forms/{formId}',
			path: {
				formId: formId,
			},
		});
	}
}
