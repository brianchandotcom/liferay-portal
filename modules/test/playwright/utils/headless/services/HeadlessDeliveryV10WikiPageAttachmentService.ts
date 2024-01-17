/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_MultipartBody} from '../models/HeadlessDelivery_v1_0_MultipartBody';
import type {HeadlessDelivery_v1_0_PageWikiPageAttachment} from '../models/HeadlessDelivery_v1_0_PageWikiPageAttachment';
import type {HeadlessDelivery_v1_0_PostWikiPageWikiPageAttachmentRequestBody} from '../models/HeadlessDelivery_v1_0_PostWikiPageWikiPageAttachmentRequestBody';
import type {HeadlessDelivery_v1_0_WikiPageAttachment} from '../models/HeadlessDelivery_v1_0_WikiPageAttachment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10WikiPageAttachmentService {

	/**
	 * Retrieves the wiki page attachment by wiki page's and wiki page attachment's external reference codes.
	 * @param siteId
	 * @param wikiPageExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_WikiPageAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode(
		siteId: string,
		wikiPageExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPageAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				wikiPageExternalReferenceCode: wikiPageExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Delete the wiki page attachment by wiki page's and wiki page attachment's external reference codes.
	 * @param siteId
	 * @param wikiPageExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode(
		siteId: string,
		wikiPageExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/wiki-pages/by-external-reference-code/{wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				wikiPageExternalReferenceCode: wikiPageExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the wiki page's attachments.
	 * @param wikiPageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_PageWikiPageAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetWikiPageWikiPageAttachmentsPage(
		wikiPageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageWikiPageAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments',
			path: {
				wikiPageId: wikiPageId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Creates an attachment for the wiki page. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`WikiPageAttachment`) with the metadata.
	 * @param wikiPageId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_WikiPageAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiPageWikiPageAttachment(
		wikiPageId: string,
		formData?: HeadlessDelivery_v1_0_PostWikiPageWikiPageAttachmentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPageAttachment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments',
			path: {
				wikiPageId: wikiPageId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Retrieves the wiki page attachment.
	 * @param wikiPageAttachmentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_WikiPageAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetWikiPageAttachment(
		wikiPageAttachmentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_WikiPageAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}',
			path: {
				wikiPageAttachmentId: wikiPageAttachmentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Deletes the wiki page attachment and returns a 204 if the operation succeeds.
	 * @param wikiPageAttachmentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteWikiPageAttachment(
		wikiPageAttachmentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/wiki-page-attachments/{wikiPageAttachmentId}',
			path: {
				wikiPageAttachmentId: wikiPageAttachmentId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteWikiPageAttachmentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/wiki-page-attachments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param wikiPageId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiPageWikiPageAttachmentsPageExportBatch(
		wikiPageId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments/export-batch',
			path: {
				wikiPageId: wikiPageId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param wikiPageId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostWikiPageWikiPageAttachmentBatch(
		wikiPageId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/wiki-pages/{wikiPageId}/wiki-page-attachments/batch',
			path: {
				wikiPageId: wikiPageId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
