/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_FormRecord} from '../models/HeadlessForm_v1_0_FormRecord';
import type {HeadlessForm_v1_0_PageFormRecord} from '../models/HeadlessForm_v1_0_PageFormRecord';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessFormV10FormRecordService {

	/**
	 * @deprecated
	 * @param formId
	 * @returns HeadlessForm_v1_0_FormRecord default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetFormFormRecordByLatestDraft(
		formId: string
	): CancelablePromise<HeadlessForm_v1_0_FormRecord> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/forms/{formId}/form-records/by-latest-draft',
			path: {
				formId: formId,
			},
		});
	}

	/**
	 * @deprecated
	 * @param formRecordId
	 * @returns HeadlessForm_v1_0_FormRecord default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetFormRecord(
		formRecordId: string
	): CancelablePromise<HeadlessForm_v1_0_FormRecord> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/form-records/{formRecordId}',
			path: {
				formRecordId: formRecordId,
			},
		});
	}

	/**
	 * @deprecated
	 * @param formRecordId
	 * @param requestBody
	 * @returns HeadlessForm_v1_0_FormRecord default response
	 * @throws ApiError
	 */
	public static headlessFormV10PutFormRecord(
		formRecordId: string,
		requestBody?: HeadlessForm_v1_0_FormRecord
	): CancelablePromise<HeadlessForm_v1_0_FormRecord> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-form/v1.0/form-records/{formRecordId}',
			path: {
				formRecordId: formRecordId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @deprecated
	 * @param formId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10PostFormFormRecordsPageExportBatch(
		formId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/forms/{formId}/form-records/export-batch',
			path: {
				formId: formId,
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10PostFormFormRecordBatch(
		formId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/forms/{formId}/form-records/batch',
			path: {
				formId: formId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @deprecated
	 * @param formId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessForm_v1_0_PageFormRecord default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetFormFormRecordsPage(
		formId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessForm_v1_0_PageFormRecord> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/forms/{formId}/form-records',
			path: {
				formId: formId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @deprecated
	 * @param formId
	 * @param requestBody
	 * @returns HeadlessForm_v1_0_FormRecord default response
	 * @throws ApiError
	 */
	public static headlessFormV10PostFormFormRecord(
		formId: string,
		requestBody?: HeadlessForm_v1_0_FormRecord
	): CancelablePromise<HeadlessForm_v1_0_FormRecord> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-form/v1.0/forms/{formId}/form-records',
			path: {
				formId: formId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @deprecated
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10PutFormRecordBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-form/v1.0/form-records/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
