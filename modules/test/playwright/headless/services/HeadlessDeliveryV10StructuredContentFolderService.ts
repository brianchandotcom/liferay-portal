/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_PageStructuredContentFolder} from '../models/HeadlessDelivery_v1_0_PageStructuredContentFolder';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_StructuredContentFolder} from '../models/HeadlessDelivery_v1_0_StructuredContentFolder';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10StructuredContentFolderService {

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
	public static headlessDeliveryV10PostAssetLibraryStructuredContentFoldersPageExportBatch(
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
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/export-batch',
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
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
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
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteStructuredContentFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteStructuredContentFolderByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the site's structured content folders. Results can be paginated, filtered, searched, flattened, and sorted.
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
	 * @returns HeadlessDelivery_v1_0_PageStructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentFoldersPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders',
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
	 * Creates a new structured content folder.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteStructuredContentFolder(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders',
			path: {
				siteId: siteId,
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
	public static headlessDeliveryV10GetSiteStructuredContentFolderPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/permissions',
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
	public static headlessDeliveryV10PutSiteStructuredContentFolderPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
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
	public static headlessDeliveryV10PostSiteStructuredContentFoldersPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/export-batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-content-folders/batch',
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
	public static headlessDeliveryV10DeleteStructuredContentFolderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/structured-content-folders/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the structured content folder.
	 * @param structuredContentFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentFolder(
		structuredContentFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the structured content folder with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param structuredContentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentFolder(
		structuredContentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the structured content folder and returns a 204 if the operation succeeds.
	 * @param structuredContentFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteStructuredContentFolder(
		structuredContentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param structuredContentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchStructuredContentFolder(
		structuredContentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentFolderId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentFolderPermissionsPage(
		structuredContentFolderId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-content-folder/{structuredContentFolderId}/permissions',
			path: {
				structuredContentFolderId: structuredContentFolderId,
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
	 * @param structuredContentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentFolderPermissionsPage(
		structuredContentFolderId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-content-folder/{structuredContentFolderId}/permissions',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentFolderUnsubscribe(
		structuredContentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/unsubscribe',
			path: {
				structuredContentFolderId: structuredContentFolderId,
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
	public static headlessDeliveryV10PostAssetLibraryStructuredContentFolderBatch(
		assetLibraryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/batch',
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
	 * @returns HeadlessDelivery_v1_0_PageStructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryStructuredContentFoldersPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders',
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
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryStructuredContentFolder(
		assetLibraryId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
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
	public static headlessDeliveryV10GetAssetLibraryStructuredContentFolderPermissionsPage(
		assetLibraryId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/permissions',
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
	public static headlessDeliveryV10PutAssetLibraryStructuredContentFolderPermissionsPage(
		assetLibraryId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the asset library's structured content folder by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryStructuredContentFolderByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
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
	 * Updates the asset library's structured content folder with the given external reference code, or creates it if it not exists.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutAssetLibraryStructuredContentFolderByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the asset library's structured content folder by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteAssetLibraryStructuredContentFolderByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-content-folders/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
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
	public static headlessDeliveryV10PostSiteStructuredContentFolderBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-content-folders/batch',
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
	 * @param structuredContentFolderId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentFolderSubscribe(
		structuredContentFolderId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/subscribe',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
		});
	}

	/**
	 * Retrieves the parent structured content folder's subfolders. Results can be paginated, filtered, searched, and sorted.
	 * @param parentStructuredContentFolderId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageStructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentFolderStructuredContentFoldersPage(
		parentStructuredContentFolderId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders',
			path: {
				parentStructuredContentFolderId:
					parentStructuredContentFolderId,
			},
			query: {
				aggregationTerms: aggregationTerms,
				fields: fields,
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
	 * Creates a new structured content folder in an existing folder.
	 * @param parentStructuredContentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContentFolder default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentFolderStructuredContentFolder(
		parentStructuredContentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContentFolder
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContentFolder> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders',
			path: {
				parentStructuredContentFolderId:
					parentStructuredContentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
