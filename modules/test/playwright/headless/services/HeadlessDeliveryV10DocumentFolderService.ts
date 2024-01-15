/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_DocumentFolder} from '../models/HeadlessDelivery_v1_0_DocumentFolder';
import type {HeadlessDelivery_v1_0_PageDocumentFolder} from '../models/HeadlessDelivery_v1_0_PageDocumentFolder';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10DocumentFolderService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/document-folders/batch',
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
	public static headlessDeliveryV10DeleteDocumentFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/document-folders/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
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
	public static headlessDeliveryV10PostAssetLibraryDocumentFoldersPageExportBatch(
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
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/export-batch',
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
	 * @param documentFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentFolderPermissionsPage(
		documentFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/permissions',
			path: {
				documentFolderId: documentFolderId,
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
	 * @param documentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentFolderPermissionsPage(
		documentFolderId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/permissions',
			path: {
				documentFolderId: documentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the document folder.
	 * @param documentFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentFolder(
		documentFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
			path: {
				documentFolderId: documentFolderId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the document folder with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param documentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentFolder(
		documentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_DocumentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
			path: {
				documentFolderId: documentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the document folder and returns a 204 if the operation succeeds.
	 * @param documentFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteDocumentFolder(
		documentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
			path: {
				documentFolderId: documentFolderId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body. Any other fields are left untouched.
	 * @param documentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchDocumentFolder(
		documentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_DocumentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}',
			path: {
				documentFolderId: documentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param documentFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentFolderSubscribe(
		documentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/subscribe',
			path: {
				documentFolderId: documentFolderId,
			},
		});
	}

	/**
	 * Retrieves the site's document folder by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentsFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents-folder/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Replaces the document folder by external reference code with the information sent in the request body, or replaces it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteDocumentsFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_DocumentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents-folder/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the site's document folder by external reference code returns a 204 if the operation succeeds.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteDocumentsFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/documents-folder/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
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
	public static headlessDeliveryV10GetAssetLibraryDocumentFolderPermissionsPage(
		assetLibraryId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/permissions',
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
	public static headlessDeliveryV10PutAssetLibraryDocumentFolderPermissionsPage(
		assetLibraryId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the document folders rated by the user.
	 * @param siteId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageDocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentFoldersRatedByMePage(
		siteId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/rated-by-me',
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
	 * Retrieves the folder's subfolders. Results can be paginated, filtered, searched, and sorted.
	 * @param parentDocumentFolderId
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
	 * @returns HeadlessDelivery_v1_0_PageDocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentFolderDocumentFoldersPage(
		parentDocumentFolderId: string,
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/document-folders/{parentDocumentFolderId}/document-folders',
			path: {
				parentDocumentFolderId: parentDocumentFolderId,
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
	 * Creates a new folder in a folder identified by `parentDocumentFolderId`.
	 * @param parentDocumentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentFolderDocumentFolder(
		parentDocumentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_DocumentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/document-folders/{parentDocumentFolderId}/document-folders',
			path: {
				parentDocumentFolderId: parentDocumentFolderId,
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
	public static headlessDeliveryV10PostSiteDocumentFolderBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/batch',
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
	 * Retrieves the document folders rated by the user.
	 * @param assetLibraryId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageDocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryDocumentFoldersRatedByMePage(
		assetLibraryId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/rated-by-me',
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
	public static headlessDeliveryV10PostSiteDocumentFoldersPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/export-batch',
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
	 * Retrieves the document folder's rating.
	 * @param documentFolderId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetDocumentFolderMyRating(
		documentFolderId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
			path: {
				documentFolderId: documentFolderId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param documentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentFolderMyRating(
		documentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
			path: {
				documentFolderId: documentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Creates a new rating for the document folder, by the user who authenticated the request.
	 * @param documentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostDocumentFolderMyRating(
		documentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
			path: {
				documentFolderId: documentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the document folder's rating and returns a 204 if the operation succeeded.
	 * @param documentFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteDocumentFolderMyRating(
		documentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/my-rating',
			path: {
				documentFolderId: documentFolderId,
			},
		});
	}

	/**
	 * Retrieves the site's document folders. Results can be paginated, filtered, searched, flattened, and sorted.
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
	 * @returns HeadlessDelivery_v1_0_PageDocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteDocumentFoldersPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders',
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
	 * Creates a new document folder.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteDocumentFolder(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_DocumentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
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
	 * @returns HeadlessDelivery_v1_0_PageDocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryDocumentFoldersPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageDocumentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders',
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
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_DocumentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryDocumentFolder(
		assetLibraryId: string,
		requestBody?: HeadlessDelivery_v1_0_DocumentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_DocumentFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders',
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
	public static headlessDeliveryV10GetSiteDocumentFolderPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/permissions',
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
	public static headlessDeliveryV10PutSiteDocumentFolderPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/document-folders/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param assetLibraryId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryDocumentFolderBatch(
		assetLibraryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/document-folders/batch',
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
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutDocumentFolderUnsubscribe(
		documentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/document-folders/{documentFolderId}/unsubscribe',
			path: {
				documentFolderId: documentFolderId,
			},
		});
	}
}
