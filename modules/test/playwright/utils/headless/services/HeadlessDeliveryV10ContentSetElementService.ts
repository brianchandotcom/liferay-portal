/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_PageContentSetElement} from '../models/HeadlessDelivery_v1_0_PageContentSetElement';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10ContentSetElementService {

	/**
	 * Retrieves the content set elements by UUID. Results can be paginated.
	 * @param siteId
	 * @param uuid
	 * @param fields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageContentSetElement default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteContentSetByUuidContentSetElementsPage(
		siteId: string,
		uuid: string,
		fields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentSetElement> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/content-sets/by-uuid/{uuid}/content-set-elements',
			path: {
				siteId: siteId,
				uuid: uuid,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param uuid
	 * @param fields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageContentSetElement default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryContentSetByUuidContentSetElementsPage(
		assetLibraryId: string,
		uuid: string,
		fields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentSetElement> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-sets/by-uuid/{uuid}/content-set-elements',
			path: {
				assetLibraryId: assetLibraryId,
				uuid: uuid,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Retrieves the content set's elements (e.g., structured content, blogs, etc.). Results can be paginated. The set of available headers are: Accept-Language (string), Host (string), User-Agent (string), X-Browser (string), X-Cookies (collection string), X-Device-Brand (string), X-Device-Model (string), X-Device-Screen-Resolution-Height (double), X-Device-Screen-Resolution-Width (double), X-Last-Sign-In-Date-Time (date time) and X-Signed-In (boolean). Local date will be always present in the request.
	 * @param contentSetId
	 * @param fields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageContentSetElement default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetContentSetContentSetElementsPage(
		contentSetId: string,
		fields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentSetElement> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/content-sets/{contentSetId}/content-set-elements',
			path: {
				contentSetId: contentSetId,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Retrieves the content set elements by key. Results can be paginated.
	 * @param siteId
	 * @param key
	 * @param fields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageContentSetElement default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteContentSetByKeyContentSetElementsPage(
		siteId: string,
		key: string,
		fields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentSetElement> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/content-sets/by-key/{key}/content-set-elements',
			path: {
				siteId: siteId,
				key: key,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param assetLibraryId
	 * @param key
	 * @param fields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageContentSetElement default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryContentSetByKeyContentSetElementsPage(
		assetLibraryId: string,
		key: string,
		fields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentSetElement> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-sets/by-key/{key}/content-set-elements',
			path: {
				assetLibraryId: assetLibraryId,
				key: key,
			},
			query: {
				fields: fields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
