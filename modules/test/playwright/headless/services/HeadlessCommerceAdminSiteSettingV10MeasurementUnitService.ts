/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit';
import type {HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit} from '../models/HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminSiteSettingV10MeasurementUnitService {

	/**
	 * @param measurementUnitType
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetMeasurementUnitsByType(
		measurementUnitType: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-type/{measurementUnitType}',
			path: {
				measurementUnitType: measurementUnitType,
			},
			query: {
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param key
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetMeasurementUnitByKey(
		key: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-key/{key}',
			path: {
				key: key,
			},
		});
	}

	/**
	 * @param key
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10DeleteMeasurementUnitByKey(
		key: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-key/{key}',
			path: {
				key: key,
			},
		});
	}

	/**
	 * @param key
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PatchMeasurementUnitByKey(
		key: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-key/{key}',
			path: {
				key: key,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetMeasurementUnitsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units',
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PostMeasurementUnit(
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetMeasurementUnit(
		id: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10DeleteMeasurementUnit(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PatchMeasurementUnit(
		id: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetMeasurementUnitByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10DeleteMeasurementUnitByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PatchMeasurementUnitByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10PostMeasurementUnitsPageExportBatch(
		filter?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/export-batch',
			query: {
				filter: filter,
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
	public static headlessCommerceAdminSiteSettingV10PostMeasurementUnitBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/batch',
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
	public static headlessCommerceAdminSiteSettingV10DeleteMeasurementUnitBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-site-setting/v1.0/measurement-units/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
