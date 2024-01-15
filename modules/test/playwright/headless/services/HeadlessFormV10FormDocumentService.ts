/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormDocument} from '../models/HeadlessForm_v1_0_FormDocument';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessFormV10FormDocumentService {

	/**
	 * @deprecated
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10DeleteFormDocumentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-form/v1.0/form-documents/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @deprecated
	 * @param formDocumentId
	 * @returns HeadlessForm_v1_0_FormDocument default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetFormDocument(
		formDocumentId: string
	): CancelablePromise<HeadlessForm_v1_0_FormDocument> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/form-documents/{formDocumentId}',
			path: {
				formDocumentId: formDocumentId,
			},
		});
	}

	/**
	 * @deprecated
	 * @param formDocumentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10DeleteFormDocument(
		formDocumentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-form/v1.0/form-documents/{formDocumentId}',
			path: {
				formDocumentId: formDocumentId,
			},
		});
	}
}
