/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminAddress_v1_0_Country} from '../models/HeadlessAdminAddress_v1_0_Country';
import type {HeadlessAdminAddress_v1_0_PageCountry} from '../models/HeadlessAdminAddress_v1_0_PageCountry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminAddressV10CountryService {

	/**
	 * @param countryId
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountry(
		countryId: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/{countryId}',
			path: {
				countryId: countryId,
			},
		});
	}

	/**
	 * @param countryId
	 * @param requestBody
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PutCountry(
		countryId: string,
		requestBody?: HeadlessAdminAddress_v1_0_Country
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-address/v1.0/countries/{countryId}',
			path: {
				countryId: countryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param countryId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10DeleteCountry(
		countryId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-address/v1.0/countries/{countryId}',
			path: {
				countryId: countryId,
			},
		});
	}

	/**
	 * @param countryId
	 * @param requestBody
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PatchCountry(
		countryId: string,
		requestBody?: HeadlessAdminAddress_v1_0_Country
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-address/v1.0/countries/{countryId}',
			path: {
				countryId: countryId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param a2
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountryByA2(
		a2: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/by-a2/{a2}',
			path: {
				a2: a2,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PutCountryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-address/v1.0/countries/batch',
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
	public static headlessAdminAddressV10PostCountryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/countries/batch',
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
	public static headlessAdminAddressV10DeleteCountryBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-address/v1.0/countries/batch',
			query: {
				callbackURL: callbackUrl,
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
	 * @returns HeadlessAdminAddress_v1_0_PageCountry default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountriesPage(
		active?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_PageCountry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries',
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
	 * @param requestBody
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10PostCountry(
		requestBody?: HeadlessAdminAddress_v1_0_Country
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/countries',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param name
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountryByName(
		name: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/by-name/{name}',
			path: {
				name: name,
			},
		});
	}

	/**
	 * @param a3
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountryByA3(
		a3: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/by-a3/{a3}',
			path: {
				a3: a3,
			},
		});
	}

	/**
	 * @param number
	 * @returns HeadlessAdminAddress_v1_0_Country default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetCountryByNumber(
		number: string
	): CancelablePromise<HeadlessAdminAddress_v1_0_Country> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/countries/by-number/{number}',
			path: {
				number: number,
			},
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
	public static headlessAdminAddressV10PostCountriesPageExportBatch(
		active?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-address/v1.0/countries/export-batch',
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
}
