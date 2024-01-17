/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Document} from '../models/HeadlessDelivery_v1_0_Document';
import type {HeadlessDelivery_v1_0_MultipartBody} from '../models/HeadlessDelivery_v1_0_MultipartBody';
import type {HeadlessDelivery_v1_0_PageDocument} from '../models/HeadlessDelivery_v1_0_PageDocument';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_PatchDocumentRequestBody} from '../models/HeadlessDelivery_v1_0_PatchDocumentRequestBody';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_PostAssetLibraryDocumentRequestBody} from '../models/HeadlessDelivery_v1_0_PostAssetLibraryDocumentRequestBody';
import type {HeadlessDelivery_v1_0_PostDocumentFolderDocumentRequestBody} from '../models/HeadlessDelivery_v1_0_PostDocumentFolderDocumentRequestBody';
import type {HeadlessDelivery_v1_0_PostSiteDocumentRequestBody} from '../models/HeadlessDelivery_v1_0_PostSiteDocumentRequestBody';
import type {HeadlessDelivery_v1_0_PutAssetLibraryDocumentByExternalReferenceCodeRequestBody} from '../models/HeadlessDelivery_v1_0_PutAssetLibraryDocumentByExternalReferenceCodeRequestBody';
import type {HeadlessDelivery_v1_0_PutDocumentRequestBody} from '../models/HeadlessDelivery_v1_0_PutDocumentRequestBody';
import type {HeadlessDelivery_v1_0_PutSiteDocumentByExternalReferenceCodeRequestBody} from '../models/HeadlessDelivery_v1_0_PutSiteDocumentByExternalReferenceCodeRequestBody';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10DocumentService {

	/**
	 * Retrieves the asset library's document by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryDocumentByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
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
	 * Replaces the document by external reference code with the information sent in the request body, or replaces it if it not exists. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file'sbytes (`file`), and an optional JSON string (`document`) with the metadata.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutAssetLibraryDocumentByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		formData?: HeadlessDelivery_v1_0_PutAssetLibraryDocumentByExternalReferenceCodeRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Deletes the asset library's document by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteAssetLibraryDocumentByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param documentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentPermissionsPage(
		documentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/documents/{documentId}/permissions',
			path: {
				documentId: documentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param documentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentPermissionsPage(
		documentId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/documents/{documentId}/permissions',
			path: {
				documentId: documentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the document.
	 * @param documentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocument(
		documentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/documents/{documentId}',
			path: {
				documentId: documentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the document with the information sent in the request body. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
	 * @param documentId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocument(
		documentId: string,
		formData?: HeadlessDelivery_v1_0_PutDocumentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/documents/{documentId}',
			path: {
				documentId: documentId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Deletes the document and returns a 204 if the operation succeeds.
	 * @param documentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteDocument(
		documentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/documents/{documentId}',
			path: {
				documentId: documentId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
	 * @param documentId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchDocument(
		documentId: string,
		formData?: HeadlessDelivery_v1_0_PatchDocumentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/documents/{documentId}',
			path: {
				documentId: documentId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param siteId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteDocumentsPageExportBatch(
		siteId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/export-batch',
			path: {
				siteId: siteId,
			},
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteDocumentBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/batch',
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
	 * Retrieves the documents rated by the user.
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageDocument default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentsRatedByMePage(
		siteId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocument> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/rated-by-me',
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
	 * @param assetLibraryId
	 * @param aggregationTerms
	 * @param fields
	 * @param flatten
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageDocument default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryDocumentsPage(
		assetLibraryId: string,
		aggregationTerms?: string,
		fields?: string,
		flatten?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocument> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				flatten: flatten,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryDocument(
		assetLibraryId: string,
		formData?: HeadlessDelivery_v1_0_PostAssetLibraryDocumentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents',
			path: {
				assetLibraryId: assetLibraryId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Retrieves the folder's documents. Results can be paginated, filtered, searched, and sorted.
	 * @param documentFolderId
	 * @param aggregationTerms
	 * @param fields
	 * @param flatten
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageDocument default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentFolderDocumentsPage(
		documentFolderId: string,
		aggregationTerms?: string,
		fields?: string,
		flatten?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocument> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/documents',
			path: {
				documentFolderId: documentFolderId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				flatten: flatten,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new document inside the folder identified by `documentFolderId`. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
	 * @param documentFolderId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentFolderDocument(
		documentFolderId: string,
		formData?: HeadlessDelivery_v1_0_PostDocumentFolderDocumentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/documents',
			path: {
				documentFolderId: documentFolderId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param assetLibraryId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryDocumentPermissionsPage(
		assetLibraryId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutAssetLibraryDocumentPermissionsPage(
		assetLibraryId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/permissions',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteDocumentPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentBatch(
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/documents/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteDocumentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/documents/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the documents in the site's root folder. Results can be paginated, filtered, searched, flattened, and sorted.
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param flatten
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageDocument default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentsPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		flatten?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocument> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents',
			path: {
				siteId: siteId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
				flatten: flatten,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new document. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`document`) with the metadata.
	 * @param siteId
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteDocument(
		siteId: string,
		formData?: HeadlessDelivery_v1_0_PostSiteDocumentRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents',
			path: {
				siteId: siteId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Retrieves the documents rated by the user.
	 * @param assetLibraryId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageDocument default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryDocumentsRatedByMePage(
		assetLibraryId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocument> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/rated-by-me',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryDocumentBatch(
		assetLibraryId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/batch',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param documentFolderId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentFolderDocumentBatch(
		documentFolderId: string,
		callbackUrl?: string,
		requestBody?: HeadlessDelivery_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/documents/batch',
			path: {
				documentFolderId: documentFolderId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the document's rating.
	 * @param documentId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentMyRating(
		documentId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
			path: {
				documentId: documentId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param documentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentMyRating(
		documentId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
			path: {
				documentId: documentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Creates a new rating for the document, by the user who authenticated the request.
	 * @param documentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentMyRating(
		documentId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
			path: {
				documentId: documentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the document's rating and returns a 204 if the operation succeeded.
	 * @param documentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteDocumentMyRating(
		documentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/documents/{documentId}/my-rating',
			path: {
				documentId: documentId,
			},
		});
	}

	/**
	 * Retrieves the site's document by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
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
	 * Replaces the document by external reference code with the information sent in the request body, or replaces it if it not exists. Any missing fields are deleted, unless they are required. The request body must be `multipart/form-data` with two parts, the file'sbytes (`file`), and an optional JSON string (`document`) with the metadata.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param formData
	 * @returns HeadlessDelivery_v1_0_Document default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteDocumentByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		formData?: HeadlessDelivery_v1_0_PutSiteDocumentByExternalReferenceCodeRequestBody
	): CancelablePromise<HeadlessDelivery_v1_0_Document> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Deletes the site's document by external reference code returns a 204 if the operation succeeds.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteDocumentByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param documentFolderId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentFolderDocumentsPageExportBatch(
		documentFolderId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/documents/export-batch',
			path: {
				documentFolderId: documentFolderId,
			},
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryDocumentsPageExportBatch(
		assetLibraryId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/documents/export-batch',
			path: {
				assetLibraryId: assetLibraryId,
			},
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieves the document's rendered display page
	 * @param documentId
	 * @param displayPageKey
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentRenderedContentByDisplayPageDisplayPageKey(
		documentId: string,
		displayPageKey: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/documents/{documentId}/rendered-content-by-display-page/{displayPageKey}',
			path: {
				documentId: documentId,
				displayPageKey: displayPageKey,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}
}
