/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DigitalSignatureRest_v1_0_DSEnvelope} from '../models/DigitalSignatureRest_v1_0_DSEnvelope';
import type {DigitalSignatureRest_v1_0_PageDSEnvelope} from '../models/DigitalSignatureRest_v1_0_PageDSEnvelope';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DigitalSignatureRestV10DsEnvelopeService {

	/**
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @returns DigitalSignatureRest_v1_0_PageDSEnvelope default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10GetSiteDsEnvelopesPage(
		siteId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<DigitalSignatureRest_v1_0_PageDSEnvelope> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-envelopes',
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
	 * @param siteId
	 * @param requestBody
	 * @returns DigitalSignatureRest_v1_0_DSEnvelope default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10PostSiteDsEnvelope(
		siteId: string,
		requestBody?: DigitalSignatureRest_v1_0_DSEnvelope
	): CancelablePromise<DigitalSignatureRest_v1_0_DSEnvelope> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-envelopes',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10PostSiteDsEnvelopeBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: DigitalSignatureRest_v1_0_DSEnvelope
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-envelopes/batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10PostSiteDsEnvelopesPageExportBatch(
		siteId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-envelopes/export-batch',
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
	 * @param siteId
	 * @param dsEnvelopeId
	 * @returns DigitalSignatureRest_v1_0_DSEnvelope default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10GetSiteDsEnvelope(
		siteId: string,
		dsEnvelopeId: string
	): CancelablePromise<DigitalSignatureRest_v1_0_DSEnvelope> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/digital-signature-rest/v1.0/sites/{siteId}/ds-envelopes/{dsEnvelopeId}',
			path: {
				siteId: siteId,
				dsEnvelopeId: dsEnvelopeId,
			},
		});
	}
}
