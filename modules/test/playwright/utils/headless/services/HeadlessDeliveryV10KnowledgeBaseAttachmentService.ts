/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_KnowledgeBaseAttachment} from '../models/HeadlessDelivery_v1_0_KnowledgeBaseAttachment';
import type {HeadlessDelivery_v1_0_MultipartBody} from '../models/HeadlessDelivery_v1_0_MultipartBody';
import type {HeadlessDelivery_v1_0_PageKnowledgeBaseAttachment} from '../models/HeadlessDelivery_v1_0_PageKnowledgeBaseAttachment';
import type {HeadlessDelivery_v1_0_PostKnowledgeBaseArticleKnowledgeBaseAttachmentRequestBody} from '../models/HeadlessDelivery_v1_0_PostKnowledgeBaseArticleKnowledgeBaseAttachmentRequestBody';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10KnowledgeBaseAttachmentService {

	/**
	 * Retrieves the knowledge base attachment.
	 * @param knowledgeBaseAttachmentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseAttachment(
		knowledgeBaseAttachmentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}',
			path: {
				knowledgeBaseAttachmentId: knowledgeBaseAttachmentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Deletes the knowledge base file attachment and returns a 204 if the operation succeeds.
	 * @param knowledgeBaseAttachmentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteKnowledgeBaseAttachment(
		knowledgeBaseAttachmentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}',
			path: {
				knowledgeBaseAttachmentId: knowledgeBaseAttachmentId,
			},
		});
	}

	/**
	 * Retrieves the knowledge base article attachment by external reference code.
	 * @param siteId
	 * @param knowledgeBaseArticleExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(
		siteId: string,
		knowledgeBaseArticleExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				knowledgeBaseArticleExternalReferenceCode:
					knowledgeBaseArticleExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Delete the knowledge base attachment by knowledge base article's and knowledge base attachment's external reference codes.
	 * @param siteId
	 * @param knowledgeBaseArticleExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(
		siteId: string,
		knowledgeBaseArticleExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				knowledgeBaseArticleExternalReferenceCode:
					knowledgeBaseArticleExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteKnowledgeBaseAttachmentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/knowledge-base-attachments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param knowledgeBaseArticleId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseArticleKnowledgeBaseAttachmentBatch(
		knowledgeBaseArticleId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments/batch',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the knowledge base article's attachments.
	 * @param knowledgeBaseArticleId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_PageKnowledgeBaseAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
		knowledgeBaseArticleId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageKnowledgeBaseAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Creates a new attachment for an existing knowledge base article. The request body must be `multipart/form-data` with two parts, a `file` part with the file's bytes, and an optional JSON string (`knowledgeBaseAttachment`) with the metadata.
	 * @param knowledgeBaseArticleId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_KnowledgeBaseAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseArticleKnowledgeBaseAttachment(
		knowledgeBaseArticleId: string,
		formData?: HeadlessDelivery_v1_0_PostKnowledgeBaseArticleKnowledgeBaseAttachmentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_KnowledgeBaseAttachment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param knowledgeBaseArticleId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostKnowledgeBaseArticleKnowledgeBaseAttachmentsPageExportBatch(
		knowledgeBaseArticleId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments/export-batch',
			path: {
				knowledgeBaseArticleId: knowledgeBaseArticleId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
