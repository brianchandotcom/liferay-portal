/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ContentTemplate} from '../models/HeadlessDelivery_v1_0_ContentTemplate';
import type {HeadlessDelivery_v1_0_PageContentTemplate} from '../models/HeadlessDelivery_v1_0_PageContentTemplate';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10ContentTemplateService {

	/**
	 * @param siteId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageContentTemplate default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteContentTemplatesPage(
		siteId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/content-templates',
			path: {
				siteId: siteId,
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
	 * @param siteId
	 * @param contentTemplateId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_ContentTemplate default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteContentTemplate(
		siteId: string,
		contentTemplateId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_ContentTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/content-templates/{contentTemplateId}',
			path: {
				siteId: siteId,
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
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostAssetLibraryContentTemplatesPageExportBatch(
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
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-templates/export-batch',
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
	 * @param assetLibraryId
	 * @param aggregationTerms
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessDelivery_v1_0_PageContentTemplate default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetAssetLibraryContentTemplatesPage(
		assetLibraryId: string,
		aggregationTerms?: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageContentTemplate> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/content-templates',
			path: {
				assetLibraryId: assetLibraryId,
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
	public static headlessDeliveryV10PostSiteContentTemplatesPageExportBatch(
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
			url: '/headless-delivery/v1.0/sites/{siteId}/content-templates/export-batch',
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
}
