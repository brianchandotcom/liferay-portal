/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminAddress_v1_0_PageRegion} from '../models/HeadlessAdminAddress_v1_0_PageRegion';
import type {HeadlessAdminAddress_v1_0_Region} from '../models/HeadlessAdminAddress_v1_0_Region';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminAddressV10RegionService {

	/**
	 * @param countryId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PostCountryRegionBatch(
		countryId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/countries/{countryId}/regions/batch',
			path: {
				countryId: countryId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param countryId
	 * @param active
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PostCountryRegionsPageExportBatch(
		countryId: string,
		active?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/countries/{countryId}/regions/export-batch',
			path: {
				countryId: countryId,
			},
			query: {
				active: active,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param countryId
	 * @param regionCode
	 * @returns HeadlessAdminAddress_v1_0_Region default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountryRegionByRegionCode(
		countryId: string,
		regionCode: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Region> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/{countryId}/regions/by-region-code/{regionCode}',
			path: {
				countryId: countryId,
				regionCode: regionCode,
			},
		});
	}

	/**
	 * @param regionId
	 * @returns HeadlessAdminAddress_v1_0_Region default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetRegion(
		regionId: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Region> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/regions/{regionId}',
			path: {
				regionId: regionId,
			},
		});
	}

	/**
	 * @param regionId
	 * @param requestBody
	 * @returns HeadlessAdminAddress_v1_0_Region default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PutRegion(
		regionId: string,
		requestBody?: HeadlessAdminAddress_v1_0_Region
	): CancelablePromise<HeadlessAdminAddress_v1_0_Region> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-address/v1.0/regions/{regionId}',
			path: {
				regionId: regionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param regionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10DeleteRegion(
		regionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-address/v1.0/regions/{regionId}',
			path: {
				regionId: regionId,
			},
		});
	}

	/**
	 * @param regionId
	 * @param requestBody
	 * @returns HeadlessAdminAddress_v1_0_Region default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PatchRegion(
		regionId: string,
		requestBody?: HeadlessAdminAddress_v1_0_Region
	): CancelablePromise<HeadlessAdminAddress_v1_0_Region> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-address/v1.0/regions/{regionId}',
			path: {
				regionId: regionId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param active
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminAddress_v1_0_PageRegion default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetRegionsPage(
		active?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_PageRegion> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/regions',
			query: {
				active: active,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PutRegionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-address/v1.0/regions/batch',
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
	public static headlessAdminAddressV10DeleteRegionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-address/v1.0/regions/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param active
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PostRegionsPageExportBatch(
		active?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/regions/export-batch',
			query: {
				active: active,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param countryId
	 * @param active
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminAddress_v1_0_PageRegion default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountryRegionsPage(
		countryId: string,
		active?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_PageRegion> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/{countryId}/regions',
			path: {
				countryId: countryId,
			},
			query: {
				active: active,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param countryId
	 * @param requestBody
	 * @returns HeadlessAdminAddress_v1_0_Region default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PostCountryRegion(
		countryId: string,
		requestBody?: HeadlessAdminAddress_v1_0_Region
	): CancelablePromise<HeadlessAdminAddress_v1_0_Region> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/countries/{countryId}/regions',
			path: {
				countryId: countryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
