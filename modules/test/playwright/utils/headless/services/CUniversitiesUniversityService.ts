/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CUniversities_PagePermission} from '../models/CUniversities_PagePermission';
import type {CUniversities_PageUniversity} from '../models/CUniversities_PageUniversity';
import type {CUniversities_Permission} from '../models/CUniversities_Permission';
import type {CUniversities_University} from '../models/CUniversities_University';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class CUniversitiesUniversityService {

	/**
	 * @param universityId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesGetUniversity(
		universityId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/c/universities/{universityId}',
			path: {
				universityId: universityId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * @param universityId
	 * @param requestBody
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesPutUniversity(
		universityId: string,
		requestBody?: CUniversities_University
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/c/universities/{universityId}',
			path: {
				universityId: universityId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param universityId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static cUniversitiesDeleteUniversity(
		universityId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/c/universities/{universityId}',
			path: {
				universityId: universityId,
			},
		});
	}

	/**
	 * @param universityId
	 * @param requestBody
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesPatchUniversity(
		universityId: string,
		requestBody?: CUniversities_University
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/c/universities/{universityId}',
			path: {
				universityId: universityId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesGetByExternalReferenceCode(
		externalReferenceCode: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/c/universities/by-external-reference-code/{externalReferenceCode}',
			path: {
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
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesPutByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: CUniversities_University
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/c/universities/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static cUniversitiesDeleteByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/c/universities/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesPatchByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: CUniversities_University
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/c/universities/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static cUniversitiesPostUniversitiesPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/c/universities/export-batch',
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
	 * @returns CUniversities_PageUniversity default response
	 * @throws ApiError
	 */
	public static cUniversitiesGetUniversitiesPage(
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
	): CancelablePromise<CUniversities_PageUniversity> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/c/universities',
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
	 * @param requestBody
	 * @returns CUniversities_University default response
	 * @throws ApiError
	 */
	public static cUniversitiesPostUniversity(
		requestBody?: CUniversities_University
	): CancelablePromise<CUniversities_University> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/c/universities',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param universityId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns CUniversities_PagePermission default response
	 * @throws ApiError
	 */
	public static cUniversitiesGetUniversityPermissionsPage(
		universityId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<CUniversities_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/c/universities/{universityId}/permissions',
			path: {
				universityId: universityId,
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
	 * @param universityId
	 * @param requestBody
	 * @returns CUniversities_PagePermission default response
	 * @throws ApiError
	 */
	public static cUniversitiesPutUniversityPermissionsPage(
		universityId: string,
		requestBody?: Array<CUniversities_Permission>
	): CancelablePromise<CUniversities_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/c/universities/{universityId}/permissions',
			path: {
				universityId: universityId,
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
	public static cUniversitiesPutUniversityBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/c/universities/batch',
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
	public static cUniversitiesPostUniversityBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/c/universities/batch',
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
	public static cUniversitiesDeleteUniversityBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/c/universities/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
