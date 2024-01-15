/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_MessageBoardAttachment} from '../models/HeadlessDelivery_v1_0_MessageBoardAttachment';
import type {HeadlessDelivery_v1_0_MultipartBody} from '../models/HeadlessDelivery_v1_0_MultipartBody';
import type {HeadlessDelivery_v1_0_PageMessageBoardAttachment} from '../models/HeadlessDelivery_v1_0_PageMessageBoardAttachment';
import type {HeadlessDelivery_v1_0_PostMessageBoardMessageMessageBoardAttachmentRequestBody} from '../models/HeadlessDelivery_v1_0_PostMessageBoardMessageMessageBoardAttachmentRequestBody';
import type {HeadlessDelivery_v1_0_PostMessageBoardThreadMessageBoardAttachmentRequestBody} from '../models/HeadlessDelivery_v1_0_PostMessageBoardThreadMessageBoardAttachmentRequestBody';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10MessageBoardAttachmentService {

	/**
	 * @param messageBoardMessageId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardMessageMessageBoardAttachmentBatch(
		messageBoardMessageId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments/batch',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the message board attachment by message board message's and message board attachment's external reference codes.
	 * @param siteId
	 * @param messageBoardMessageExternalReferenceCode
	 * @param externalReferenceCode
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(
		siteId: string,
		messageBoardMessageExternalReferenceCode: string,
		externalReferenceCode: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				messageBoardMessageExternalReferenceCode:
					messageBoardMessageExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Delete the message board attachment by message board message's and message board attachment's external reference codes.
	 * @param siteId
	 * @param messageBoardMessageExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(
		siteId: string,
		messageBoardMessageExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				messageBoardMessageExternalReferenceCode:
					messageBoardMessageExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the message board message's attachments.
	 * @param messageBoardMessageId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardMessageMessageBoardAttachmentsPage(
		messageBoardMessageId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Creates an attachment for the message board message. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`MessageBoardAttachment`) with the metadata.
	 * @param messageBoardMessageId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_MessageBoardAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardMessageMessageBoardAttachment(
		messageBoardMessageId: string,
		formData?: HeadlessDelivery_v1_0_PostMessageBoardMessageMessageBoardAttachmentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardAttachment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Retrieves the message board attachment.
	 * @param messageBoardAttachmentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_MessageBoardAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardAttachment(
		messageBoardAttachmentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}',
			path: {
				messageBoardAttachmentId: messageBoardAttachmentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Deletes the message board attachment and returns a 204 if the operation succeeds.
	 * @param messageBoardAttachmentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardAttachment(
		messageBoardAttachmentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}',
			path: {
				messageBoardAttachmentId: messageBoardAttachmentId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteMessageBoardAttachmentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/message-board-attachments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMessageBoardAttachmentsPageExportBatch(
		messageBoardThreadId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments/export-batch',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param messageBoardThreadId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMessageBoardAttachmentBatch(
		messageBoardThreadId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments/batch',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the message board thread's attachments.
	 * @param messageBoardThreadId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_PageMessageBoardAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetMessageBoardThreadMessageBoardAttachmentsPage(
		messageBoardThreadId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageMessageBoardAttachment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Creates a new attachment for the message board thread. The request body should be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`knowledgeBaseAttachment`) with the metadata.
	 * @param messageBoardThreadId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_MessageBoardAttachment default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardThreadMessageBoardAttachment(
		messageBoardThreadId: string,
		formData?: HeadlessDelivery_v1_0_PostMessageBoardThreadMessageBoardAttachmentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_MessageBoardAttachment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments',
			path: {
				messageBoardThreadId: messageBoardThreadId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param messageBoardMessageId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostMessageBoardMessageMessageBoardAttachmentsPageExportBatch(
		messageBoardMessageId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments/export-batch',
			path: {
				messageBoardMessageId: messageBoardMessageId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
