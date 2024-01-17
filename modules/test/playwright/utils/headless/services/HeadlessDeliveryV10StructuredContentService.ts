/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_PageStructuredContent} from '../models/HeadlessDelivery_v1_0_PageStructuredContent';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {HeadlessDelivery_v1_0_Rating} from '../models/HeadlessDelivery_v1_0_Rating';
import type {HeadlessDelivery_v1_0_StructuredContent} from '../models/HeadlessDelivery_v1_0_StructuredContent';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10StructuredContentService {

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
	public static headlessDeliveryV10PostAssetLibraryStructuredContentsPageExportBatch(
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
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/export-batch',
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
	 * Retrieves the site's structured content. Results can be paginated, filtered, searched, flattened, and sorted.
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
	 * @returns HeadlessDelivery_v1_0_PageStructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentsPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents',
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
	 * Creates a new structured content.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteStructuredContent(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents',
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
	public static headlessDeliveryV10PostSiteStructuredContentsPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/export-batch',
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
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions',
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
	public static headlessDeliveryV10PutSiteStructuredContentPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions',
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
	public static headlessDeliveryV10PostAssetLibraryStructuredContentBatch(
		assetLibraryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-contents/batch',
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
	public static headlessDeliveryV10DeleteStructuredContentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/structured-contents/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a structured content by its UUID.
	 * @param siteId
	 * @param uuid
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentByUuid(
		siteId: string,
		uuid: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-uuid/{uuid}',
			path: {
				siteId: siteId,
				uuid: uuid,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the structured content via its ID.
	 * @param structuredContentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContent(
		structuredContentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
			path: {
				structuredContentId: structuredContentId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the structured content with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param structuredContentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContent(
		structuredContentId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
			path: {
				structuredContentId: structuredContentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the structured content and returns a 204 if the operation succeeds.
	 * @param structuredContentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteStructuredContent(
		structuredContentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
			path: {
				structuredContentId: structuredContentId,
			},
		});
	}

	/**
	 * Updates only the fields received in the request body, leaving any other fields untouched.
	 * @param structuredContentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PatchStructuredContent(
		structuredContentId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}',
			path: {
				structuredContentId: structuredContentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentFolderId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentFolderStructuredContentsPageExportBatch(
		structuredContentFolderId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents/export-batch',
			path: {
				structuredContentFolderId: structuredContentFolderId,
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
	 * @returns HeadlessDelivery_v1_0_PageStructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryStructuredContentsPage(
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents',
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
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryStructuredContent(
		assetLibraryId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site's structured content by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
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
	 * Updates the site's structured content with the given external reference code, or creates it if it not exists.
	 * @param siteId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteStructuredContentByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the site's structured content by external reference code.
	 * @param siteId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteSiteStructuredContentByExternalReferenceCode(
		siteId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
			path: {
				siteId: siteId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryStructuredContentByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
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
	 * Updates the asset library's structured content with the given external reference code, or creates it if it not exists.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutAssetLibraryStructuredContentByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the asset library's structured content by external reference code.
	 * @param assetLibraryId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteAssetLibraryStructuredContentByExternalReferenceCode(
		assetLibraryId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/by-external-reference-code/{externalReferenceCode}',
			path: {
				assetLibraryId: assetLibraryId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param contentStructureId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostContentStructureStructuredContentsPageExportBatch(
		contentStructureId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/content-structures/{contentStructureId}/structured-contents/export-batch',
			path: {
				contentStructureId: contentStructureId,
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
	public static headlessDeliveryV10PostSiteStructuredContentBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/batch',
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
	 * Retrieves the folder's structured content. Results can be paginated, filtered, searched, and sorted.
	 * @param structuredContentFolderId
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
	 * @returns HeadlessDelivery_v1_0_PageStructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentFolderStructuredContentsPage(
		structuredContentFolderId: string,
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
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents',
			path: {
				structuredContentFolderId: structuredContentFolderId,
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
	 * Creates a new structured content in the folder.
	 * @param structuredContentFolderId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentFolderStructuredContent(
		structuredContentFolderId: string,
		requestBody?: HeadlessDelivery_v1_0_StructuredContent
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentFolderId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentFolderStructuredContentBatch(
		structuredContentFolderId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents/batch',
			path: {
				structuredContentFolderId: structuredContentFolderId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentPermissionsPage(
		structuredContentId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions',
			path: {
				structuredContentId: structuredContentId,
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
	 * @param structuredContentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentPermissionsPage(
		structuredContentId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions',
			path: {
				structuredContentId: structuredContentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves a structured content by its key (`articleKey`).
	 * @param siteId
	 * @param key
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_StructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteStructuredContentByKey(
		siteId: string,
		key: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_StructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-key/{key}',
			path: {
				siteId: siteId,
				key: key,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves the structured content's rating.
	 * @param structuredContentId
	 * @param fields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentMyRating(
		structuredContentId: string,
		fields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
			path: {
				structuredContentId: structuredContentId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param structuredContentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentMyRating(
		structuredContentId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
			path: {
				structuredContentId: structuredContentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Create a rating for the structured content.
	 * @param structuredContentId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_Rating default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostStructuredContentMyRating(
		structuredContentId: string,
		requestBody?: HeadlessDelivery_v1_0_Rating
	): CancelablePromise<HeadlessDelivery_v1_0_Rating> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
			path: {
				structuredContentId: structuredContentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the structured content's rating and returns a 204 if the operation succeeds.
	 * @param structuredContentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteStructuredContentMyRating(
		structuredContentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating',
			path: {
				structuredContentId: structuredContentId,
			},
		});
	}

	/**
	 * @param structuredContentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentSubscribe(
		structuredContentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/subscribe',
			path: {
				structuredContentId: structuredContentId,
			},
		});
	}

	/**
	 * Retrieves the structured content's rendered display page
	 * @param structuredContentId
	 * @param displayPageKey
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentRenderedContentByDisplayPageDisplayPageKey(
		structuredContentId: string,
		displayPageKey: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content-by-display-page/{displayPageKey}',
			path: {
				structuredContentId: structuredContentId,
				displayPageKey: displayPageKey,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Retrieves a list of the content structure's structured content. Results can be paginated, filtered, searched, and sorted.
	 * @param contentStructureId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageStructuredContent default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetContentStructureStructuredContentsPage(
		contentStructureId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageStructuredContent> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/content-structures/{contentStructureId}/structured-contents',
			path: {
				contentStructureId: contentStructureId,
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
	 * Retrieves the structured content's rendered template (the result of applying the structure's values to a template).
	 * @param structuredContentId
	 * @param contentTemplateId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetStructuredContentRenderedContentContentTemplate(
		structuredContentId: string,
		contentTemplateId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content/{contentTemplateId}',
			path: {
				structuredContentId: structuredContentId,
				contentTemplateId: contentTemplateId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
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
	public static headlessDeliveryV10GetAssetLibraryStructuredContentPermissionsPage(
		assetLibraryId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions',
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
	public static headlessDeliveryV10PutAssetLibraryStructuredContentPermissionsPage(
		assetLibraryId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions',
			path: {
				assetLibraryId: assetLibraryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param structuredContentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutStructuredContentUnsubscribe(
		structuredContentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/structured-contents/{structuredContentId}/unsubscribe',
			path: {
				structuredContentId: structuredContentId,
			},
		});
	}
}
